package xilion.content;

import arc.graphics.g2d.Fill;
import arc.math.Rand;
import arc.math.geom.Vec2;
import mindustry.content.Items;
import mindustry.entities.Effect;

import static arc.graphics.g2d.Draw.alpha;
import static arc.graphics.g2d.Draw.color;

public class XFx {
    public static final Vec2 v = new Vec2();
    public static final Rand rand = new Rand();
    public static Effect thoriumFission;

    public void load(){
        thoriumFission = new Effect(400f, e -> {
            color(Items.thorium.color);
            alpha(0.7f);
            rand.setSeed(e.id);
            for(int i = 0; i < 3; i++){
                float len = rand.random(15f), rot = rand.range(20f) + 45f;

                e.scaled(e.lifetime * rand.random(0.3f, 1f), b -> {
                    v.trns(rot, (15f +len) * b.fin()+ (5f *b.fslope()));
                    Fill.circle(e.x + v.x, e.y + v.y, 7f * b.fslope() + 2f);
                });
            }
        });
    }
}
