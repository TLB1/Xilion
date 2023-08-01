package xilion.content;

import arc.func.Prov;
import arc.graphics.Color;
import arc.graphics.g2d.Lines;
import arc.math.Interp;
import arc.math.Mathf;
import mindustry.ai.types.DefenderAI;
import mindustry.ai.types.FlyingAI;
import mindustry.ai.types.FlyingFollowAI;
import mindustry.content.Fx;
import mindustry.content.StatusEffects;
import mindustry.entities.Effect;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.ContinuousLaserBulletType;
import mindustry.entities.bullet.MissileBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootAlternate;
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
import xilion.entities.XErekirAbilityUnitType;
import xilion.entities.abilities.XDashAbility;
import xilion.entities.abilities.XSpeedBuffFieldAbility;
import xilion.util.XActiveAbility;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.lineAngle;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;
import static mindustry.Vars.tilePayload;

public class XUnitTypes {
    public static  UnitType
/*TODO: add new units:
    supersonic, hypersonic --> speed-buff to allies
    hypersonic beam that slows enemies and can teleport
*/
    // Erekir base units
     acari, salticidae, astacoidea,

    //Attack class units:
    attack, strike, assault, battery, violence,
    snake,

    //Quick class units:
    quick, dash, leap, supersonic,hypersonic, leaptest,

            ember, cerberus;


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
        ember = new ErekirUnitType("ember"){{
            constructor = (Prov<Unit>) UnitEntity::create;
            lowAltitude = false;
            flying = true;
            drag = 0.08f;
            speed = 1.2f;

            rotateSpeed = 1.2f;
            accel = 0.09f;
            health = 2000f;
            armor = 5f;
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
                reload = 120f;
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
                health = 7500f;
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
                    reload = 45f;
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

                    bullet = new MissileBulletType(4f, 20){{
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
                    reload = 100f;
                    recoil = 1f;
                    shootSound = Sounds.beam;
                    continuous = true;
                    cooldownTime =60;
                    immunities.add(StatusEffects.burning);

                    bullet = new ContinuousLaserBulletType(){{
                        maxRange = 70f;
                        damage = 10f;
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
        acari = new ErekirUnitType("acari"){
            {
                constructor = (Prov<Unit>) LegsUnit::create;
                speed = 0.66f;
                drag = 0.11f;
                hitSize = 10f;
                rotateSpeed = 3f;
                health = 1100;
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

                    progress = PartProgress.warmup;
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

        strike = new UnitType("strike"){{

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
        quick =  new UnitType("quick"){{
            constructor = (Prov<Unit>) UnitEntity::create;
            aiController = DefenderAI::new;
            health = 70;
            armor =0;
            speed = 4.4f;
            hitSize = 6;
            itemCapacity = 10;
            //isCounted = false;
            buildSpeed =  0.4f;
            faceTarget = false;
            rotateSpeed = 10f;
            flying = true;
        }};

        dash = new UnitType("dash"){{
            constructor = (Prov<Unit>) UnitEntity::create;
            aiController = FlyingAI::new;
            health = 270;
            armor =0;
            speed = 4.8f;
            hitSize = 9;
             
            itemCapacity = 20;
           // isCounted = false;
            buildSpeed =  0.6f;
            faceTarget = true;
            rotateSpeed = 10f;
            flying = true;
            //rotateShooting = true;
            engineSize = 3.0f;
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

        }};

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