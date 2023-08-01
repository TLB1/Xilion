package xilion.blockTypes;

import arc.graphics.g2d.Draw;
import mindustry.gen.Building;
import mindustry.type.Liquid;
import mindustry.world.blocks.liquid.LiquidRouter;

public class XLiquidDistributor extends LiquidRouter {

    public XLiquidDistributor(String name) {
        super(name);
        rotate = true;
        liquidPadding = 1f;
    }
    public class LiquidDistributorBuild extends LiquidRouterBuild{

        @Override
        public void draw(){

            Draw.rect(bottomRegion, x, y, rotation*90f);

            if(liquids.currentAmount() > 0.001f){
                drawTiledFrames(size, x, y, liquidPadding, liquids.current(), liquids.currentAmount() / liquidCapacity);
            }

            Draw.rect(region, x, y, rotation*90f);
        }

        @Override
        public boolean acceptLiquid(Building source, Liquid liquid){
            return (liquids.current() == liquid || liquids.currentAmount() < 0.2f) && (source.relativeTo(tile.x, tile.y) % 4 == rotation);
        }
    }
}
