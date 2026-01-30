package xilion.content;

import arc.Core;
import arc.graphics.Blending;
import arc.graphics.Color;
import arc.math.Interp;
import arc.math.Mathf;
import mindustry.content.*;
import mindustry.entities.UnitSorts;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.entities.part.DrawPart;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootBarrel;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.ContinuousTurret;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.blocks.distribution.DuctBridge;
import mindustry.world.blocks.distribution.DuctRouter;
import mindustry.world.blocks.environment.*;
import mindustry.world.blocks.heat.HeatProducer;
import mindustry.world.blocks.liquid.ArmoredConduit;
import mindustry.world.blocks.liquid.LiquidRouter;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.storage.CoreBlock;
import mindustry.world.consumers.ConsumeLiquid;
import mindustry.world.draw.*;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Env;
import xilion.XilionJavaMod;
import xilion.blockTypes.*;
import xilion.entities.XMirrorShootAlternate;

import java.util.function.Supplier;

import static mindustry.type.ItemStack.with;

public class XBlocks {
    public static class Environment {
        public static Block

        /* --- Ores --- */
        germaniumWallOre, malachiteWallOre, largeTungstenOre, largeBoronOre,

        /* --- Floors --- */
        corrodedPhase, concentratedCarbon, pureCarbon, darkRedStone, erythriteStone, cobaltPhosphateStone, pinkstone, darkPinkstone,
        purpurRock, purpurRockDark, slateKhakiStone, slateKhakiStoneDark, greenBubbleStone, greenBubbleStoneDark, blueBubbleStone,
        blueBubbleStoneDark, slateBlueStone, slateBlueStoneDark,

        /* --- Walls --- */
        slateBlueStoneWall, slateKhakiStoneWall, greenBubbleStoneWall, blueBubbleStoneWall, purpurRockWall, corrodedPhaseWall,
        concentratedCarbonWall, pureCarbonWall, darkRedStoneWall, erythriteStoneWall, pinkstoneWall,

        /* --- Vents --- */
        corrodedPhaseVent, darkRedStoneVent, slateKhakiStoneVent, slateBlueStoneVent, greenBubbleStoneVent, blueBubbleStoneVent,

        /* --- Props --- */
        slateKhakiStoneBoulder,  greenBubbleStoneBoulder,  blueBubbleStoneBoulder,

        /* --- TallBlocks --- */
        turquoiseFlower, turquoiseFlowerBush, DarkRedCrystalBlocks, erythriteSpikes, corrodedPhaseSpikes, slateBlueCrystalCluster;
        private static void load(){
            loadOres();
            loadFloors();
            loadWalls();
            loadVents();
            loadProps();
            loadTallBlocks();
        }
        private static void loadOres() {
            largeTungstenOre = new XLargeOre("large-tungsten-ore", Items.tungsten) {{
                variants = 2;
            }};
            largeBoronOre = new XLargeOre("large-boron-ore", XItems.boron) {{
                variants = 2;
            }};
            germaniumWallOre = new OreBlock("ore-wall-germanium", XItems.germanium) {{
                wallOre = true;
                variants = 3;
            }};
            malachiteWallOre = new OreBlock("ore-wall-malachite", XItems.malachite) {{
                wallOre = true;
                variants = 3;
            }};
        }
        private static void loadFloors() {
            concentratedCarbon = new Floor("concentrated-carbon") {{
                variants = 5;
            }};
            pureCarbon = new Floor("pure-carbon") {{
                variants = 5;
            }};
            corrodedPhase = new Floor("corroded-phase") {{
                variants = 5;
            }};
            darkRedStone = new Floor("dark-red-stone") {{
                variants = 5;
            }};
            darkPinkstone = new Floor("dark-pinkstone") {{
                variants = 4;
            }};
            pinkstone = new Floor("pinkstone") {{
                variants = 5;
            }};
            greenBubbleStone = new Floor("green-bubble-stone") {{
                variants = 4;
            }};
            greenBubbleStoneDark = new Floor("green-bubble-stone-dark") {{
                variants = 4;
            }};
            blueBubbleStone = new Floor("blue-bubble-stone") {{
                variants = 9;
            }};
            blueBubbleStoneDark = new Floor("blue-bubble-stone-dark") {{
                variants = 9;
            }};
            purpurRock = new Floor("purpur-rock") {{
                variants = 5;
            }};
            purpurRockDark = new Floor("purpur-rock-dark") {{
                variants = 5;
            }};
            slateBlueStone = new Floor("slate-blue-stone") {{
                variants = 4;
            }};
            slateBlueStoneDark = new Floor("slate-blue-stone-dark") {{
                variants = 4;
            }};
            slateKhakiStone = new Floor("slate-khaki-stone") {{
                variants = 4;
            }};
            slateKhakiStoneDark = new Floor("slate-khaki-stone-dark") {{
                variants = 4;
            }};
            cobaltPhosphateStone = new Floor("cobalt-phosphate-stone") {{
                variants = 5;
                itemDrop = XItems.cobaltPhosphate;
                playerUnmineable = true;
            }};
            erythriteStone = new Floor("erythrite-stone") {{
                variants = 5;
                itemDrop = XItems.erythrite;
                playerUnmineable = true;

            }};
        }
        private static void loadWalls() {
            concentratedCarbonWall = new XStaticWall("concentrated-carbon-wall") {{
                concentratedCarbon.asFloor().wall = this;
                variants = 4;
                largeVariants = 2;
                attributes.set(XAttributes.carbon, 0.6f);
            }};
            pureCarbonWall = new XStaticWall("pure-carbon-wall") {{
                pureCarbon.asFloor().wall = this;
                variants = 4;
                largeVariants = 2;
                attributes.set(XAttributes.carbon, 1f);
            }};
            corrodedPhaseWall = new XStaticWall("corroded-phase-wall") {{
                corrodedPhase.asFloor().wall = this;
                variants = 4;
                largeVariants = 2;
                attributes.set(Attribute.sand, 1.2f);
            }};
            darkRedStoneWall = new XStaticWall("dark-red-stone-wall") {{
                darkRedStone.asFloor().wall = this;
                variants = 4;
                largeVariants = 3;
                attributes.set(Attribute.sand, 1.6f);
            }};
            pinkstoneWall = new XStaticWall("pinkstone-wall") {{
                pinkstone.asFloor().wall = this;
                variants = 3;
                largeVariants = 2;
                attributes.set(Attribute.sand, 0.6f);
            }};
            erythriteStoneWall = new XStaticWall("erythrite-stone-wall") {{
                erythriteStone.asFloor().wall = this;
                variants = 4;
                largeVariants = 2;
            }};
            greenBubbleStoneWall = new XStaticWall("green-bubble-stone-wall") {{
                greenBubbleStone.asFloor().wall = this;
                variants = 4;
                largeVariants = 2;
                attributes.set(Attribute.sand, 1.2f);
            }};
            blueBubbleStoneWall = new XStaticWall("blue-bubble-stone-wall") {{
                greenBubbleStone.asFloor().wall = this;
                variants = 4;
                largeVariants = 2;
                attributes.set(Attribute.sand, 1.2f);
            }};
            purpurRockWall = new XStaticWall("purpur-rock-wall") {{
                purpurRock.asFloor().wall = this;
                variants = 4;
                largeVariants = 2;
                attributes.set(Attribute.sand, 0.8f);
            }};
            slateBlueStoneWall = new XStaticWall("slate-blue-stone-wall") {{
                slateBlueStone.asFloor().wall = this;
                variants = 4;
                largeVariants = 2;
                attributes.set(Attribute.sand, 0.6f);
            }};
            slateKhakiStoneWall = new XStaticWall("slate-khaki-stone-wall") {{
                slateKhakiStone.asFloor().wall = this;
                variants = 4;
                largeVariants = 2;
                attributes.set(Attribute.sand, 0.6f);
            }};
        }
        private static void loadVents(){
            corrodedPhaseVent = new XSteamVentSmall("corroded-phase-vent") {
                {
                    parent = blendGroup = corrodedPhase;
                    variants = 2;
                    attributes.set(Attribute.steam, 1f);
                }
            };
            darkRedStoneVent = new XSteamVentSmall("dark-red-stone-vent") {{
                parent = blendGroup = darkRedStone;
                variants = 2;
                attributes.set(Attribute.steam, 1f);
            }};
            greenBubbleStoneVent = new XSteamVentSmall("green-bubble-stone-vent") {
                {
                    parent = blendGroup = greenBubbleStone;
                    variants = 2;
                    attributes.set(Attribute.steam, 1f);
                }
            };
            blueBubbleStoneVent = new XSteamVentSmall("blue-bubble-stone-vent") {
                {
                    parent = blendGroup = blueBubbleStone;
                    variants = 2;
                    attributes.set(Attribute.steam, 1f);
                }
            };
            slateBlueStoneVent = new XSteamVentSmall("slate-blue-stone-vent") {
                {
                    parent = blendGroup = slateBlueStone;
                    variants = 2;
                    attributes.set(Attribute.steam, 1f);
                }
            };
            slateKhakiStoneVent = new XSteamVentSmall("slate-khaki-stone-vent") {
                {
                    parent = blendGroup = slateKhakiStone;
                    variants = 2;
                    attributes.set(Attribute.steam, 1f);
                }
            };
        }
        private static void loadProps(){
            greenBubbleStoneBoulder = new Prop("green-bubble-stone-boulder") {{
                variants = 1;
                greenBubbleStone.asFloor().decoration = this;
            }};
            blueBubbleStoneBoulder = new Prop("blue-bubble-stone-boulder") {{
                variants = 1;
                blueBubbleStone.asFloor().decoration = this;
            }};
            slateKhakiStoneBoulder = new Prop("slate-khaki-stone-boulder") {{
                variants = 2;
                slateKhakiStone.asFloor().decoration = this;
            }};
        }
        private static void loadTallBlocks(){
            turquoiseFlower = new TallBlock("turquoise-flower") {{
                variants = 2;
                clipSize = 128f;
                hasShadow = false;
                shadowAlpha = 0f;
            }};
            turquoiseFlowerBush = new TallBlock("turquoise-flower-bush") {{
                variants = 3;
                clipSize = 128f;
                hasShadow = false;
                shadowAlpha = 0f;
            }};
            DarkRedCrystalBlocks = new TallBlock("dark-red-crystal-blocks") {{
                variants = 2;
                clipSize = 128f;
                shadowAlpha = 0.5f;
                shadowOffset = -4f;
            }};
            corrodedPhaseSpikes = new TallBlock("corroded-phase-spikes") {{
                variants = 3;
                clipSize = 96f;
                shadowAlpha = 0.5f;
                shadowOffset = -2.5f;
            }};
            erythriteSpikes = new TallBlock("erythrite-spikes") {{
                variants = 3;
                clipSize = 96f;
                shadowAlpha = 0.5f;
                shadowOffset = -2.5f;
            }};
            slateBlueCrystalCluster = new TallBlock("slate-blue-crystal-cluster"){{
                variants = 3;
                clipSize = 128f;
            }};
        }
    }
    public static class Distribution {
        public static Block pipe, pipeRouter, pipeBridge;
        private static void load(){
            pipe = new XPipe("pipe") {{
                researchCost = with(XItems.germanium, 5);
                requirements(Category.distribution, with(XItems.germanium, 1));
                health = 90;
                speed = 3.75f;
            }};
            pipeRouter = new DuctRouter("pipe-router") {{
                researchCostMultiplier = 0.1f;
                requirements(Category.distribution, with(XItems.germanium, 8));
                health = 90;
                speed = 3.75f;
                regionRotated1 = 1;
                solid = false;
            }};
            pipeBridge = new DuctBridge("pipe-bridge") {{
                researchCostMultiplier = 0.1f;
                requirements(Category.distribution, with(XItems.germanium, 15));
                health = 90;
                speed = 3.75f;
                range = 5;
                buildCostMultiplier = 2f;
                researchCostMultiplier = 0.3f;
            }};
        }
    }
    public static class Liquid {
        public static Block basicConduit, basicLiquidRouter, liquidDistributor, liquidTunnel, liquidCollector, basicPump;

        private static void load(){
            basicConduit = new ArmoredConduit("basic-conduit") {{
                requirements(Category.liquid, with(XItems.germanium, 2));

                botColor = Pal.darkestMetal;
                leaks = true;
                liquidCapacity = 20f;
                liquidPressure = 1.03f;
                health = 250;
                researchCostMultiplier = 0.5f;
                underBullets = true;
            }};
            basicLiquidRouter = new LiquidRouter("basic-liquid-router") {{
                requirements(Category.liquid, with(XItems.cobalt, 2, XItems.germanium, 4));
                liquidCapacity = 30f;
                liquidPadding = 3f / 4f;
                researchCostMultiplier = 0.5f;
                underBullets = true;
                solid = false;
                squareSprite = false;
            }};
            liquidDistributor = new XLiquidDistributor("liquid-distributor") {{
                requirements(Category.liquid, with(Items.tungsten, 2, XItems.germanium, 4));
                liquidCapacity = 30f;
                underBullets = true;
                solid = false;
                squareSprite = false;
            }};
            liquidCollector = new XLiquidCollector("liquid-collector") {{
                requirements(Category.liquid, with(Items.tungsten, 2, XItems.germanium, 4));
                liquidCapacity = 30f;
                underBullets = true;
                solid = false;
                squareSprite = false;
            }};
            liquidTunnel = new XLiquidTunnel("liquid-tunnel") {{
                requirements(Category.liquid, with(Items.tungsten, 8, XItems.germanium, 15));
                range = 5;
            }};
            basicPump = new Pump("basic-pump") {{
                requirements(Category.liquid, with(XItems.cobalt, 40, Items.tungsten, 20, Items.silicon, 20));
                consumeLiquid(Liquids.hydrogen, 2f / 60f);

                pumpAmount = 60f / 60f / 4f;
                liquidCapacity = 80f;
                size = 2;
            }};
        }

    }
    public static class Walls {
        public static Block

        /* --- Normal Walls --- */
        germaniumWall, germaniumWallLarge, germaniumWallHuge, cobaltWall, cobaltWallLarge, cobaltWallHuge, reinforcedCopperWall,
        reinforcedCopperWallLarge, reinforcedCopperWallHuge, reinforcedTungstenWall, reinforcedTungstenWallLarge, reinforcedTungstenWallHuge,
        reinforcedCarbideWall, reinforcedCarbideWallLarge, reinforcedCarbideWallHuge, boronWall, boronWallLarge, boronWallHuge,
        boronCarbideWall, boronCarbideWallLarge, boronCarbideWallHuge,

        /* --- Special Walls --- */
        techWall, techWallLarge, techWallHuge, virusWall;
        private static void load(){
            loadWalls();
            loadSpecialWalls();
        }
        private static void loadWalls() {
            germaniumWall = new Wall("germanium-wall") {{
                requirements(Category.defense, with(XItems.germanium, 4));
                health = 110 * 4;
                armor = 2f;
                buildCostMultiplier = 8f;
            }};
            germaniumWallLarge = new Wall("germanium-wall-large") {{
                requirements(Category.defense, with(XItems.germanium, 16));
                health = 110 * 4 * 4;
                armor = 2f;
                buildCostMultiplier = 8f;
                size = 2;
            }};
            germaniumWallHuge = new Wall("germanium-wall-huge") {{
                requirements(Category.defense, with(XItems.germanium, 36));
                health = 110 * 4 * 9;
                armor = 2f;
                buildCostMultiplier = 8f;
                size = 3;
            }};

            cobaltWall = new Wall("cobalt-wall") {{
                requirements(Category.defense, with(XItems.cobalt, 4));
                health = 150 * 4;
                armor = 3f;
                buildCostMultiplier = 10f;
            }};
            cobaltWallLarge = new Wall("cobalt-wall-large") {{
                requirements(Category.defense, with(XItems.cobalt, 16));
                health = 150 * 4 * 4;
                armor = 3f;
                buildCostMultiplier = 10f;
                size = 2;
            }};
            cobaltWallHuge = new Wall("cobalt-wall-huge") {{
                requirements(Category.defense, with(XItems.cobalt, 36));
                health = 150 * 4 * 9;
                armor = 3f;
                buildCostMultiplier = 10f;
                size = 3;
            }};
            reinforcedCopperWall = new Wall("reinforced-copper-wall") {{
                requirements(Category.defense, with(Items.copper, 4, Items.silicon, 2));
                health = 110 * 4;
                armor = 4f;
                buildCostMultiplier = 10f;
                conductivePower = true;
                hasPower = true;
                connectedPower = true;
            }};
            reinforcedCopperWallLarge = new Wall("reinforced-copper-wall-large") {{
                requirements(Category.defense, with(Items.copper, 16, Items.silicon, 8));
                health = 110 * 4 * 4;
                armor = 4f;
                buildCostMultiplier = 10f;
                size = 2;
                conductivePower = true;
                hasPower = true;
                connectedPower = true;
            }};
            reinforcedCopperWallHuge = new Wall("reinforced-copper-wall-huge") {{
                requirements(Category.defense, with(Items.copper, 36, Items.silicon, 18));
                health = 110 * 4 * 9;
                armor = 4f;
                buildCostMultiplier = 10f;
                size = 3;
                conductivePower = true;
                hasPower = true;
                connectedPower = true;
            }};
            reinforcedTungstenWall = new Wall("reinforced-tungsten-wall") {{
                requirements(Category.defense, with(Items.tungsten, 4, Items.silicon, 2));
                health = 180 * 4;
                armor = 14f;
                buildCostMultiplier = 10f;
            }};
            reinforcedTungstenWallLarge = new Wall("reinforced-tungsten-wall-large") {{
                requirements(Category.defense, with(Items.tungsten, 16, Items.silicon, 8));
                health = 180 * 4 * 4;
                armor = 14f;
                buildCostMultiplier = 10f;
                size = 2;
            }};
            reinforcedTungstenWallHuge = new Wall("reinforced-tungsten-wall-huge") {{
                requirements(Category.defense, with(Items.tungsten, 36, Items.silicon, 18));
                health = 180 * 4 * 9;
                armor = 14f;
                buildCostMultiplier = 10f;
                size = 3;
            }};

            reinforcedCarbideWall = new Wall("reinforced-carbide-wall") {{
                requirements(Category.defense, with(Items.carbide, 4, Items.silicon, 2));
                health = 240 * 4;
                armor = 16f;
                buildCostMultiplier = 10f;
            }};
            reinforcedCarbideWallLarge = new Wall("reinforced-carbide-wall-large") {{
                requirements(Category.defense, with(Items.carbide, 16, Items.silicon, 8));
                health = 240 * 4 * 4;
                armor = 16f;
                buildCostMultiplier = 10f;
                size = 2;
            }};
            reinforcedCarbideWallHuge = new Wall("reinforced-carbide-wall-huge") {{
                requirements(Category.defense, with(Items.carbide, 36, Items.silicon, 18));
                health = 240 * 4 * 9;
                armor = 16f;
                buildCostMultiplier = 10f;
                size = 3;
            }};

            boronWall = new Wall("boron-wall") {{
                requirements(Category.defense, with(XItems.boron, 6));
                health = 230 * 4;
                armor = 15f;
                buildCostMultiplier = 10f;
            }};
            boronWallLarge = new Wall("boron-wall-large") {{
                requirements(Category.defense, with(XItems.boron, 24));
                health = 230 * 4 * 4;
                armor = 15f;
                buildCostMultiplier = 10f;
                size = 2;
            }};
            boronWallHuge = new Wall("boron-wall-huge") {{
                requirements(Category.defense, with(XItems.boron, 54));
                health = 230 * 4 * 9;
                armor = 18f;
                buildCostMultiplier = 10f;
                size = 3;
            }};
            boronCarbideWall = new Wall("boron-carbide-wall") {{
                requirements(Category.defense, with(XItems.boronCarbide, 4, Items.silicon, 2));
                health = 300 * 4;
                armor = 18f;
                buildCostMultiplier = 10f;
            }};
            boronCarbideWallLarge = new Wall("boron-carbide-wall-large") {{
                requirements(Category.defense, with(XItems.boronCarbide, 16, Items.silicon, 8));
                health = 300 * 4 * 4;
                armor = 18f;
                buildCostMultiplier = 10f;
                size = 2;
            }};
            boronCarbideWallHuge = new Wall("boron-carbide-wall-huge") {{
                requirements(Category.defense, with(XItems.boronCarbide, 36, Items.silicon, 18));
                health = 300 * 4 * 9;
                armor = 18f;
                buildCostMultiplier = 10f;
                size = 3;
            }};
        }
        private static void loadSpecialWalls(){
            techWall = new XTechWall("tech-wall") {{
                requirements(Category.defense, with(XItems.thermoplastic, 8, Items.silicon, 4, Items.surgeAlloy, 4));
                health = 225 * 4;
                armor = 8f;
                buildCostMultiplier = 10f;
                setTechStats();
            }};
            techWallLarge = new XTechWall("tech-wall-large") {{
                requirements(Category.defense, with(XItems.thermoplastic, 16, Items.silicon, 8, Items.surgeAlloy, 8));
                health = 225 * 4 * 4;
                armor = 8f;
                buildCostMultiplier = 10f;
                size = 2;
                setTechStats();
            }};
            techWallHuge = new XTechWall("tech-wall-huge") {{
                requirements(Category.defense, with(XItems.thermoplastic, 32, Items.silicon, 16, Items.surgeAlloy, 16));
                health = 225 * 4 * 9;
                armor = 8f;
                buildCostMultiplier = 10f;
                size = 3;
                setTechStats();
            }};
            virusWall = new XVirusWall("virus-wall") {{
                requirements(Category.defense, with(Items.thorium, 20));
                health = 200;
            }};
        }
    }
    public static class Turrets {
        public static Block giantBomb, tarMine, shock, heavy, regularity, prevent, discharge, isolate, divide, focus, aquila;
        private static void load() {
            giantBomb = new XBomb("giant-bomb") {{
                explosionRadius = 5 * 8f;
                explodeSound = Sounds.explosion;
                triggerRadius = 2 * 8f;
                size = 2;
                explosionDamage = 350f;
                requirements = with(Items.silicon, 40, XItems.cobalt, 30);
            }};
            tarMine = new XStatusMine("tar-mine"){{
                explosionRadius = 4 * 8f;
                statusRadius = 4 * 8f;
                canStopActivating = true;
                activationTime = 60f;
                status = StatusEffects.tarred;
                statusDuration = 240f;
                explodeSound = Sounds.loopExtract;
                triggerRadius = 2 * 8f;
                size = 2;
                explosionDamage = 50f;
                requirements = with(Items.silicon, 35, Items.tungsten, 20);
            }};
            shock = new PowerTurret("shock") {{
                researchCost = with(Items.silicon, 60, XItems.germanium, 100, XItems.cobalt, 60);
                researchCostMultiplier = 0.5f;
                targetAir = true;
                size = 2;
                drawer = new DrawTurret(XilionJavaMod.TURRET_BASE) {{

                }};
                requirements(Category.turret, with(Items.silicon, 60, XItems.germanium, 100, XItems.cobalt, 60));
                buildCostMultiplier = 0.5f;
                shootType = new LightningBulletType() {{
                    damage = 20f;
                    lightningLength = 25;
                    collidesAir = true;
                    ammoMultiplier = 1f;
                    targetAir = true;


                    //for visual stats only.
                    buildingDamageMultiplier = 0.25f;

                    lightningType = new BulletType(0.0001f, 0f) {{
                        lifetime = Fx.lightning.lifetime;
                        hitEffect = Fx.hitLancer;
                        despawnEffect = Fx.none;
                        status = StatusEffects.shocked;
                        statusDuration = 10f;
                        hittable = false;
                        targetAir = true;
                        collidesAir = true;
                        lightColor = Color.white;
                        buildingDamageMultiplier = 0.25f;
                    }};
                }};
                reload = 25f;
                shootCone = 10f;
                rotateSpeed = 8f;
                targetAir = true;
                range = 144f;
                shootEffect = Fx.lightningShoot;
                heatColor = Color.red;
                recoil = 1f;
                health = 800;
                shootSound = Sounds.shootArc;
                consumePower(4f);
                coolant = consumeCoolant(0.1f);
                outlineColor = XColors.outline;
            }};
            heavy = new ItemTurret("heavy") {
                {

                    researchCost = with(Items.silicon, 50, XItems.germanium, 80, XItems.cobalt, 80);
                    requirements(Category.turret, with(Items.silicon, 50, XItems.germanium, 80, XItems.cobalt, 80));
                    buildCostMultiplier = 0.5f;
                    health = 800;
                    size = 2;
                    squareSprite = false;
                    cooldownTime = 90f;
                    drawer = new DrawTurret(XilionJavaMod.TURRET_BASE) {{

                    }};
                    ammo(

                            XItems.cobalt, new ArtilleryBulletType(3f, 250, "shell") {{
                                hitEffect = new MultiEffect(Fx.titanExplosion, Fx.titanSmoke);
                                despawnEffect = Fx.none;
                                knockback = 2f;
                                lifetime = 40f;
                                height = 12f;
                                width = 11f;
                                splashDamageRadius = 24f;
                                splashDamage = 250f;
                                scaledSplashDamage = true;
                                backColor = hitColor = trailColor = Pal.sapBullet.lerp(Pal.sapBulletBack, 0.5f);
                                frontColor = Color.white;
                                ammoMultiplier = 1f;
                                hitSound = Sounds.explosionTitan;

                                status = StatusEffects.blasted;

                                trailLength = 32;
                                trailWidth = 3.35f;
                                trailSinScl = 2.5f;
                                trailSinMag = 0.5f;
                                trailEffect = Fx.none;
                                despawnShake = 7f;

                                shootEffect = Fx.shootTitan;
                                smokeEffect = Fx.shootSmokeTitan;

                                trailInterp = v -> Math.max(Mathf.slope(v), 0.8f);
                                shrinkX = 0.2f;
                                shrinkY = 0.1f;
                                buildingDamageMultiplier = 0.3f;
                            }},
                            Items.surgeAlloy, new BasicBulletType(3f, 350, "shell") {{
                                hitEffect = new MultiEffect(Fx.titanExplosion, Fx.titanSmoke);
                                despawnEffect = Fx.none;
                                knockback = 2f;
                                lifetime = 40f;
                                height = 12f;
                                width = 11f;
                                splashDamageRadius = 40f;
                                splashDamage = 200f;
                                scaledSplashDamage = true;
                                backColor = hitColor = trailColor = Pal.bulletYellow.lerp(Pal.bulletYellowBack, 0.5f);
                                frontColor = Color.white;
                                ammoMultiplier = 1f;
                                hitSound = Sounds.explosionTitan;

                                status = StatusEffects.blasted;

                                trailLength = 32;
                                trailWidth = 3.35f;
                                trailSinScl = 2.5f;
                                trailSinMag = 0.5f;
                                trailEffect = Fx.none;
                                despawnShake = 7f;

                                shootEffect = Fx.shootTitan;
                                smokeEffect = Fx.shootSmokeTitan;

                                trailInterp = v -> Math.max(Mathf.slope(v), 0.8f);
                                shrinkX = 0.2f;
                                shrinkY = 0.1f;
                                buildingDamageMultiplier = 0.3f;
                            }}
                    );

                    shootSound = Sounds.shootArtillery;
                    ammoPerShot = 4;
                    maxAmmo = ammoPerShot * 3;
                    targetAir = false;
                    shake = 4f;
                    recoil = 1f;
                    reload = 60f * 2.5f;
                    shootY = 6f;
                    rotateSpeed = 2f;
                    range = 120f;
                    outlineColor = XColors.outline;
                }
            };
            regularity = new ItemTurret("regularity") {{
                researchCost = with(XItems.germanium, 250, XItems.cobalt, 200, Items.silicon, 180);
                requirements(Category.turret, with(XItems.germanium, 250, XItems.cobalt, 200, Items.silicon, 180));
                buildCostMultiplier = 0.5f;
                ammo(
                        XItems.germanium, new BasicBulletType(6f, 45) {{
                            collidesGround = false;
                            width = 10f;
                            height = 20f;
                            lifetime = 34f;
                            ammoMultiplier = 2;
                            targetGround = false;
                        }},
                        Items.silicon, new BasicBulletType(6f, 40) {{
                            collidesGround = false;
                            width = 10f;
                            height = 20f;
                            reloadMultiplier = 2f;
                            ammoMultiplier = 2;
                            lifetime = 34f;
                            targetGround = false;
                        }}
                );

                drawer = new DrawTurret(XilionJavaMod.TURRET_BASE) {{
                }};

                size = 3;
                range = 200f;
                reload = 10f;
                ammoEjectBack = 3f;
                recoil = 2f;
                shake = 1f;

                targetGround = false;
                ammoUseEffect = Fx.casing2;
                scaledHealth = 260;
                shootSound = Sounds.shootScatter;
                coolant = consume(new ConsumeLiquid(Liquids.hydrogen, 5f / 60f));
                consumeLiquid(Liquids.water, 10f / 60f);
                squareSprite = false;
                outlineColor = XColors.outline;
            }};

            discharge = new ItemTurret("discharge") {{
                consumesPower = true;
                buildCostMultiplier = 0.5f;
                consumePower(6f);
                requirements(Category.turret, ItemStack.with(Items.copper, 180, XItems.cobalt, 280, Items.silicon, 300));
                drawer = new DrawTurret(XilionJavaMod.TURRET_BASE) {{
                }};
                ammo(Items.copper, new BasicBulletType() {{
                    smokeEffect = Fx.shootSmokeTitan;
                    this.hitColor = Pal.techBlue;
                    this.despawnSound = hitSound = Sounds.shootArc;
                    ammoMultiplier = 1f;
                    this.sprite = "large-orb";
                    this.trailEffect = Fx.missileTrail;
                    this.trailInterval = 6.0F;
                    this.trailParam = 4.0F;
                    this.speed = 6.0F;
                    this.damage = 25F;
                    splashDamage = 35f;
                    splashDamageRadius = 12f;
                    this.lifetime = 30.0F;
                    this.width = this.height = 15.0F;
                    this.backColor = Pal.techBlue;
                    this.frontColor = Color.white;
                    this.shrinkX = this.shrinkY = 0.0F;
                    this.trailColor = Pal.techBlue;
                    this.trailLength = 12;
                    this.trailWidth = 2.2F;
                    this.lightningDamage = 10.0F;
                    this.lightning = 2;
                    this.lightningLength = 10;
                    this.despawnEffect = this.hitEffect = new

                            ExplosionEffect() {
                                {
                                    this.waveColor = Pal.techBlue;
                                    this.smokeColor = Color.gray;
                                    this.sparkColor = Color.white;
                                    this.waveStroke = 4.0F;
                                    this.waveRad = 20.0F;
                                }
                            };
                }});
                shoot = new ShootBarrel() {
                    {
                        this.barrels = new float[]{-7F, 0F, 0.0F, 0.0F, 0.0F, 0.0F, 7F, 0F, 0.0F};
                        shots = 3;
                        shotDelay = 10F;
                    }
                };
                this.shootY = 10F;
                reload = 30.0F;
                inaccuracy = 5.0F;
                range = 184.0F;
                this.consumeAmmoOnce = false;
                this.size = 3;
                this.scaledHealth = 270f;
                this.shootSound = Sounds.shootMissile;

                coolant = consume(new ConsumeLiquid(Liquids.hydrogen, 10f / 60f));
                coolant.optional = true;
                coolantMultiplier = 2f;
                squareSprite = false;
                outlineColor = XColors.outline;
            }};
            isolate = new ItemTurret("isolate") {{
                    final float brange = range = 240.0F;
                    requirements(Category.turret, ItemStack.with(Items.copper, 200, XItems.cobalt, 300, Items.silicon, 300));
                    ammo(Items.surgeAlloy, new PointBulletType() {
                        {
                            shootEffect = Fx.instShoot;
                            hitEffect = Fx.instHit;
                            smokeEffect = Fx.smokeCloud;
                            trailEffect = Fx.instTrail;
                            despawnEffect = Fx.instBomb;
                            trailSpacing = 20.0F;
                            damage = 550F;
                            buildingDamageMultiplier = 0.2F;
                            speed = brange;
                            hitShake = 6.0F;
                            ammoMultiplier = 1.0F;
                        }
                    });
                    heatColor = Pal.turretHeat;
                    minWarmup = 0.94f;
                    shootWarmupSpeed = 0.06f;
                    maxAmmo = 40;
                    ammoPerShot = 3;
                    rotateSpeed = 2.0F;
                    reload = 120.0F;
                    ammoUseEffect = Fx.casing3Double;
                    recoil = 4.0F;
                    cooldownTime = reload*2f;
                    shake = 2.0F;
                    size = 3;
                    shootCone = 2.0F;
                    shootSound = Sounds.shootLaser;
                    unitSort = UnitSorts.weakest;
                    coolantMultiplier = 1F;
                    scaledHealth = 280f;
                    coolant = consumeCoolant(10 / 60F);
                    consumePower(5.0F);
                    outlineColor = XColors.outline;
                    fullIcon = Core.atlas.find("isolate-full");
                    drawer = new DrawTurret(XilionJavaMod.TURRET_BASE) {{
                        parts.add(new RegionPart("-side") {{
                            progress = PartProgress.warmup;
                            heatProgress = PartProgress.warmup;
                            heatColor = Pal.turretHeat;
                            mirror = true;
                            under = true;
                            x  = - 0.75f;
                            y = -0.75f;
                            moves.add(new PartMove(PartProgress.warmup, 2f, 2f, 0f));
                        }});
                    }};
                }};

            prevent = new ItemTurret("prevent") {{
                requirements(Category.turret, with(Items.tungsten, 200, XItems.cobalt, 220, Items.silicon, 200));
                buildCostMultiplier = 0.5f;

                ammo(Items.tungsten, new BasicBulletType() {{

                    damage = 40;
                    speed = 5.5f;
                    width = height = 12;
                    shrinkY = 0.3f;
                    backSprite = "large-bomb-back";
                    sprite = "mine-bullet";
                    velocityRnd = 0.11f;
                    targetGround = true;
                    collidesGround = true;
                    collidesTiles = false;
                    shootEffect = Fx.shootBig2;
                    smokeEffect = Fx.shootSmokeDisperse;
                    frontColor = Color.white;
                    backColor = trailColor = hitColor = Color.crimson;
                    trailChance = 0.44f;
                    ammoMultiplier = 1f;
                    knockback = 8f;
                    lifetime = 24f;
                    rotationOffset = 90f;
                    trailRotation = true;
                    trailEffect = Fx.disperseTrail;

                    hitEffect = despawnEffect = Fx.hitBulletColor;
                }});

                reload = 16f;
                shootY = 9.5f;

                rotateSpeed = 5f;
                shootCone = 30f;
                consumeAmmoOnce = true;
                shootSound = Sounds.shootSalvo;

                drawer = new DrawTurret(XilionJavaMod.TURRET_BASE) {{
                    parts.add(
                            new RegionPart("-mid") {{
                                under = true;
                                moveY = -1.5f;
                                progress = PartProgress.recoil;
                                heatProgress = PartProgress.recoil.add(0.25f).min(PartProgress.warmup);
                                //heatColor = Color.sky.cpy().a(0.9f);
                            }},
                            new RegionPart("-outer") {{
                                heatProgress = PartProgress.warmup;
                                heatColor = Color.sky.cpy().a(0.9f);
                                recoilIndex = 1;
                                progress = PartProgress.recoil;
                                heatProgress = PartProgress.recoil.add(0.25f).min(PartProgress.warmup);
                                under = true;
                                moveY = -1.5f;
                            }});
                }};

                shoot = new XMirrorShootAlternate() {{
                    spread = 3.5f;
                    shots = 4;
                    barrels = 2;
                    shotDelay = 8f;
                }};

                targetGround = true;
                inaccuracy = 8f;

                shootWarmupSpeed = 0.08f;

                outlineColor = XColors.outline;

                scaledHealth = 280;
                range = 128f;
                size = 3;

                coolant = consume(new ConsumeLiquid(Liquids.water, 10f / 60f));
                coolantMultiplier = 2f;

                limitRange(-4f);
            }};

            divide = new ItemTurret("divide") {{
                researchCost = with(XItems.boron, 150, XItems.cobalt, 350, Items.silicon, 300);
                requirements(Category.turret, with(XItems.boron, 300, XItems.cobalt, 700, Items.silicon, 600));
                buildCostMultiplier = 0.5f;
                ammo(
                        XItems.cobalt, new BasicBulletType(6f, 20) {{
                            collidesGround = false;
                            width = 12f;
                            height = 20f;
                            ammoMultiplier = 2;
                            lifetime = 40f;
                            targetGround = false;
                            splashDamage = 40f;
                            splashDamageRadius= 56f;
                        }},
                        XItems.boron, new BasicBulletType(6f, 40) {{
                            collidesGround = false;
                            width = 12f;
                            height = 20f;
                            lifetime = 40f;
                            ammoMultiplier = 2;
                            splashDamage = 50f;
                            splashDamageRadius= 40f;
                            targetGround = false;
                        }}

                );

                drawer = new DrawTurret(XilionJavaMod.TURRET_BASE) {{

                }};

                size = 4;
                range = 240f;
                reload = 6f;
                ammoEjectBack = 3f;
                recoil = 2f;
                shake = 1f;

                targetGround = false;
                ammoUseEffect = Fx.casing2;
                scaledHealth = 260;
                shootSound = Sounds.shootSalvo;
                coolant = consume(new ConsumeLiquid(Liquids.nitrogen, 10f / 60f));
                consumeLiquid(Liquids.hydrogen, 10f / 60f);
                squareSprite = false;
                outlineColor = XColors.outline;
            }};

            aquila = new ItemTurret("aquila") {{
                requirements(Category.turret, with(Items.silicon, 500, XItems.cobalt, 600, Items.tungsten, 500, XItems.boron, 300));
                heatColor = Pal.turretHeat;
                squareSprite = false;
                cooldownTime = 360f;
                recoil = 8f;
                reload = 240f;
                liquidCapacity = 50f;
                minWarmup = 0.94f;
                shootWarmupSpeed = 0.04f;
                scaledHealth = 340;
                size = 5;
                range = 480f;
                shootY = 19f;
                shootSound = Sounds.shootArtillery;
                ammoPerShot = 6;
                maxAmmo = 12;
                outlineColor = XColors.outline;
                shootCone = 1.25f;
                rotateSpeed = 1.5f;
                consumeLiquid(XItems.synGas, 8f / 60f);
                ammo(
                        XItems.boron, new BasicBulletType(8f, 3000) {{
                            width = 24f;
                            height = 24f;
                            lifetime = 60f;
                            splashDamageRadius = 10f;
                            pierce = true;
                            pierceDamageFactor = 0.8f;
                            knockback = 60f;

                        }},
                        Items.surgeAlloy, new BasicBulletType(8f, 3000) {{
                            width = 24f;
                            height = 24f;
                            lifetime = 60f;
                            reloadMultiplier = 1.5f;
                            ammoMultiplier = 1f;
                            pierce = true;
                            knockback = 60f;
                            pierceDamageFactor = 0.8f;
                        }}
                );
                consumePower(25f);
                drawer = new DrawTurret(XilionJavaMod.TURRET_BASE) {{
                    //var heatp = DrawPart.PartProgress.warmup.blend(p -> Mathf.absin(2f, 1f) * p.warmup, 0.2f);
                    parts.add(new RegionPart("-wing") {{
                        progress = PartProgress.warmup;
                        heatProgress = PartProgress.warmup;
                        heatColor = Color.valueOf("ffffff");
                        mirror = true;
                        under = true;
                        moves.add(new PartMove(PartProgress.warmup, 32f / 4f, 20f / 4f, -2f));
                    }}, new RegionPart("-glow") {{
                        outline = false;
                        color = Color.white;
                        blending = Blending.normal;
                        //blending = Blending.additive;
                    }});
                }};
            }};

            //TODO: Finally add Focus to the game
            focus = new ContinuousTurret("lustre") {{
                requirements(Category.turret, with(Items.silicon, 250, Items.graphite, 200, Items.oxide, 50, Items.carbide, 90));

                shootType = new PointLaserBulletType() {{
                    damage = 200f;
                    buildingDamageMultiplier = 0.3f;
                    hitColor = Color.valueOf("fda981");
                }};

                drawer = new DrawTurret("reinforced-") {{
                    var heatp = DrawPart.PartProgress.warmup.blend(p -> Mathf.absin(2f, 1f) * p.warmup, 0.2f);

                    parts.add(new RegionPart("-blade") {{
                                  progress = PartProgress.warmup;
                                  heatProgress = PartProgress.warmup;
                                  heatColor = Color.valueOf("ff6214");
                                  mirror = true;
                                  under = true;
                                  moveX = 2f;
                                  moveRot = -7f;
                                  moves.add(new PartMove(PartProgress.warmup, 0f, -2f, 3f));
                              }},
                            new RegionPart("-inner") {{
                                heatProgress = heatp;
                                progress = PartProgress.warmup;
                                heatColor = Color.valueOf("ff6214");
                                mirror = true;
                                under = false;
                                moveX = 2f;
                                moveY = -8f;
                            }},
                            new RegionPart("-mid") {{
                                heatProgress = heatp;
                                progress = PartProgress.warmup;
                                heatColor = Color.valueOf("ff6214");
                                moveY = -8f;
                                mirror = false;
                                under = true;
                            }});
                }};

                shootSound = Sounds.none;
                loopSoundVolume = 1f;
                loopSound = Sounds.shootLaser;

                shootWarmupSpeed = 0.08f;
                shootCone = 360f;

                aimChangeSpeed = 0.9f;
                rotateSpeed = 0.9f;

                shootY = 0.5f;
                outlineColor = Pal.darkOutline;
                size = 4;
                envEnabled |= Env.space;
                range = 250f;
                scaledHealth = 210;

                //TODO is this a good idea to begin with?
                unitSort = UnitSorts.strongest;

                consumeLiquid(Liquids.nitrogen, 6f / 60f);
            }};
        }
    }
    public static class BattleTurrets{
        public static Block
                /* 2x2 */
                shock, heavy,
                /* 3x3 */
                regularity, prevent, isolate,
                /* 4x4 */
                divide,
                /* 5x5 */
                zeus        ;
        private static void load() {
            shock = new BattleTurret("shock") {{

                targetAir = true;
                size = 2;
                drawer = new DrawTurret(XilionJavaMod.TURRET_BASE) {{

                }};
                requirements(Category.turret, with(XItems.guardianOre, 100));
                buildCostMultiplier = 0.5f;
                shootType = new LightningBulletType() {{
                    damage = 20f;
                    lightningLength = 25;
                    collidesAir = true;
                    ammoMultiplier = 1f;
                    targetAir = true;


                    //for visual stats only.
                    buildingDamageMultiplier = 0.25f;

                    lightningType = new BulletType(0.0001f, 0f) {{
                        lifetime = Fx.lightning.lifetime;
                        hitEffect = Fx.hitLancer;
                        despawnEffect = Fx.none;
                        status = StatusEffects.shocked;
                        statusDuration = 10f;
                        hittable = false;
                        targetAir = true;
                        collidesAir = true;
                        lightColor = Color.white;
                        buildingDamageMultiplier = 0.25f;
                    }};
                }};
                reload = 25f;
                shootCone = 10f;
                rotateSpeed = 8f;
                targetAir = true;
                range = 144f;
                shootEffect = Fx.lightningShoot;
                heatColor = Color.red;
                recoil = 1f;
                health = 800;
                shootSound = Sounds.shootArc;
                //consumePower(4f);
                //coolant = consumeCoolant(0.1f);
                outlineColor = XColors.outline;
            }};
            heavy = new BattleTurret("heavy") {
                {
                    requirements(Category.turret, with(XItems.guardianOre, 100));
                    buildCostMultiplier = 0.5f;
                    health = 800;
                    size = 2;
                    squareSprite = false;
                    cooldownTime = 90f;
                    drawer = new DrawTurret(XilionJavaMod.TURRET_BASE) {{

                    }};
                   shootType = new ArtilleryBulletType(3f, 250, "shell") {{
                                hitEffect = new MultiEffect(Fx.titanExplosion, Fx.titanSmoke);
                                despawnEffect = Fx.none;
                                knockback = 2f;
                                lifetime = 40f;
                                height = 12f;
                                width = 11f;
                                splashDamageRadius = 24f;
                                splashDamage = 250f;
                                scaledSplashDamage = true;
                                backColor = hitColor = trailColor = Pal.sapBullet.lerp(Pal.sapBulletBack, 0.5f);
                                frontColor = Color.white;
                                ammoMultiplier = 1f;
                                hitSound = Sounds.explosionTitan;

                                status = StatusEffects.blasted;

                                trailLength = 32;
                                trailWidth = 3.35f;
                                trailSinScl = 2.5f;
                                trailSinMag = 0.5f;
                                trailEffect = Fx.none;
                                despawnShake = 7f;

                                shootEffect = Fx.shootTitan;
                                smokeEffect = Fx.shootSmokeTitan;

                                trailInterp = v -> Math.max(Mathf.slope(v), 0.8f);
                                shrinkX = 0.2f;
                                shrinkY = 0.1f;
                                buildingDamageMultiplier = 0.3f;
                            }};

                    shootSound = Sounds.shootArtillerySmall;
                    targetAir = false;
                    shake = 4f;
                    recoil = 1f;
                    reload = 60f * 2.5f;
                    shootY = 6f;
                    rotateSpeed = 2f;
                    range = 120f;
                    outlineColor = XColors.outline;
                }
            };
            regularity = new BattleTurret("regularity") {{
                requirements(Category.turret, with(XItems.guardianOre, 300));

                shootType = new BasicBulletType(6f, 40) {{
                            collidesGround = false;
                            width = 10f;
                            height = 20f;
                            lifetime = 34f;
                            targetGround = false;
                        }};

                drawer = new DrawTurret(XilionJavaMod.TURRET_BASE) {{
                }};

                size = 3;
                range = 200f;
                reload = 5f;
                ammoEjectBack = 3f;
                recoil = 2f;
                shake = 1f;

                targetGround = false;
                ammoUseEffect = Fx.casing2;
                scaledHealth = 260;
                shootSound = Sounds.shootScatter;
                //coolant = consume(new ConsumeLiquid(Liquids.hydrogen, 5f / 60f));
                //consumeLiquid(Liquids.water, 10f / 60f);
                squareSprite = false;
                outlineColor = XColors.outline;
            }};
            prevent = new BattleTurret("prevent") {{
                requirements(Category.turret, with(XItems.guardianOre, 300));
                buildCostMultiplier = 0.5f;

                shootType = new BasicBulletType() {{
                    damage = 40;
                    speed = 5.5f;
                    width = height = 12;
                    shrinkY = 0.3f;
                    backSprite = "large-bomb-back";
                    sprite = "mine-bullet";
                    velocityRnd = 0.11f;
                    targetGround = true;
                    collidesGround = true;
                    collidesTiles = false;
                    shootEffect = Fx.shootBig2;
                    smokeEffect = Fx.shootSmokeDisperse;
                    frontColor = Color.white;
                    backColor = trailColor = hitColor = Color.crimson;
                    trailChance = 0.44f;
                    knockback = 8f;
                    lifetime = 24f;
                    rotationOffset = 90f;
                    trailRotation = true;
                    trailEffect = Fx.disperseTrail;

                    hitEffect = despawnEffect = Fx.hitBulletColor;
                }};

                reload = 16f;
                shootY = 9.5f;

                rotateSpeed = 5f;
                shootCone = 30f;
                consumeAmmoOnce = true;
                shootSound = Sounds.shootSalvo;

                drawer = new DrawTurret(XilionJavaMod.TURRET_BASE) {{
                    parts.add(
                            new RegionPart("-mid") {{
                                under = true;
                                moveY = -1.5f;
                                progress = PartProgress.recoil;
                                heatProgress = PartProgress.recoil.add(0.25f).min(PartProgress.warmup);
                            }},
                            new RegionPart("-outer") {{
                                heatProgress = PartProgress.warmup;
                                heatColor = Color.sky.cpy().a(0.9f);
                                recoilIndex = 1;
                                progress = PartProgress.recoil;
                                heatProgress = PartProgress.recoil.add(0.25f).min(PartProgress.warmup);
                                under = true;
                                moveY = -1.5f;
                            }});
                }};

                shoot = new XMirrorShootAlternate() {{
                    spread = 3.5f;
                    shots = 4;
                    barrels = 2;
                    shotDelay = 8f;
                }};

                targetGround = true;
                inaccuracy = 8f;

                shootWarmupSpeed = 0.08f;

                outlineColor = XColors.outline;

                scaledHealth = 280;
                range = 128f;
                size = 3;

                //coolant = consume(new ConsumeLiquid(Liquids.water, 10f / 60f));
                //coolantMultiplier = 2f;

                limitRange(-4f);
            }};
            isolate = new BattleTurret("isolate") {{
                final float brange = range = 240.0F;
                requirements(Category.turret, with(XItems.guardianOre, 300));
                shootType = new PointBulletType() {
                    {
                        shootEffect = Fx.instShoot;
                        hitEffect = Fx.instHit;
                        smokeEffect = Fx.smokeCloud;
                        trailEffect = Fx.instTrail;
                        despawnEffect = Fx.instBomb;
                        trailSpacing = 20.0F;
                        damage = 750F;
                        buildingDamageMultiplier = 0.2F;
                        speed = brange;
                        hitShake = 6.0F;
                        ammoMultiplier = 1.0F;
                    }
                };
                heatColor = Pal.turretHeat;
                minWarmup = 0.94f;
                shootWarmupSpeed = 0.06f;
                rotateSpeed = 2.0F;
                reload = 120.0F;
                ammoUseEffect = Fx.casing3Double;
                recoil = 4.0F;
                cooldownTime = reload*2f;
                shake = 2.0F;
                size = 3;
                shootCone = 2.0F;
                shootSound = Sounds.shootLaser;
                unitSort = UnitSorts.weakest;
                //coolantMultiplier = 1F;
                scaledHealth = 280f;
                //coolant = consumeCoolant(10 / 60F);
                outlineColor = XColors.outline;
                fullIcon = Core.atlas.find("isolate-full");
                drawer = new DrawTurret(XilionJavaMod.TURRET_BASE) {{
                    parts.add(new RegionPart("-side") {{
                        progress = PartProgress.warmup;
                        heatProgress = PartProgress.warmup;
                        heatColor = Pal.turretHeat;
                        mirror = true;
                        under = true;
                        x  = - 0.75f;
                        y = -0.75f;
                        moves.add(new PartMove(PartProgress.warmup, 2f, 2f, 0f));
                    }});
                }};
            }};
            divide = new BattleTurret("divide") {
                {
                    requirements(Category.turret, with(XItems.guardianOre, 600));
                    buildCostMultiplier = 0.5f;
                    scaledHealth = 280f;
                    size = 4;
                    squareSprite = false;
                    cooldownTime = 90f;
                    drawer = new DrawTurret(XilionJavaMod.TURRET_BASE) {{

                    }};
                    shootType = new BasicBulletType(4.5f, 200, "shell") {{
                                hitEffect = Fx.titanExplosion;
                                despawnEffect = Fx.titanExplosion;
                                knockback = 2f;
                                lifetime = 40f;
                                height = 12f;
                                width = 11f;
                                splashDamageRadius = 40f;
                                splashDamage = 200f;
                                scaledSplashDamage = true;
                                backColor = hitColor = trailColor = Pal.bulletYellow.lerp(Pal.bulletYellowBack, 0.5f);
                                frontColor = Color.white;
                                ammoMultiplier = 1f;
                                hitSound = Sounds.explosionTitan;
                                collidesAir = false;
                                targetAir = false;

                                status = StatusEffects.blasted;

                                trailLength = 32;
                                trailWidth = 3.35f;
                                trailSinScl = 2.5f;
                                trailSinMag = 0.5f;
                                trailEffect = Fx.none;
                                despawnShake = 7f;

                                shootEffect = Fx.shootTitan;
                                smokeEffect = Fx.shootSmokeTitan;

                                trailInterp = v -> Math.max(Mathf.slope(v), 0.8f);
                                shrinkX = 0.2f;
                                shrinkY = 0.1f;
                                buildingDamageMultiplier = 0.3f;
                            }};

                    shootSound = Sounds.shootSalvo;
                    ammoPerShot = 2;
                    maxAmmo = ammoPerShot * 3;
                    targetAir = false;
                    shake = 4f;
                    recoil = 1f;
                    reload = 60f * 2.5f / 4;
                    shootY = 6f;
                    rotateSpeed = 3f;
                    range = 180f;
                    outlineColor = XColors.outline;
                }
            };
            zeus = new BattleTurret("zeus") {{
                buildCostMultiplier = 0.5f;
                requirements(Category.turret, ItemStack.with(XItems.guardianOre, 1200));
                drawer = new DrawTurret(XilionJavaMod.TURRET_BASE) {{
                }};
                shootType = new BasicBulletType() {{
                    smokeEffect = Fx.shootSmokeTitan;
                    this.hitColor = Pal.techBlue;
                    this.despawnSound = hitSound = Sounds.shootArc;
                    ammoMultiplier = 1f;
                    this.sprite = "large-orb";
                    this.trailEffect = Fx.missileTrail;
                    this.trailInterval = 6.0F;

                    this.trailParam = 4.0F;
                    this.speed = 8.0F;
                    this.damage = 20F;
                    splashDamage = 35f;
                    splashDamageRadius = 18f;
                    this.lifetime = 30.0F;
                    this.width = this.height = 22.5F;
                    this.backColor = Pal.techBlue;
                    this.frontColor = Color.white;
                    this.shrinkX = this.shrinkY = 0.0F;
                    this.trailColor = Pal.techBlue;
                    this.trailLength = 12;
                    this.trailWidth = 3.3F;
                    this.lightningDamage = 10.0F;
                    this.lightning = 2;
                    lightningLength = 16;
                    this.knockback = 8f;
                    this.lightningLength = 10;
                    this.despawnEffect = this.hitEffect = new

                            ExplosionEffect() {
                                {
                                    this.waveColor = Pal.techBlue;
                                    this.smokeColor = Color.gray;
                                    this.sparkColor = Color.white;
                                    this.waveStroke = 4.0F;
                                    this.waveRad = 20.0F;
                                }
                            };
                }};
                shoot = new ShootBarrel() {
                    {
                        this.barrels = new float[]{-10F, 0F, 0.0F, 0.0F, 0.0F, 0.0F, 10F, 0F, 0.0F};
                        shots = 3;
                        shotDelay = 3.3F;
                    }
                };
                this.shootY = 10F;
                reload = 10.0F;
                inaccuracy = 5.0F;
                range = 240.0F;
                this.consumeAmmoOnce = false;
                this.size = 5;
                this.scaledHealth = 270f;
                this.shootSound = Sounds.shootMissile;

                coolant = consume(new ConsumeLiquid(Liquids.hydrogen, 10f / 60f));
                coolant.optional = true;
                coolantMultiplier = 2f;
                squareSprite = false;
                outlineColor = XColors.outline;
            }};
        }
    }
    public static class Power{
        public static Block wireNode, beamWireNode, ventTurbine, batteryCell, solarCell, thermoelectricGenerator,
                ammoniaTurbineGenerator, biogasCombustionChamber, hybridTurbineGenerator, nuclearFissionReactor;

        private static void load() {
            wireNode = new BeamNode("wire-node") {{
                requirements(Category.power, with(XItems.germanium, 4));
                consumesPower = outputsPower = true;
                health = 70;
                range = 3;
                fogRadius = 1;
                researchCost = with(XItems.germanium, 5);
                consumePowerBuffered(100f);
            }};
            beamWireNode = new BeamNode("beam-wire-node") {{
                requirements(Category.power, with(XItems.cobalt, 12, XItems.germanium, 4));
                consumesPower = outputsPower = true;
                health = 100;
                range = 15;
                fogRadius = 1;
                consumePowerBuffered(1000f);
            }};
            batteryCell = new PowerDistributor("battery-cell") {{
                requirements = with(XItems.germanium, 200, XItems.cobalt, 600, Items.silicon, 150);
                requirements(Category.power, with(XItems.germanium, 20, XItems.cobalt, 60, Items.silicon, 15));
                size = 2;
                consumesPower = outputsPower = true;
                fillsTile = false;
                consumePowerBuffered(20000f);
            }};
            ventTurbine = new ThermalGenerator("vent-turbine") {{
                requirements(Category.power, with(XItems.germanium, 40));
                squareSprite = false;
                attribute = Attribute.steam;
                group = BlockGroup.liquids;
                displayEfficiencyScale = 1f / 4f;
                minEfficiency = 4f - 0.0001f;
                powerProduction = 1.5f / 4f;
                displayEfficiency = false;
                generateEffect = Fx.turbinegenerate;
                effectChance = 0.04f;
                size = 2;
                ambientSound = Sounds.loopHum;
                ambientSoundVolume = 0.04f;

                drawer = new DrawMulti(new DrawDefault(), new DrawBlurSpin("-rotator", 1.1f * 9f) {{
                    blurThresh = 0.01f;
                }});

                hasLiquids = true;
                outputLiquid = new LiquidStack(Liquids.water, 5f / 60f / 4f);
                liquidCapacity = 10f;
                fogRadius = 3;
                researchCost = with(XItems.germanium, 15);
            }};
            solarCell = new SolarGenerator("solar-cell") {{
                requirements(Category.power, with(XItems.cobalt, 40, Items.silicon, 60));
                size = 2;
                powerProduction = 0.6f;
                fillsTile = false;
            }};
            thermoelectricGenerator = new ThermalGenerator("thermoelectric-generator") {{
                requirements(Category.power, with(XItems.cobalt, 50, Items.silicon, 45, Items.tungsten, 60));
                powerProduction = 2f;
                generateEffect = Fx.redgeneratespark;
                effectChance = 0.011f;
                size = 2;
                floating = false;
                ambientSound = Sounds.loopHum;
                ambientSoundVolume = 0.07f;
                drawer = new DrawMulti(new DrawDefault(), new DrawGlowRegion() {{
                    alpha = 1f;
                    glowScale = 7f;
                    color = Color.valueOf("ff1e1e");
                }});
            }};
            hybridTurbineGenerator = new ConsumeGenerator("hybrid-turbine-generator") {{
                squareSprite = false;
                fillsTile = false;
                requirements(Category.power, with(Items.tungsten, 30, Items.copper, 50, Items.silicon, 30));
                consumeLiquid(XItems.synGas, 1 / 60f);
                powerProduction = 8.5f;
                generateEffect = Fx.turbinegenerate;
                effectChance = 0.01f;
                size = 2;
                ambientSound = Sounds.loopHum;
                ambientSoundVolume = 0.05f;
                drawer = new DrawMulti(new DrawDefault(), new DrawBlurSpin("-rotator", 1.5f * 4f) {{
                    blurThresh = 0.01f;
                }});

                hasLiquids = true;
                liquidCapacity = 10f;
                fogRadius = 3;
            }};

            ammoniaTurbineGenerator = new ConsumeGenerator("ammonia-turbine-generator") {{
                squareSprite = false;
                fillsTile = false;
                requirements(Category.power, with(Items.tungsten, 70, XItems.cobalt, 100, Items.silicon, 50));
                consumeLiquid(XItems.ammonia, 2f / 60f);
                powerProduction = 16f;
                generateEffect = Fx.turbinegenerate;
                effectChance = 0.06f;
                size = 3;
                ambientSound = Sounds.loopHum;
                ambientSoundVolume = 0.06f;
                drawer = new DrawMulti(new DrawDefault(), new DrawBlurSpin("-rotator", 0.9f * 9f) {{
                    blurThresh = 0.01f;
                }});

                hasLiquids = true;
                outputLiquid = new LiquidStack(Liquids.hydrogen, 2f / 60f);
                liquidCapacity = 20f;
                fogRadius = 3;
            }};
            biogasCombustionChamber = new ConsumeGenerator("biogas-combustion-chamber") {{
                squareSprite = false;
                fillsTile = false;
                requirements(Category.power, with(XItems.cobalt, 180f, Items.tungsten, 200f, Items.copper, 50f, Items.silicon, 65));
                powerProduction = 50f;
                consumeLiquid(XItems.methane, 6 / 60f);
                size = 4;
                drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawPistons() {{
                    suffix = "-large-piston";
                    angleOffset = 45f;
                    lenOffset = -0.2f;
                    sinMag = 2f;
                    sinScl = 4f;
                }}, new DrawPistons() {{
                    suffix = "-small-piston";
                    angleOffset = 0f;
                    lenOffset = 0.1f;
                    sinMag = 2f;
                    sinScl = 2f;
                }}, new DrawLiquidTile(XItems.methane, 1.5f), new DrawDefault(), new DrawGlowRegion() {{
                    alpha = 1f;
                    glowScale = 7f;
                    color = Color.valueOf("e6723cA9");
                }});
                generateEffect = Fx.none;

                liquidCapacity = 60f;

                ambientSound = Sounds.loopSmelter;
                ambientSoundVolume = 0.12f;

            }};

            nuclearFissionReactor = new XFissionReactor("nuclear-fission-reactor") {{
                requirements(Category.power, with(Items.tungsten, 800, Items.carbide, 300, Items.oxide, 150, Items.silicon, 500, XItems.cobalt, 400, Items.surgeAlloy, 200));
                squareSprite = false;
                size = 5;
                liquidCapacity = 80f;
                explodeOnFull = true;


                consumeLiquid(Liquids.ozone, 30f / 60f);
                consumeItem(Items.thorium);

                itemDuration = 60f * 2f;
                itemCapacity = 10;

                explosionRadius = 9;
                explosionDamage = 2000;
                explodeEffect = new MultiEffect(Fx.bigShockwave, new WrapEffect(Fx.titanSmoke, Liquids.ozone.color), Fx.massiveExplosion);
                explodeSound = Sounds.explosion;

                powerProduction = 130f;

                ambientSound = Sounds.loopBio;
                ambientSoundVolume = 0.2f;

                explosionPuddles = 80;
                explosionPuddleRange = 8 * 6f;
                explosionPuddleLiquid = Liquids.ozone;
                explosionPuddleAmount = 200f;
                explosionMinWarmup = 0.5f;

                consumeEffect = new MultiEffect(new RadialEffect(XFx.thoriumFission, 4, 90f, 46f / 4f) {{
                    rotationOffset = 45f;
                }},
                        XFx.thoriumFission);

                drawer = new DrawMulti(
                        new DrawRegion("-bottom"),
                        new DrawLiquidTile(Liquids.ozone, 8f),
                        new DrawCircles() {{
                            color = Items.thorium.color.a(0.8f);
                            strokeMax = 3.25f;
                            radius = 65f / 4f;
                            amount = 5;
                            timeScl = 200f;
                        }},

                        // new DrawRegion("-mid"),
                   /*
                    new DrawCells(){{
                        color = Pal.thoriumPink;
                        particleColorFrom = Color.valueOf("8a73c6");
                        particleColorTo = Color.valueOf("665c9f");
                        particles = 50;
                        range = 4f;
                    }},
                    */
                        new DrawDefault(),
                        new DrawGlowRegion("-glow") {{
                            color = Color.valueOf("ffffff");
                            alpha = 0.7f;
                        }}, new DrawGlowRegion("-active-glow") {{
                    color = Pal.thoriumPink;
                    alpha = 0.7f;
                }}

                );
            }};
        }
    }
    public static class Production {
        public static Block biogasSynthesizer, gasOxidisationSynthesizer, haberProcessFacility,
                cobaltRefineryOven, phosphateDebonder, compactElectrolyzer,surgeOven,
                erythriteDissolver, malachiteDissolver, malachiteSmelter, siliconOven, methaneHeater;
        private static void load() {
            cobaltRefineryOven = new GenericCrafter("cobalt-refinery-oven") {{
                researchCost = with(XItems.germanium, 100);
                requirements(Category.crafting, with(XItems.germanium, 60));
                craftEffect = Fx.none;
                outputItem = new ItemStack(XItems.cobalt, 4);
                craftTime = 40f;
                size = 3;
                hasPower = true;
                hasLiquids = false;
                envEnabled |= Env.space | Env.underwater;
                envDisabled = Env.none;
                itemCapacity = 30;
                drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawArcSmelt(), new DrawDefault());
                fogRadius = 3;
                ambientSound = Sounds.loopSmelter;
                ambientSoundVolume = 0.12f;
                squareSprite = false;
                consumeItems(with(XItems.erythrite, 6));
                consumePower(4f);
            }};
            siliconOven = new GenericCrafter("silicon-oven") {{
                researchCost = with(XItems.germanium, 100, XItems.cobalt, 70);
                requirements(Category.crafting, with(XItems.germanium, 100, XItems.cobalt, 70));
                craftEffect = Fx.none;
                outputItem = new ItemStack(Items.silicon, 4);
                craftTime = 60f;
                size = 3;
                hasPower = true;
                hasLiquids = false;
                envEnabled |= Env.space | Env.underwater;
                envDisabled = Env.none;
                itemCapacity = 30;
                drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawArcSmelt(), new DrawDefault());
                fogRadius = 3;
                ambientSound = Sounds.loopSmelter;
                ambientSoundVolume = 0.12f;
                squareSprite = false;
                consumeItems(with(XItems.carbon, 2, Items.sand, 5));
                consumePower(5f);
            }};
            malachiteSmelter = new GenericCrafter("malachite-smelter") {{
                researchCost = with(XItems.germanium, 700, XItems.cobalt, 300, Items.silicon, 500);
                requirements(Category.crafting, with(XItems.germanium, 70, XItems.cobalt, 30, Items.silicon, 50));
                craftEffect = Fx.none;
                outputItem = new ItemStack(Items.copper, 4);
                craftTime = 40f;
                size = 3;
                hasPower = true;
                hasLiquids = false;
                envEnabled |= Env.space | Env.underwater;
                envDisabled = Env.none;
                itemCapacity = 30;
                drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawArcSmelt(), new DrawDefault());
                fogRadius = 3;
                ambientSound = Sounds.loopSmelter;
                ambientSoundVolume = 0.12f;
                squareSprite = false;
                consumeItems(with(XItems.malachite, 4));
                consumePower(4f);
            }};
            erythriteDissolver = new GenericCrafter("erythrite-dissolver") {
                {
                    requirements(Category.crafting, ItemStack.with(XItems.cobalt, 20, Items.copper, 20, Items.tungsten, 20, Items.silicon, 30));
                    liquidCapacity = 30.0F;
                    craftTime = 120F;
                    squareSprite = false;
                    size = 2;
                    hasLiquids = true;
                    outputLiquid = new LiquidStack(Liquids.water, 5f / 60f);
                    hasPower = true;
                    craftEffect = Fx.none;
                    drawer = new DrawMulti(new DrawRegion("-bottom"),
                            new DrawLiquidTile(Liquids.water, 2f), new DrawBubbles(Color.valueOf("7693e3")) {{
                        sides = 10;
                        recurrence = 3f;
                        spread = 6;
                        radius = 1.5f;
                        amount = 6;
                    }}, new DrawDefault());
                    consumeItem(XItems.erythrite, 3);
                    consumeLiquid(Liquids.arkycite, 5f / 60f);
                    outputItem = new ItemStack(XItems.cobalt, 2);
                    consumePower(0.7F);
                }
            };
            malachiteDissolver = new GenericCrafter("malachite-dissolver") {
                {
                    requirements(Category.crafting, ItemStack.with(XItems.cobalt, 20, Items.copper, 20, Items.tungsten, 20, Items.silicon, 30));
                    liquidCapacity = 30.0F;
                    craftTime = 60F;
                    squareSprite = false;
                    size = 2;
                    hasLiquids = true;
                    outputLiquid = new LiquidStack(Liquids.water, 5f / 60f);
                    hasPower = true;
                    craftEffect = Fx.none;
                    drawer = new DrawMulti(new DrawRegion("-bottom"),
                            new DrawLiquidTile(Liquids.arkycite, 2f), new DrawBubbles(Color.valueOf("76e3bd")) {{
                        sides = 10;
                        recurrence = 3f;
                        spread = 6;
                        radius = 1.5f;
                        amount = 6;
                    }}, new DrawDefault());
                    consumeItem(XItems.malachite, 1);
                    consumeLiquid(Liquids.arkycite, 5f / 60f);
                    outputItem = new ItemStack(Items.copper, 1);
                    consumePower(0.7F);
                }
            };
            compactElectrolyzer = new GenericCrafter("compact-electrolyzer") {{
                requirements(Category.crafting, with(Items.silicon, 30, XItems.germanium, 60, Items.tungsten, 30));
                size = 2;

                researchCostMultiplier = 2f;
                craftTime = 10f;
                rotate = true;
                invertFlip = true;
                group = BlockGroup.liquids;

                liquidCapacity = 20f;

                consumeLiquid(Liquids.water, 5f / 60f);
                consumePower(0.5f);

                drawer = new DrawMulti(
                        new DrawRegion("-bottom"),
                        new DrawLiquidTile(Liquids.water, 2f),
                        new DrawBubbles(Color.valueOf("7693e3")) {{
                            sides = 10;
                            recurrence = 3f;
                            spread = 6;
                            radius = 1.5f;
                            amount = 10;
                        }},
                        new DrawRegion(),
                        new DrawLiquidOutputs(),
                        new DrawGlowRegion() {{
                            alpha = 0.7f;
                            color = Color.valueOf("c4bdf3");
                            glowIntensity = 0.3f;
                            glowScale = 6f;
                        }}
                );

                ambientSound = Sounds.loopElectricHum;
                ambientSoundVolume = 0.06f;

                regionRotated1 = 3;
                outputLiquids = LiquidStack.with(Liquids.ozone, 2f / 60, Liquids.hydrogen, 3f / 60);
                liquidOutputDirections = new int[]{1, 3};
            }};
            biogasSynthesizer = new GenericCrafter("biogas-synthesizer") {{
                requirements(Category.crafting, with(Items.tungsten, 100, Items.silicon, 80, XItems.germanium, 120));

                drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(XItems.methane) {{
                    padding = 3f;
                }},
                        new DrawParticles() {{
                            color = Color.valueOf("e69d62");
                            alpha = 0.7f;
                            particleSize = 2.5f;
                            particles = 20;
                            particleRad = 9f;
                            particleLife = 200f;
                            reverse = true;
                            particleSizeInterp = Interp.one;
                        }}, new DrawDefault(), new DrawHeatRegion("-heat-top"));

                size = 3;

                ambientSound = Sounds.loopExtract;
                ambientSoundVolume = 0.08f;
                squareSprite = false;
                liquidCapacity = 80f;
                outputLiquid = new LiquidStack(XItems.methane, 8f / 60f);

                //consumeLiquids(LiquidStack.with(Liquids.hydrogen, 3f / 60f, Liquids.nitrogen, 2f / 60f));
                craftTime = 15f;
                consumeLiquid(Liquids.hydrogen, 8f / 60f);
                consumeItem(XItems.carbon, 1);
                consumePower(5f);
            }};
            gasOxidisationSynthesizer = new GenericCrafter("gas-oxidation-synthesizer") {{
                requirements(Category.crafting, with(XItems.cobalt, 160, Items.silicon, 90, Items.surgeAlloy, 30));

                drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(XItems.synGas) {{
                    padding = 3f;
                }},
                        new DrawParticles() {{
                            color = Color.valueOf("febcd3");
                            alpha = 0.6f;
                            particleSize = 1.5f;
                            particles = 10;
                            particleRad = 9f;
                            particleLife = 180f;
                            reverse = true;
                            particleSizeInterp = Interp.one;
                        }}, new DrawDefault(), new DrawHeatRegion("-heat-top"));

                size = 3;

                ambientSound = Sounds.loopHum;
                ambientSoundVolume = 0.08f;
                squareSprite = false;
                liquidCapacity = 80f;
                outputLiquid = new LiquidStack(XItems.synGas, 6f / 60f);

                //consumeLiquids(LiquidStack.with(Liquids.hydrogen, 3f / 60f, Liquids.nitrogen, 2f / 60f));
                craftTime = 15f;
                consumeLiquids(LiquidStack.with(XItems.methane, 4f / 60f, Liquids.ozone, 2f / 60f));
                consumePower(4f);
            }};
            surgeOven = new GenericCrafter("surge-oven") {{
                requirements(Category.crafting, with(Items.silicon, 100, XItems.germanium, 200, Items.tungsten, 80));

                size = 3;
                itemCapacity = 20;
                craftTime = 60f * 3f;

                ambientSound = Sounds.loopSmelter;
                ambientSoundVolume = 0.9f;

                outputItem = new ItemStack(Items.surgeAlloy, 2);
                craftEffect = new RadialEffect(Fx.surgeCruciSmoke, 4, 90f, 5f);
                squareSprite = false;
                drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawCircles() {{
                    color = Color.valueOf("ffc073").a(0.24f);
                    strokeMax = 2.5f;
                    radius = 10f;
                    amount = 3;
                }}, new DrawDefault(), new DrawHeatInput(),
                        new DrawHeatRegion() {{
                            color = Color.valueOf("ff6060ff");
                        }});

                consumeItems(ItemStack.with(Items.silicon, 3, Items.tungsten, 4, XItems.erythrite, 4));
                consumePower(8f);
            }};
            phosphateDebonder = new GenericCrafter("phosphate-debonder") {{
                requirements(Category.crafting, with(Items.silicon, 70, XItems.cobalt, 50, XItems.germanium, 140, Items.tungsten, 80));
                size = 3;

                researchCostMultiplier = 1.2f;
                craftTime = 60f;
                rotate = true;
                invertFlip = true;
                group = BlockGroup.liquids;

                liquidCapacity = 40f;
                itemCapacity = 20;
                consumeItem(XItems.cobaltPhosphate, 6);
                consumePower(1.5f);

                drawer = new DrawMulti(
                        new DrawRegion(),
                        new DrawLiquidOutputs(),
                        new DrawGlowRegion() {{
                            alpha = 0.7f;
                            color = Color.valueOf("c4bdf3");
                            glowIntensity = 0.3f;
                            glowScale = 6f;
                        }}
                );
                ambientSound = Sounds.loopElectricHum;
                ambientSoundVolume = 0.1f;
                squareSprite = false;
                regionRotated1 = 3;
                outputLiquids = LiquidStack.with(Liquids.ozone, 4f / 60, XItems.phosphorus, 1f / 60);
                outputItem = new ItemStack(XItems.cobalt, 1);
                liquidOutputDirections = new int[]{1, 3};
                squareSprite = false;
            }};
            methaneHeater = new HeatProducer("methane-heater") {{
                requirements(Category.crafting, with(Items.oxide, 30, Items.tungsten, 30, XItems.germanium, 30));

                drawer = new DrawMulti(new DrawDefault(), new DrawHeatOutput());
                size = 2;
                heatOutput = 8f;
                craftTime = 60f;
                ambientSound = Sounds.loopFlux;
                consumeLiquid(XItems.methane, 1f / 180f);
            }};
            haberProcessFacility = new HeatCrafter("haber-process-facility") {{
                requirements(Category.crafting, with(Items.thorium, 25, Items.silicon, 80, XItems.germanium, 160));

                heatRequirement = 6f;

                drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(XItems.ammonia),
                        new DrawParticles() {{
                            color = Color.valueOf("e4ca5a");
                            alpha = 0.7f;
                            particleSize = 1f;
                            particles = 25;
                            particleRad = 9f;
                            particleLife = 200f;
                            reverse = true;
                            particleSizeInterp = Interp.one;
                        }}, new DrawDefault(), new DrawHeatInput(), new DrawHeatRegion("-heat-top"));

                size = 3;

                ambientSound = Sounds.loopExtract;
                ambientSoundVolume = 0.08f;

                liquidCapacity = 80f;
                outputLiquid = new LiquidStack(XItems.ammonia, 2f / 60f);

                //consumeLiquids(LiquidStack.with(Liquids.hydrogen, 3f / 60f, Liquids.nitrogen, 2f / 60f));
                consumeLiquid(Liquids.hydrogen, 6f / 60f);
                consumeLiquid(Liquids.nitrogen, 2f / 60f);
                consumePower(2f);
            }};
        }
    }
    public static class Drills {
        public static Block plasmaCollector, wallCrusher, oreDrill, crusherDrill, superOreDrill, waterExtractionBore;
        private static void load(){
            plasmaCollector = new BeamDrill("plasma-collector") {{
                requirements(Category.production, with(XItems.germanium, 50));
                consumePower(30 / 60f);
                researchCost = with(XItems.germanium, 50);
                drillTime = 120f;
                tier = 3;
                size = 2;
                range = 4;
                fogRadius = 3;

                consumeLiquid(XItems.ammonia, 0.1f / 60f).boost();
            }};
            crusherDrill = new BurstDrill("crusher-drill") {{
                requirements(Category.production, with(XItems.germanium, 120));
                researchCost = with(XItems.germanium, 100);
                drillTime = 60f * 10f;

                size = 3;
                hasPower = true;
                tier = 2;
                drillEffect = new MultiEffect(Fx.mineImpact, Fx.drillSteam, Fx.mineImpactWave.wrap(Pal.redLight, 40f));
                shake = 4f;
                itemCapacity = 40;

                researchCostMultiplier = 0.5f;

                drillMultipliers.put(XItems.erythrite, 2.5f);
                drillMultipliers.put(XItems.cobaltPhosphate, 2.5f);

                fogRadius = 4;

                consumePower(90f / 60f);
                //consumeLiquid(Liquids.water, 0.2f);
            }};
            wallCrusher = new XWallCrafter("wall-crusher") {{
                requirements(Category.production, with(XItems.cobalt, 30, XItems.germanium, 40));
                researchCost = with(XItems.cobalt, 30, XItems.germanium, 40);
                consumePower(30 / 60f);
                researchCostMultiplier = 1f;
                drillTime = 110f;
                size = 2;
                attribute = Attribute.sand;
                output = Items.sand;
                attribute2 = XAttributes.carbon;
                output2 = XItems.carbon;
                fogRadius = 2;
                ambientSound = Sounds.drillCharge;
                ambientSoundVolume = 0.04f;
            }};
            oreDrill = new XBurstDrill("ore-drill") {{
                requirements(Category.production, with(XItems.cobalt, 80, Items.silicon, 60));
                drillTime = 60f * 6f;
                squareSprite = false;
                size = 2;
                hasPower = true;
                tier = 5;
                drillEffect = new MultiEffect(Fx.mineImpact, Fx.drillSteam, Fx.mineImpactWave.wrap(Pal.redLight, 40f));
                shake = 4f;
                itemCapacity = 10;


                researchCostMultiplier = 2f;
/*
            drillMultipliers.put(XItems.erythrite, 3.5f);
            drillMultipliers.put(XItems.cobaltPhosphate, 3.5f);
 */
                fogRadius = 4;

                consumePower(90f / 60f);
            }};
            superOreDrill = new XBurstDrill("super-ore-drill") {{
                requirements(Category.production, with(Items.tungsten, 40, Items.silicon, 40, Items.surgeAlloy, 30));
                drillTime = 60f * 6f;
                squareSprite = false;
                size = 2;
                hasPower = true;
                tier = 6;
                drillEffect = new MultiEffect(Fx.mineImpact, Fx.drillSteam, Fx.mineImpactWave.wrap(Pal.redLight, 40f));
                shake = 4f;
                itemCapacity = 10;
                hasLiquids = true;
                liquidCapacity = 10f;

                researchCostMultiplier = 2f;

                drillMultipliers.put(Items.tungsten, 2f);
                fogRadius = 4;

                consumePower(90f / 60f);
                consumeLiquids(LiquidStack.with(Liquids.water, 5f / 60));
            }};

            waterExtractionBore = new GenericCrafter("water-extraction-bore") {{
                requirements(Category.production, with(Items.carbide, 15, Items.silicon, 80, Items.tungsten, 110));
                fillsTile = false;
                liquidCapacity = 60f;
                craftTime = 300f;
                outputLiquid = new LiquidStack(Liquids.water, 15f / 60f);
                size = 3;
                hasLiquids = true;
                hasPower = true;
                craftEffect = Fx.none;
                squareSprite = false;
                consumeLiquid(Liquids.ozone, 4 / 60f);
                consumeLiquid(XItems.ammonia, 1 / 60f);
                consumePower(1.5f);
            }};
        }
    }
    public static class Base {
        public static Block coreExplorer, corePathfinder, coreVanguard, coreOverseer;
        public static void load() {
            coreExplorer = new CoreBlock("core-explorer") {
                {

                    this.requirements(Category.effect, ItemStack.with(XItems.germanium, 1000, Items.silicon, 500, XItems.cobalt, 800));
                    this.isFirstTier = true;
                    this.squareSprite = false;
                    this.unitType = XUnitTypes.explorer;
                    this.health = 4500;
                    this.itemCapacity = 3000;
                    this.size = 3;
                    this.thrusterLength = 8.5F;
                    this.armor = 2f;
                    this.alwaysUnlocked = true;
                    this.incinerateNonBuildable = true;
                    this.unitCapModifier = 1000;
                }
            };
            corePathfinder = new CoreBlock("core-pathfinder") {
                {

                    this.requirements(Category.effect, ItemStack.with(XItems.germanium, 3000, Items.silicon, 2000, XItems.cobalt, 3000));
                    this.isFirstTier = false;
                    this.squareSprite = false;
                    this.unitType = XUnitTypes.explorer;
                    this.health = 8000;
                    this.itemCapacity = 5000;
                    this.size = 4;
                    this.thrusterLength = 11F;
                    this.armor = 4f;
                    this.alwaysUnlocked = false;
                    this.incinerateNonBuildable = true;
                    this.unitCapModifier = 1000;
                }
            };
            coreVanguard = new CoreBlock("core-vanguard") {
                {

                    this.requirements(Category.effect, ItemStack.with(XItems.germanium, 5000, Items.silicon, 4000, XItems.cobalt, 5000, Items.tungsten, 2000));
                    this.isFirstTier = false;
                    this.squareSprite = false;
                    this.unitType = XUnitTypes.explorer;
                    this.health = 12500;
                    this.itemCapacity = 8000;
                    this.size = 5;
                    this.thrusterLength = 15F;
                    this.armor = 6f;
                    this.alwaysUnlocked = false;
                    this.incinerateNonBuildable = true;
                    this.unitCapModifier = 1000;
                }
            };
            coreOverseer = new CoreBlock("core-overseer") {
                {

                    this.requirements(Category.effect, ItemStack.with(XItems.germanium, 8000, Items.silicon, 6000, XItems.cobalt, 8000, Items.tungsten, 3000, XItems.boronCarbide, 1500));
                    this.isFirstTier = false;
                    this.squareSprite = false;
                    this.unitType = XUnitTypes.explorer;
                    this.health = 18000;
                    this.itemCapacity = 12000;
                    this.size = 6;
                    this.thrusterLength = 18F;
                    this.armor = 6f;
                    this.alwaysUnlocked = false;
                    this.incinerateNonBuildable = true;
                    this.unitCapModifier = 1000;
                }
            };
        }
    }
    public static void load(){

        Environment.load();
        Distribution.load();
        Liquid.load();
        Walls.load();
        Turrets.load();
        //BattleTurrets.load();
        Power.load();
        Production.load();
        Drills.load();
        Base.load();
    }
}
