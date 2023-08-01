package xilion.blockTypes;

import arc.Events;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.content.Items;
import mindustry.game.EventType;
import mindustry.world.blocks.power.ConsumeGenerator;

public class XFissionReactor extends ConsumeGenerator {
    public XFissionReactor(String name) {
        super(name);
    }

    @Override
    protected void initBuilding() {
        super.initBuilding();
    }

    public class UPFissionReactorBuild extends ConsumeGeneratorBuild{

        @Override
        public void updateTile(){
            boolean valid = efficiency > 0;

            warmup = Mathf.lerpDelta(warmup, valid ? 1f : 0f, warmupSpeed);

            productionEfficiency = efficiency * efficiencyMultiplier;
            totalTime += warmup * Time.delta;

            //randomly produce the effect
            if(valid && Mathf.chanceDelta(effectChance)){
                generateEffect.at(x + Mathf.range(generateEffectRange), y + Mathf.range(generateEffectRange));
            }
            if(items.has(Items.thorium) && liquids.currentAmount() < 0.51f && generateTime > 0.5f){
                createExplosion();
                //kill();
               // kill();
               // Events.fire(new EventType.GeneratorPressureExplodeEvent(this));

            }
            //take in items periodically
            if(hasItems && valid && generateTime <= 0f){
                consume();
                consumeEffect.at(x + Mathf.range(generateEffectRange), y + Mathf.range(generateEffectRange));
                generateTime = 1f;
            }

            if(outputLiquid != null){
                float added = Math.min(productionEfficiency * delta() * outputLiquid.amount, liquidCapacity - liquids.get(outputLiquid.liquid));
                liquids.add(outputLiquid.liquid, added);
                dumpLiquid(outputLiquid.liquid);

                if(explodeOnFull && liquids.get(outputLiquid.liquid) >= liquidCapacity - 0.0001f){
                    kill();
                    Events.fire(new EventType.GeneratorPressureExplodeEvent(this));
                }
            }

            //generation time always goes down, but only at the end so consumeTriggerValid doesn't assume fake items
            generateTime -= delta() / itemDuration;
        }
    }
}
