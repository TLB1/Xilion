package xilion.content;

import arc.graphics.Color;
import arc.math.Interp;
import arc.math.Mathf;
import arc.math.geom.Point2;
import mindustry.content.*;
import mindustry.entities.UnitSorts;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.LightningBulletType;
import mindustry.entities.bullet.PointLaserBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.RadialEffect;
import mindustry.entities.effect.WrapEffect;
import mindustry.entities.part.DrawPart;
import mindustry.entities.part.RegionPart;
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
import mindustry.world.blocks.distribution.Duct;
import mindustry.world.blocks.distribution.DuctBridge;
import mindustry.world.blocks.distribution.DuctRouter;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.OreBlock;
import mindustry.world.blocks.environment.SteamVent;
import mindustry.world.blocks.environment.TallBlock;
import mindustry.world.blocks.heat.HeatProducer;
import mindustry.world.blocks.liquid.ArmoredConduit;
import mindustry.world.blocks.liquid.LiquidRouter;
import mindustry.world.blocks.power.*;
import mindustry.world.blocks.production.BeamDrill;
import mindustry.world.blocks.production.BurstDrill;
import mindustry.world.blocks.production.GenericCrafter;
import mindustry.world.blocks.production.HeatCrafter;
import mindustry.world.consumers.ConsumeLiquid;
import mindustry.world.draw.*;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Env;
import xilion.blockTypes.*;
import xilion.entities.XMirrorShootAlternate;

import static mindustry.type.ItemStack.with;

public class XBlocks {

    public static Block plasmaCollector, wallCrusher, siliconOven, pipe, pipeRouter,pipeBridge, regularity;
    public static XUnitFactory merui;
    public static XStaticWall corrodedPhaseWall,concentratedCarbonWall, pureCarbonWall, darkRedStoneWall, erythriteStoneWall, pinkstoneWall;
    public static OreBlock germaniumWallOre;
    public static Floor corrodedPhase,concentratedCarbon,pureCarbon, darkRedStone, erythriteStone, cobaltPhosphateStone, pinkstone, darkPinkstone;
    public static SteamVent corrodedPhaseVent, darkRedStoneVent;
    public static TallBlock DarkRedCrystalBlocks, erythriteSpikes, corrodedPhaseSpikes;
    public static ItemTurret prevent;
    public static PowerTurret shock;
    public static ContinuousTurret focus;
    public static HeatCrafter biogasSynthesizer, haberProcessFacility;
    public static GenericCrafter waterExtractionBore, cobaltRefineryOven, phosphateDebonder;
    public static HeatProducer methaneHeater;
    public static BeamNode wireNode, beamWireNode;
    public static PowerDistributor batteryCell;
    public static ThermalGenerator ventTurbine, thermoelectricGenerator;
    public static SolarGenerator solarCell;
    public static ConsumeGenerator ammoniaTurbineGenerator, biogasCombustionChamber;
    public static XFissionReactor nuclearFissionReactor;

    public static BurstDrill crusherDrill;

    public static Wall germaniumWall, germaniumWallLarge, germaniumWallHuge, cobaltWall, cobaltWallLarge, cobaltWallHuge,
            reinforcedTungstenWall, reinforcedTungstenWallLarge, reinforcedTungstenWallHuge, reinforcedCarbideWall, reinforcedCarbideWallLarge, reinforcedCarbideWallHuge,
            boronWall,boronWallLarge, boronWallHuge, boronCarbideWall, boronCarbideWallLarge, boronCarbideWallHuge, virusWall;

    public static ArmoredConduit basicConduit;
    public static LiquidRouter basicLiquidRouter;
    public static XLiquidDistributor liquidDistributor;
    public static XLiquidTunnel liquidTunnel;

    public static XLiquidCollector liquidCollector;

    public void load(){
        basicConduit  = new ArmoredConduit("basic-conduit"){{
            requirements(Category.liquid, with(XItems.germanium, 2));
            botColor = Pal.darkestMetal;
            leaks = true;
            liquidCapacity = 20f;
            liquidPressure = 1.03f;
            health = 250;
            researchCostMultiplier = 3;
            underBullets = true;
        }};
        basicLiquidRouter = new LiquidRouter("basic-liquid-router"){{
            requirements(Category.liquid, with(XItems.cobalt, 2, XItems.germanium, 4));
            liquidCapacity = 30f;
            liquidPadding = 3f/4f;
            researchCostMultiplier = 3;
            underBullets = true;
            solid = false;
            squareSprite = false;
        }};
        liquidDistributor = new XLiquidDistributor("liquid-distributor"){{
            requirements(Category.liquid, with(Items.tungsten,2, XItems.germanium, 4));
            liquidCapacity = 30f;
            underBullets = true;
            solid = false;
            squareSprite =false;
        }};
        liquidCollector = new XLiquidCollector("liquid-collector"){{
            requirements(Category.liquid, with(Items.tungsten,2, XItems.germanium, 4));
            liquidCapacity = 30f;
            underBullets = true;
            solid = false;
            squareSprite =false;
        }};
        liquidTunnel = new XLiquidTunnel("liquid-tunnel"){{
            requirements(Category.liquid, with(Items.tungsten, 8, XItems.germanium, 15));
            range = 5;
        }};
        merui = new XUnitFactory("merui-fabricator"){{
            requirements(Category.units, with(Items.silicon, 200, Items.graphite, 300, Items.tungsten, 60));
            size = 3;
            configurable = false;
            plans.add(new UnitPlan(UnitTypes.merui, 60f * 40f, with(XItems.germanium, 50, Items.silicon, 70)));
            regionSuffix = "-dark";
            fogRadius = 3;
            researchCostMultiplier = 0.65f;
            consumePower(2f);
        }};
        //environment
        concentratedCarbon = new Floor("concentrated-carbon"){{
            variants = 5;
        }};
        pureCarbon = new Floor("pure-carbon"){{
            variants = 5;
        }};
        corrodedPhase =  new Floor("corroded-phase"){{
            variants = 5;
        }};
        darkRedStone = new Floor("dark-red-stone"){{
            variants = 5;
        }};
        darkPinkstone = new Floor("dark-pinkstone"){{
            variants = 4;
        }};
       pinkstone = new Floor("pinkstone"){{
            variants = 5;
        }};
        erythriteStone = new Floor("erythrite-stone"){{
            variants = 5;
            itemDrop = XItems.erythrite;
            playerUnmineable = true;

        }};
        cobaltPhosphateStone = new Floor("cobalt-phosphate-stone"){{
            variants = 5;
            itemDrop = XItems.cobaltPhosphate;
            playerUnmineable = true;
        }};
        corrodedPhaseVent   = new XSteamVentSmall("corroded-phase-vent"){
            {
                parent = blendGroup = corrodedPhase;
                variants = 2;
                attributes.set(Attribute.steam, 1f);
            }};
        darkRedStoneVent = new XSteamVentSmall("dark-red-stone-vent"){{
            parent = blendGroup = darkRedStone;
            variants = 2;
            attributes.set(Attribute.steam, 1f);
        }};
        //environment walls:

        concentratedCarbonWall = new XStaticWall("concentrated-carbon-wall"){{
            concentratedCarbon.asFloor().wall = this;
            variants = 4;
            largeVariants = 2;
            attributes.set(XAttributes.carbon, 0.6f);
        }};
        pureCarbonWall = new XStaticWall("pure-carbon-wall"){{
            pureCarbon.asFloor().wall = this;
            variants = 4;
            largeVariants = 2;
            attributes.set(XAttributes.carbon, 1f);
        }};
        corrodedPhaseWall = new XStaticWall("corroded-phase-wall"){{
            corrodedPhase.asFloor().wall = this;
            variants = 4;
            largeVariants = 2;
            attributes.set(Attribute.sand, 1.2f);
        }};
        darkRedStoneWall = new XStaticWall("dark-red-stone-wall"){{
            darkRedStone.asFloor().wall = this;
            variants = 4;
            largeVariants = 3;
            attributes.set(Attribute.sand, 1.6f);
        }};
        pinkstoneWall = new XStaticWall("pinkstone-wall"){{
           pinkstone.asFloor().wall = this;
            variants = 3;
            largeVariants = 2;
            attributes.set(Attribute.sand, 0.6f);
        }};
        erythriteStoneWall = new XStaticWall("erythrite-stone-wall"){{
            erythriteStone.asFloor().wall = this;
            variants = 4;
            largeVariants = 2;
        }};
        DarkRedCrystalBlocks = new TallBlock("dark-red-crystal-blocks"){{
            variants = 2;
            clipSize = 128f;
            shadowAlpha = 0.5f;
            shadowOffset = -2.5f;
        }};
        corrodedPhaseSpikes = new TallBlock("corroded-phase-spikes"){{
            variants = 3;
            clipSize = 96f;
            shadowAlpha = 0.5f;
            shadowOffset = -2.5f;
        }};
        erythriteSpikes = new TallBlock("erythrite-spikes"){{
            variants = 3;
            clipSize = 96f;
            shadowAlpha = 0.5f;
            shadowOffset = -2.5f;
        }};
        germaniumWallOre = new OreBlock("ore-wall-germanium", XItems.germanium){{
            wallOre = true;
            variants = 3;
        }};
        plasmaCollector = new BeamDrill("plasma-collector"){{
            requirements(Category.production, with(XItems.germanium, 50f));
            consumePower(30/60f);

            drillTime = 120f;
            tier = 3;
            size = 2;
            range = 4;
            fogRadius = 3;

            consumeLiquid(XItems.ammonia, 0.1f / 60f).boost();
        }};
        crusherDrill = new BurstDrill("crusher-drill"){{
            requirements(Category.production, with(XItems.germanium, 120));
            drillTime = 60f * 10f;

            size = 3;
            hasPower = true;
            tier = 2;
            drillEffect = new MultiEffect(Fx.mineImpact, Fx.drillSteam, Fx.mineImpactWave.wrap(Pal.redLight, 40f));
            shake = 4f;
            itemCapacity = 40;

            researchCostMultiplier = 0.5f;

            drillMultipliers.put(XItems.erythrite, 3.5f);
            drillMultipliers.put(XItems.cobaltPhosphate, 3.5f);

            fogRadius = 4;

            consumePower(90f / 60f);
            //consumeLiquid(Liquids.water, 0.2f);
        }};
        wallCrusher = new XWallCrafter("wall-crusher"){{
            requirements(Category.production, with(XItems.cobalt, 30, XItems.germanium, 40));
            consumePower(30 / 60f);

            drillTime = 110f;
            size = 2;
            attribute = Attribute.sand;
            output = Items.sand;
            attribute2 = XAttributes.carbon;
            output2 = XItems.carbon;
            fogRadius = 2;
            ambientSound = Sounds.drill;
            ambientSoundVolume = 0.04f;
        }};
        pipe = new XPipe("pipe"){{
            requirements(Category.distribution, with(XItems.germanium, 1));
            health = 90;
            speed = 3.75f;
        }};
        pipeRouter = new DuctRouter("pipe-router"){{
            requirements(Category.distribution, with(XItems.germanium, 8));
            health = 90;
            speed = 3.75f;
            regionRotated1 = 1;
            solid = false;
        }};
        pipeBridge = new DuctBridge("pipe-bridge"){{
            requirements(Category.distribution, with(XItems.germanium, 15));
            health = 90;
            speed = 3.75f;
            range = 5;
            buildCostMultiplier = 2f;
            researchCostMultiplier = 0.3f;
        }};
        shock = new PowerTurret("shock"){{
            size = 2;
            drawer = new DrawTurret("reinforced-"){{

            }};
            requirements(Category.turret, with(Items.silicon, 60, XItems.germanium, 100, XItems.cobalt, 60));
            buildCostMultiplier = 0.5f;
            shootType = new LightningBulletType(){{
                damage = 30;
                lightningLength = 25;
                collidesAir = false;
                ammoMultiplier = 1f;


                //for visual stats only.
                buildingDamageMultiplier = 0.25f;

                lightningType = new BulletType(0.0001f, 0f){{
                    lifetime = Fx.lightning.lifetime;
                    hitEffect = Fx.hitLancer;
                    despawnEffect = Fx.none;
                    status = StatusEffects.shocked;
                    statusDuration = 10f;
                    hittable = false;
                    lightColor = Color.white;
                    collidesAir = false;
                    buildingDamageMultiplier = 0.25f;
                }};
            }};
            reload = 25f;
            shootCone = 10f;
            rotateSpeed = 8f;
            targetAir = false;
            range = 144f;
            shootEffect = Fx.lightningShoot;
            heatColor = Color.red;
            recoil = 1f;
            health = 800;
            shootSound = Sounds.spark;
            consumePower(4f);
            coolant = consumeCoolant(0.1f);
        }};
        regularity = new ItemTurret("regularity"){{
            requirements(Category.turret, with(XItems.germanium, 250, XItems.cobalt, 200, Items.silicon, 180));
            buildCostMultiplier = 0.5f;
            ammo(
                    XItems.germanium,  new BasicBulletType(5.5f, 45){{
                        width = 10f;
                        height = 20f;
                        lifetime = 32f;
                        ammoMultiplier = 2;
                        targetGround = false;
                    }},
                    Items.silicon, new BasicBulletType(5.5f, 40){{
                        width = 10f;
                        height = 20f;
                        reloadMultiplier = 2f;
                        ammoMultiplier = 2;
                        lifetime = 32f;
                        targetGround = false;
                    }}
            );

            drawer = new DrawTurret("reinforced-"){{
            }};

            size = 3;
            range = 176f;
            reload = 10f;
            ammoEjectBack = 3f;
            recoil = 2f;
            shake = 1f;

            targetGround = false;
            ammoUseEffect = Fx.casing2;
            scaledHealth = 260;
            shootSound = Sounds.shootBig;
            coolant = consume(new ConsumeLiquid(Liquids.hydrogen, 5f / 60f));
            consumeLiquid(Liquids.water, 10f / 60f);
            squareSprite = false;
        }};
        prevent = new ItemTurret("prevent"){{
            requirements(Category.turret, with(Items.tungsten, 200, XItems.cobalt, 220, Items.silicon, 200));
            buildCostMultiplier = 0.5f;

            ammo(Items.tungsten, new BasicBulletType(){{

                damage = 40;
                speed = 5f;
                width = height = 12;
                shrinkY = 0.3f;
                backSprite = "large-bomb-back";
                sprite = "mine-bullet";
                velocityRnd = 0.11f;
                targetGround =true;
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

            reload = 20f;
            shootY = 9.5f;

            rotateSpeed = 5f;
            shootCone = 30f;
            consumeAmmoOnce = true;
            shootSound = Sounds.shootBig;

            drawer = new DrawTurret("reinforced-"){{
                parts.add(
                        new RegionPart("-mid"){{
                            under = true;
                            moveY = -1.5f;
                            progress = PartProgress.recoil;
                            heatProgress = PartProgress.recoil.add(0.25f).min(PartProgress.warmup);
                            //heatColor = Color.sky.cpy().a(0.9f);
                        }},
                        new RegionPart("-outer"){{
                            heatProgress = PartProgress.warmup;
                            heatColor = Color.sky.cpy().a(0.9f);
                            recoilIndex = 1;
                            progress = PartProgress.recoil;
                            heatProgress = PartProgress.recoil.add(0.25f).min(PartProgress.warmup);
                            under = true;
                            moveY = -1.5f;
                        }});
            }};

            shoot = new XMirrorShootAlternate(){{
                spread = 3.5f;
                shots = 4;
                barrels = 2;
                shotDelay = 10f;
            }};

            targetGround = true;
            inaccuracy = 8f;

            shootWarmupSpeed = 0.08f;

            outlineColor = Pal.darkOutline;

            scaledHealth = 280;
            range = 120f;
            size = 3;

            coolant = consume(new ConsumeLiquid(Liquids.water, 10f / 60f));
            coolantMultiplier = 2f;

            limitRange(-4f);
        }};
        focus =  new ContinuousTurret("lustre"){{
            requirements(Category.turret, with(Items.silicon, 250, Items.graphite, 200, Items.oxide, 50, Items.carbide, 90));

            shootType = new PointLaserBulletType(){{
                damage = 200f;
                buildingDamageMultiplier = 0.3f;
                hitColor = Color.valueOf("fda981");
            }};

            drawer = new DrawTurret("reinforced-"){{
                var heatp = DrawPart.PartProgress.warmup.blend(p -> Mathf.absin(2f, 1f) * p.warmup, 0.2f);

                parts.add(new RegionPart("-blade"){{
                              progress = PartProgress.warmup;
                              heatProgress = PartProgress.warmup;
                              heatColor = Color.valueOf("ff6214");
                              mirror = true;
                              under = true;
                              moveX = 2f;
                              moveRot = -7f;
                              moves.add(new PartMove(PartProgress.warmup, 0f, -2f, 3f));
                          }},
                        new RegionPart("-inner"){{
                            heatProgress = heatp;
                            progress = PartProgress.warmup;
                            heatColor = Color.valueOf("ff6214");
                            mirror = true;
                            under = false;
                            moveX = 2f;
                            moveY = -8f;
                        }},
                        new RegionPart("-mid"){{
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
            loopSound = Sounds.laserbeam;

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

        biogasSynthesizer = new HeatCrafter("biogas-synthesizer"){{
            requirements(Category.crafting, with(Items.tungsten, 100, Items.silicon, 80, XItems.germanium, 120));

            heatRequirement = 4f;

            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(XItems.methane),
                    new DrawParticles(){{
                        color = Color.valueOf("e69d62");
                        alpha = 0.7f;
                        particleSize = 2.5f;
                        particles = 20;
                        particleRad = 9f;
                        particleLife = 200f;
                        reverse = true;
                        particleSizeInterp = Interp.one;
                    }}, new DrawDefault(), new DrawHeatInput(), new DrawHeatRegion("-heat-top"));

            size = 3;

            ambientSound = Sounds.extractLoop;
            ambientSoundVolume = 0.08f;

            liquidCapacity = 80f;
            outputLiquid = new LiquidStack(XItems.methane, 1f / 60f);

            //consumeLiquids(LiquidStack.with(Liquids.hydrogen, 3f / 60f, Liquids.nitrogen, 2f / 60f));
            consumeLiquid(Liquids.hydrogen, 4f / 60f);
            consumeItem(Items.graphite);
            consumePower(2f);
        }};

        haberProcessFacility = new HeatCrafter("haber-process-facility"){{
            requirements(Category.crafting, with(Items.thorium, 25, Items.silicon, 80, XItems.germanium, 160));

            heatRequirement = 6f;

            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawLiquidTile(XItems.ammonia),
                    new DrawParticles(){{
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

            ambientSound = Sounds.extractLoop;
            ambientSoundVolume = 0.08f;

            liquidCapacity = 80f;
            outputLiquid = new LiquidStack(XItems.ammonia, 2f / 60f);

            //consumeLiquids(LiquidStack.with(Liquids.hydrogen, 3f / 60f, Liquids.nitrogen, 2f / 60f));
            consumeLiquid(Liquids.hydrogen, 6f / 60f);
            consumeLiquid(Liquids.nitrogen, 2f / 60f);
            consumePower(2f);
        }};

        waterExtractionBore = new GenericCrafter("water-extraction-bore"){{
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
            consumeLiquid(Liquids.ozone,4/60f);
            consumeLiquid(XItems.ammonia,1/60f);
            consumePower(1.5f);
        }};
        cobaltRefineryOven = new GenericCrafter("cobalt-refinery-oven"){{
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
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.12f;
            squareSprite = false;
            consumeItems(with(XItems.erythrite, 6));
            consumePower(4f);
        }};
       siliconOven = new GenericCrafter("silicon-oven"){{
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
            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.12f;
            squareSprite =false;
            consumeItems(with(XItems.carbon, 2, Items.sand, 5));
            consumePower(5f);
        }};
        phosphateDebonder = new GenericCrafter("phosphate-debonder"){{
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
                    new DrawGlowRegion(){{
                        alpha = 0.7f;
                        color = Color.valueOf("c4bdf3");
                        glowIntensity = 0.3f;
                        glowScale = 6f;
                    }}
            );
            ambientSound = Sounds.electricHum;
            ambientSoundVolume = 0.1f;
            squareSprite = false;
            regionRotated1 = 3;
            outputLiquids = LiquidStack.with(Liquids.ozone, 4f / 60, XItems.phosphorus, 1f / 60);
            outputItem = new ItemStack(XItems.cobalt, 1);
            liquidOutputDirections = new int[]{1, 3};
            squareSprite = false;
        }};
        methaneHeater = new HeatProducer("methane-heater"){{
            requirements(Category.crafting, with(Items.oxide, 30, Items.tungsten, 30, XItems.germanium, 30));

            drawer = new DrawMulti(new DrawDefault(), new DrawHeatOutput());
            size = 2;
            heatOutput = 8f;
            craftTime = 60f;
            ambientSound = Sounds.flux;
          consumeLiquid(XItems.methane, 1f/180f);
        }};

        wireNode = new BeamNode("wire-node"){{
            requirements(Category.power, with(XItems.germanium, 4));
            consumesPower = outputsPower = true;
            health = 70;
            range = 3;
            fogRadius = 1;
            researchCost = with(XItems.germanium, 5);
            consumePowerBuffered(100f);
        }};
         beamWireNode = new BeamNode("beam-wire-node"){{
            requirements(Category.power, with(XItems.cobalt, 12, XItems.germanium, 4));
            consumesPower = outputsPower = true;
            health = 100;
            range = 15;
            fogRadius = 1;
            consumePowerBuffered(1000f);
        }};
        batteryCell = new PowerDistributor("battery-cell"){{
            requirements(Category.power, with(XItems.germanium, 20, XItems.cobalt, 60, Items.silicon, 15));
            size = 2;
            consumesPower = outputsPower = true;
            fillsTile = false;
            consumePowerBuffered(20000f);
        }};
        ventTurbine = new ThermalGenerator("vent-turbine"){{
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
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.04f;

            drawer = new DrawMulti(new DrawDefault(), new DrawBlurSpin("-rotator", 1.1f * 9f){{
                blurThresh = 0.01f;
            }});

            hasLiquids = true;
            outputLiquid = new LiquidStack(Liquids.water, 5f / 60f / 4f);
            liquidCapacity = 10f;
            fogRadius = 3;
            researchCost = with(XItems.germanium, 15);
        }};
        solarCell = new SolarGenerator("solar-cell"){{
            requirements(Category.power, with(XItems.cobalt, 40, Items.silicon, 60));
            size = 2;
            powerProduction = 0.6f;
            fillsTile = false;
        }};
        thermoelectricGenerator =  new ThermalGenerator("thermoelectric-generator"){{
            requirements(Category.power, with(XItems.cobalt, 50 , Items.silicon, 45, Items.tungsten, 60));
            powerProduction = 2f;
            generateEffect = Fx.redgeneratespark;
            effectChance = 0.011f;
            size = 2;
            floating = false;
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.07f;
            drawer = new DrawMulti(new DrawDefault(), new DrawGlowRegion(){{
                alpha = 1f;
                glowScale = 7f;
                color = Color.valueOf("ff1e1e");
            }});
        }};

        ammoniaTurbineGenerator = new ConsumeGenerator("ammonia-turbine-generator"){{
            squareSprite = false;
            fillsTile = false;
            requirements(Category.power, with(Items.tungsten, 70, XItems.cobalt, 100, Items.silicon, 50));
            consumeLiquid(XItems.ammonia, 2f/60f);
            powerProduction = 16f;
            generateEffect = Fx.turbinegenerate;
            effectChance = 0.06f;
            size = 3;
            ambientSound = Sounds.hum;
            ambientSoundVolume = 0.06f;
            drawer = new DrawMulti(new DrawDefault(), new DrawBlurSpin("-rotator", 0.9f * 9f){{
                blurThresh = 0.01f;
            }});

            hasLiquids = true;
            outputLiquid = new LiquidStack(Liquids.hydrogen, 2f / 60f );
            liquidCapacity = 20f;
            fogRadius = 3;
        }};
        biogasCombustionChamber = new ConsumeGenerator("biogas-combustion-chamber"){{
            squareSprite = false;
            fillsTile = false;
            requirements(Category.power, with(XItems.cobalt, 180f, Items.tungsten, 200f, Items.oxide, 50f, Items.silicon, 65));
            powerProduction = 50f;
            consumeLiquid(XItems.methane, 10f / 180f);
            size = 4;
            drawer = new DrawMulti(new DrawRegion("-bottom"), new DrawPistons(){{
                suffix = "-large-piston";
                angleOffset = 45f;
                lenOffset = -0.2f;
                sinMag = 2f;
                sinScl = 4f;
            }}, new DrawPistons(){{
                suffix = "-small-piston";
                angleOffset = 0f;
                lenOffset = 0.1f;
                sinMag = 2f;
                sinScl = 2f;
            }}, new DrawLiquidTile(XItems.methane, 1.5f), new DrawDefault(), new DrawGlowRegion(){{
                alpha = 1f;
                glowScale = 7f;
                color = Color.valueOf("e6723cA9");
            }});
            generateEffect = Fx.none;

            liquidCapacity =60f;

            ambientSound = Sounds.smelter;
            ambientSoundVolume = 0.12f;

        }};

        nuclearFissionReactor = new XFissionReactor("nuclear-fission-reactor"){{
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
            explodeSound = Sounds.largeExplosion;

            powerProduction = 130f;

            ambientSound = Sounds.bioLoop;
            ambientSoundVolume = 0.2f;

            explosionPuddles = 80;
            explosionPuddleRange = 8 * 6f;
            explosionPuddleLiquid = Liquids.ozone;
            explosionPuddleAmount = 200f;
            explosionMinWarmup = 0.5f;

            consumeEffect =new MultiEffect( new RadialEffect(XFx.thoriumFission, 4, 90f, 46f / 4f){{
                rotationOffset = 45f;
            }},
             XFx.thoriumFission);

            drawer = new DrawMulti(
                    new DrawRegion("-bottom"),
                    new DrawLiquidTile(Liquids.ozone, 8f),
                    new DrawCircles(){{
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
                    new DrawGlowRegion("-glow"){{
                        color = Color.valueOf("ffffff");
                        alpha = 0.7f;
                    }}, new DrawGlowRegion("-active-glow"){{
                        color = Pal.thoriumPink;
                        alpha = 0.7f;
                    }}

            );
        }};

        germaniumWall = new Wall("germanium-wall"){{
            requirements(Category.defense, with(XItems.germanium, 4));
            health = 110 * 4;
            armor = 2f;
            buildCostMultiplier = 8f;
        }};
        germaniumWallLarge = new Wall("germanium-wall-large"){{
            requirements(Category.defense, with(XItems.germanium, 16));
            health = 110 * 4 * 4;
            armor = 2f;
            buildCostMultiplier = 8f;
            size = 2;
        }};
        germaniumWallHuge = new Wall("germanium-wall-huge"){{
            requirements(Category.defense, with(XItems.germanium, 36));
            health = 110 * 4 * 9;
            armor = 2f;
            buildCostMultiplier = 8f;
            size = 3;
        }};

        cobaltWall = new Wall("cobalt-wall"){{
            requirements(Category.defense, with(XItems.cobalt, 4));
            health = 150 * 4;
            armor = 3f;
            buildCostMultiplier = 10f;
        }};
        cobaltWallLarge = new Wall("cobalt-wall-large"){{
            requirements(Category.defense, with(XItems.cobalt, 16));
            health = 150 * 4 * 4;
            armor = 3f;
            buildCostMultiplier = 10f;
            size = 2;
        }};
        cobaltWallHuge = new Wall("cobalt-wall-huge"){{
            requirements(Category.defense, with(XItems.cobalt, 36));
            health = 150 * 4 * 9;
            armor = 3f;
            buildCostMultiplier = 10f;
            size = 3;
        }};

        reinforcedTungstenWall = new Wall("reinforced-tungsten-wall"){{
            requirements(Category.defense, with(Items.tungsten, 4,Items.silicon, 2));
            health = 180 * 4;
            armor = 14f;
            buildCostMultiplier = 10f;
        }};
        reinforcedTungstenWallLarge = new Wall("reinforced-tungsten-wall-large"){{
            requirements(Category.defense, with(Items.tungsten, 16,Items.silicon, 8));
            health = 180 * 4 * 4;
            armor = 14f;
            buildCostMultiplier = 10f;
            size = 2;
        }};
        reinforcedTungstenWallHuge = new Wall("reinforced-tungsten-wall-huge"){{
            requirements(Category.defense, with(Items.tungsten, 36,Items.silicon, 18));
            health = 180 * 4 * 9;
            armor = 14f;
            buildCostMultiplier = 10f;
            size = 3;
        }};

        reinforcedCarbideWall = new Wall("reinforced-carbide-wall"){{
            requirements(Category.defense, with(Items.carbide, 4,Items.silicon, 2));
            health = 240 * 4;
            armor = 16f;
            buildCostMultiplier = 10f;
        }};
        reinforcedCarbideWallLarge = new Wall("reinforced-carbide-wall-large"){{
            requirements(Category.defense, with(Items.carbide, 16,Items.silicon, 8));
            health = 240 * 4 * 4;
            armor = 16f;
            buildCostMultiplier = 10f;
            size = 2;
        }};
        reinforcedCarbideWallHuge = new Wall("reinforced-carbide-wall-huge"){{
            requirements(Category.defense, with(Items.carbide, 36,Items.silicon, 18));
            health = 240 * 4 * 9;
            armor = 16f;
            buildCostMultiplier = 10f;
            size = 3;
        }};

        boronWall = new Wall("boron-wall"){{
            requirements(Category.defense, with(XItems.boron, 6));
            health = 230 * 4;
            armor = 15f;
            buildCostMultiplier = 10f;
        }};
        boronWallLarge = new Wall("boron-wall-large"){{
            requirements(Category.defense, with(XItems.boron, 24));
            health = 230 * 4 * 4;
            armor = 15f;
            buildCostMultiplier = 10f;
            size = 2;
        }};
        boronWallHuge = new Wall("boron-wall-huge"){{
            requirements(Category.defense, with(XItems.boron, 54));
            health = 230 * 4 * 9;
            armor = 18f;
            buildCostMultiplier = 10f;
            size = 3;
        }};
        boronCarbideWall = new Wall("boron-carbide-wall"){{
            requirements(Category.defense, with(XItems.boronCarbide, 4,Items.silicon, 2));
            health = 300 * 4;
            armor = 18f;
            buildCostMultiplier = 10f;
        }};
        boronCarbideWallLarge = new Wall("boron-carbide-wall-large"){{
            requirements(Category.defense, with(XItems.boronCarbide, 16,Items.silicon, 8));
            health = 300 * 4 * 4;
            armor = 18f;
            buildCostMultiplier = 10f;
            size = 2;
        }};
        boronCarbideWallHuge = new Wall("boron-carbide-wall-huge"){{
            requirements(Category.defense, with(XItems.boronCarbide, 36,Items.silicon, 18));
            health = 300 * 4 * 9;
            armor = 18f;
            buildCostMultiplier = 10f;
            size = 3;
        }};

        virusWall = new XVirusWall("virus-wall"){{
            requirements(Category.defense, with(Items.thorium, 20));
            health = 200;
        }
        };
    }
}
