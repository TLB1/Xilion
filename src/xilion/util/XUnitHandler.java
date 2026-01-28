package xilion.util;

import arc.Events;
import arc.util.Log;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.game.EventType;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.gen.Groups;
import mindustry.gen.Unit;
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

    public static boolean isReady;

    private static void initUnitMap() {
        unitMap.put(XUnitTypes.bug, 1);
        unitMap.put(XUnitTypes.ship, 1);

        unitMap.put(XUnitTypes.aura, 1);
        unitMap.put(XUnitTypes.strike, 1);
        unitMap.put(XUnitTypes.blaze, 1);
        unitMap.put(XUnitTypes.acari, 1);
        unitMap.put(XUnitTypes.sanatick, 1);
        unitMap.put(XUnitTypes.quick, 1);
    }

    private static void initBlockMap() {
        blockMap.put(XBlocks.Base.coreExplorer, new MaxUnitModifier(25, 10, 5, 0));
    }

    public static void init() {
        initBlockMap();
        initUnitMap();
        for (int i = 0; i < unitCaps.length; i++) {
            unitCaps[i] = new UnitCap();
        }
        // handle events for it to work properly
        Events.on(EventType.UnitDestroyEvent.class, e -> {
            XUnitHandler.getUnitCap(e.unit.team()).removeUnit(e.unit.type());
        });
        Events.on(EventType.UnitDrownEvent.class, e -> {
            XUnitHandler.getUnitCap(e.unit.team()).removeUnit(e.unit.type());
        });
        Events.on(EventType.WorldLoadBeginEvent.class, e -> {
                    isReady = false;
                    // clear existing counts first
                    for (UnitCap uc : unitCaps) {
                        Arrays.fill(uc.unitCount, 0);
                    }
                });

        Events.on(EventType.WorldLoadEndEvent.class, e -> {
            Time.run(1f, XUnitHandler::load);
        });
    }

    public static void load() {
        checkCores();
        registerExistingUnits();
        isReady = true;
    }

    public static UnitCap getUnitCap(Team team) {
        return unitCaps[team.id];
    }

    public static void checkCores() {
        List<Building> knownCores = new ArrayList<>();
        for (UnitCap uc : unitCaps) {
            uc.clearMax();
        }
        for (Tile tile : Vars.world.tiles) {
            if (tile.build != null && tile.build.block != null) {

                if (blockMap.get(tile.build.block) != null) {
                    if (knownCores.contains(tile.build)) break;
                    knownCores.add(tile.build);
                    getUnitCap(tile.build.team()).addMaxUnitModifier(blockMap.get(tile.build.block));
                    Log.info(tile.build.team() + ": " + Arrays.toString(getUnitCap(tile.build.team()).maxUnits));

                }
            }
        }
    }
    public static void registerExistingUnits() {

        // iterate all units currently in the world
        for (Unit unit : Groups.unit) {
            Team team = unit.team();
            UnitCap cap = getUnitCap(team);

            if (cap != null) {
                cap.tryAddToUnits(unit.type());
            }
        }

    }


    public static class MaxUnitModifier {
        private int[] maxUnits = new int[5];

        MaxUnitModifier(int t1, int t2, int t3, int t4) {
            maxUnits[0] = 1000;
            maxUnits[1] = t1;
            maxUnits[2] = t2;
            maxUnits[3] = t3;
            maxUnits[4] = t4;
        }
    }

    public static class UnitCap {
        private int[] maxUnits = new int[5];
        private int[] unitCount = new int[5];

        public int getUnitCap(int tier) {
            if (tier > 4 || tier < 0) return 0;
            return maxUnits[tier];
        }

        public int getUnitCount(int tier) {
            if (tier > 4 || tier < 0) return 0;
            return unitCount[tier];
        }
        public int getUnitCapForTier(UnitType type) {
            int tier = (int) (unitMap.get(type) == null ? 0 : unitMap.get(type));
            return getUnitCap(tier);
        }

        public int getUnitCountForTier(UnitType type) {
            int tier = (int) (unitMap.get(type) == null ? 0 : unitMap.get(type));
            return getUnitCount(tier);
        }

        public void clearMax() {
            Arrays.fill(maxUnits, 0);
        }

        private UnitCap() {
            Arrays.fill(maxUnits, 0);
            Arrays.fill(unitCount, 0);
        }

        public void addMaxUnitModifier(MaxUnitModifier modifier) {
            for (int i = 0; i < modifier.maxUnits.length; i++) {
                maxUnits[i] += modifier.maxUnits[i];
            }
        }

        public UnitCap createBaseCap() {
            UnitCap uc = new UnitCap();
            uc.maxUnits[0] = 15;
            uc.maxUnits[1] = 10;
            uc.maxUnits[2] = 5;
            return uc;
        }

        public boolean canAddUnit(UnitType type) {
            int tier = (int) (unitMap.get(type) == null ? 0 : unitMap.get(type));
            return unitCount[tier] < maxUnits[tier];
        }

        public boolean tryAddToUnits(UnitType type) {
            int tier = (int) (unitMap.get(type) == null ? 0 : unitMap.get(type));
            if (unitCount[tier] < maxUnits[tier]){
                unitCount[tier]++;
                Log.info("Added a new tier " + tier + "unit: " + type.name + " count: " + unitCount[tier]);
                return true;
            }
            return false;
        }

        public void removeUnit(UnitType type) {
            int tier = (int) (unitMap.get(type) == null ? 0 : unitMap.get(type));
            Log.info("Removed a new tier " + tier + "unit: " + type.name);
            unitCount[tier]--;
        }
    }
}
