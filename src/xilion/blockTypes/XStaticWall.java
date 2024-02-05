package xilion.blockTypes;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Point2;
import mindustry.graphics.CacheLayer;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.Prop;

import static mindustry.Vars.world;

public class XStaticWall extends Prop {

    //public static Random rand;
    public TextureRegion[] large;
    public TextureRegion[][][] split;
    public int largeVariants;


    public XStaticWall(String name){
        super(name);
        //rand =  new Random();
        largeVariants = 1;
        breakable = alwaysReplace = false;
        solid = true;
        variants = 2;
        cacheLayer = CacheLayer.walls;
    }

    @Override
    public void drawBase(Tile tile){
        int rx = tile.x / 2 * 2;
        int ry = tile.y / 2 * 2;

        if(largeVariants > 0 && eq(rx, ry) && Mathf.randomSeed(Point2.pack(rx, ry)) < 0.5){
            //rand.setSeed((rx >> 1  << 16 | ry >> 1  << 8)+ 9990*(rx >> 1 % 255)+ ry >> 1 + rx * ry);
            Draw.rect(split[Mathf.randomSeed(rx >> 1  << 16 | ry >> 1, 0, Math.max(0,largeVariants - 1))][tile.x % 2][1 - tile.y % 2], tile.worldx(), tile.worldy());
        }else if(variants > 0){
            Draw.rect(variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))], tile.worldx(), tile.worldy());
        }else{
            Draw.rect(region, tile.worldx(), tile.worldy());
        }

        //draw ore on top
        if(tile.overlay().wallOre){
            tile.overlay().drawBase(tile);
        }
    }

    @Override
    public void load(){
        super.load();
        large = new TextureRegion[largeVariants];
        split = new TextureRegion[largeVariants][][];
        //variantRegions = new TextureRegion[variants];
        //TextureRegion allRegion = Base.atlas.find(name+"-map");
        for (int i = 1; i < largeVariants +1; i++) {
           large[i-1] =  Core.atlas.find(name+"-large"+i);
        }
        for (int i = 1; i < largeVariants +1; i++) {
            split[i-1] = large[i-1].split(32, 32);
        }

    }

    boolean eq(int rx, int ry){
        return rx < world.width() - 1 && ry < world.height() - 1
                && world.tile(rx + 1, ry).block() == this
                && world.tile(rx, ry + 1).block() == this
                && world.tile(rx, ry).block() == this
                && world.tile(rx + 1, ry + 1).block() == this;
    }
}
