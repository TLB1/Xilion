package xilion.blockTypes;

import arc.graphics.g2d.Draw;
import mindustry.world.blocks.liquid.LiquidRouter;

public class XLiquidCollector extends LiquidRouter {
    public XLiquidCollector(String name) {
        super(name);
        rotate = true;
        liquidPadding = 1f;
    }

    public class LiquidCollectorBuild extends LiquidRouterBuild {

        @Override
        public void updateTile() {
            if (liquids.currentAmount() > 0.0001f) {
                //Dump liquid only into the direction of the block
                dumpLiquid(liquids.current(), 0.05f, 0);
            }
        }

        @Override
        public void draw() {

            Draw.rect(bottomRegion, x, y, rotation * 90f);

            if (liquids.currentAmount() > 0.001f) {
                drawTiledFrames(size, x, y, liquidPadding, liquids.current(), liquids.currentAmount() / liquidCapacity);
            }
            //arrows and top region
            Draw.rect(region, x, y, rotation * 90f);
        }

    }
}
