package xilion.content;

import arc.struct.Seq;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.game.Objectives;

import static mindustry.content.TechTree.*;
import static xilion.content.XBlocks.Drills.*;
import static xilion.content.XBlocks.Distribution.*;
import static xilion.content.XBlocks.Liquid.*;
import static xilion.content.XBlocks.Turrets.*;
import static xilion.content.XBlocks.Production.*;
import static xilion.content.XBlocks.Walls.*;
import static xilion.content.XBlocks.Power.*;
import static xilion.content.XItems.*;

public class XTechTree {
    public static void load() {
        XPlanets.xilion.techTree = nodeRoot("xilion", XBlocks.Base.coreExplorer, () -> {
            //Pipes
            node(pipe, () -> {
                node(basicConduit, () -> {
                    node(basicLiquidRouter, () -> {
                        node(compactElectrolyzer, Seq.with(new Objectives.OnSector(XSectorPresets.ashes)), () -> {

                        });
                    });
                });

                node(pipeRouter, () -> {
                    node(pipeBridge, () -> {

                    });
                });
            });
            //Drills
            node(plasmaCollector, () -> {
                node(crusherDrill, () -> {
                    node(wallCrusher, () -> {

                    });
                });
            });
            //Production
            node(cobaltRefineryOven, () -> {
                node(siliconOven, () -> {

                });
            });
            //Defence
            node(germaniumWall, () -> {
                node(germaniumWallLarge, () -> {
                    node(germaniumWallHuge, Seq.with(new Objectives.OnSector(XSectorPresets.inferno)), () -> {

                    });
                });
                node(cobaltWall, Seq.with(new Objectives.OnSector(XSectorPresets.cinderpath)), () -> {
                    node(cobaltWallLarge, () -> {
                        node(cobaltWallHuge, Seq.with(new Objectives.OnSector(XSectorPresets.helios)), () -> {

                        });
                    });
                });
            });

            //Turrets
            node(shock, () -> {
                node(heavy, () -> {
                    node(regularity, () -> {
                        node(charred, Seq.with(new Objectives.OnSector(XSectorPresets.cinderpath)), () -> {
                            node(prevent, Seq.with(new Objectives.OnSector(XSectorPresets.ashes)), () -> {

                            });
                        });
                    });
                });
            });
            //Items/Liquids
            node(germanium, () -> {
                node(Liquids.water, () -> {

                });
                node(erythrite, () -> {
                    node(cobalt, () -> {
                        node(carbon, () -> {

                        });
                        node(Items.sand, () -> {
                            node(Items.silicon, () -> {

                            });
                        });
                    });
                });
            });
            //Power
            node(ventTurbine, () -> {
                node(wireNode, () -> {
                    node(beamWireNode, () -> {
                        node(batteryCell, Seq.with(new Objectives.OnSector(XSectorPresets.inferno)), () -> {

                        });
                        node(solarCell, Seq.with(new Objectives.OnSector(XSectorPresets.helios)), () -> {

                        });
                    });
                });
            });
            //Units
            node(XUnitFactories.prototypeFabricator, Seq.with(new Objectives.OnSector(XSectorPresets.inferno)), () -> {
                node(XUnitFactories.attackTransformer, () -> {

                });
                node(XUnitFactories.supportTransformer, () -> {

                });
                node(XUnitFactories.tankTransformer, () -> {

                });
                node(XUnitFactories.advancedRefabricator, Seq.with(new Objectives.OnSector(XSectorPresets.ashes)), () -> {

                });
            });
            //Sectors
            node(XSectorPresets.horizon, () -> {
                node(XSectorPresets.cinderpath, () -> {
                    node(XSectorPresets.inferno, () -> {
                        node(XSectorPresets.ashes, () -> {

                        });
                    });
                    node(XSectorPresets.helios, () -> {

                    });
                });
            });
        });
    }
}
