package xilion.content;

import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.UnitTypes;
import mindustry.type.Category;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.blocks.units.UnitFactory;

import static mindustry.type.ItemStack.with;

public class XUnitFactories {
    public static Block prototypeFabricator, attackTransformer, supportTransformer, tankTransformer;

    public void load(){
        prototypeFabricator = new UnitFactory("prototype-fabricator"){{
            requirements(Category.units, with(XItems.germanium, 60, XItems.cobalt, 40, Items.silicon, 120));
            regionSuffix = "-xilion";
            size = 3;
            consumePower(2f);
            plans.add(new UnitPlan(XUnitTypes.ship, 60f * 15f, with(XItems.germanium, 25, Items.silicon, 15)));
            researchCostMultiplier = 0.75f;
        }};
        attackTransformer = new Reconstructor("attack-transformer"){{
            requirements(Category.units, with(XItems.germanium, 120, XItems.cobalt, 80, Items.silicon, 80));
            regionSuffix = "-xilion";

            size = 3;
            consumePower(2f);
            consumeItems(with(Items.silicon, 25, XItems.cobalt, 40));

            constructTime = 60f * 30f;
            researchCostMultiplier = 0.75f;

            upgrades.addAll(
                    new UnitType[]{XUnitTypes.ship, XUnitTypes.aura}
            );
        }};
        supportTransformer = new Reconstructor("support-transformer"){{
            requirements(Category.units, with(XItems.germanium, 120, XItems.cobalt, 80, Items.silicon, 80));
            regionSuffix = "-xilion";

            size = 3;
            consumePower(2f);
            consumeItems(with(Items.silicon, 25, XItems.cobalt, 40));

            constructTime = 60f * 30f;
            researchCostMultiplier = 0.75f;

            upgrades.addAll(
                    new UnitType[]{XUnitTypes.ship, XUnitTypes.quick}
            );
        }};
        tankTransformer = new Reconstructor("tank-transformer"){{
            requirements(Category.units, with(XItems.germanium, 120, XItems.cobalt, 80, Items.silicon, 80));
            regionSuffix = "-xilion";

            size = 3;
            consumePower(2f);
            consumeItems(with(Items.silicon, 25, XItems.cobalt, 40));

            constructTime = 60f * 30f;
            researchCostMultiplier = 0.75f;

            upgrades.addAll(
                    new UnitType[]{XUnitTypes.ship, XUnitTypes.blaze}
            );
        }};
    }
}
