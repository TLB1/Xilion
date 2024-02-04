package xilion.content;

import arc.graphics.Color;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.ParticleEffect;
import mindustry.entities.part.RegionPart;
import mindustry.entities.pattern.ShootPattern;
import mindustry.graphics.Pal;
import mindustry.type.Weapon;
import xilion.XilionJavaMod;

public class XWeapons {

    public static Weapon strikeOrb = new Weapon("strike-orb"){{
        shootCone = 12;
        x = 0;
        y = 3;
        shootY = 0;
        mirror = false;
        top = true;

        inaccuracy = 12;

        //shots = 1;
        reload = 32;
        recoil = 1.3f;
       // firstShotDelay = 12;
        shoot = new ShootPattern(){{
            shots = 1;
            shotDelay = 0f;
            firstShotDelay =12f;
        }};
        shootSound = XSounds.attackMissileHeavy;
        rotateSpeed = 4;


        bullet = new BasicBulletType(2.8f, 20){{
            shootEffect = new MultiEffect() {{
                lifetime = 20;
                effects = new Effect[2];
                effects[0] = new ParticleEffect(){{
                    line = true;
                    particles = 15;
                    lifetime = 20;
                    length = 35;
                    baseLength = -35;
                    cone = -360;
                    lenFrom = 4;
                    lenTo = 0;
                    //DONE:Make UnitsPlusColors class separate from UnitsPlusUnitTypes (Colors)
                    colorFrom = XColors.attackClassEC1;
                    colorTo = XColors.attackClassEC2;
                }};
                effects[1] = new ParticleEffect(){{
                    particles = 1;
                    sizeFrom = 0.5f;
                    sizeTo = 2.5f;
                    length = 0;
                    lifetime = 26;
                    cone = 360;
                    colorFrom = XColors.attackClassEC1;
                    colorTo = XColors.attackClassEC2;
                }};
            }};
            trailChance = 1;
            trailEffect = new ParticleEffect(){{
                particles = 1;
                length = 0;
                lifetime = 15;
                sizeFrom = 5;
                colorFrom = XColors.attackClassEC1;;
                colorTo = XColors.attackClassEC2;;
            }};
            keepVelocity = false;
            width = 4;
            height = 4.5f;
            lifetime = 32;
            shootCone = 12;
            homingDelay = 5;
            homingPower = 0.07f;
            homingRange = 40;
            splashDamageRadius = 8;
            splashDamage = 6;
            buildingDamageMultiplier = 0.2f;
            frontColor = XColors.attackClassEC1;
            backColor = XColors.attackClassEC2;

        }};
    }};
    public static Weapon StrikeOrbTriple = new Weapon("strike-orb"){{
        shootCone = 12;
        x = 0;
        y = 6;
        shootY = 0;
        mirror = false;
        top = true;

        inaccuracy = 18;

        //shots = 3;
        reload = 32;
        recoil = 0.8f;
        //firstShotDelay = 12;
        shoot = new ShootPattern(){{
            shots = 3;
            shotDelay = 0f;
            firstShotDelay =12f;
        }};
        shootSound = XSounds.attackMissileHeavy;
        rotateSpeed = 4;


        bullet = new BasicBulletType(3.8f, 20){{
            shootEffect = new MultiEffect() {{
                lifetime = 15;

                effects = new Effect[2];
                effects[0] = new ParticleEffect(){{
                    line = true;
                    particles = 15;
                    lifetime = 20;
                    length = 35;
                    baseLength = -35;
                    cone = -360;
                    lenFrom = 4;
                    lenTo = 0;
                    colorFrom = XColors.attackClassEC1;
                    colorTo = XColors.attackClassEC2;
                }};
                effects[1] = new ParticleEffect(){{
                    particles = 1;
                    sizeFrom = 0.5f;
                    sizeTo = 2.5f;
                    length = 0;
                    lifetime = 26;
                    cone = 360;
                    colorFrom = XColors.attackClassEC1;
                    colorTo = XColors.attackClassEC2;
                }};
            }};
            trailChance = 1;
            trailEffect = new ParticleEffect(){{
                particles = 1;
                length = 0;
                lifetime = 15;
                sizeFrom = 5;
                colorFrom = XColors.attackClassEC1;
                colorTo = XColors.attackClassEC2;
            }};
            keepVelocity = false;
            width = 4;
            height = 4.5f;
            lifetime = 32;
            shootCone = 12;
            homingDelay = 5;
            homingPower = 0.07f;
            homingRange = 40;
            splashDamageRadius = 8;
            splashDamage = 6;
            buildingDamageMultiplier = 0.2f;
            frontColor = XColors.attackClassEC1;
            backColor = XColors.attackClassEC2;

        }};
    }};


    public static Weapon assaultOrbLauncher = new Weapon(XilionJavaMod.name("assault-orb-launcher")){{
        shootCone = 12;
        x = 10;
        y = 0;
        shootY = 0;
        mirror = true;
        top = true;

        heatColor = Pal.sapBullet;
        shootWarmupSpeed = 0.06f;
        cooldownTime = 80f;
        minWarmup = 0.9f;

        //shots = 1;
        reload = 60;
        recoil = 1.3f;
        // firstShotDelay = 12;
        shoot = new ShootPattern(){{
            shots = 1;
            shotDelay = 0f;
            firstShotDelay =0f;
            alternate = true;
        }};
        shootSound = XSounds.attackMissileHeavy;
        rotateSpeed = 4;


        bullet = XBullets.assaultTypeBullet;
        //Head blades
        for(int i = 1; i <= 2; i++){
            int fi = i-1;
            parts.add(new RegionPart("-blade"){{
                progress = PartProgress.warmup.delay((3 - fi) * 0.3f).blend(PartProgress.reload, 0.3f);
                heatProgress = PartProgress.heat.add(0.3f).min(PartProgress.warmup);
                heatColor = new Color(1f, 0.1f, 0.1f);
                mirror = true;
                under = true;
                rotation = 180f;
                moveRot =10f +35f * fi;
                moveX = 1f;
                layerOffset = -0.002f;

                x = 11 / 4f;
            }});
        }
    }};
}

