package xilion.blockTypes;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.struct.Seq;
import arc.util.Eachable;
import arc.util.Nullable;
import mindustry.Vars;
import mindustry.entities.TargetPriority;
import mindustry.entities.units.BuildPlan;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Pal;
import mindustry.input.Placement;
import mindustry.type.Liquid;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.blocks.distribution.DirectionBridge;
import mindustry.world.blocks.liquid.LiquidBlock;
import mindustry.world.meta.BlockGroup;
import mindustry.world.meta.Env;

import static mindustry.Vars.*;
import static mindustry.Vars.tilesize;

public class XLiquidTunnel extends Block {
    private static BuildPlan otherReq;
    private int otherDst = 0;
    public int range = 5;
    public final int timerFlow = timers++;

    public float speed = 5f;
    public float liquidPadding = 1f;

    TextureRegion bottomRegion, dirRegion /*,activeRegion*/;

    public XLiquidTunnel(String name) {
        super(name);
        update = true;
        solid = true;
        rotate = true;
        outputsLiquid = true;
        group = BlockGroup.liquids;
        canOverdrive = false;
        liquidCapacity = 20f;
        hasLiquids = true;
        noUpdateDisabled = true;
        priority = TargetPriority.transport;
        envEnabled = Env.space | Env.terrestrial | Env.underwater;
        drawArrow = false;
        //regionRotated1 = 1;
    }


    @Override
    public void load() {
        super.load();
        bottomRegion = Core.atlas.find(name + "-bottom");
        dirRegion = Core.atlas.find(name + "-dir");
        //activeRegion = Base.atlas.find(name+"-active");
        fullIcon = Core.atlas.find(name + "-full");
    }

    @Override
    public void init() {
        updateClipRadius((range + 0.5f) * tilesize);
        super.init();
    }

    @Override
    public void drawPlanRegion(BuildPlan plan, Eachable<BuildPlan> list) {
        Draw.rect(region, plan.drawx(), plan.drawy());
        Draw.rect(dirRegion, plan.drawx(), plan.drawy(), plan.rotation * 90);
    }

    @Override
    public void drawPlanConfigTop(BuildPlan plan, Eachable<BuildPlan> list) {
        otherReq = null;
        otherDst = range;
        Point2 d = Geometry.d4(plan.rotation);
        list.each(other -> {
            if (other.block == this && plan != other && Mathf.clamp(other.x - plan.x, -1, 1) == d.x && Mathf.clamp(other.y - plan.y, -1, 1) == d.y) {
                int dst = Math.max(Math.abs(other.x - plan.x), Math.abs(other.y - plan.y));
                if (dst <= otherDst) {
                    otherReq = other;
                    otherDst = dst;
                }
            }
        });
    }

    @Override
    public TextureRegion[] icons() {
        return new TextureRegion[]{fullIcon};
    }


    @Override
    public void changePlacementPath(Seq<Point2> points, int rotation) {
        Placement.calculateNodes(points, this, rotation, (point, other) -> Math.max(Math.abs(point.x - other.x), Math.abs(point.y - other.y)) <= range);
    }

    public void drawPlace(int x, int y, int rotation, boolean valid, boolean line) {
        int length = range;
        Building found = null;
        int dx = Geometry.d4x(rotation), dy = Geometry.d4y(rotation);

        //find the link
        for (int i = 1; i <= range; i++) {
            Tile other = world.tile(x + dx * i, y + dy * i);

            if (other != null && other.build instanceof DirectionBridge.DirectionBridgeBuild build && build.block == this && build.team == player.team()) {
                length = i;
                found = other.build;
                break;
            }
        }

        if (line || found != null) {
            Drawf.dashLine(Pal.placing,
                    x * tilesize + dx * (tilesize / 2f + 2),
                    y * tilesize + dy * (tilesize / 2f + 2),
                    x * tilesize + dx * (length) * tilesize,
                    y * tilesize + dy * (length) * tilesize
            );
        }

        if (found != null) {
            if (line) {
                Drawf.square(found.x, found.y, found.block.size * tilesize / 2f + 2.5f, 0f);
                Drawf.square(x, y, size * tilesize / 2f + 2.5f, 0f);
            } else {
                Drawf.square(found.x, found.y, 2f);
                Drawf.square(x, y, 2f);
            }

        }
    }

    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid) {
        super.drawPlace(x, y, rotation, valid);

        drawPlace(x, y, rotation, valid, true);
    }


    public boolean positionsValid(int x1, int y1, int x2, int y2) {
        if (x1 == x2) {
            return Math.abs(y1 - y2) <= range;
        } else if (y1 == y2) {
            return Math.abs(x1 - x2) <= range;
        } else {
            return false;
        }
    }

    public class LiquidTunnelBuild extends Building {

        @Override
        public void updateTile() {
            if (liquids.currentAmount() > 0.01f) {
                if (liquids.currentAmount() > 0.0001f && timer(timerFlow, 4)) {
                    dumpLiquid(liquids.current(), 1.2f, 2);
                    var link = findLink();
                    if (link != null) {
                        if (link.liquids.currentAmount() < this.liquids.currentAmount()) {
                            transferLiquid(link, (liquids.currentAmount() - link.liquids.currentAmount()) / 2, liquids.current());
                        }
                    }
                }
            }
        }

        @Override
        public void draw() {
            Draw.rect(bottomRegion, x, y);

            if (liquids.currentAmount() > 0.001f) {
                LiquidBlock.drawTiledFrames(size, x, y, liquidPadding, liquids.current(), liquids.currentAmount() / liquidCapacity);
            }

            Draw.rect(block.region, x, y);

            Draw.rect(dirRegion, x, y, rotdeg());
            /*
            var link = findLink();
            if(link != null){
                Draw.rect(activeRegion,x, y, rotdeg());
            }
            */

        }

        @Override
        public void drawSelect() {
            drawPlace(tile.x, tile.y, rotation, true, false);
            //draw incoming bridges

            int dir = rotation;
            int dx = Geometry.d4x(dir), dy = Geometry.d4y(dir);
            Building found = (Building) findLink();

            if (found != null) {
                int length = Math.max(Math.abs(found.tileX() - tileX()), Math.abs(found.tileY() - tileY()));
                Drawf.dashLine(Pal.place,
                        found.x - dx * (tilesize / 2f + 2),
                        found.y - dy * (tilesize / 2f + 2),
                        found.x - dx * (length) * tilesize,
                        found.y - dy * (length) * tilesize
                );

                Drawf.square(found.x, found.y, 2f, 45f, Pal.place);
            }
        }


        @Nullable
        public XLiquidTunnel.LiquidTunnelBuild findLink() {
            for (int i = 1; i <= range; i++) {
                Tile other = tile.nearby(Geometry.d4x(rotation) * i, Geometry.d4y(rotation) * i);
                if (other != null && other.build instanceof XLiquidTunnel.LiquidTunnelBuild build && build.block == XLiquidTunnel.this && build.team == team && ((build.rotation + 2) % 4 == this.rotation)) {
                    return build;
                }
            }
            return null;
        }

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid) {
            var link = findLink();
            //only accept if there's an output point, or it comes from a link
            if (link == null && !(source instanceof XLiquidTunnel.LiquidTunnelBuild b && b.findLink() == this))
                return false;
            if (link != null && source instanceof XLiquidTunnel.LiquidTunnelBuild b && b.liquids.get(b.liquids.current()) > liquids.get(liquids.current()))
                return true;
            int rel = this.relativeToEdge(source.tile);

            return
                    hasLiquids && team == source.team &&
                            (liquids.current() == liquid || liquids.get(liquids.current()) < 0.2f) && rel != rotation;
        }
    }
}
