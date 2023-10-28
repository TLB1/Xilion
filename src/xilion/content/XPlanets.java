package xilion.content;

import arc.graphics.Color;
import arc.struct.Seq;
import mindustry.content.Items;
import mindustry.content.Planets;
import mindustry.game.Team;
import mindustry.graphics.g3d.HexMesh;
import mindustry.graphics.g3d.HexSkyMesh;
import mindustry.graphics.g3d.MultiMesh;
import mindustry.maps.planet.ErekirPlanetGenerator;
import mindustry.type.Item;
import mindustry.type.Planet;
import mindustry.world.meta.Attribute;
import xilion.generation.XilionPlanetGenerator;

import static xilion.content.XItems.*;

public class XPlanets {
    public static Planet xilion;
    public static Seq<Item> allowedItems = new Seq<>().with( Items.sand, Items.silicon, Items.tungsten, Items.surgeAlloy, Items.carbide, Items.copper);
    public static void load(){
        xilion = new Planet("xilion", Planets.sun, 0.8f, 2){{
            generator = new XilionPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 6);
            cloudMeshLoader = () -> new MultiMesh(
                    new HexSkyMesh(this, 11, 0.25f, 0.14f, 5, Color.valueOf("5de7a3").a(0.75f), 2, 0.45f, 0.87f, 0.38f),
                    new HexSkyMesh(this, 2, 0.6f, 0.17f, 5, Color.valueOf("c1f4e4").a(0.75f), 2, 0.45f, 1f, 0.43f)
            );

            launchCapacityMultiplier = 0.5f;
            sectorSeed = 2;
            orbitTime = 3 * 60f * 60f;
            rotateTime = 62f * 60f;
            orbitSpacing = 10;


            allowLaunchToNumbered = false;
            allowWaves = true;
            allowWaveSimulation = true;
            allowSectorInvasion = true;
            allowLaunchSchematics = true;
            enemyCoreSpawnReplace = true;
            allowLaunchLoadout = true;
            clearSectorOnLose = true;
            iconColor = Color.valueOf("43b6bc");
            atmosphereColor = Color.valueOf("43b6bc");
            defaultCore = XBlocks.coreExplorer;
            atmosphereRadIn = 0.025f;
            atmosphereRadOut = 0.35f;
            startSector = 33;
            orbitRadius = 55f;
            alwaysUnlocked = true;
            this.defaultAttributes.set(Attribute.heat, 0.5f);
            landCloudColor = Color.valueOf("24b95a");
            //defaultEnv = Planets.erekir.defaultEnv;
            //itemWhitelist.addAll(Items.sand, Items.silicon, Items.tungsten, Items.surgeAlloy, Items.carbide, germanium, erythrite,cobaltPhosphate, carbon, cobalt, thermoplastic, boron, boronCarbide);
            hiddenItems.addAll(Items.serpuloItems).addAll(Items.erekirOnlyItems).removeAll(allowedItems);
            hiddenItems.removeAll(xilionItems);
            ruleSetter = r -> {
                r.waveTeam = Team.malis;
                r.placeRangeCheck = false;
                r.showSpawns = true;
                r.coreDestroyClear = false;
            };
        }};
    }
}
