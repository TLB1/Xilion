package xilion.content;

import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.UnitTypes;
import mindustry.type.Category;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.blocks.units.UnitFactory;
import xilion.blockTypes.XReconstructor;
import xilion.blockTypes.XUnitFactory;

import static mindustry.type.ItemStack.with;

public class XUnitFactories {
    public static Block prototypeFabricator, attackTransformer, supportTransformer, tankTransformer, advancedRefabricator;

    public void load(){
        prototypeFabricator = new XUnitFactory("prototype-fabricator"){{
            researchCost = with(XItems.germanium, 120, XItems.cobalt, 80, Items.silicon, 250);
            requirements(Category.units, with(XItems.germanium, 60, XItems.cobalt, 40, Items.silicon, 120));
            regionSuffix = "-xilion";
            size = 3;
            consumePower(2f);
            plans.add(new UnitPlan(XUnitTypes.ship, 60f * 15f, with(XItems.germanium, 25, Items.silicon, 15)));
            plans.add(new UnitPlan(XUnitTypes.bug, 60f * 15f, with(XItems.germanium, 25, Items.silicon, 15)));
            researchCostMultiplier = 0.75f;
        }};
        attackTransformer = new XReconstructor("attack-transformer"){{
            researchCost = with(XItems.germanium, 250, XItems.cobalt, 150, Items.silicon, 150);
            requirements(Category.units, with(XItems.germanium, 120, XItems.cobalt, 80, Items.silicon, 80));
            regionSuffix = "-xilion";

            size = 3;
            consumePower(2f);
            consumeItems(with(Items.silicon, 25, XItems.cobalt, 40));

            constructTime = 60f * 30f;
            researchCostMultiplier = 0.75f;

            upgrades.addAll(
                    new UnitType[]{XUnitTypes.ship, XUnitTypes.aura},
                    new UnitType[]{XUnitTypes.bug, XUnitTypes.strike}
            );
        }};
        supportTransformer = new XReconstructor("support-transformer"){{
            researchCost = with(XItems.germanium, 250, XItems.cobalt, 150, Items.silicon, 150);
            requirements(Category.units, with(XItems.germanium, 120, XItems.cobalt, 80, Items.silicon, 80));
            regionSuffix = "-xilion";

            size = 3;
            consumePower(2f);
            consumeItems(with(Items.silicon, 25, XItems.cobalt, 40));

            constructTime = 60f * 30f;
            researchCostMultiplier = 0.75f;

            upgrades.addAll(
                    new UnitType[]{XUnitTypes.ship, XUnitTypes.quick},
                    new UnitType[]{XUnitTypes.bug, XUnitTypes.sanatick}
            );
        }};
        tankTransformer = new XReconstructor("tank-transformer"){{
            researchCost = with(XItems.germanium, 250, XItems.cobalt, 150, Items.silicon, 150);
            requirements(Category.units, with(XItems.germanium, 120, XItems.cobalt, 80, Items.silicon, 80));
            regionSuffix = "-xilion";

            size = 3;
            consumePower(2f);
            consumeItems(with(Items.silicon, 25, XItems.cobalt, 40));

            constructTime = 60f * 30f;
            researchCostMultiplier = 0.75f;

            upgrades.addAll(
                    new UnitType[]{XUnitTypes.ship, XUnitTypes.blaze},
                    new UnitType[]{XUnitTypes.bug, XUnitTypes.acari}
            );
        }};
        advancedRefabricator = new XReconstructor("advanced-refabricator"){{
            researchCost = with(XItems.germanium, 500, XItems.cobalt, 500, Items.silicon, 500, XItems.chromium, 160);
            requirements(Category.units, with(XItems.germanium, 120, XItems.cobalt, 120, Items.silicon, 120, XItems.chromium, 40));
            regionSuffix = "-xilion";

            size = 3;
            consumePower(3f);
            consumeItems(with(Items.silicon, 50, XItems.cobalt, 50, XItems.chromium, 30));
            consumeLiquid(Liquids.hydrogen, 6 / 60f);
            constructTime = 60f * 40f;
            researchCostMultiplier = 0.75f;

            upgrades.addAll(
                    new UnitType[]{XUnitTypes.blaze, XUnitTypes.ember},
                    new UnitType[]{XUnitTypes.acari, XUnitTypes.blastbeetle},
                    new UnitType[]{XUnitTypes.aura, XUnitTypes.spectra},
                    new UnitType[]{XUnitTypes.strike, XUnitTypes.assault},
                    new UnitType[]{XUnitTypes.quick, XUnitTypes.dash},
                    new UnitType[]{XUnitTypes.sanatick, XUnitTypes.sanamite}
            );
        }};
    }
}
