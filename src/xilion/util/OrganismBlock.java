package xilion.util;

public interface OrganismBlock {

    public default int getEnergyStorageCapacity(){
        return 5;
    }
    public int getGrowRequirements();
    
    public float getPassiveEnergyRequirements();

    public default float getPassiveEnergyProduction(){
        return 0f;
    }


   interface OrganismBuild{

       public  float getEnergyStored();
       public float getSaturationLevel();
       public boolean isDecaying();
       public boolean isGrowing();
       public float growProgress();

       public void produceEnergy(float amount);

       public float receiveEnergy(float amount);
       public float transferEnergyNeeds(float saturation);
   }
}
