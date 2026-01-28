package xilion.blockTypes;

import arc.struct.ObjectMap;
import mindustry.entities.bullet.BulletType;
import mindustry.logic.LAccess;
import mindustry.world.blocks.defense.turrets.Turret;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatValues;

public class BattleTurret extends Turret {
    public BulletType shootType;

    public BattleTurret(String name){
        super("battle-" + name);
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.ammo, StatValues.ammo(ObjectMap.of(this, shootType)));
    }

    public void limitRange(float margin){
        limitRange(shootType, margin);
    }

    public class BattleTurretBuild extends TurretBuild {

        @Override
        public void updateTile() {
            super.updateTile();
        }

        @Override
        public double sense(LAccess sensor) {
            return switch (sensor) {
                case ammo -> 1.0f;
                case ammoCapacity -> 1;
                default -> super.sense(sensor);
            };
        }

        @Override
        public BulletType useAmmo() {
            return shootType;
        }

        @Override
        public boolean hasAmmo() {
            return true;
        }

        @Override
        public BulletType peekAmmo() {
            return shootType;
        }
    }
}
