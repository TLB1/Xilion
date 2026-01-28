package xilion;

import arc.*;
import arc.util.*;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.content.UnitTypes;
import mindustry.game.EventType.*;
import mindustry.mod.*;

import xilion.content.*;
import xilion.util.UnitUIFragment;
import xilion.util.XPacketHelper;
import xilion.util.XUnitHandler;

import static arc.Core.*;
import static mindustry.Vars.*;


public class XilionJavaMod extends Mod{
    public UnitUIFragment unitUI = new UnitUIFragment();
    public static final String MOD_NAME = "xilion";
    public static final String TURRET_BASE = MOD_NAME + "-";
    public static String name(String name){
        return MOD_NAME + "-" + name;
    }

    public void createBaseContent(){

           new XSounds().load();
           new XStatusEffects().load();
           new XFx().load();
           new XItems().load();
           new XUnitTypes().load();
           XBlocks.load();
           new XUnitFactories().load();
           new XSchematics().load();
           new XPlanets().load();
           new XSectorPresets().load();
           new XTechTree().load();
           XUnitHandler.init();
           XPacketHelper.init();
           UnitTypes.merui.useUnitCap = false;
    }

    public XilionJavaMod(){
        Log.info("Loaded ExampleJavaMod constructor.");
    }

    @Override
    public void init(){
        Items.copper.uiIcon = Items.copper.fullIcon;
        unitUI.build(ui.hudGroup);
    }

    @Override
    public void loadContent(){
        Log.info("Loading Xilion content.");
        createBaseContent();
    }

}
