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


    //private static final ContentList[] content = {

   // };

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

           // Does not work because no multiplier
        //Blocks.eruptionDrill.consumeLiquid(UPItems.ammonia, 2.5f / 60f).boost();
       // Blocks.impactDrill.consumeLiquid(UPItems.ammonia, 1.5f / 60f).boost().;

    }

    public XilionJavaMod(){

        Log.info("Loaded ExampleJavaMod constructor.");



      /*  Events.on( BlockBuildBeginEvent.class, event ->{
            Time.runTask(10f, () -> {
                BaseDialog dialog = new BaseDialog("frog");
                dialog.cont.add("behold").row();
                //mod sprites are prefixed with the mod name (this mod is called 'example-java-mod' in its config)
                dialog.cont.image(Base.atlas.find("xilion-frog")).pad(20f).row();
                dialog.cont.button("I see", dialog::hide).size(100f, 50f);
                dialog.show();
                Vars.content.getByName(ContentType.unit, "xilion-");
            });


            if (event.unit.isPlayer() && event.tile.build.block == Blocks.groundFactory) {
                //vars
                Unit unit = event.unit;
                Tile tile = event.tile;
                Team team = event.team;

                boolean breaking = event.breaking;
                Time.runTask(10f, () -> {
                    BaseDialog dialog = new BaseDialog("frog");
                    dialog.cont.add("behold").row();
                    //mod sprites are prefixed with the mod name (this mod is called 'example-java-mod' in its config)
                    dialog.cont.image(Base.atlas.find("xilion-frog")).pad(20f).row();
                    dialog.cont.button("I see", dialog::hide).size(100f, 50f);
                    dialog.show();
                    Vars.content.getByName(ContentType.unit, "xilion-");
                });

            //   new BlockBuildEndEvent(tile,unit,team, breaking, null);

                tile.build.block = UltimateUnitFactories.groundFactoryNew;
                event.tile.setBlock(tile.build.block,team);
               //new BlockBuildBeginEvent(tile,team,unit, breaking);

            }
        });
*/
/*
        //listen for game load event
        Events.on(ClientLoadEvent.class, e -> {
            //show dialog upon startup
            Time.runTask(10f, () -> {
                BaseDialog dialog = new BaseDialog("frog");
                dialog.cont.add("behold").row();
                //mod sprites are prefixed with the mod name (this mod is qcalled 'example-java-mod' in its config)
                dialog.cont.image(Base.atlas.find("xilion-frog")).pad(20f).row();
                dialog.cont.button("I see", dialog::hide).size(100f, 50f);
                dialog.show();
                Vars.content.getByName(ContentType.unit, "xilion-");
            });
        }); */

    }

    public void init(){
        Items.copper.uiIcon = Items.copper.fullIcon;
        unitUI.build(ui.hudGroup);
    }


    @Override
    public void loadContent(){
        Log.info("Loading UnitsPlus content.");
        createBaseContent();
    }


}
