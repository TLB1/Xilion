package xilion.blockTypes;

import arc.audio.Sound;
import arc.math.Mathf;
import arc.struct.Sort;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.UnitSorts;
import mindustry.entities.Units;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.blocks.power.NuclearReactor;
import mindustry.world.blocks.power.PowerGenerator;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;

public class XBomb extends XTrap{

    public float triggerRadius = 10f;
    public float explosionRadius = 20f;
    public float explosionShake, explosionShakeDuration;
    public float explosionDamage = 100f;
    public float smokeChance = 0.05f;
    public Effect explodeEffect;
    public Sound explodeSound;
    public XBomb(String name) {
        super(name);
        activationTime = 30f;
        canStopActivating = false;
        explodeEffect = Fx.none;
        explodeSound = Sounds.none;
    }

    @Override
    public void setStats() {
        super.setStats();
        stats.add(Stat.damage, explosionDamage);
        stats.add(Stat.range, explosionRadius/8, StatUnit.blocks);
        stats.add(Stat.charge, activationTime/60f, StatUnit.seconds);
    }

    public class XBombBuild extends XTrapBuild{
        @Override
        public void drawDetonationProgress() {
            super.drawDetonationProgress();
            if(Mathf.chance(size*smokeChance*Time.delta)){
                Fx.reactorsmoke.at(this.x + Mathf.range((float)(size * 8) / 2.0F), this.y + Mathf.range((float)(size * 8) / 2.0F));
            }
        }

        @Override
        public boolean shouldActivate() {
            Unit target = Units.bestEnemy(this.team, this.x, this.y, triggerRadius, (e) -> (!e.dead() && !e.isFlying()), UnitSorts.strongest);
            return target != null;
        }

        @Override
        public void detonate() {
            Effect.shake(explosionShake, explosionShakeDuration, this);
            Damage.damage(this.x, this.y,explosionRadius, explosionDamage);
            explodeEffect.at(this);
            explodeSound.at(this);
            super.detonate();
        }
    }
}
