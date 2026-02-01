package xilion.generation;

import arc.graphics.Color;
import arc.graphics.VertexAttribute;
import arc.math.geom.Geometry;
import arc.math.geom.Point2;
import arc.math.geom.Vec2;
import arc.math.geom.Vec3;
import arc.util.Tmp;
import arc.util.noise.Ridged;
import mindustry.ai.Astar;
import mindustry.content.Blocks;
import mindustry.game.Schematics;
import mindustry.maps.generators.PlanetGenerator;
import mindustry.world.Block;
import arc.math.Mathf;
import arc.util.noise.Simplex;
import mindustry.world.Tile;
import mindustry.world.TileGen;
import mindustry.world.blocks.environment.TallBlock;
import mindustry.world.meta.Attribute;
import xilion.blockTypes.XSteamVentSmall;
import xilion.content.XBlocks;
import xilion.content.XSchematics;

import static arc.graphics.VertexAttribute.position;
import static mindustry.Vars.state;
import static mindustry.Vars.world;
import static xilion.content.XBlocks.Environment.*;

public class XilionPlanetGenerator extends PlanetGenerator {
    float scl = 5f;
    float waterOffset = 0.07f;

    public float heightScl = 0.9f, octaves = 8, persistence = 0.7f, heightPow = 3f, heightMult = 1.6f;
    public static float airThresh = 0.13f, airScl = 14;
    public static float liqThresh = 0.64f, liqScl = 87f;


    {
        defaultLoadout = XSchematics.baseExplorer;
    }
    Block[][] arr =
            {
                    {slateBlueStone, slateBlueStone, slateKhakiStone, slateKhakiStone, slateBlueStoneDark, concentratedCarbon, pureCarbon, concentratedCarbon, slateBlueStone},
                    {slateBlueStone, concentratedCarbon, pureCarbon, slateBlueStoneDark, slateKhakiStone, slateKhakiStone, greenBubbleStone, slateKhakiStoneDark, greenBubbleStone},
                    {purpurRock, purpurRockDark, purpurRock, darkRedStone, corrodedPhase, darkRedStone, slateBlueStoneDark, pureCarbon, concentratedCarbon},
                    {purpurRock, corrodedPhase, darkRedStone, erythriteStone, corrodedPhase, darkRedStone, pureCarbon, concentratedCarbon, slateBlueStoneDark},
                    {slateKhakiStone, slateKhakiStoneDark, purpurRock, greenBubbleStone, greenBubbleStoneDark,concentratedCarbon, slateBlueStoneDark, purpurRockDark,corrodedPhase },
                    {slateKhakiStone, greenBubbleStoneDark, purpurRock, greenBubbleStoneDark, greenBubbleStone,concentratedCarbon, slateBlueStoneDark, purpurRockDark,corrodedPhase },
            };
    @Override
    public float getHeight(Vec3 vec3) {
        float height = rawHeight(vec3);
        return Math.max(height, 0.3f);
    }

    float rawHeight(Vec3 position){
        position = Tmp.v33.set(position).scl(scl);
        return (Mathf.pow(Simplex.noise3d(seed, 8, 0.5f, 1f/3f, position.x, position.y, position.z), 2.3f) + waterOffset) / (1f + waterOffset);
    }

    @Override
    public void getColor(Vec3 position, Color out) {
        Block block = getBlock(position);
        out.set(Tmp.c1.set(block.mapColor).a(1f - block.albedo));
    }

    @Override
    public float getSizeScl(){
        return 1500 * 1.07f * 6f / 5f;
    }

    Block getBlock(Vec3 position){
        float height = rawHeight(position);
        Tmp.v31.set(position);
        position = Tmp.v33.set(position).scl(2f);
        float temp = Simplex.noise3d(seed, 8, 0.6, 1f/4f, position.x, position.y + 99f, position.z);
        height *= 1.2f;
        height = Mathf.clamp(height);

        return arr[Mathf.clamp((int)(temp * arr.length), 0, arr[0].length - 1)][Mathf.clamp((int)(height * arr[0].length), 0, arr[0].length - 1)];
    }

    @Override
    public void genTile(Vec3 position, TileGen tile){
        tile.floor = getBlock(position);

        tile.block = tile.floor.asFloor().wall;

        if(Ridged.noise3d(seed + 1, position.x, position.y, position.z, 2, airScl) > airThresh){
            tile.block = Blocks.air;
        }

        if(Ridged.noise3d(seed + 2, position.x, position.y + 4f, position.z, 3, 6f) > 0.6){
            //tile.floor = Blocks.;
        }
    }

    float rawTemp(Vec3 position){
        return position.dst(0, 0, 1)*2.2f - Simplex.noise3d(seed, 8, 0.54f, 0.8f, 10f + position.x, 10f + position.y, 10f + position.z) * 2.9f;
    }

    @Override
    protected void generate(){
        float temp = rawTemp(sector.tile.v);

        if(temp > 0.7){

            pass((x, y) -> {
                if(floor != Blocks.redIce){
                    float noise = noise(x + 786, y, 7, 0.8f, 380f, 1f);
                    if(noise > 0.62f){
                        if(noise > 0.635f){
                            floor = Blocks.slag;
                        }else{
                            floor = pinkstone;
                        }
                        ore = Blocks.air;
                    }

                    if(noise > 0.55f && floor == greenBubbleStone){
                        floor = slateKhakiStoneDark;
                    }
                }
            });
        }

        cells(4);

        //regolith walls for more dense terrain
        pass((x, y) -> {
            if(floor == slateBlueStone && noise(x, y, 3, 0.4f, 13f, 1f) > 0.59f){
                block = slateBlueStoneWall;
            }
        });


        float length = width/2.6f;
        Vec2 trns = Tmp.v1.trns(rand.random(360f), length);
        int
                spawnX = (int)(trns.x + width/2f), spawnY = (int)(trns.y + height/2f),
                endX = (int)(-trns.x + width/2f), endY = (int)(-trns.y + height/2f);
        float maxd = Mathf.dst(width/2f, height/2f);

        erase(spawnX, spawnY, 15);
        brush(pathfind(spawnX, spawnY, endX, endY, tile -> (tile.solid() ? 300f : 0f) + maxd - tile.dst(width/2f, height/2f)/10f, Astar.manhattan), 9);
        erase(endX, endY, 15);

        /*
        //arkycite
        pass((x, y) -> {
            if(floor != Blocks.beryllicStone) return;

            //TODO bad
            if(Math.abs(noise(x, y + 500f, 5, 0.6f, 40f, 1f) - 0.5f) < 0.09f){
                floor = Blocks.arkyicStone;
            }

            if(nearWall(x, y)) return;

            float noise = noise(x + 300, y - x*1.6f + 100, 4, 0.8f, liqScl, 1f);

            if(noise > liqThresh){
                floor = Blocks.arkyciteFloor;
            }
        });



        median(2, 0.6, Blocks.arkyciteFloor);

        blend(Blocks.arkyciteFloor, Blocks.arkyicStone, 4);
*/
        //TODO may overwrite floor blocks under walls and look bad
        blend(Blocks.slag, darkPinkstone, 3);

        distort(12f, 15f);
        distort(6f, 8f);

        //smooth out slag to prevent random 1-tile patches
        median(3, 0.6, Blocks.slag);

        pass((x, y) -> {
            //rough rhyolite
            if(noise(x, y + 600 + x, 5, 0.86f, 60f, 1f) < 0.41f && floor == slateBlueStone){
                floor = slateBlueStoneDark;
            }

            if(floor == Blocks.slag && Mathf.within(x, y, spawnX, spawnY, 30f + noise(x, y, 2, 0.8f, 9f, 15f))){
                floor = darkPinkstone;
            }

            float max = 0;
            for(Point2 p : Geometry.d8){
                //TODO I think this is the cause of lag
                max = Math.max(max, world.getDarkness(x + p.x, y + p.y));
            }
            if(max > 0){
                block = floor.asFloor().wall;
                if(block == Blocks.air) block = slateBlueStoneWall;
            }

            if(floor == darkPinkstone && noise(x + 78 + y, y, 3, 0.8f, 6f, 1f) > 0.44f){
                floor = pinkstone;
            }

            if(floor == purpurRock && noise(x + 78 - y, y, 4, 0.73f, 19f, 1f) > 0.63f){
                floor = purpurRockDark;
            }
        });

        inverseFloodFill(tiles.getn(spawnX, spawnY));

        //TODO veins, blend after inverse flood fill?
        blend(purpurRockWall, purpurRock, 4);

        //make sure enemies have room
        erase(endX, endY, 6);

        //TODO enemies get stuck on 1x1 passages.

        tiles.getn(endX, endY).setOverlay(Blocks.spawn);

        //ores
        pass((x, y) -> {

            if(block != Blocks.air){
                if(nearAir(x, y)){
                    if(noise(x + 782, y, 4, 0.8f, 38f, 1f) > 0.665f){
                        ore = germaniumWallOre;
                    }

                }
            }else if(!nearWall(x, y)){
            /*
                if(noise(x + 150, y + x*2 + 100, 4, 0.8f, 55f, 1f) > 0.76f){
                    ore = Blocks.oreTungsten;
                }

                //TODO design ore generation so it doesn't overlap
                if(noise(x + 999, y + 600 - x, 4, 0.63f, 45f, 1f) < 0.27f && floor == Blocks.crystallineStone){
                    ore = Blocks.oreCrystalThorium;
                }

             */

            }

            if(noise(x + 999, y + 600 - x, 5, 0.8f, 45f, 1f) < 0.44f && floor == slateKhakiStone){
                floor = slateKhakiStoneDark;
            }

            if(block == Blocks.air && (floor == slateBlueStoneDark || floor == slateBlueStone) && rand.chance(0.18) && nearWall(x, y)
                     && !near(x, y, 4, slateBlueCrystalCluster)){
                block = slateBlueCrystalCluster;
                ore = Blocks.air;
            }

            if(block == corrodedPhaseWall && rand.chance(0.48) && nearAir(x, y) && !near(x, y, 1, corrodedPhaseSpikes)){
                block = corrodedPhaseSpikes;
                ore = Blocks.air;
            }
            if(block == erythriteStoneWall && rand.chance(0.48) && nearAir(x, y) && !near(x, y, 2, erythriteSpikes)){
                block = erythriteSpikes;
                ore = Blocks.air;
            }

        });

        //remove props near ores, they're too annoying
        pass((x, y) -> {
            if(ore.asFloor().wallOre || block.itemDrop != null || (block == Blocks.air && ore != Blocks.air)){
                removeWall(x, y, 3, b -> b instanceof TallBlock);
            }
        });

        trimDark();

        int minVents = rand.random(12, 18);
        int ventCount = 0;

        //vents
        outer:
        for(Tile tile : tiles){
            var floor = tile.floor();
            if((floor == slateBlueStone|| floor == slateBlueStoneDark) && rand.chance(0.004)){
                int radius = 2;
                for(int x = -radius; x <= radius; x++){
                    for(int y = -radius; y <= radius; y++){
                        Tile other = tiles.get(x + tile.x, y + tile.y);
                        if(other == null || (other.floor() != slateBlueStone && other.floor() != slateBlueStoneDark) || other.block().solid){
                            continue outer;
                        }
                    }
                }

                ventCount ++;
                for(var pos : XSteamVentSmall.offsets){
                    Tile other = tiles.get(pos.x + tile.x + 1, pos.y + tile.y + 1);
                    other.setFloor(slateBlueStoneVent.asFloor());
                }
            }
        }

        int iterations = 0;
        int maxIterations = 5;

        //try to add additional vents, but only several times to prevent infinite loops in bad maps
        while(ventCount < minVents && iterations++ < maxIterations){
            outer:
            for(Tile tile : tiles){
                if(rand.chance(0.00036 * (1 + iterations)) && !Mathf.within(tile.x, tile.y, spawnX, spawnY, 5f)){
                    //skip crystals, but only when directly on them
                    if(tile.floor() == erythriteStone || tile.floor() == concentratedCarbon || tile.floor() == pureCarbon){
                        continue;
                    }

                    int radius = 1;
                    for(int x = -radius; x <= radius; x++){
                        for(int y = -radius; y <= radius; y++){
                            Tile other = tiles.get(x + tile.x, y + tile.y);
                            //skip solids / other vents / arkycite / slag
                            if(other == null || other.block().solid || other.floor().attributes.get(Attribute.steam) != 0 || other.floor() == Blocks.slag){
                                continue outer;
                            }
                        }
                    }

                    Block
                            floor = slateBlueStone,
                            secondFloor = slateBlueStoneDark,
                            vent = slateBlueStoneVent;

                    int xDir = 1;
                    //set target material depending on what's encountered
                    if(tile.floor() == slateKhakiStone || tile.floor() == slateKhakiStoneDark){
                        floor = secondFloor = slateKhakiStone;
                        vent = slateKhakiStoneVent;
                    }else if(tile.floor() == corrodedPhase){
                        floor = secondFloor = corrodedPhase;
                        vent = corrodedPhaseVent;
                    }else if(tile.floor() == darkRedStone){
                        floor = secondFloor = darkRedStone;
                        vent = darkRedStoneVent;
                        xDir = -1;
                    }


                    ventCount ++;
                    for(var pos : XSteamVentSmall.offsets){
                        Tile other = tiles.get(pos.x + tile.x + 1, pos.y + tile.y + 1);
                        other.setFloor(vent.asFloor());
                    }

                    //"circle" for blending
                    //TODO should it replace akrycite? slag?
                    int crad = rand.random(6, 14), crad2 = crad * crad;
                    for(int cx = -crad; cx <= crad; cx++){
                        for(int cy = -crad; cy <= crad; cy++){
                            int rx = cx + tile.x, ry = cy + tile.y;
                            //skew circle Y
                            float rcy = cy + cx*0.9f;
                            if(cx*cx + rcy*rcy <= crad2 - noise(rx, ry + rx * 2f * xDir, 2, 0.7f, 8f, crad2 * 1.1f)){
                                Tile dest = tiles.get(rx, ry);
                                if(dest != null && dest.floor().attributes.get(Attribute.steam) == 0 && dest.floor() != Blocks.roughRhyolite && dest.floor() != Blocks.arkyciteFloor && dest.floor() != Blocks.slag){

                                    dest.setFloor(rand.chance(0.08) ? secondFloor.asFloor() : floor.asFloor());

                                    if(dest.block().isStatic()){
                                        dest.setBlock(floor.asFloor().wall);
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }

        for(Tile tile : tiles){
            if(tile.overlay().needsSurface && !tile.floor().hasSurface()){
                tile.setOverlay(Blocks.air);
            }
        }

        decoration(0.018f);

        //it is very hot
        state.rules.env = sector.planet.defaultEnv;
        state.rules.placeRangeCheck = true;

        //TODO remove slag and arkycite around core.
        Schematics.placeLaunchLoadout(spawnX, spawnY);

        //all sectors are wave sectors
        state.rules.waves = false;
        state.rules.showSpawns = true;
    }

}
