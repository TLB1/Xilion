package xilion.blockTypes;

import arc.Core;
import arc.func.Cons;
import arc.func.Intc2;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.util.Log;
import arc.util.Nullable;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.Tile;
import mindustry.world.blocks.production.WallCrafter;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.Stat;
import mindustry.world.meta.StatUnit;
import mindustry.world.meta.StatValues;
import xilion.content.XAttributes;
import xilion.content.XItems;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

public class XWallCrafter extends WallCrafter {
    public Attribute attribute2 = XAttributes.carbon;
    public Item output2 = XItems.carbon;

    public XWallCrafter(String name) {
        super(name);
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        float eff = getEfficiency(x, y, rotation, null, null);

        drawPlaceText(Core.bundle.formatFloat("bar.drillspeed", 60f / drillTime * eff, 2), x, y, valid);
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        return getEfficiency(tile.x, tile.y, rotation, null, null) > 0;
    }

    float getEfficiency(int tx, int ty, int rotation, @Nullable Cons<Tile> ctile, @Nullable Intc2 cpos) {
        int cornerX = tx - (size - 1) / 2, cornerY = ty - (size - 1) / 2, s = size;
        float[] effTypes = {0f, 0f};
        for (int i = 0; i < size; i++) {
            int rx = 0, ry = 0;

            switch (rotation) {
                case 0 -> {
                    rx = cornerX + s;
                    ry = cornerY + i;
                }
                case 1 -> {
                    rx = cornerX + i;
                    ry = cornerY + s;
                }
                case 2 -> {
                    rx = cornerX - 1;
                    ry = cornerY + i;
                }
                case 3 -> {
                    rx = cornerX + i;
                    ry = cornerY - 1;
                }
            }

            if (cpos != null) {
                cpos.get(rx, ry);
            }

            Tile other = world.tile(rx, ry);
            if (other != null && other.solid()) {
                float at = other.block().attributes.get(attribute);
                float at2 = other.block().attributes.get(attribute2);
                effTypes[0] += at;
                effTypes[1] += at2;
                if ((at > 0 || at2 > 0) && ctile != null) {
                    ctile.get(other);
                }
            }
        }
        return Math.max(effTypes[0], effTypes[1]);
    }

    boolean getMainAttribute(int tx, int ty, int rotation) {
        int cornerX = tx - (size - 1) / 2, cornerY = ty - (size - 1) / 2, s = size;
        float[] effTypes = {0f, 0f};
        for (int i = 0; i < size; i++) {
            int rx = 0, ry = 0;

            switch (rotation) {
                case 0 -> {
                    rx = cornerX + s;
                    ry = cornerY + i;
                }
                case 1 -> {
                    rx = cornerX + i;
                    ry = cornerY + s;
                }
                case 2 -> {
                    rx = cornerX - 1;
                    ry = cornerY + i;
                }
                case 3 -> {
                    rx = cornerX + i;
                    ry = cornerY - 1;
                }
            }


            Tile other = world.tile(rx, ry);
            if (other != null && other.solid()) {
                float at = other.block().attributes.get(attribute);
                float at2 = other.block().attributes.get(attribute2);
                effTypes[0] += at;
                effTypes[1] += at2;
            }
        }
        return effTypes[0] > effTypes[1];
    }

    @Override
    public void setStats() {
        super.setStats();

        stats.add(Stat.output, output2);
        stats.add(Stat.tiles, StatValues.blocks(attribute2, floating, 1f, true, false));
        stats.add(Stat.drillSpeed, 60f / drillTime * size, StatUnit.itemsSecond);
    }

    public class XWallCrafterBuild extends WallCrafterBuild {
        @Override
        public boolean isValid() {
            return super.isValid();
        }

        @Override
        public boolean canDump(Building to, Item item) {
            if (item.equals(XItems.carbon)) return true;
            return super.canDump(to, item);
        }

        @Override
        public boolean shouldConsume() {
            return items.get(output) < itemCapacity & items.get(output2) < itemCapacity;
        }

        public void dumpRemaining() {
            for (int i = 0; i < items.get(output2); i++) {
                dump(output2);
            }
        }

        @Override
        public void updateTile() {
            super.updateTile();
            dumpRemaining();

            boolean cons = shouldConsume();

            warmup = Mathf.approachDelta(warmup, Mathf.num(efficiency > 0), 1f / 40f);
            float dx = Geometry.d4x(rotation) * 0.5f, dy = Geometry.d4y(rotation) * 0.5f;

            float eff = getEfficiency(tile.x, tile.y, rotation, dest -> {

                if (wasVisible && cons && Mathf.chanceDelta(updateEffectChance * warmup)) {
                    updateEffect.at(
                            dest.worldx() + Mathf.range(3f) - dx * tilesize,
                            dest.worldy() + Mathf.range(3f) - dy * tilesize,
                            dest.block().mapColor
                    );
                }
            }, null);

            lastEfficiency = eff * timeScale * efficiency;

            if (cons && (time += edelta() * eff) >= drillTime) {
                if (getMainAttribute(tile.x, tile.y, rotation)) {
                    offload(output);
                } else offload(output2);
                time %= drillTime;
            }

            totalTime += edelta() * warmup;

            if (timer(timerDump, dumpTime)) {
                dump();
            }
        }
    }
}
