package xilion.generation;

import arc.graphics.Color;
import arc.graphics.VertexAttribute;
import arc.math.geom.Vec3;
import arc.util.Tmp;
import mindustry.maps.generators.PlanetGenerator;
import mindustry.world.Block;
import arc.math.Mathf;
import arc.util.noise.Simplex;
import xilion.content.XBlocks;
import xilion.content.XSchematics;

import static arc.graphics.VertexAttribute.position;
import static xilion.content.XBlocks.*;

public class XilionPlanetGenerator extends PlanetGenerator {
    float scl = 5f;
    float waterOffset = 0.07f;

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
        return (Mathf.pow(Simplex.noise3d(seed, 7, 0.5f, 1f/3f, position.x, position.y, position.z), 2.3f) + waterOffset) / (1f + waterOffset);
    }
    @Override
    public Color getColor(Vec3 vec3) {
        Block block = getBlock(vec3);

        return Tmp.c1.set(block.mapColor).a(1f - block.albedo);
    }

    Block getBlock(Vec3 position){
        float height = rawHeight(position);
        Tmp.v31.set(position);
        position = Tmp.v33.set(position).scl(2f);
        float temp = Simplex.noise3d(seed, 8, 0.6, 1f/2f, position.x, position.y + 99f, position.z);
        height *= 1.2f;
        height = Mathf.clamp(height);

        //float tar = (float)noise.octaveNoise3D(4, 0.55f, 1f/2f, position.x, position.y + 999f, position.z) * 0.3f + Tmp.v31.dst(0, 0, 1f) * 0.2f;

        return arr[Mathf.clamp((int)(temp * arr.length), 0, arr[0].length - 1)][Mathf.clamp((int)(height * arr[0].length), 0, arr[0].length - 1)];
    }
}
