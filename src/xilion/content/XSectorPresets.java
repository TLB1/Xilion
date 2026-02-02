package xilion.content;

import mindustry.type.SectorPreset;

public class XSectorPresets {
    public static SectorPreset horizon, cinderpath, helios, inferno, ashes;

    public static void load(){
        horizon = new SectorPreset("horizon", XPlanets.xilion, 33){{
            alwaysUnlocked = true;
            difficulty = 2;
            captureWave = 12;
        }};
        cinderpath = new SectorPreset("cinderpath", XPlanets.xilion, 35){{
            difficulty = 3;
            captureWave = 16;
        }};
        helios = new SectorPreset("helios", XPlanets.xilion, 44){{
            difficulty = 3;
            captureWave = 18;
        }};
        inferno = new SectorPreset("inferno", XPlanets.xilion, 38){{
            difficulty = 3;
            attackAfterWaves = true;
        }};
        ashes = new SectorPreset("ashes", XPlanets.xilion, 43){{
            difficulty = 5;
            captureWave = 19;
        }};
    }
}
