package xilion.core;

import arc.Events;
import arc.math.Mathf;
import arc.struct.Seq;
import arc.util.Log;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.gen.Icon;
import xilion.content.XPlanets;
import xilion.ui.Achievement;
import xilion.ui.AchievementDialog;

public class XilionAchievements {
    private static final Seq<Achievement> achievements = new Seq<>();
    private static final AchievementDialog achievementDialog = new AchievementDialog(achievements);

    public static void init() {
        achievements.addAll(
                new Achievement("blocks-placed", Seq.with(5_000, 20_000, 50_000),
                (a) ->
                    Events.on(EventType.BlockBuildEndEvent.class, event -> {
                        if (isNotXilion() || !event.unit.getPlayer().equals(Vars.player)) return;
                        a.progress(Mathf.pow(event.tile.build.block.size, 2));
                    })
                ),
                new Achievement("units-made", Seq.with(100, 400, 1_000),
                        (a) ->
                                Events.on(EventType.UnitCreateEvent.class, event -> {
                                    if (isNotXilion() || !event.spawner.team.equals(Vars.player.team())) return;
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
    }

    private static boolean isNotXilion() {
        return !Vars.state.getPlanet().equals(XPlanets.xilion);
    }
}
