package xilion.content;

import arc.func.Prov;
import arc.graphics.Color;
import arc.graphics.g2d.Lines;
import arc.math.Interp;
import arc.math.Mathf;
import mindustry.ai.UnitCommand;
import mindustry.ai.types.BuilderAI;
import mindustry.ai.types.FlyingAI;
import mindustry.ai.types.FlyingFollowAI;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.Effect;
import mindustry.entities.abilities.MoveEffectAbility;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.part.DrawPart;
import mindustry.entities.part.HoverPart;
import mindustry.entities.part.RegionPart;
import mindustry.entities.part.ShapePart;
import mindustry.entities.pattern.ShootAlternate;
import mindustry.entities.pattern.ShootHelix;
import mindustry.entities.pattern.ShootPattern;
import mindustry.gen.*;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.type.unit.ErekirUnitType;
import mindustry.type.weapons.RepairBeamWeapon;
import xilion.XilionJavaMod;
import xilion.activeAbilities.TurboAA;
import xilion.entities.LegUnitFaceBuildingAI;
import xilion.entities.XErekirAbilityUnitType;
import xilion.entities.abilities.XDashAbility;
import xilion.entities.abilities.XSpeedBuffFieldAbility;
import xilion.util.XActiveAbility;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.lineAngle;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;
import static mindustry.Vars.tilePayload;
import static mindustry.content.Fx.shootSmall;
import static mindustry.content.Fx.shootSmallSmoke;
import static mindustry.gen.Sounds.blaster;

public class XUnitTypes {
    public static  UnitType
/*TODO: add new units:
    supersonic, hypersonic --> speed-buff to allies
    hypersonic beam that slows enemies and can teleport
*/
    // Erekir base units
     salticidae, astacoidea,

    //Attack class units:
    attack, strike, assault, battery, violence,
    snake,
    explorer,
    //Quick class units:
    quick, dash, leap, supersonic,hypersonic, leaptest,
    acari, blastbeetle, sanatick,

           blaze, ember, hellhound, cerberus,aura,spectra, annihilate, etherium,
                   ship, bug;


    public XUnitTypes() {

        EntityMapping.nameMap.put(XilionJavaMod.name("attack"), EntityMapping.idMap[1]);
        EntityMapping.nameMap.put(XilionJavaMod.name("strike"), EntityMapping.idMap[2]);

        EntityMapping.nameMap.put(XilionJavaMod.name("quick"), EntityMapping.idMap[6]);
        EntityMapping.nameMap.put(XilionJavaMod.name("dash"), EntityMapping.idMap[7]);
        EntityMapping.nameMap.put(XilionJavaMod.name("leap"), EntityMapping.idMap[8]);
        EntityMapping.nameMap.put(XilionJavaMod.name("supersonic"), EntityMapping.idMap[9]);
        EntityMapping.nameMap.put(XilionJavaMod.name("acari"), EntityMapping.idMap[11]);
    }



    public void load() {
        explorer = new XErekirAbilityUnitType("explorer") {
            {
                activeAbility = new XActiveAbility(new TurboAA("boost"));
                constructor = UnitEntity::create;
                this.coreUnitDock = true;
                this.controller = (u) -> {
                    return new BuilderAI(true, 400f);
                };
                this.isEnemy = false;
                this.envDisabled = 0;
                this.targetPriority = -2.0F;
                this.lowAltitude = false;
                this.mineWalls = true;
                this.mineFloor = false;
                this.mineHardnessScaling = false;
                this.flying = true;
                this.mineSpeed = 8.0F;
                this.mineTier = 3;
                this.buildSpeed = 1.2F;
                this.drag = 0.08F;
                this.speed = 4.5F;
                this.rotateSpeed = 7.0F;
                this.accel = 0.09F;
                this.itemCapacity = 60;
                this.health = 400.0F;
                this.armor = 0.0F;
                this.hitSize = 10.0F;
                this.engineSize = 0.0F;
                this.payloadCapacity = 256.0F;
                this.pickupUnits = false;
                this.vulnerableWithPayloads = true;
                this.fogRadius = 0.0F;
                this.targetable = false;
                this.hittable = false;
                engines.add(new UnitEngine(0F, -5F, 3.9F, 45F));

                this.weapons.add(new RepairBeamWeapon() {
                    {
                        this.widthSinMag = 0.11F;
                        this.reload = 20.0F;
                        this.x = 0.0F;
                        this.y = 6.5F;
                        this.rotate = false;
                        this.shootY = 0.0F;
                        this.beamWidth = 0.7F;
                        this.repairSpeed = 3.1F;
                        this.fractionRepairSpeed = 0.06F;
                        aimDst = 0.0F;
                        this.shootCone = 15.0F;
                        this.mirror = false;
                        this.targetUnits = false;
                        this.targetBuildings = true;
                        this.autoTarget = false;
                        this.controllable = true;
                        this.laserColor = Pal.heal;
                        this.healColor = Pal.heal;
                        this.bullet = new BulletType() {
                            {
                                this.maxRange = 45.0F;
                            }
                        };
                    }
                });
            }
        };
        ship = new ErekirUnitType("ship"){{
            constructor = (Prov<Unit>) UnitEntity::create;
            flying = true;
            speed  = engineSize = 1f;
            rotateSpeed = 10f;
            itemCapacity = 0;
            health = 100f;
            hitSize = 6f;
        }};
        bug = new ErekirUnitType("bug"){{
            constructor = (Prov<Unit>) LegsUnit::create;
            speed = 0.7f;
            drag = 0.1f;
            hitSize = 6f;
            rotateSpeed = 3f;
            health = 300f;
            armor = 0f;
            legStraightness = 0.4f;
            stepShake = 0f;
            legCount = 4;
            legLength = 8f;
            lockLegBase = false;
            legContinuousMove = true;
            legExtension = -1f;
            legBaseOffset = 3f;
            legMaxLength = 1.1f;
            legMinLength = 0.2f;
            legLengthScl = 0.96f;
            legForwardScl = 1.1f;
            legGroupSize = 2;
            rippleScale = 0.2f;

            legMoveSpace = 1f;
            allowLegStep = true;
            hovering = true;
            legPhysicsLayer = false;

            shadowElevation = 0.1f;
            groundLayer = Layer.legUnit - 1f;
            targetAir = false;
            researchCostMultiplier = 0f;
        }};
        /*
        leaptest = new XErekirAbilityUnitType("fs3"){{
            activeAbility = new XActiveAbility(new TurboAA("turbo"));

            constructor = (Prov<Unit>) UnitEntity::create;
            aiController = FlyingAI::new;

            health = 570;
            this.
                    armor =2;
            speed = 4.0f;
            hitSize = 16;

            itemCapacity = 40;
            //isCounted = false;
            buildSpeed =  1.6f;
            faceTarget = true;
            rotateSpeed = 8f;
            flying = true;
            //rotateShooting = true;
            engineSize = 5.0f;
            abilities.add(new XDashAbility(27, 360));
            engineLayer = Layer.flyingUnit-0.1f;




            weapons.add(
                    new Weapon(XilionJavaMod.name("leap-weapon-mount")){{
                        shootSound = Sounds.lasershoot;
                        // firstShotDelay = 10;
                        shoot = new ShootPattern(){{
                            shots = 1;
                            shotDelay = 0f;
                            firstShotDelay =10f;
                        }};

                        reload = 10f;
                        x = 6;
                        y = 0;
                        rotate = true;

                        mirror = true;
                        bullet = XBullets.QuickTypeBullet;
                        bullet.lifetime = 35;
                    }});

        }};
        */
        blaze = new ErekirUnitType("blaze"){
            {
                constructor = (Prov<Unit>) ElevationMoveUnit::create;
                hovering = true;
                shadowElevation = 0.1f;

                drag = 0.07f;
                speed = 1.2f;
                rotateSpeed = 3f;

                accel = 0.09f;
                health = 800f;
                armor = 5f;
                hitSize = 12f;
                engineOffset = 7f;
                engineSize = 2f;
                itemCapacity = 0;
                useEngineElevation = false;
                researchCostMultiplier = 0f;
                faceTarget = false;
                targetAir = true;

                abilities.add(new MoveEffectAbility(0f, -8f, XColors.ember1, Fx.missileTrailShort, 5f){{
                    teamColor = true;
                }});
                immunities.add(StatusEffects.burning);
                for(float f : new float[]{-4f, 4f}){
                    parts.add(new HoverPart(){{
                        x = 4f;
                        y = f;
                        mirror = true;
                        radius = 8f;
                        phase = 90f;
                        stroke = 2f;
                        layerOffset = -0.001f;
                        color = XColors.ember2;
                    }});
                }

                weapons.add(new Weapon(XilionJavaMod.name("blaze-flamethrower")){{
                    heatColor = Color.valueOf("f9350f");
                    y = 0;
                    x = 0;
                    top = true;
                    layerOffset = 0.001f;
                    rotate = true;
                    rotateSpeed = 2f;
                    shootSound = Sounds.flame;
                    shootY = 5f;
                    reload = 12f;
                    recoil = 1f;
                    mirror = false;
                    ejectEffect = Fx.none;
                    bullet = new BulletType(4.5f, 11f){{
                        ammoMultiplier = 3f;
                        hitSize = 7f;
                        lifetime = 20f;
                        pierce = true;
                        pierceBuilding = true;
                        pierceCap = 2;
                        shootEffect = Fx.shootSmallFlame;
                        hitEffect = Fx.hitFlameSmall;
                        despawnEffect = Fx.none;
                        keepVelocity = false;
                        hittable = false;
                    }};
                }});
            }};
        ember = new ErekirUnitType("ember"){{
            constructor = (Prov<Unit>) UnitEntity::create;
            lowAltitude = false;
            flying = true;
            drag = 0.08f;
            speed = 1.2f;

            rotateSpeed = 1.2f;
            accel = 0.09f;
            health = 1700f;
            armor = 8f;
            hitSize = 16f;
            engineSize = 0;
            fogRadius = 25;
            itemCapacity = 0;
            faceTarget = false;
            setEnginesMirror(
                    new UnitEngine(16 / 4f, -39 / 4f, 5f, 0f)/*,
                    new UnitEngine(38 / 4f, -43 / 4f, 4f, 315f)
                    */
            );
            weapons.add(new Weapon(XilionJavaMod.name("ember-cannon")){{
                shootSound = Sounds.missileLarge;
                layerOffset = 0.0001f;
                reload = 133f;
                shootY = 8f;
                recoil = 2f;
                rotate = true;
                rotateSpeed = 1.4f;
                mirror = false;
                shootCone = 3f;
                x = 0f;
                y = -1f;
                heatColor = Color.valueOf("f9350f");
                cooldownTime = 90f;

                bullet = XBullets.emberBulletType;
            }});
        }};

        hellhound = new ErekirUnitType("hellhound"){
            {
                constructor = (Prov<Unit>) UnitEntity::create;
                envDisabled = 0;

                lowAltitude = false;
                flying = true;
                drag = 0.06f;
                speed = 1.5f;
                rotateSpeed = 3.4f;
                accel = 0.1f;
                health = 5000f;
                armor = 10f;
                hitSize = 32f;
                payloadCapacity = Mathf.sqr(3f) * tilePayload;
                researchCostMultiplier = 0f;
                targetAir = true;

                engineSize = 6f;
                engineOffset = 61 / 4f;
                weapons.add(
                        new Weapon(XilionJavaMod.name("hellhould-flamethrower")) {{
                            ejectEffect = new Effect();
                            mirror = true;
                            alternate = true;
                            x = 29 / 4f;
                            y = 44 / 4f;
                            layerOffset = -0.001f;
                            cooldownTime = 30f;
                            recoil = 1.5f;
                            heatColor = Color.valueOf("f9350f");
                            rotate = true;
                            rotateSpeed = 3f;
                            shootSound = Sounds.flame;
                            shootY = 5f;
                            reload = 6f;
                            recoil = 1f;
                            ejectEffect = Fx.none;
                            bullet = new BulletType(4.5f, 11f) {{
                                ammoMultiplier = 3f;
                                hitSize = 7f;
                                lifetime = 20f;
                                pierce = true;
                                pierceBuilding = true;
                                pierceCap = 2;
                                shootEffect = Fx.shootSmallFlame;
                                hitEffect = Fx.hitFlameSmall;
                                despawnEffect = Fx.none;
                                keepVelocity = false;
                                hittable = false;
                            }};
                        }});
            }};
        cerberus =  new ErekirUnitType("cerberus"){
            {
                constructor = (Prov<Unit>) UnitEntity::create;
                aiController = FlyingFollowAI::new;
                envDisabled = 0;

                lowAltitude = false;
                flying = true;
                drag = 0.06f;
                speed = 1.0f;
                rotateSpeed = 1.5f;
                accel = 0.1f;
                health = 8000f;
                armor = 12f;
                hitSize = 36f;
                payloadCapacity = Mathf.sqr(3f) * tilePayload;
                researchCostMultiplier = 0f;
                targetAir = true;

                engineSize = 4.8f;
                engineOffset = 61 / 4f;

                weapons.add(new Weapon(XilionJavaMod.name("cerberus-swarm-cannon")){{

                    heatColor = Color.valueOf("f9350f");
                    x = 0f;
                    y = -8f;
                    rotate = true;
                    rotateSpeed = 4f;
                    mirror = false;

                    shadow = 20f;

                    shootY = 4.5f;
                    recoil = 3f;
                    reload = 60f;
                    velocityRnd = 0.4f;
                    inaccuracy = 7f;
                    ejectEffect = Fx.none;
                    shake = 1f;
                    shootSound = Sounds.missile;

                    shoot = new ShootAlternate(){{
                        shots = 6;
                        shotDelay = 1.5f;
                        spread = 4f;
                        barrels = 3;
                    }};

                    bullet = new MissileBulletType(4f, 25){{
                        layer = Layer.flyingUnit + 0.01f;
                        homingPower = 0.12f;
                        width = 8f;
                        height = 8f;
                        shrinkX = shrinkY = 0f;
                        drag = -0.003f;
                        homingRange = 80f;
                        keepVelocity = false;
                        splashDamageRadius = 35f;
                        splashDamage = 35f;
                        lifetime = 60f;
                        trailColor = XColors.ember1;
                        backColor = XColors.ember2;
                        frontColor = XColors.ember1;
                        hitEffect = Fx.blastExplosion;
                        despawnEffect = Fx.blastExplosion;
                        weaveScale = 8f;
                        weaveMag = 2f;
                    }};
                }});
                weapons.add(new Weapon(XilionJavaMod.name("cerberus-nozzle")){{
                    heatColor = Color.valueOf("f9350f");
                    rotationLimit = 120f;
                    layerOffset = -0.001f;
                    shadow = 20f;
                    controllable = false;
                    autoTarget = true;
                    shake = 3f;
                    shootY = 6f;
                    rotate = true;
                    x = 60f/4f;
                    y = 25/4f;
                    mirror = true;
                    alternate = false;

                    targetInterval = 20f;
                    targetSwitchInterval = 35f;

                    rotateSpeed = 3.5f;
                    reload = 50f;
                    recoil = 1f;
                    shootSound = Sounds.beam;
                    continuous = true;
                    cooldownTime =60;
                    immunities.add(StatusEffects.burning);

                    bullet = new ContinuousLaserBulletType(){{
                        maxRange = 70f;
                        damage = 15f;
                        length = 95f;
                        hitEffect = Fx.hitMeltdown;
                        drawSize = 200f;
                        lifetime = 200f;
                        shake = 1f;

                        shootEffect = Fx.shootBig;
                        smokeEffect = Fx.none;
                        width = 4f;
                        largeHit = false;

                        incendChance = 0.03f;
                        incendSpread = 5f;
                        incendAmount = 1;
                        ;
                        collidesTeam = false;

                        colors = new Color[]{XColors.ember1.cpy().a(0.2f), XColors.ember1.cpy().a(.5f), XColors.ember1.cpy().mul(1.2f), Color.white};
                    }};
                }});
            }};

        aura = new ErekirUnitType("aura"){{
            constructor = (Prov<Unit>) UnitEntity::create;
            flying = true;
            speed = 1.6f;
            accel = 0.09f;
            drag = 0.08f;
            health = 400f;
            hitSize = 10f;
            armor = 0f;
            circleTarget = true;
            range = 120;
            mineWalls = false;
            mineFloor =  false;
            setEnginesMirror( new UnitEngine(){{
                x = 4f;
                y = -5f;
                rotation = 270f;
                radius = 2.4f;
            }});
            weapons.add(
                    new Weapon("aura-helix"){{
                reload = 44;
                x = 1;
                y = 2;
                recoil = 4;
                shootCone = 30;
                shootSound =  blaster;
                rotate = false;
                shoot = new ShootHelix(){{
                    scl = 1f;
                    mag = 4f;
                }};
                bullet = new BasicBulletType(){{
                    damage = 11;
                    homingPower =  0.03f;
                    frontColor = Pal.sapBullet;
                    backColor = Pal.sapBulletBack;
                    lightColor = Pal.sapBullet;
                    /*
                    frontColor = new Color(0x665c9f);
                    backColor = new Color(0xbf92f9);
                    lightColor = new Color(0x665c9f);

                     */
                    speed = 4f;
                    width =  10f;
                    height = 12f;
                    lifetime = 30f;
                    //trailColor = new Color(0xbf92f9);
                    trailColor = Pal.sapBulletBack;
                    trailLength = 32;
                    trailWidth = 2f;
                    shootEffect = shootSmall;
                    smokeEffect = shootSmallSmoke;
                }};
            }},
                    new Weapon("aura-main"){{
                        reload = 44;
                        x = 1;
                        y = 2;
                        recoil = 4;
                        shootCone = 30;
                        shootSound =  blaster;
                        rotate = false;
                        bullet = new BasicBulletType(){{
                            damage = 11;
                            homingPower =  0.03f;
                            /*
                            frontColor = new Color(0x665c9f);
                            backColor = new Color(0xbf92f9);
                            lightColor = new Color(0x665c9f);

                             */
                            frontColor = Pal.sapBullet;
                            backColor = Pal.sapBulletBack;
                            lightColor = Pal.sapBullet;
                            speed = 4f;
                            width =  10f;
                            height = 12f;
                            lifetime = 30f;
                            //trailColor = new Color(0xbf92f9);
                            trailColor = Pal.sapBulletBack;
                            trailLength = 32;
                            trailWidth = 2f;
                            shootEffect = shootSmall;
                            smokeEffect = shootSmallSmoke;
                        }};
                    }}
                    );
        }};
        spectra = new ErekirUnitType("spectra"){{
            constructor = (Prov<Unit>) UnitEntity::create;
            flying = true;
            speed = 1.6f;
            accel = 0.04f;
            drag = 0.08f;
            health = 1200f;
            armor = 2f;
            hitSize = 18f;
            range = 136f;
            setEnginesMirror(new UnitEngine(){{
                x = 5;
                y = -7;
                rotation = 270f;
                radius = 3.2f;
            }});
            engines.add(new UnitEngine(){{
                x = 0;
                y = -8;
                rotation = 270f;
                radius = 4f;
            }});
            weapons.add(
                    new Weapon(XilionJavaMod.name("spectra-helix")){{
                        x = 0;
                        y = 0;
                        reload = 90f;
                        mirror = false;
                        recoil = 6f;
                        shootCone = 20f;
                        rotate = false;
                        shoot = new ShootHelix(){{
                            scl = 5f;
                            mag = 3f;
                        }};
                        bullet = new BasicBulletType(4f, 25f){{
                            homingPower = 0.03f;
                            frontColor = Pal.sapBullet;
                            backColor = Pal.sapBulletBack;
                            lightColor = Pal.sapBullet;
                            trailColor = Pal.sapBulletBack;
                            width = 16f;
                            height = 16f;
                            lifetime = 34f;
                            trailLength = 46;
                            trailWidth = 3f;
                            shootEffect = shootSmall;
                            smokeEffect = shootSmallSmoke;
                        }};
                    }},

                    new Weapon(XilionJavaMod.name("spectra-helix-middle")){{
                        x = 0;
                        y = 0;
                        reload = 90f;
                        mirror = false;
                        recoil = 6f;
                        shootCone = 20f;
                        rotate = false;
                        bullet = new BasicBulletType(4f, 50f){{
                            homingPower = 0.03f;
                            frontColor = Pal.sapBullet;
                            backColor = Pal.sapBulletBack;
                            lightColor = Pal.sapBullet;
                            trailColor = Pal.sapBulletBack;
                            width = 16f;
                            height = 16f;
                            lifetime = 34f;
                            trailLength = 46;
                            trailWidth = 3f;
                            splashDamage = 40f;
                            splashDamageRadius = 32f;
                            shootEffect = shootSmall;
                            smokeEffect = shootSmallSmoke;
                            intervalBullet = new BasicBulletType(4f, 10f){{
                                homingPower = 0.03f;
                                frontColor = Pal.sapBullet;
                                backColor = Pal.sapBulletBack;
                                lightColor = Pal.sapBullet;
                                trailColor = Pal.sapBulletBack;
                                width = 10f;
                                height = 12f;
                                lifetime = 10f;
                                trailLength = 32;
                                trailWidth = 2f;
                                shootEffect = shootSmall;
                                smokeEffect = shootSmallSmoke;
                            }};
                            fragBullet = intervalBullet;
                            bulletInterval = 4f;
                            intervalRandomSpread = 0f;
                            intervalBullets = 2;
                            intervalSpread = 225f;
                            intervalAngle = 0f;
                            fragLifeMin = 0.4f;
                            fragOnHit = true;
                            fragVelocityMin = 0.4f;
                            fragVelocityMax = 0.8f;
                            fragBullets = 10;
                            fragSpread = 36f;
                            fragRandomSpread = 0f;
                            fragAngle = 0f;

                            hitEffect = new WaveEffect(){{
                                colorFrom = Pal.sapBullet;
                                colorTo = Pal.sapBulletBack;
                                sizeTo = 64f;
                                lifetime = 80f;
                                strokeFrom = 5f;
                            }};
                            despawnEffect = new WaveEffect(){{
                                colorFrom = Pal.sapBullet;
                                colorTo = Pal.sapBulletBack;
                                sizeTo = 10f;
                                lifetime = 45f;
                                strokeFrom = 4f;
                            }};

                        }};
                    }}
            );
        }};
        annihilate = new ErekirUnitType("annihilate"){{
            constructor = (Prov<Unit>) UnitEntity::create;
            envDisabled = 0;

            lowAltitude = false;
            flying = true;
            drag = 0.06f;
            speed = 1.4f;
            rotateSpeed = 3.2f;
            accel = 0.1f;
            health = 2700f;
            armor = 4f;
            hitSize = 28f;
            payloadCapacity = Mathf.sqr(3f) * tilePayload;
            researchCostMultiplier = 0f;
            targetAir = true;

            engineSize = 4.5f;
            engineOffset = 61 / 4f;
            setEnginesMirror(
                    new UnitEngine(44 / 4f, -52 / 4f, 3f, 315f)
            );
            weapons.add(
            new Weapon(XilionJavaMod.name("annihilate-lazer")){{
                heatColor = Pal.sapBulletBack;
                shootSound = Sounds.lasershoot;
                ejectEffect = new Effect();

                reload = 20f;
                mirror = true;
                alternate = true;
                x = 29/4f;
                y = 39/4f;
                rotate = false;
                layerOffset = -0.001f;
                cooldownTime = 30f;
                recoil = 1.5f;
                /*
                bullet = new LaserBoltBulletType(5f, 120){{
                    ejectEffect= new Effect();
                    lifetime = 30f;
                    width = width *3;
                    height = height * 1.5f;
                    pierce = true;
                    pierceArmor = true;
                    pierceCap = 2;
                    backColor = Pal.sapBulletBack;
                    frontColor = Pal.sapBullet;
                    hitEffect =  new Effect();
                }};

                 */
                bullet = XBullets.assaultTypeBullet;
            }});
        }};
        quick =  new ErekirUnitType("quick"){{
            constructor = (Prov<Unit>) UnitEntity::create;
            defaultCommand = UnitCommand.repairCommand;
            commands = new UnitCommand[]{UnitCommand.moveCommand, UnitCommand.repairCommand};
            health = 300;
            armor =0;
            speed = 2f;
            hitSize = 10;
            itemCapacity = 10;
            //isCounted = false;
            faceTarget = true;
            rotateSpeed = 10f;
            flying = true;
            weapons.add(new RepairBeamWeapon(){{
                widthSinMag = 0.11f;
                reload = 20f;

                x = 0f;
                y = 5f;
                rotate = false;
                shootY = 0f;
                beamWidth = 0.7f;
                aimDst = 0f;
                shootCone = 40f;
                shoot = new ShootPattern(){{
                    firstShotDelay = 60f;
                    shotDelay = 1f;
                }};
                mirror = false;
                repairSpeed = 0.4f;
                fractionRepairSpeed = 0f;
                canHeal =true;
                targetUnits = true;
                faceTarget =true;
                targetBuildings = true;
                autoTarget = true;
                controllable = false;

                laserColor = XColors.QuickClassEC2;
                healColor = XColors.QuickClassEC1;


                bullet = new BulletType(){{
                    maxRange =48f;
                }};
            }});
        }};
        dash = new ErekirUnitType("dash"){{
            constructor = (Prov<Unit>) UnitEntity::create;
            aiController = FlyingAI::new;
            health = 600;
            armor =0;
            speed = 4f;
            hitSize = 12;

            itemCapacity = 20;
            // isCounted = false;
            buildSpeed =  0.6f;
            faceTarget = true;
            rotateSpeed = 15f;
            flying = true;
            //rotateShooting = true;
            engineSize = 4.5f;
            engineOffset = 8f;
            buildRange = 80f;
            buildBeamOffset = 6f;

            /*
            weapons.add(
                    new Weapon("quick-weapon-mount"){{
                        shootSound = Sounds.lasershoot;

                        reload = 20f;
                        x = 3f;
                        y = 3f;
                        rotate = true;

                        mirror = true;
                        bullet = XBullets.QuickTypeBullet;
                    }});

             */
        }};
        acari = new ErekirUnitType("acari"){
            {
                constructor = (Prov<Unit>) LegsUnit::create;
                speed = 0.66f;
                drag = 0.11f;
                hitSize = 10f;
                rotateSpeed = 3f;
                health = 1000;
                armor = 6f;
                legStraightness = 0.3f;
                stepShake = 0f;

                legCount = 6;
                legLength = 9f;
                lockLegBase = false;
                legContinuousMove = true;
                legExtension = -1f;
                legBaseOffset = 3f;
                legMaxLength = 1.1f;
                legMinLength = 0.2f;
                legLengthScl = 0.96f;
                legForwardScl = 1.1f;
                legGroupSize = 3;
                rippleScale = 0.2f;

                legMoveSpace = 1f;
                allowLegStep = true;
                hovering = true;
                legPhysicsLayer = false;

                shadowElevation = 0.1f;
                groundLayer = Layer.legUnit - 1f;
                targetAir = false;
                researchCostMultiplier = 0f;
                range = 32f;

                weapons.add(new Weapon("acari-weapon") {{
                    shootSound = Sounds.missile;
                    mirror = false;
                    showStatSprite = false;
                    x = 0f;
                    y = 1f;
                    shootY = 6f;
                    reload = 60f;

                    cooldownTime = 42f;
                    range = 32f;
                    heatColor = Pal.turretHeat;
                    bullet = new ArtilleryBulletType(3f, 40) {{
                        shootEffect = new MultiEffect(Fx.shootSmallColor, new Effect(9, e -> {
                            color(XColors.attackClassEC2, e.color, e.fin());
                            stroke(0.7f + e.fout());
                            Lines.square(e.x, e.y, e.fin() * 5f, e.rotation + 45f);

                            Drawf.light(e.x, e.y, 23f, e.color, e.fout() * 0.7f);
                        }));

                        collidesTiles = true;
                        backColor = hitColor = XColors.attackClassEC2;
                        frontColor = Color.white;

                        lifetime = 8f;
                        width = height = 9f;
                        splashDamageRadius = 10f;
                        splashDamage = 50f;

                        trailLength = 27;
                        trailWidth = 2.5f;
                        trailEffect = Fx.none;
                        trailColor = backColor;

                        trailInterp = Interp.slope;

                        shrinkX = 0.6f;
                        shrinkY = 0.2f;

                        hitEffect = despawnEffect = new MultiEffect(Fx.hitSquaresColor, new WaveEffect() {{
                            colorFrom = colorTo = XColors.attackClassEC2;
                            sizeTo = splashDamageRadius + 1f;
                            lifetime = 6f;
                            strokeFrom = 2f;
                        }});
                    }};
                }});
            }};
        blastbeetle = new ErekirUnitType("blastbeetle"){
            {
                constructor = (Prov<Unit>) LegsUnit::create;
                speed = 0.62f;
                drag = 0.11f;
                hitSize = 16f;
                rotateSpeed = 3f;
                health = 2300;
                armor = 10f;
                legStraightness = 0.3f;
                stepShake = 0f;

                legCount = 6;
                legLength = 11f;
                lockLegBase = false;
                legContinuousMove = true;
                legExtension = -2f;
                legBaseOffset = 5f;
                legMaxLength = 1.3f;
                legMinLength = 0.6f;
                legLengthScl = 0.96f;
                legForwardScl = 1.4f;
                legGroupSize = 3;
                rippleScale = 0.2f;

                legMoveSpace = 1f;
                allowLegStep = true;
                hovering = true;
                legPhysicsLayer = false;

                shadowElevation = 0.1f;
                groundLayer = Layer.legUnit - 1f;
                targetAir = true;
                researchCostMultiplier = 0f;
                range = 40f;

                weapons.add(new Weapon(XilionJavaMod.name("blastbeetle-weapon")) {{
                    shootSound = Sounds.missile;

                    mirror = false;
                    showStatSprite = false;
                    x = 0f;
                    y = 0f;
                    shootY = 9f;
                    reload = 120f;

                    cooldownTime = 90f;
                    range = 40f;
                    heatColor = Pal.turretHeat;
                    bullet = XBullets.blastTypeBullet;
                    recoil = 0f;
                }});
            }};
        sanatick = new ErekirUnitType("sanatick"){
            {
                constructor = (Prov<Unit>) LegsUnit::create;
                //defaultCommand = UnitCommand.rebuildCommand;
                defaultCommand = UnitCommand.repairCommand;
                commands =  new UnitCommand[]{UnitCommand.moveCommand, UnitCommand.repairCommand};
                //commands = new UnitCommand[]{UnitCommand.moveCommand, UnitCommand.rebuildCommand, UnitCommand.assistCommand};
                //aiController =  LegUnitFaceBuildingAI::new;
                buildSpeed = 0.3f;
                rotateToBuilding = true;
                rotateMoveFirst = true;
                buildRange = 40f;
                faceTarget = true;
                speed = 0.7f;
                drag = 0.1f;
                hitSize = 9f;
                rotateSpeed = 3f;
                health = 500;
                armor = 0f;
                legStraightness = 0.4f;
                stepShake = 0f;
                legCount = 6;
                legLength = 8f;
                lockLegBase = false;
                legContinuousMove = true;
                legExtension = -1f;
                legBaseOffset = 3f;
                legMaxLength = 1.1f;
                legMinLength = 0.2f;
                legLengthScl = 0.96f;
                legForwardScl = 1.1f;
                legGroupSize = 3;
                rippleScale = 0.2f;

                legMoveSpace = 1f;
                allowLegStep = true;
                hovering = true;
                legPhysicsLayer = false;

                shadowElevation = 0.1f;
                groundLayer = Layer.legUnit - 1f;
                targetAir = false;
                researchCostMultiplier = 0f;
                weapons.add(new RepairBeamWeapon(){{
                    widthSinMag = 0.11f;
                    reload = 20f;

                    x = 0f;
                    y = 5f;
                    rotate = false;
                    shootY = 0f;
                    beamWidth = 0.7f;
                    aimDst = 0f;
                    shootCone = 40f;
                    shoot = new ShootPattern(){{
                        firstShotDelay = 60f;
                        shotDelay = 1f;
                    }};
                    mirror = false;
                    repairSpeed = 0.6f;
                    fractionRepairSpeed = 0f;
                    canHeal =true;
                    targetUnits = true;
                    faceTarget =true;
                    targetBuildings = true;
                    autoTarget = true;
                    controllable = false;

                    laserColor = XColors.QuickClassEC2;
                    healColor = XColors.QuickClassEC1;


                    bullet = new BulletType(){{
                        maxRange =48f;
                    }};
                }});
            }};
            strike =  new ErekirUnitType("strike"){{
                constructor = (Prov<Unit>) LegsUnit::create;
                faceTarget = true;
                speed = 0.9f;
                drag = 0.1f;
                hitSize = 9f;
                rotateSpeed = 3f;
                health = 600;
                armor = 0f;
                legStraightness = 0.4f;
                stepShake = 0f;
                legCount = 4;
                legLength = 8f;
                lockLegBase = false;
                legContinuousMove = true;
                legExtension = -1f;
                legBaseOffset = 3f;
                legMaxLength = 1.1f;
                legMinLength = 0.2f;
                legLengthScl = 0.96f;
                legForwardScl = 1.1f;
                legGroupSize = 2;
                rippleScale = 0.2f;

                legMoveSpace = 1f;
                allowLegStep = true;
                hovering = true;
                legPhysicsLayer = false;

                shadowElevation = 0.1f;
                groundLayer = Layer.legUnit - 1f;
                targetAir = false;
                researchCostMultiplier = 0f;


                weapons.add(new Weapon(XilionJavaMod.name("strike-weapon")){{
                    reload = 30f;
                    x = 18/4f;
                    y = 0f;
                    top = false;
                    shootY = 1f;
                    alternate = true;
                    mirror = true;
                    shootSound = Sounds.missile;
                    recoil = 4f;
                    bullet = new ArtilleryBulletType(2.5f, 40){{
                        shootEffect = new MultiEffect(Fx.shootSmallColor, new Effect(9, e -> {
                            color(Color.white, e.color, e.fin());
                            stroke(0.7f + e.fout());
                            Lines.square(e.x, e.y, e.fin() * 5f, e.rotation + 45f);

                            Drawf.light(e.x, e.y, 23f, e.color, e.fout() * 0.7f);
                        }));

                        collidesTiles = true;
                        backColor = hitColor = Pal.sapBulletBack;
                        frontColor = Pal.sapBullet;

                        knockback = 0.8f;
                        lifetime = 35f;
                        width = height = 9f;
                        splashDamageRadius = 19f;
                        splashDamage = 30f;

                        trailLength = 27;
                        trailWidth = 2.5f;
                        trailEffect = Fx.none;
                        trailColor = backColor;

                        trailInterp = Interp.slope;

                        shrinkX = 0.6f;
                        shrinkY = 0.2f;

                        hitEffect = despawnEffect = new MultiEffect(Fx.hitSquaresColor, new WaveEffect(){{
                            colorFrom = colorTo = Pal.sapBullet;
                            sizeTo = splashDamageRadius + 2f;
                            lifetime = 9f;
                            strokeFrom = 2f;
                        }});
                    }};
                }});
            }};
            salticidae = new ErekirUnitType("salticidae"){{
                constructor = (Prov<Unit>) LegsUnit::create;
                speed = 0.78f;
                drag = 0.11f;
                hitSize = 9f;
                rotateSpeed = 3f;
                health = 700;
                armor = 5f;
                legStraightness = 0.3f;
                stepShake = 0f;

                legCount = 6;
                legLength = 9f;
                lockLegBase = false;
                legContinuousMove = true;
                legExtension = -1f;
                legBaseOffset = 3f;
                legMaxLength = 1.1f;
                legMinLength = 0.2f;
                legLengthScl = 0.96f;
                legForwardScl = 1.1f;
                legGroupSize = 3;
                rippleScale = 0.2f;

                legMoveSpace = 1f;
                allowLegStep = true;
                hovering = true;
                legPhysicsLayer = false;

                shadowElevation = 0.1f;
                groundLayer = Layer.legUnit - 1f;
                targetAir = false;
                researchCostMultiplier = 0f;
                range = 32f;

                weapons.add(new Weapon("salticidae-weapon"){{
                    shootSound = Sounds.missile;
                    mirror = false;
                    showStatSprite = false;

                    x = 0f;
                    y = 1f;
                    shootY = 6f;
                    reload = 25f;

                    cooldownTime = 42f;
                    range = 32f;
                    heatColor = Pal.turretHeat;
                    bullet = new ArtilleryBulletType(3f, 20){{
                        shootEffect = new MultiEffect(Fx.shootSmallColor, new Effect(9, e -> {
                            color(Pal.sapBullet, e.color, e.fin());
                            stroke(0.7f + e.fout());
                            Lines.square(e.x, e.y, e.fin() * 5f, e.rotation + 45f);

                            Drawf.light(e.x, e.y, 23f, e.color, e.fout() * 0.7f);
                        }));

                        collidesTiles = true;
                        backColor = hitColor = Pal.sapBullet;
                        frontColor = Color.white;

                        lifetime = 8f;
                        width = height = 9f;
                        splashDamageRadius = 10f;
                        splashDamage = 30f;

                        trailLength = 27;
                        trailWidth = 2.5f;
                        trailEffect = Fx.none;
                        trailColor = backColor;

                        trailInterp = Interp.slope;

                        shrinkX = 0.6f;
                        shrinkY = 0.2f;

                        hitEffect = despawnEffect = new MultiEffect(Fx.hitSquaresColor, new WaveEffect(){{
                            colorFrom = colorTo = Pal.sapBullet;
                            sizeTo = splashDamageRadius + 1f;
                            lifetime = 6f;
                            strokeFrom = 2f;
                        }});
                    }};
                }});



        }};
        astacoidea = new ErekirUnitType("astacoidea"){
            {
                constructor = (Prov<Unit>) LegsUnit::create;
                speed = 0.7f;
                drag = 0.11f;
                hitSize = 13f;
                rotateSpeed = 3f;
                health = 2000;
                armor = 6f;
                legStraightness = 0.3f;
                stepShake = 0f;

                legCount = 6;
                legLength = 9f;
                lockLegBase = false;
                legContinuousMove = true;
                legExtension = -1f;
                legBaseOffset = 3f;
                legMaxLength = 1.1f;
                legMinLength = 0.2f;
                legLengthScl = 0.96f;
                legForwardScl = 1.1f;
                legGroupSize = 3;
                rippleScale = 0.2f;

                legMoveSpace = 1f;
                allowLegStep = true;
                hovering = true;
                legPhysicsLayer = false;

                shadowElevation = 0.1f;
                groundLayer = Layer.legUnit - 1f;
                targetAir = false;
                researchCostMultiplier = 0f;
                range = 32f;
                faceTarget = true;
                parts.add(new RegionPart("-mandible"){{
                    y = 0;
                    moveX = 7f/4;
                    moveY = 9f/4;
                    progress = PartProgress.reload;
                    //progress.sin(2f, 4f);

                    layerOffset = -0.001f;
                    mirror = true;
                }});
                weapons.add(new RepairBeamWeapon(){{
                    widthSinMag = 0.11f;
                    reload = 20f;

                    x = 0f;
                    y = 8f;
                    rotate = false;
                    shootY = 0f;
                    beamWidth = 0.7f;
                    aimDst = 0f;
                    shootCone = 40f;
                    shoot = new ShootPattern(){{
                        firstShotDelay = 60f;
                        shotDelay = 1f;
                    }};
                    mirror = false;
                    repairSpeed = 2f;
                    fractionRepairSpeed = 0.5f;
                    canHeal =true;
                    targetUnits = true;
                    faceTarget =true;
                    targetBuildings = true;
                    autoTarget = true;
                    controllable = false;
                    laserColor = Pal.heal;
                    healColor = Pal.heal;


                    bullet = new BulletType(){{
                        maxRange =40f;

                    }};
                }});
            }};
        attack = new UnitType("attack"){{

            constructor = (Prov<Unit>) LegsUnit::create;

            health = 180;
            armor = 2;
            speed = 1.2f;
            hitSize = 8;
            itemCapacity = 15;
            buildSpeed = 1.2f;
            faceTarget = true;
           // rotateShooting =true;
            range = 70;
            rotateSpeed = 3;
            flying=false;
            targetAir =true;
            //legTrns = 0.6f;
            legMoveSpace = 0.6f;
            legCount = 4;
            legMoveSpace = 1.5f;
            legLength = 7;

            //visualElevation = 0.5f;
            hovering =true;
            allowLegStep =true;



            //This unit class(tree) have universal immunities
            immunities.addAll(XEffectsLists.attackClassImmune);

            //weapons.add(UPWeapons.strikeOrb);
            weapons.add(XWeapons.strikeOrb);

        }};

        /*strike = new UnitType("strike"){{

            constructor = (Prov<Unit>) LegsUnit::create;

            health = 450;
            armor = 3;
            speed = 1.6f;
            hitSize = 12;
            itemCapacity = 25;
            //isCounted = true;
            buildSpeed = 1.5f;
            faceTarget = false;
            //rotateShooting =true;
            range = 80;
            rotateSpeed = 3.7f;
            flying=false;
            targetAir =true;
            //legTrns = 0.6f;
            legMoveSpace = 0.6f;
            legCount = 6;
            legMoveSpace = 1.5f;
            legLength = 15;
            //visualElevation = 0.7f;
            hovering =true;
            allowLegStep =true;

            //This unit class(tree) have universal immunities
            immunities.addAll(XEffectsLists.attackClassImmune);

            weapons.add(XWeapons.StrikeOrbTriple);

        }};
        */

       /* assault = new UnitType("assault"){{
            constructor = (Prov<Unit>) LegsUnit::create;
            health = 700f;
            armor = 4;
            speed = 1f;
            hitSize = 16;
            itemCapacity = 40;
            buildSpeed = 1f;
            faceTarget = true;
            range = 96;
            rotateSpeed = 2.7f;
            flying=false; Qdnim6tif
            targetAir =true;
            legMoveSpace = 0.6f;
            legCount = 6;
            legMoveSpace = 1.5f;
            legLength = 15;
            hovering =true;
            allowLegStep =true;
            weapons.add(UPWeapons.assaultOrbLauncher);
            //This unit class(tree) have universal immunities
            immunities.addAll(UPEffectsLists.attackClassImmune);

        }};

        */




        leap = new UnitType("leap"){{
            constructor = (Prov<Unit>) UnitEntity::create;
            aiController = FlyingAI::new;

            health = 570;
            this.
            armor =2;
            speed = 4.0f;
            hitSize = 16;

            itemCapacity = 40;
            //isCounted = false;
            buildSpeed =  1.6f;
            faceTarget = true;
            rotateSpeed = 8f;
            flying = true;
            //rotateShooting = true;
            engineSize = 5.0f;
            abilities.add(new XDashAbility(27, 360));
            engineLayer = Layer.flyingUnit-0.1f;




            weapons.add(
                    new Weapon(XilionJavaMod.name("leap-weapon-mount")){{
                        shootSound = Sounds.lasershoot;
                       // firstShotDelay = 10;
                        shoot = new ShootPattern(){{
                            shots = 1;
                            shotDelay = 0f;
                            firstShotDelay =10f;
                        }};

                        reload = 10f;
                        x = 6;
                        y = 0;
                        rotate = true;

                        mirror = true;
                        bullet = XBullets.QuickTypeBullet;
                        bullet.lifetime = 35;
                    }});

        }};

        supersonic = new UnitType("supersonic"){{

            constructor = (Prov<PayloadUnit>) PayloadUnit::create;
            aiController = FlyingAI::new;
            health = 6000;
            armor =4;
            speed = 2.0f;
            hitSize = 36;
            itemCapacity = 80;
            payloadCapacity = (3f * 3f) * tilePayload;
            //isCounted = false;
            buildSpeed =  2.8f;
            faceTarget = true;
            rotateSpeed = 3f;
            flying = true;
            engineLayer = Layer.flyingUnit-0.1f;
            //rotateShooting = true;
            engineSize = 10.0f;
            engineOffset = 18f;
            abilities.add(new XDashAbility(60, 1000));
            abilities.add(new XSpeedBuffFieldAbility( XStatusEffects.speedTierOne, 480f, 480f, 80));
        }};
        /*
       snake = new SnakeEntityType("snake"){{
            constructor = (Prov<Unit>) UnitEntity::create;
            aiController = SleepingAI::new;
            range = 20;
            health = 800;
            this.
                    armor =1;
            speed = 1f;
            hitSize = 8;

            itemCapacity = 0;
            //isCounted = false;
            buildSpeed =  0f;
            faceTarget = true;
            rotateSpeed = 6f;
        }};
    */

    }
}
