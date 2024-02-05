package xilion.entities;

import mindustry.type.ammo.ItemAmmoType;
import mindustry.type.unit.ErekirUnitType;
import xilion.content.XColors;
import xilion.content.XItems;

public class XilionUnitType extends ErekirUnitType {
    public XilionUnitType(String name){
        super(name);
        outlineColor = XColors.outline;
        envDisabled = 0;
        ammoType = new ItemAmmoType(XItems.germanium);
        researchCostMultiplier = 10f;
    }
}
