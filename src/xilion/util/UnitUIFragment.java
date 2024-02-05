package xilion.util;

import arc.Core;
import arc.graphics.Color;
import arc.scene.Group;
import arc.scene.event.Touchable;
import arc.scene.ui.layout.Scl;
import mindustry.ui.IntFormat;
import mindustry.ui.Styles;
import xilion.XilionJavaMod;
import xilion.content.XUnitTypes;

import static mindustry.Vars.*;


public class UnitUIFragment{
    public void build(Group parent){
        parent.fill(cont -> {
            cont.name = "xilion-unit-stats";
            cont.top().right().marginTop(6f);
            cont.update(() -> {
                //TODO
            });

            cont.table(info -> {
                info.name = "fps/ping";
                info.touchable = Touchable.disabled;
                info.top().right().marginTop(Scl.scl(160f)).marginRight(20f).visible(() -> Core.settings.getBool("fps"));
                /*
                IntFormat fps = new IntFormat("fps");
                IntFormat ping = new IntFormat("ping");
                IntFormat tps = new IntFormat("tps");
                IntFormat mem = new IntFormat("memory");
                IntFormat memnative = new IntFormat("memory2");
                IntFormat units = new IntFormat("t1-units");

                //info.label(() -> fps.get(Base.graphics.getFramesPerSecond())).left().style(Styles.outlineLabel).name("fps");
                info.row();
                if(android){
                    info.label(() -> memnative.get((int)(Base.app.getJavaHeap() / 1024 / 1024), (int)(Base.app.getNativeHeap() / 1024 / 1024))).left().style(Styles.outlineLabel).name("memory2");
                }else{
                    info.label(() -> mem.get((int)(Base.app.getJavaHeap() / 1024 / 1024))).left().style(Styles.outlineLabel).name("memory");
                }
                info.row();

                info.label(() -> ping.get(netClient.getPing())).visible(net::client).left().style(Styles.outlineLabel).name("ping").row();
                info.label(() -> tps.get(state.serverTps == -1 ? 60 : state.serverTps)).visible(net::client).left().style(Styles.outlineLabel).name("tps").row();
                info.row();
                info.label(() -> fps.get(XUnitHandler.getUnitCap(player.team()).getUnitCount(1))).name("fps");
                */
                info.label(() -> XUnitHandler.getUnitCap(player.team()).getUnitCount(1) + " / " + XUnitHandler.getUnitCap(player.team()).getUnitCap(1)).color(Color.lightGray);
                info.image(Core.atlas.find(XilionJavaMod.name("ui-tier1")));
                info.row();
                info.label(() -> XUnitHandler.getUnitCap(player.team()).getUnitCount(2) + " / " + XUnitHandler.getUnitCap(player.team()).getUnitCap(2)).color(Color.lightGray);
                info.image(Core.atlas.find(XilionJavaMod.name("ui-tier2")));
            }).top().left();
        });
    }
}
