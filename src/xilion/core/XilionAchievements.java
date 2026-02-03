package xilion.core;

import arc.Events;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.serialization.Jval;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Icon;
import mindustry.world.blocks.defense.turrets.Turret;
import xilion.XilionJavaMod;
import xilion.content.XPlanets;
import xilion.ui.AchievementDialog;

public class XilionAchievements {
    private static final Seq<Achievement> achievements = new Seq<>();
    private static final AchievementDialog achievementDialog = new AchievementDialog(achievements);

    public static void init() {
        achievements.addAll(
                new Achievement("blocks-placed", Seq.with(5_000, 10_000, 25_000, 50_000, 100_000),
                (a) ->
                    Events.on(EventType.BlockBuildEndEvent.class, event -> {
                        if (isNotXilion() || !event.unit.getPlayer().equals(Vars.player)) return;
                        a.progress(Mathf.pow(event.tile.build.block.size, 2));
                    })
                ),
                new Achievement("units-made", Seq.with(100, 200, 500, 1_000, 2_000),
                        (a) ->
                                Events.on(EventType.UnitCreateEvent.class, event -> {
                                    if (isNotXilion() || !event.spawner.team.equals(Vars.player.team())) return;
                                    a.progress(1);
                                })
                ),
                new Achievement("turrets-placed", Seq.with(50, 100, 250, 500, 1000),
                        (a) ->
                                Events.on(EventType.BlockBuildEndEvent.class, event -> {
                                    if (isNotXilion() || !event.unit.getPlayer().equals(Vars.player)) return;
                                    if(!(event.tile.build.block instanceof Turret)) return;
                                    a.progress(1);
                                })
                ),
                new Achievement("sector-completed", Seq.with(3, 5, 10, 15, 20),
                        (a) ->
                                Events.on(EventType.SectorCaptureEvent.class, event -> {
                                    if(!event.sector.planet.equals(XPlanets.xilion)) return;
                                    a.progress(1);
                                })
                )
        );

        achievements.forEach(Achievement::init);

        try {
            Vars.ui.menufrag.addButton("Achievements", Icon.star, achievementDialog::show);
        } catch (Exception e) {
            Log.err(e);
        }
        if(XilionJavaMod.achievementsFile.exists()){
            Jval json = Jval.read(XilionJavaMod.achievementsFile.readString());
            achievements.forEach(achievement -> {
                int progress = 0;
                try{
                    progress = Integer.parseInt(json.getString(achievement.getName()));
                }catch (Exception ignored){}
                achievement.setProgress(progress);
            });
        }

        Events.on(EventType.SaveWriteEvent.class, event -> {
            Jval json = Jval.newObject();
            achievements.forEach(achievement -> json.add(achievement.getName(), achievement.getProgress()+""));
            XilionJavaMod.achievementsFile.writeString(json.toString(), false);
        });
    }

    private static boolean isNotXilion() {
        return !Vars.state.getPlanet().equals(XPlanets.xilion);
    }
}
