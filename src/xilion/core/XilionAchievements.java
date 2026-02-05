package xilion.core;

import arc.Events;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Log;
import arc.util.serialization.Jval;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.game.Gamemode;
import mindustry.gen.Icon;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.blocks.power.PowerBlock;
import mindustry.world.blocks.power.PowerGenerator;
import mindustry.world.blocks.production.BeamDrill;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.production.GenericCrafter;
import xilion.XilionJavaMod;
import xilion.blockTypes.XWallCrafter;
import xilion.content.XBlocks;
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
                        if(Vars.state.rules.mode().equals(Gamemode.sandbox)) return;
                        if(Vars.state.rules.mode().equals(Gamemode.editor)) return;
                        if (isNotXilion() || !event.unit.getPlayer().equals(Vars.player)) return;
                        a.progress(Mathf.pow(event.tile.build.block.size, 2));
                    })
                ),
                new Achievement("routers-placed", Seq.with(500, 1_000, 2_500, 5_000, 10_000),
                        (a) ->
                                Events.on(EventType.BlockBuildEndEvent.class, event -> {
                                    if(Vars.state.rules.mode().equals(Gamemode.sandbox)) return;
                                    if(Vars.state.rules.mode().equals(Gamemode.editor)) return;
                                    if (isNotXilion() || !event.unit.getPlayer().equals(Vars.player)) return;
                                    if(event.tile.build.block != XBlocks.Distribution.pipeRouter) return;
                                    a.progress(1);
                                })
                ),
                new Achievement("units-made", Seq.with(100, 200, 500, 1_000, 2_000),
                        (a) ->
                                Events.on(EventType.UnitCreateEvent.class, event -> {
                                    if(Vars.state.rules.mode().equals(Gamemode.sandbox)) return;
                                    if(Vars.state.rules.mode().equals(Gamemode.editor)) return;
                                    if (isNotXilion() || !event.spawner.team.equals(Vars.player.team())) return;
                                    a.progress(1);
                                })
                ),
                new Achievement("turrets-placed", Seq.with(50, 100, 250, 500, 1_000),
                        (a) ->
                                Events.on(EventType.BlockBuildEndEvent.class, event -> {
                                    if(Vars.state.rules.mode().equals(Gamemode.sandbox)) return;
                                    if(Vars.state.rules.mode().equals(Gamemode.editor)) return;
                                    if (isNotXilion() || !event.unit.getPlayer().equals(Vars.player)) return;
                                    if(!(event.tile.build.block instanceof Turret)) return;
                                    a.progress(1);
                                })
                ),
                new Achievement("vent-turbines-placed", Seq.with(25, 50, 125, 250, 500),
                        (a) ->
                                Events.on(EventType.BlockBuildEndEvent.class, event -> {
                                    if(Vars.state.rules.mode().equals(Gamemode.sandbox)) return;
                                    if(Vars.state.rules.mode().equals(Gamemode.editor)) return;
                                    if (isNotXilion() || !event.unit.getPlayer().equals(Vars.player)) return;
                                    if(!(event.tile.build.block.equals(XBlocks.Power.ventTurbine))) return;
                                    a.progress(1);
                                })
                ),
                new Achievement("sector-completed", Seq.with(3, 5, 10, 15, 20),
                        (a) ->
                                Events.on(EventType.SectorCaptureEvent.class, event -> {
                                    if(!event.sector.planet.equals(XPlanets.xilion)) return;
                                    a.progress(1);
                                })
                ),
                new Achievement("chat-count", Seq.with(10, 20, 50, 100, 200),
                        (a) ->
                                Events.on(EventType.PlayerChatEvent.class, event -> {
                                    if(!event.player.equals(Vars.player)) return;
                                    a.progress(1);
                                })
                ),
                new Achievement("power-blocks-placed", Seq.with(250, 500, 1250, 2500, 5_000),
                        (a) ->
                                Events.on(EventType.BlockBuildEndEvent.class, event -> {
                                    if(Vars.state.rules.mode().equals(Gamemode.sandbox)) return;
                                    if(Vars.state.rules.mode().equals(Gamemode.editor)) return;
                                    if (isNotXilion() || !event.unit.getPlayer().equals(Vars.player)) return;
                                    if (!(event.tile.build.block instanceof PowerBlock)) return;
                                    a.progress(1);
                                })
                ),

                new Achievement("drills-placed", Seq.with(50, 100, 250, 500, 1_000),
                        (a) ->
                                Events.on(EventType.BlockBuildEndEvent.class, event -> {
                                    if(Vars.state.rules.mode().equals(Gamemode.sandbox)) return;
                                    if(Vars.state.rules.mode().equals(Gamemode.editor)) return;
                                    if (isNotXilion() || !event.unit.getPlayer().equals(Vars.player)) return;
                                    if (!(event.tile.build.block instanceof BeamDrill)
                                            && !(event.tile.build.block instanceof XWallCrafter)
                                            && !(event.tile.build.block instanceof Drill)) return;
                                    a.progress(1);
                                })
                ),

                new Achievement("logistics-placed", Seq.with(1000, 2_000, 5_000, 10_000, 20_000),
                        (a) ->
                                Events.on(EventType.BlockBuildEndEvent.class, event -> {
                                    if(Vars.state.rules.mode().equals(Gamemode.sandbox)) return;
                                    if(Vars.state.rules.mode().equals(Gamemode.editor)) return;
                                    if (isNotXilion() || !event.unit.getPlayer().equals(Vars.player)) return;
                                    if (!(event.tile.build.block.equals(XBlocks.Distribution.pipe))
                                            && !(event.tile.build.block.equals(XBlocks.Distribution.pipeBridge))
                                            && !(event.tile.build.block.equals(XBlocks.Distribution.pipeRouter))) return;
                                    a.progress(1);
                                })
                ),

                new Achievement("factories-placed", Seq.with(25, 50, 125, 250, 500),
                        (a) ->
                                Events.on(EventType.BlockBuildEndEvent.class, event -> {
                                    if(Vars.state.rules.mode().equals(Gamemode.sandbox)) return;
                                    if(Vars.state.rules.mode().equals(Gamemode.editor)) return;
                                    if (isNotXilion() || !event.unit.getPlayer().equals(Vars.player)) return;
                                    if (!(event.tile.build.block instanceof GenericCrafter)) return;
                                    a.progress(1);
                                })
                ),

                new Achievement("waves-cleared", Seq.with(25, 50, 125, 250, 500),
                        (a) ->
                                Events.on(EventType.WaveEvent.class, event -> {
                                    if(Vars.state.rules.mode().equals(Gamemode.sandbox)) return;
                                    if(Vars.state.rules.mode().equals(Gamemode.editor)) return;
                                    if (isNotXilion()) return;
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
