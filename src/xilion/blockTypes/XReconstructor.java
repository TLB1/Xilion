package xilion.blockTypes;

import arc.Core;
import arc.graphics.Color;
import arc.math.Mathf;
import arc.util.io.Reads;
import mindustry.core.UI;
import mindustry.entities.Units;
import mindustry.gen.Iconc;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.ui.Fonts;
import mindustry.world.blocks.units.Reconstructor;
import xilion.util.XUnitHandler;
import xilion.util.XUnitPayload;

public class XReconstructor extends Reconstructor {
    public XReconstructor(String name) {
        super(name);
    }

    @Override
    public void setBars() {
        addBar("health", entity -> new Bar("stat.health", Pal.health, entity::healthf).blink(Color.white));

        if(consPower != null){
            boolean buffered = consPower.buffered;
            float capacity = consPower.capacity;

            addBar("power", entity -> new Bar(
                    () -> buffered ? Core.bundle.format("bar.poweramount", Float.isNaN(entity.power.status * capacity) ? "<ERROR>" : UI.formatAmount((int)(entity.power.status * capacity))) :
                            Core.bundle.get("bar.power"),
                    () -> Pal.powerBar,
                    () -> Mathf.zero(consPower.requestedPower(entity)) && entity.power.graph.getPowerProduced() + entity.power.graph.getBatteryStored() > 0f ? 1f : entity.power.status)
            );
        }
        addBar("progress", (XReconstructorBuild entity) -> new Bar("bar.progress", Pal.ammo, entity::fraction));
        addBar("units", (XReconstructorBuild e) ->
                new Bar(
                        () -> e.unit() == null ? "[lightgray]" + Iconc.cancel :
                                Core.bundle.format("bar.unitcap",
                                        Fonts.getUnicodeStr(e.unit().name),
                                        XUnitHandler.getUnitCap(e.team).getUnitCountForTier(e.unit()),
                                        e.unit() == null || e.unit().useUnitCap ? XUnitHandler.getUnitCap(e.team).getUnitCapForTier(e.unit()) : "∞"
                                ),
                        () -> Pal.power,
                        () -> e.unit() == null ? 0f : (e.unit().useUnitCap ? (float)XUnitHandler.getUnitCap(e.team).getUnitCountForTier(e.unit()) / XUnitHandler.getUnitCap(e.team).getUnitCapForTier(e.unit()) : 1f)
                ));
    }

    public class XReconstructorBuild extends ReconstructorBuild {


        @Override
        public void read(Reads read) {
            super.read(read);
            if(payload != null) {
                payload = new XUnitPayload(payload.unit);
            }
        }

        @Override
        public void read(Reads read, byte revision) {
            super.read(read, revision);
            if(payload != null) {
                payload = new XUnitPayload(payload.unit);
            }
        }
    }
}
