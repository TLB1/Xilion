package xilion.util;

import arc.Events;
import arc.util.Log;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.UnitTypes;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.type.UnitType;
import mindustry.world.Block;
import mindustry.world.Tile;
import xilion.content.XBlocks;
import xilion.content.XUnitTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class XUnitHandler {
    public static HashMap<UnitType, Integer> unitMap = new HashMap<>();
    public static HashMap<Block, MaxUnitModifier> blockMap = new HashMap<>();
    public static UnitCap[] unitCaps = new UnitCap[255];

    private static void initUnitMap(){
        //Add in-game Erekir units
        unitMap.put(UnitTypes.merui,0);
        unitMap.put(UnitTypes.cleroi,1);
        unitMap.put(UnitTypes.anthicus,2);
        unitMap.put(UnitTypes.tecta, 3);
        unitMap.put(UnitTypes.collaris, 4);
        unitMap.put(UnitTypes.stell, 0);
        unitMap.put(UnitTypes.locus,1);
        unitMap.put(UnitTypes.precept,2);
        unitMap.put(UnitTypes.vanquish, 3);
        unitMap.put(UnitTypes.conquer, 4);
        unitMap.put(UnitTypes.elude, 0);
        unitMap.put(UnitTypes.avert,1);
        unitMap.put(UnitTypes.obviate,2);
        unitMap.put(UnitTypes.quell, 3);
        unitMap.put(UnitTypes.disrupt, 4);

        unitMap.put(XUnitTypes.bug, 0);

        unitMap.put(XUnitTypes.aura, 0);
        unitMap.put(XUnitTypes.blaze, 0);
        unitMap.put(XUnitTypes.acari, 0);
        unitMap.put(XUnitTypes.sanatick, 0);
    }
    private static void initBlockMap(){
        blockMap.put(Blocks.coreBastion, new MaxUnitModifier(20,10,5,0,0));
        blockMap.put(Blocks.coreCitadel, new MaxUnitModifier(25,15,10,5,0));
        blockMap.put(Blocks.coreAcropolis, new MaxUnitModifier(30,20,15,10,5));
        blockMap.put(XBlocks.Base.coreExplorer, new MaxUnitModifier(20, 10, 5, 0, 0));
    }
    public static void init(){
        initBlockMap();
        initUnitMap();
         //Arrays.fill( unitCaps,new UnitCap());  // PLZ NO
        for (int i = 0; i < unitCaps.length; i++) {
            unitCaps[i] = new UnitCap();
        }
        // handle events for it to work properly
        Events.on(EventType.UnitDestroyEvent.class, e->{
          XUnitHandler.getUnitCap(e.unit.team()).removeUnit(e.unit.type());
        });
        Events.on(EventType.UnitDrownEvent.class, e->{
            XUnitHandler.getUnitCap(e.unit.team()).removeUnit(  e.unit.type());
        });
        /*
        Events.on(EventType.UnitCreateEvent.class, e -> {
            XUnitHandler.getUnitCap(e.unit.team()).tryAddToUnits(e.unit.type());
            Log.info("CreateEvent");
        });
        Events.on(EventType.UnitChangeEvent.class, e -> {
            Log.info("UnitChangeEvent");
        });
        */
        Events.on(EventType.UnitUnloadEvent.class, e -> {
            Log.info("UnitUnloadEvent");
        });
        /*
        Events.on(EventType.UnitSpawnEvent.class, e -> {
            XUnitHandler.getUnitCap(e.unit.team()).tryAddToUnits(e.unit.type());
            Log.info("SpawnEvent");
        });

         */

        Events.on(EventType.WorldLoadEndEvent.class, e->{
          load();
        });
        /*
        Events.on(EventType.SaveWriteEvent.class, e->{

            String additionalSaveFile;
            DataInputStream stream;

               additionalSaveFile = Vars.control.saves.getCurrent().file.pathWithoutExtension() + ".xilion";
               Fi file = Fi.get(additionalSaveFile);
               if(file.exists()){
                   stream = new DataInputStream(new InflaterInputStream(file.read(8192)));
               }


        });

         */
    }

    public static void load(){
       /* for (UnitCap uc: unitCaps) {
            uc.createBaseCap();
        }

        */
        checkCores();

    }
    public static UnitCap getUnitCap(Team team){
        return unitCaps[team.id];
    }

    public static void checkCores(){
        List<Building> knownCores = new ArrayList<>();
        for (UnitCap uc: unitCaps) {
            uc.clearMax();
        }
        for (Tile tile : Vars.world.tiles) {
         if(tile.build != null && tile.build.block != null){

           if(blockMap.get(tile.build.block) != null){
               if(knownCores.contains(tile.build)) break;
               knownCores.add(tile.build);
            getUnitCap(tile.build.team()).addMaxUnitModifier(blockMap.get(tile.build.block));
               Log.info(tile.build.team()+": "+ Arrays.toString(getUnitCap(tile.build.team()).maxUnits));

           }
         }
        }
    }

    public static class MaxUnitModifier{
        private int[] maxUnits = new int[5];
        MaxUnitModifier(int t1, int t2, int t3, int t4, int t5){
            maxUnits[0] = t1;
            maxUnits[1] = t2;
            maxUnits[2] = t3;
            maxUnits[3] = t4;
            maxUnits[4] = t5;
        }
    }
    public static class UnitCap{
        private int[] maxUnits = new int[5];
        private int[] unitCount = new int[5];
        public int getUnitCap(int tier){
            if (tier > 5 || tier < 1) return 0;
            return maxUnits[tier-1];
        }
        public int getUnitCount(int tier){
            if (tier > 5 || tier < 1) return 0;
            return unitCount[tier-1];
        }

        public void clearMax(){
            Arrays.fill(maxUnits, 0);
        }
        private UnitCap(){
            Arrays.fill(maxUnits, 0);
            Arrays.fill(unitCount, 0);
        }
        public void addMaxUnitModifier(MaxUnitModifier modifier){
            for (int i = 0; i < modifier.maxUnits.length; i++) {
                maxUnits[i] += modifier.maxUnits[i];
            }
        }
        public UnitCap createBaseCap(){
            UnitCap uc = new UnitCap();
            uc.maxUnits[0] = 15;
            uc.maxUnits[1] = 10;
            uc.maxUnits[2] = 5;
            return  uc;
        }

        public boolean canAddUnit(UnitType type){
            int tier = (int)(unitMap.get(type) == null ? 0 : unitMap.get(type));
            return unitCount[tier] < maxUnits[tier];
        }
        public boolean tryAddToUnits(UnitType type){
            int tier = (int)(unitMap.get(type) == null ? 0 : unitMap.get(type));
            if(unitCount[tier] < maxUnits[tier]) unitCount[tier]++;
            Log.info("Added a new tier "+ tier +"unit: "+ type.name +" count: " + unitCount[tier]);

            return unitCount[tier] <= maxUnits[tier];
        }
        public void removeUnit(UnitType type){
            int tier = (int)(unitMap.get(type) == null ? 0 : unitMap.get(type));
            Log.info("Removed a new tier "+ tier +"unit: "+ type.name);
            unitCount[tier]--;
        }
    }
}
