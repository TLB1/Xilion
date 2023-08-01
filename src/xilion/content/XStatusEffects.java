package xilion.content;

import arc.Core;
import arc.graphics.Color;
import mindustry.type.StatusEffect;
import xilion.XilionJavaMod;

public class XStatusEffects {
    public static StatusEffect leapDash, speedTierOne, speedTierTwo, astacoideaBuff;
    static {
        leapDash = new  StatusEffect("leap-dash"){{
            speedMultiplier = 2.5f;
        }};
        speedTierOne = new StatusEffect("speed-one"){{
            speedMultiplier = 1.25f;
        }};
        astacoideaBuff = new StatusEffect("light-overclock"){{
            speedMultiplier = 1.15f;
            reloadMultiplier = 1.15f;
        }};

    }




    public void load() {

        speedTierTwo = new StatusEffect("haste"){{
            speedMultiplier = 1.3f;
            reloadMultiplier = 1.1f;
            color= Color.rgb(90, 230, 242);
            uiIcon = Core.atlas.find(XilionJavaMod.name(name));

        }};
        speedTierTwo.loadIcon();
    }
}
