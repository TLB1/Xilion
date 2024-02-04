package xilion.blockTypes;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Time;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

public class XTechWall extends Wall {
    public TextureRegion glowRegion;
    public XTechWall(String name) {
        super(name);
        conductivePower = true;
        hasPower = true;
        connectedPower = true;
        consumesPower = true;
        update = true;
    }

    public void setTechStats(){
        consumePowerBuffered(Mathf.pow(size, 2)*100);
        consumePower(size*8f);
    }

    @Override
    public void load() {
        glowRegion = Core.atlas.find(name+"-glow");
        uiIcon = Core.atlas.find(name+"-ui");
        super.load();
    }

    @Override
    public void setStats() {
        this.stats.add(Stat.healing, size*2*60, StatUnit.perSecond);
        super.setStats();
    }

    public class XTechWallBuild extends WallBuild{
        @Override
        public boolean shouldConsume() {
            return canConsume();
        }
        @Override
        public void updateTile() {
            if(canConsume()){
                health = Math.min(health + (size *2 * Time.delta), maxHealth);
                consume();
            }
        }
        @Override
        public boolean canConsume() {
            return this.potentialEfficiency > 0.0F && health < maxHealth;
        }
        @Override
        public void draw() {
            super.draw();
            if(power != null && power.status > 0f){
                float f = health /maxHealth;
                float opacity = Mathf.clamp(Math.max(f-(1-f)*2, 0f) + Mathf.absin(Time.time, Math.max(f * 15.0F, 3.0F), 1.0F - f));
                Draw.alpha(power.status*0.7f*opacity);
                Draw.z();
                Draw.rect(glowRegion, this.x, this.y, this.drawrot());
                Draw.alpha(1f);
            }

        }
    }

}
