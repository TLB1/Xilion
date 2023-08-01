package xilion.activeAbilities;

import mindustry.gen.Unit;
import xilion.util.XActiveAbilityType;

public class TurboAA extends XActiveAbilityType {
    public TurboAA(String name) {
        super(name);
        setCooldownTime(10f);
    }

    @Override
    public void onUse(Unit unit) {
        super.onUse(unit);
    }
}
