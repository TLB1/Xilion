package xilion.ui;

import arc.Core;
import arc.scene.ui.Dialog;
import arc.scene.ui.ScrollPane;
import arc.scene.ui.layout.Scl;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Align;
import mindustry.ui.BorderImage;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import xilion.core.Achievement;
import xilion.core.XilionUpdater;

import java.awt.*;

public class AchievementDialog extends BaseDialog {
    private final Seq<Achievement> achievements;
    private final Table achievementTable;

    public AchievementDialog(Seq<Achievement> achievements) {
        super("Xilion Achievements");
        addCloseButton();
        this.achievements = achievements;
        achievementTable = new Table();
        ScrollPane scrollPane = new ScrollPane(achievementTable);
        cont.add(scrollPane);
        buttons.button("View changelog", XilionUpdater::getChangelog); //TODO: Fix for Android
    }

    @Override
    public Dialog show() {
        rebuildAchievements();
        return super.show();
    }

    // TODO: Make sure stuff doesn't get double highlighted on some Android versions
    public void rebuildAchievements() {
        achievementTable.clear();

        int i = 0;
        int cols = (int) Math.max(Core.graphics.getWidth() / Scl.scl(360), 1);
        for (Achievement achievement : achievements) {
            achievementTable.button(button -> {
                button.margin(0f);
                button.left();
                button.add(new BorderImage() {
                    @Override
                    public void draw() {
                        super.draw();
                        setDrawable(achievement.getIcon());
                    }
                }).size(64).pad(8);

                StringBuilder starsBuilder = new StringBuilder();
                for (int s = 0; s < achievement.getStars(); s++) {
                    starsBuilder.append("\uE809 ");
                }

                String infoText = String.format(
                        "[accent]%s\n[accent]%s\n[lightgray]%d/%d",
                        achievement.getDisplayName(),
                        starsBuilder.toString(),
                        achievement.getProgress(),
                        achievement.getGoal()
                );
                button.add(infoText).width(280).wrap().grow().pad(4f, 2f, 4f, 6f).top().left().labelAlign(Align.topLeft);
            }, Styles.flatBordert, () -> {

            });

            if (++i % cols == 0) achievementTable.row();
        }
    }
}
