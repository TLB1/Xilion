package xilion.blockTypes;

import mindustry.content.StatusEffects;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.Units;
import mindustry.type.StatusEffect;

public class XStatusMine extends XBomb{
    public float statusRadius; // in wu
    public float statusDuration; // in game-ticks
    public StatusEffect status;
    public XStatusMine(String name) {
        super(name);
        statusRadius = 16f;
        explosionDamage = 50f;
        statusDuration = 120f;
        status = StatusEffects.none;
    }
    @Override
    public void setStats() {
        super.setStats();
    }
    public class XStatusMineBuild extends XBombBuild{
        @Override
        public void detonate() {
            Effect.shake(explosionShake, explosionShakeDuration, this);
            Damage.damage(this.x, this.y,explosionRadius, explosionDamage);
            Units.nearby(null, this.x, this.y, statusRadius, other -> other.apply(status, statusDuration));
            explodeEffect.at(this);
            explodeSound.at(this);
            kill();
        }
    }
}
