package xilion.blockTypes;

import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.SteamVent;

import static mindustry.Vars.tilesize;

public class XSteamVentSmall extends SteamVent {
    public XSteamVentSmall(String name) {
        super(name);
        effectSpacing = 20f;
    }

    public static final Point2[] newOffsets = {
            new Point2(0, 0),
            new Point2(-1, 0),
            new Point2(-1, -1),
            new Point2(0, -1)
    };
    @Override
    public void drawBase(Tile tile){
        parent.drawBase(tile);
        if(checkAdjacent(tile)){
            Mathf.rand.setSeed(tile.pos());
            Draw.rect(variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))], tile.worldx() -4, tile.worldy() -4);
        }
    }

    @Override
    public void renderUpdate(UpdateRenderState state){
        if(state.tile.block() == Blocks.air && (state.data += Time.delta) >= effectSpacing){
            effect.at(state.tile.x * tilesize -4, state.tile.y * tilesize -4, effectColor);
            state.data = 0f;
        }
    }


    public boolean checkAdjacent(Tile tile){
        for(var point : newOffsets){
            Tile other = Vars.world.tile(tile.x + point.x, tile.y + point.y);
            if(other == null || other.floor() != this){
                return false;
            }
        }
        return true;
    }
}
