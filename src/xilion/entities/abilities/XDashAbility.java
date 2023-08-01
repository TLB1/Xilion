package xilion.entities.abilities;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;
import mindustry.graphics.Layer;
import mindustry.type.UnitType;
import xilion.content.XColors;
import xilion.content.XSounds;
import xilion.content.XStatusEffects;

public class XDashAbility extends Ability {
    public float reload = 360, duration = 24;
    public float x, y, radius, rotation;
    protected float timer;

    protected boolean isBoosting = false;
    protected float lastX = 0f;
    protected float lastY = 0f;


    XDashAbility(){}

    public XDashAbility(float duration, float reload){
        this.duration = duration;
        this.reload = reload;
    }

    public String localized() {
        return Core.bundle.get("ability.dash");
    }
    @Override
    public void update(Unit unit){
        timer += Time.delta;

        if(timer >= reload){
            if (unit.isPlayer()){
                if (unit.getPlayer().boosting && (unit.x != lastX || unit.y != lastY)){
                    unit.apply(XStatusEffects.leapDash, duration);
                    XSounds.dashAbilitySound.at(unit.x, unit.y, 1f,0.75f);
                    timer = 0;
                    isBoosting =true;
                    draw(unit);
                } else {
                    timer = reload -Time.delta;
                }
            } else {

                if (unit.moving()){
                    unit.apply(XStatusEffects.leapDash, duration);
                    XSounds.dashAbilitySound.at(unit.x, unit.y, 1f,0.5f);
                    timer = 0;
                    isBoosting =true;
                    draw(unit);
                } else {
                    timer = reload -Time.delta;
                }
            }
        }
        if (timer == Time.delta*duration){

        }
        lastX = unit.x;
        lastY = unit.y;

    }
    @Override
    public void draw(Unit unit) {
        super.draw(unit);
        if(timer < duration && isBoosting){
        UnitType type = unit.type;
        float scale = type.useEngineElevation ? unit.elevation : 1f;

        if(scale <= 0.0001f) return;
        y = -type.engineOffset;
        x=0;
        float rot = unit.rotation - 90;
        Tmp.v1.set(x, y).rotate(rot);
        float ex = Tmp.v1.x, ey = Tmp.v1.y;
        radius = type.engineSize;

        float z = Draw.z();
        Draw.z(Layer.flyingUnit-0.01f);

        Draw.color(XColors.QuickClassEC2);
        Fill.circle(
                unit.x + ex,
                unit.y + ey,
                (radius + Mathf.absin(Time.time, 2f, radius / 4f)) * scale*1.5f
        );
        Draw.color(XColors.QuickClassEC1);
        Fill.circle(
                unit.x + ex - Angles.trnsx(rot + rotation, 1f),
                unit.y + ey - Angles.trnsy(rot + rotation, 1f),
                (radius + Mathf.absin(Time.time, 2f, radius / 4f))/1.5f  * scale
        );
        Draw.z(z);

    }else isBoosting = false;

    }
}
