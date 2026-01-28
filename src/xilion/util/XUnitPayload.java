package xilion.util;

import arc.Events;
import arc.math.Mathf;
import arc.util.Tmp;
import mindustry.Vars;
import mindustry.core.World;
import mindustry.entities.EntityCollisions;
import mindustry.entities.Units;
import mindustry.game.EventType;
import mindustry.gen.Unit;
import mindustry.world.blocks.payloads.UnitPayload;

public class XUnitPayload extends UnitPayload {
    public XUnitPayload(Unit unit) {
        super(unit);
    }
    @Override
    public boolean dump(){
        if(unit.type == null) return true;

        if(!XUnitHandler.isReady) return false;
        if(!XUnitHandler.getUnitCap(unit.team).canAddUnit(unit.type)){
            overlayTime = 1f;
            overlayRegion = null;
            return false;
        }

        //check if unit can be dumped here
        EntityCollisions.SolidPred solid = unit.solidity();
        if(solid != null){
            Tmp.v1.trns(unit.rotation, 1f);

            int tx = World.toTile(unit.x + Tmp.v1.x), ty = World.toTile(unit.y + Tmp.v1.y);

            //cannot dump on solid blocks
            if(solid.solid(tx, ty)) return false;
        }

        //cannot dump when there's a lot of overlap going on
        if(!unit.type.flying && Units.count(unit.x, unit.y, unit.physicSize(), o -> o.isGrounded() && (o.type.allowLegStep == unit.type.allowLegStep)) > 0){
            return false;
        }
        if(!XUnitHandler.getUnitCap(unit.team()).tryAddToUnits(unit.type)) return false;
        //no client dumping
        if(Vars.net.client()) return true;

        //prevents stacking
        unit.vel.add(Mathf.range(0.5f), Mathf.range(0.5f));
        unit.add();
        unit.unloaded();
        Events.fire(new EventType.UnitUnloadEvent(unit));

        return true;
    }
}
