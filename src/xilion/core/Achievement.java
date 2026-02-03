package xilion.core;

import arc.Core;
import arc.func.Cons;
import arc.graphics.g2d.TextureRegion;
import arc.struct.Seq;

public class Achievement {

    private final String name;
    private final Cons<Achievement> init;

    private final Seq<Integer> goals;

    private int progress;

    private String displayName, description;
    private TextureRegion icon;

    public Achievement(String name, Seq<Integer> goals, Cons<Achievement> init) {
        this.name = name;
        this.init = init;
        this.goals = goals;
    }


    public void init() {
        displayName = Core.bundle.get(String.format("achievement.xilion-%s.name", name));
        description = Core.bundle.get(String.format("achievement.xilion-%s.description", name));
        icon = Core.atlas.find(String.format("xilion-achievement-%s", name));
        init.get(this);
    }

    public void progress(int amount) {
        progress += amount;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getProgress() {
        return progress;
    }

    public int getGoal() {
        for (Integer i : goals) {
            if (progress < i) return i;
        }
        return goals.get(goals.size - 1);
    }

    public int getStars() {
        int stars = 0;
        for (Integer i : goals) {
            if (progress >= i) stars++;
        }
        return stars;
    }

    public TextureRegion getIcon() {
        return icon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
