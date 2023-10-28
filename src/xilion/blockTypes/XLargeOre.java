package xilion.blockTypes;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.Pixmap;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.PixmapRegion;
import arc.math.Mathf;
import arc.math.geom.Point2;
import mindustry.Vars;
import mindustry.graphics.MultiPacker;
import mindustry.type.Item;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.OverlayFloor;

import static mindustry.Vars.tilesize;

public class XLargeOre extends OverlayFloor {
    public XLargeOre(String name, Item oreItem) {
        super(name);
        itemDrop = oreItem;
        useColor = true;
        mapColor.set(oreItem.color);
        hasShadow = true;

    }

    public static final Point2[] newOffsets = {
            new Point2(0, 0),
            new Point2(-1, 0),
            new Point2(-1, -1),
            new Point2(0, -1)
    };
    @Override
    public void drawBase(Tile tile){
        if(checkAdjacent(tile)){
            Mathf.rand.setSeed(tile.pos());
            Draw.rect(variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))], tile.worldx() -4, tile.worldy() -4);
        }
    }
    public boolean checkAdjacent(Tile tile){
        for(var point : newOffsets){
            Tile other = Vars.world.tile(tile.x + point.x, tile.y + point.y);
            if(other == null || other.overlay() != this){
                return false;
            }
        }
        return true;
    }
    @Override
    public void createIcons(MultiPacker packer){
        for(int i = 0; i < variants; i++){
            //use name (e.g. "ore-copper1"), fallback to "copper1" as per the old naming system
            PixmapRegion shadow = Core.atlas.has(name + (i + 1)) ?
                    Core.atlas.getPixmap(name + (i + 1)) :
                    Core.atlas.getPixmap(itemDrop.name + (i + 1));

            Pixmap image = shadow.crop();

            int offset = image.width / tilesize/2 - 1;
            int shadowColor = Color.rgba8888(0, 0, 0, 0.3f);

            for(int x = 0; x < image.width; x++){
                for(int y = offset; y < image.height; y++){
                    if(shadow.getA(x, y) == 0 && shadow.getA(x, y - offset) != 0){
                        image.setRaw(x, y, shadowColor);
                    }
                }
            }

            packer.add(MultiPacker.PageType.environment, name + (i + 1), image);
            packer.add(MultiPacker.PageType.editor, "editor-" + name + (i + 1), image);

            if(i == 0){
                packer.add(MultiPacker.PageType.editor, "editor-block-" + name + "-full", image);
                packer.add(MultiPacker.PageType.main, "block-" + name + "-full", image);
            }
        }
    }
}
