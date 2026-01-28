package xilion.content;

import mindustry.type.SectorPreset;

public class XSectorPresets {
    public static SectorPreset horizon, inferno, ashes;

    public static void load(){
        horizon = new SectorPreset("horizon", XPlanets.xilion, 33){{
            alwaysUnlocked = true;
            difficulty = 2;
            captureWave = 12;
        }};
        inferno = new SectorPreset("inferno", XPlanets.xilion, 38){{
            difficulty = 3;
            addStartingItems = true;
        }};
        ashes = new SectorPreset("ashes", XPlanets.xilion, 43){{
            difficulty = 5;
            captureWave = 19;
        }};
    }
}
