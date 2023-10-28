package xilion.entities;

import mindustry.entities.units.AIController;

public class LegUnitFaceBuildingAI extends AIController {
    @Override
    public void updateVisuals() {
        unit.lookAt(unit.prefRotation());
    }
}
