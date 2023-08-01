package xilion.content;

import arc.graphics.Color;
import arc.math.Interp;
import arc.math.Mathf;
import mindustry.content.*;
import mindustry.entities.UnitSorts;
import mindustry.entities.bullet.BasicBulletType;
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
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.ContinuousTurret;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.environment.Floor;
import mindustry.world.blocks.environment.TallBlock;
import mindustry.world.blocks.heat.HeatProducer;
import mindustry.world.blocks.liquid.ArmoredConduit;
import mindustry.world.blocks.liquid.LiquidRouter;
import mindustry.world.blocks.power.*;
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
    public static XUnitFactory merui;
    public static XStaticWall corrodedPhaseWall,concentratedCarbonWall, pureCarbonWall, darkRedStoneWall;
    public static Floor corrodedPhase,concentratedCarbon,pureCarbon, darkRedStone;
    public static TallBlock DarkRedCrystalBlocks;
    public static ItemTurret prevent;
    public static ContinuousTurret focus;
    public static HeatCrafter biogasSynthesizer, haberProcessFacility;
    public static GenericCrafter waterExtractionBore, cobaltRefineryOven;
    public static HeatProducer methaneHeater;
    public static BeamNode wireNode, beamWireNode;
    public static PowerDistributor batteryCell;
    public static ThermalGenerator ventTurbine, thermoelectricGenerator;
    public static SolarGenerator solarCell;
    public static ConsumeGenerator ammoniaTurbineGenerator, biogasCombustionChamber;
    public static XFissionReactor nuclearFissionReactor;

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

        //environment walls:

        concentratedCarbonWall = new XStaticWall("concentrated-carbon-wall"){{
            concentratedCarbon.asFloor().wall = this;
            variants = 4;
            largeVariants = 2;
        }};
        pureCarbonWall = new XStaticWall("pure-carbon-wall"){{
            pureCarbon.asFloor().wall = this;
            variants = 4;
            largeVariants = 2;
        }};
        corrodedPhaseWall = new XStaticWall("corroded-phase-wall"){{
            corrodedPhase.asFloor().wall = this;
            variants = 4;
            largeVariants = 2;
        }};
        darkRedStoneWall = new XStaticWall("dark-red-stone-wall"){{
            darkRedStone.asFloor().wall = this;
            variants = 4;
            largeVariants = 3;
        }};
        DarkRedCrystalBlocks = new TallBlock("dark-red-crystal-blocks"){{
            variants = 2;
            clipSize = 128f;
            shadowAlpha = 0.5f;
            shadowOffset = -2.5f;
        }};

        prevent = new ItemTurret("prevent"){{
            requirements(Category.turret, with( XItems.cobalt, 100, Items.silicon, 100, XItems.germanium, 150));

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

            scaledHealth = 220;
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
            requirements(Category.crafting, with(XItems.germanium, 60, Items.graphite, 50));
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

            consumeItems(with(XItems.erythrite, 6));
            consumePower(5f);
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
            powerProduction = 2f / 4f;
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
