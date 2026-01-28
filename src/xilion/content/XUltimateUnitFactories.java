package xilion.content;

import arc.struct.Seq;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.UnitTypes;

import mindustry.type.Category;
import mindustry.type.UnitType;
import mindustry.world.blocks.units.Reconstructor;
import mindustry.world.blocks.units.UnitFactory;


import static mindustry.type.ItemStack.with;

public class XUltimateUnitFactories {
    public static UnitFactory groundFactoryNew, airFactoryNew;
    public static Reconstructor  additiveReconstructorNew, multiplicativeReconstructorNew, exponentialReconstructorNew;
  //  public static UPUnitPost flarePost;


    public void load() {
        /*
        flarePost = new UPUnitPost("flare-post"){{
            requirements(Category.units, ItemStack.with(Items.copper, 200, Items.lead, 250, Items.silicon, 250, Items.graphite, 100));
            size = 2;
            health = size * size * 50;
            UPUnitPostBuild.create();

        }};

         */

        groundFactoryNew = new UnitFactory("ground-factory-new") {{
            //Using data from
           size = Blocks.groundFactory.size;
            //buildCost = Blocks.groundFactory.buildCost;



            //new Sequence for adding more than 4 units
              plans = new Seq<>(8);


            requirements(Category.units, with(Items.copper, 50, Items.lead, 120, Items.silicon, 80));
            plans = Seq.with(
                    new UnitFactory.UnitPlan(UnitTypes.dagger, 60f * 15, with(Items.silicon, 10, Items.lead, 10)),
                    new UnitFactory.UnitPlan(UnitTypes.crawler, 60f * 10, with(Items.silicon, 8, Items.coal, 10)),
                    new UnitFactory.UnitPlan(UnitTypes.nova, 60f * 40, with(Items.silicon, 30, Items.lead, 20, Items.titanium, 20)),
                    /*
                    new UnitFactory.UnitPlan(UnitTypes.stell, 60f * 60, with(Items.silicon, 60, Items.titanium, 50)),
                    new UnitFactory.UnitPlan(UnitTypes.merui, 60f * 60, with(Items.silicon, 70, Items.lead, 30, Items.titanium, 50)),

                     */
                    new UnitFactory.UnitPlan(XUnitTypes.attack, 60f * 20, with(Items.silicon, 20, Items.titanium, 15, Items.pyratite, 10))
            );
            consPower = consumePower(1.2f);

        }};
        airFactoryNew = new UnitFactory("air-factory-new"){{

            plans = new Seq<>(8);
            requirements(Category.units, with(Items.copper, 60, Items.lead, 70));
            plans = Seq.with(
                    new UnitPlan(UnitTypes.flare, 60f * 15, with(Items.silicon, 15)),
                    new UnitPlan(UnitTypes.mono, 60f * 35, with(Items.silicon, 30, Items.lead, 15)),
                    /*
                    new UnitPlan(UnitTypes.elude, 60f * 60, with(Items.silicon, 70,Items.metaglass, 30, Items.graphite, 60)),

                     */
                    new UnitPlan(XUnitTypes.quick, 60f*20, with( Items.silicon, 15, Items.metaglass, 20))

            );
            size = 3;
            consPower = consumePower(1.2f);

        }};

        additiveReconstructorNew = new Reconstructor("additive-reconstructor-new"){{
            requirements(Category.units, with(Items.copper, 200, Items.lead, 120, Items.silicon, 90));

            size = 3;
            consPower = consumePower(3f);
            consumeItems(with(Items.silicon, 40, Items.graphite, 40));

            constructTime = 60f * 10f;


            upgrades.addAll(
                    new UnitType[]{UnitTypes.nova, UnitTypes.pulsar},
                    new UnitType[]{UnitTypes.dagger, UnitTypes.mace},
                    new UnitType[]{UnitTypes.crawler, UnitTypes.atrax},
                    /*
                    new UnitType[]{UnitTypes.stell, UnitTypes.locus},
                    new UnitType[]{UnitTypes.merui, UnitTypes.cleroi},

                     */
                    new UnitType[]{XUnitTypes.attack, XUnitTypes.strike},
                    new UnitType[]{UnitTypes.flare, UnitTypes.horizon},
                    new UnitType[]{UnitTypes.mono, UnitTypes.poly},
                    //new UnitType[]{UnitTypes.elude, UnitTypes.avert},
                    new UnitType[]{XUnitTypes.quick, XUnitTypes.dash},
                    new UnitType[]{UnitTypes.risso, UnitTypes.minke},
                    new UnitType[]{UnitTypes.retusa, UnitTypes.oxynoe}

            );
            /*
            if (Version.number >= 7){
                upgrades.add();

            } */

        }};

        multiplicativeReconstructorNew = new Reconstructor("multiplicative-reconstructor-new"){{
            requirements(Category.units, with(Items.lead, 650, Items.silicon, 450, Items.titanium, 350, Items.thorium, 650));

            size = 5;
            consPower = consumePower(6f);
            consumeItems(with(Items.silicon, 130, Items.titanium, 80, Items.metaglass, 40));

            constructTime = 60f * 30f;

            upgrades.addAll(
                    new UnitType[]{UnitTypes.mace, UnitTypes.fortress},
                    new UnitType[]{UnitTypes.pulsar, UnitTypes.quasar},
                    new UnitType[]{UnitTypes.atrax, UnitTypes.spiroct},
                   // new UnitType[]{UnitTypes.locus, UnitTypes.precept},
                    //new UnitType[]{UnitTypes.cleroi, UnitTypes.anthicus},
                    new UnitType[]{UnitTypes.horizon, UnitTypes.zenith},
                    new UnitType[]{UnitTypes.poly, UnitTypes.mega},
                   // new UnitType[]{UnitTypes.avert, UnitTypes.obviate},
                    new UnitType[]{XUnitTypes.dash, XUnitTypes.leap},
                    new UnitType[]{UnitTypes.minke, UnitTypes.bryde},
                    new UnitType[]{UnitTypes.oxynoe, UnitTypes.cyerce}
            );
            /*
            if (Version.number >= 7){
                upgrades.add();

            }
             */
        }};
        exponentialReconstructorNew = new Reconstructor("exponential-reconstructor-new"){{
            requirements(Category.units, with(Items.lead,2000, Items.silicon, 1000, Items.titanium, 2000, Items.thorium, 750, Items.plastanium, 450, Items.phaseFabric, 600));
            liquidCapacity = 60;
            consumeLiquid(Liquids.cryofluid, 1f);
            size = 7;
            consPower = consumePower(13f);
            consumeItems(with(Items.silicon, 850, Items.titanium, 750, Items.plastanium, 650));
            constructTime = 60f * 90f;
            upgrades.addAll(
                    new UnitType[]{UnitTypes.fortress, UnitTypes.scepter},
                    new UnitType[]{UnitTypes.quasar, UnitTypes.vela},
                    new UnitType[]{UnitTypes.spiroct, UnitTypes.arkyid},

                    new UnitType[]{UnitTypes.zenith, UnitTypes.antumbra},
                    new UnitType[]{UnitTypes.mega, UnitTypes.quad},
                    new UnitType[]{XUnitTypes.leap, XUnitTypes.sonic},

                    new UnitType[]{UnitTypes.bryde, UnitTypes.sei},
                    new UnitType[]{UnitTypes.cyerce, UnitTypes.aegires}

            );
        }};
    }
}
