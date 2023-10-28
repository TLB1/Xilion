package xilion.blockTypes;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import mindustry.gen.Building;
import mindustry.world.blocks.production.GenericCrafter;
import xilion.util.OrganismBlock;


public class Eater extends GenericCrafter implements OrganismBlock {

    public TextureRegion upperTeeth, lowerTeeth, support;

    @Override
    public void load() {
        super.load();
        upperTeeth = Core.atlas.find(name+"-upper-teeth");
        lowerTeeth = Core.atlas.find(name+"-lower-teeth");
        support = Core.atlas.find(name + "-support");
    }

    public Eater(String name) {
        super(name);
    }
    @Override
    public int getEnergyStorageCapacity(){
        return 150;
    }
    @Override
    public int getGrowRequirements() {
        return 200;
    }

    @Override
    public float getPassiveEnergyRequirements() {
        return 10;
    }

    class EaterBuild extends GenericCrafterBuild implements OrganismBuild{


        @Override
        public void draw() {
            super.draw();
        }

        @Override
        public Building buildOn() {

            return super.buildOn();
        }

        float storedEnergy = 0f;

        @Override
        public float getEnergyStored() {
            return storedEnergy;
        }

        @Override
        public float getSaturationLevel() {
            return getEnergyStored() / ((OrganismBlock)block).getEnergyStorageCapacity();
        }

        @Override
        public boolean isDecaying() {
            return storedEnergy < ((OrganismBlock)block).getPassiveEnergyRequirements();
        }

        @Override
        public boolean isGrowing() {
            return false;
        }

        @Override
        public float growProgress() {
            return 0;
        }

        @Override
        public void produceEnergy(float amount) {

        }

        @Override
        public float receiveEnergy(float amount) {
            float allowed = Math.min(amount, ((OrganismBlock)block).getEnergyStorageCapacity()-storedEnergy);
            storedEnergy += allowed;
            return allowed;
        }

        @Override
        public float transferEnergyNeeds(float saturation) {
            return saturation - getSaturationLevel();
        }
    }
}
