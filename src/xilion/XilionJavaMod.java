package xilion;

import arc.*;
import arc.files.Fi;
import arc.util.*;
import mindustry.content.Items;
import mindustry.game.EventType.*;
import mindustry.mod.*;

import xilion.content.*;
import xilion.core.XilionAchievements;
import xilion.core.XilionUpdater;
import xilion.ui.UnitUIFragment;
import xilion.util.XPacketHelper;
import xilion.util.XUnitHandler;

import static mindustry.Vars.*;


public class XilionJavaMod extends Mod{
    public static Fi achievementsFile = dataDirectory.child(name("achievements.json"));
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
    }

    public XilionJavaMod(){
        Log.info("Loaded ExampleJavaMod constructor.");
    }

    @Override
    public void init(){
        Events.on(ClientLoadEvent.class, event -> {
            XilionUpdater.checkUpdates();
            XilionAchievements.init();

            Items.copper.uiIcon = Items.copper.fullIcon;
            unitUI.build(ui.hudGroup);
        });
    }

    @Override
    public void loadContent(){
        Log.info("Loading Xilion content.");
        createBaseContent();
    }

}
