package xilion.content;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.content.StatusEffects;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BulletType;
import mindustry.entities.bullet.LaserBoltBulletType;
import mindustry.entities.effect.ParticleEffect;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;

import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.stroke;

public class XBullets {
    public static BulletType QuickTypeBullet, assaultTypeBullet, emberBulletType;
 static {
    QuickTypeBullet = new LaserBoltBulletType(5f, 7){{
        lifetime = 25f;
        backColor = XColors.QuickClassEC1;
        frontColor = XColors.QuickClassEC2;
        hitColor = XColors.attackClassEC2;
        lightColor = XColors.attackClassEC2;

        hitEffect= new Effect(8, e -> {
            color(Color.white, XColors.QuickClassEC2, e.fin());
            stroke(0.4f + e.fout());
            Lines.circle(e.x, e.y, e.fin() * 4f);

            Drawf.light(e.x, e.y, 23f, XColors.QuickClassEC2, e.fout() * 0.7f);
        }) ;
        despawnEffect = new Effect(8, e -> {
            color(Color.white, XColors.QuickClassEC2, e.fin());
            stroke(0.4f + e.fout());
            Lines.circle(e.x, e.y, e.fin() * 4f);

            Drawf.light(e.x, e.y, 23f, XColors.QuickClassEC2, e.fout() * 0.7f);
        }) ;
        smokeEffect = new Effect(8, e -> {
            color(Color.white, XColors.QuickClassEC2, e.fin());
            stroke(0.4f + e.fout());
            Lines.circle(e.x, e.y, e.fin() * 4f);

            Drawf.light(e.x, e.y, 23f, XColors.QuickClassEC2, e.fout() * 0.7f);
        }) ;
    }};

     emberBulletType = new BulletType(3f, 150){
         {
             status = StatusEffects.burning;
             statusDuration = 300f;
             incendChance = 0.25f;
             trailChance = 1f;
             lifetime = 43f;
             hitSize = 10;
             drawSize = 35f;
             splashDamageRadius = 24f;
             splashDamage = 50f;
             buildingDamageMultiplier = 0.2f;
             hitSound = Sounds.explosion;
             hitEffect = new Effect(50f, 30f, e -> {
                 float rad = 16f;
                 e.scaled(7f, b -> {
                     color(XColors.ember1, b.fout());
                     Fill.circle(e.x, e.y, rad);
                 });

                 color(XColors.ember1);
                 stroke(e.fout() * 3f);
                 Lines.circle(e.x, e.y, rad);

                 int points = 6;
                 float offset = Mathf.randomSeed(e.id, 360f);
                 for (int i = 0; i < points; i++) {
                     float angle = i * 360f / points + offset;
                     Drawf.tri(e.x + Angles.trnsx(angle, rad), e.y + Angles.trnsy(angle, rad), 3f, 20f * e.fout(), angle);
                 }
                 Fill.circle(e.x, e.y, 6f * e.fout());
                 color();
                 Fill.circle(e.x, e.y, 3f * e.fout());
                 Drawf.light(e.x, e.y, rad * 1.6f, XColors.ember1, e.fout());
             });

             trailEffect = new ParticleEffect() {{
                 layer = Layer.flyingUnit +0.1f;
                 particles = 1;
                 length = 0;
                 lifetime = 15;
                 sizeFrom = 5;

                 colorFrom = XColors.ember1;

                 colorTo = XColors.ember2
                 ;
             }};
         }};

    assaultTypeBullet = new BulletType(3f, 200){{
        trailChance = 100f;
        lifetime = 64f;
        hitSize = 8;
        drawSize = 30f;
        splashDamageRadius = 24f;
        splashDamage = 50f;
        buildingDamageMultiplier = 0.2f;
        hitSound = Sounds.explosion;
        hitEffect = new Effect(50f, 30f, e -> {
            float rad = 16f;
            e.scaled(7f, b -> {
                color(XColors.attackClassEC1, b.fout());
                Fill.circle(e.x, e.y, rad);
            });

            color(XColors.attackClassEC1);
            stroke(e.fout() * 3f);
            Lines.circle(e.x, e.y, rad);

            int points = 6;
            float offset = Mathf.randomSeed(e.id, 360f);
            for(int i = 0; i < points; i++){
                float angle = i* 360f / points + offset;
                Drawf.tri(e.x + Angles.trnsx(angle, rad), e.y + Angles.trnsy(angle, rad), 3f, 12f * e.fout(), angle);
            }
            Fill.circle(e.x, e.y, 6f * e.fout());
            color();
            Fill.circle(e.x, e.y, 3f * e.fout());
            Drawf.light(e.x, e.y, rad * 1.6f, XColors.attackClassEC1, e.fout());
        });

        trailEffect = new Effect(0.1f, 18f, e->{
            float range = 4f;
            float blinkScl = 20f;
            int sectors = 6;
            float effectRadius = 4f, blinkSize = 0.1f;
            float rotateSpeed = 6f;
            float sectorRad = 0.14f;
            Draw.z(layer);
            Draw.color(XColors.attackClassEC2);
            float orbRadius = effectRadius * (1f + Mathf.absin(blinkScl, blinkSize));

            Fill.circle(e.x, e.y, orbRadius);
            Draw.color();
            Fill.circle(e.x, e.y, orbRadius / 2f);

            Lines.stroke((0.7f + Mathf.absin(blinkScl, 0.7f)), XColors.attackClassEC2);

            for(int i = 0; i < sectors; i++){
                float rot = e.rotation + i * 360f/sectors - Time.time * rotateSpeed;
                Lines.arc(e.x, e.y, orbRadius + 3f, sectorRad, rot);
            }

                for(int i = 0; i < sectors; i++){
                    float rot = e.rotation + i * 360f/sectors + Time.time * rotateSpeed;
                    Lines.arc(e.x, e.y, range, sectorRad, rot);
                }

            Drawf.light(e.x, e.y, range * 1.5f, XColors.attackClassEC2, 0.8f);

            Draw.reset();
        });
    }};
 }
}
