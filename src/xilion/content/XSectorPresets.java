package xilion.content;

import arc.func.Cons;
import arc.graphics.Color;
import mindustry.game.Rules;
import mindustry.type.SectorPreset;

public class XSectorPresets {
    public static SectorPreset horizon, inferno, ashes;

    public static void load(){
        horizon = new SectorPreset("horizon", XPlanets.xilion, 33){{
            alwaysUnlocked = true;
            difficulty = 2;
            captureWave = 12;
            rules = hotSector();
        }};
        inferno = new SectorPreset("inferno", XPlanets.xilion, 38){{
            difficulty = 3;
            addStartingItems = true;
            rules = hotSector();
        }};
        ashes = new SectorPreset("ashes", XPlanets.xilion, 43){{
            difficulty = 5;
            captureWave = 19;
        }};
    }
    private static Cons<Rules> hotSector(){
        return r -> {
            r.ambientLight = new Color(0xff553d17);
            r.lighting = true;
            r.solarMultiplier = 1f;
        };
    }
}
