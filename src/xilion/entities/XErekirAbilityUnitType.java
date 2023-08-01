package xilion.entities;

import arc.Core;
import arc.input.KeyCode;
import arc.util.Log;
import mindustry.Vars;
import mindustry.gen.Unit;
import mindustry.type.unit.ErekirUnitType;
import xilion.util.XActiveAbility;
import xilion.util.XActiveAbilityUnit;

public class XErekirAbilityUnitType extends ErekirUnitType implements XActiveAbilityUnit {
    public XActiveAbility activeAbility;
    public XErekirAbilityUnitType(String name) {
        super(name);
    }

    @Override
    public XActiveAbility getActiveAbility() {
        return activeAbility;
    }

    @Override
    public void update(Unit unit) {
        if(unit.isPlayer() && Vars.player.unit() != null ){
            if(   Vars.player.unit() == unit &&  Core.input.keyDown(KeyCode.f6)){
                activeAbility.triggerAbility(unit);
                Log.info("Pressed ability button");
            }
        }
        activeAbility.update();
        super.update(unit);
    }
}
