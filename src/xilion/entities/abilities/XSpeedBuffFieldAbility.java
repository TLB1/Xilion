package xilion.entities.abilities;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Angles;
import arc.math.Mathf;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.entities.abilities.Ability;
import mindustry.gen.Unit;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.type.StatusEffect;
import xilion.content.XColors;

public class XSpeedBuffFieldAbility extends Ability {
    public StatusEffect effect;
    public float duration = 60, reload = 100, range = 20;
    public Effect applyEffect = Fx.none;
    public Effect activeEffect = Fx.overdriveWave;
    public float effectX, effectY;
    public boolean parentizeEffects;
    public float x, y;

    public float layer = Layer.bullet - 0.001f, blinkScl = 20f, blinkSize = 0.1f;
    public float effectRadius = 5f, sectorRad = 0.14f, rotateSpeed = 1f;
    public int sectors = 12;
    public Color color = XColors.QuickClassEC1;

    protected float timer, curStroke;


    XSpeedBuffFieldAbility(){}

    public XSpeedBuffFieldAbility(StatusEffect effect, float duration, float reload, float range){
        this.duration = duration;
        this.reload = reload;
        this.range = range;
        this.effect = effect;

    }

    @Override
    public String localized(){
        return Core.bundle.format("ability.speedfield", effect.emoji());
    }

    @Override
    public void update(Unit unit){
        timer += Time.delta;

        if(timer >= reload){
            Units.nearby(unit.team, unit.x, unit.y, range, other -> {
                other.apply(effect, duration);
                applyEffect.at(other, parentizeEffects);
            });

            float x = unit.x + Angles.trnsx(unit.rotation, effectY, effectX), y = unit.y + Angles.trnsy(unit.rotation, effectY, effectX);
            activeEffect.at(x, y, 120f, parentizeEffects ? unit : null);

            timer = 0f;
        }
    }
    @Override
    public void draw(Unit unit){
        super.draw(unit);

        Draw.z(layer);
        Draw.color(color);
        Tmp.v1.trns(unit.rotation - 90, x, y).add(unit.x, unit.y);
        float rx = Tmp.v1.x, ry = Tmp.v1.y;
        float orbRadius = effectRadius *Math.max(timer/reload, 0.2f) * (1f + Mathf.absin(blinkScl, blinkSize));

        Fill.circle(rx, ry, orbRadius);
        Draw.color();
        Fill.circle(rx, ry, orbRadius / 2f);

        Lines.stroke((0.7f + Mathf.absin(blinkScl, 0.7f)), color);

        for(int i = 0; i < sectors; i++){
            float rot = unit.rotation + i * 360f/sectors - Time.time * rotateSpeed;
            Lines.arc(rx, ry, orbRadius + 3f, sectorRad, rot);
        }

        Lines.stroke(Lines.getStroke() * curStroke);

        if(curStroke > 0){
            for(int i = 0; i < sectors; i++){
                float rot = unit.rotation + i * 360f/sectors + Time.time * rotateSpeed;
                Lines.arc(rx, ry, range, sectorRad, rot);
            }
        }

        Drawf.light(rx, ry, range * 1.5f, color, curStroke * 0.8f);
        Draw.reset();
    }

}
