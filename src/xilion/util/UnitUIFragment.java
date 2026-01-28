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

            });

            cont.table(info -> {
                info.name = "fps/ping";
                info.touchable = Touchable.disabled;
                info.top().right().marginTop(Scl.scl(160f)).marginRight(20f).visible(() -> Core.settings.getBool("fps"));
                info.label(() -> XUnitHandler.getUnitCap(player.team()).getUnitCount(1) + " / " + XUnitHandler.getUnitCap(player.team()).getUnitCap(1)).color(Color.lightGray);
                info.image(Core.atlas.find(XilionJavaMod.name("ui-tier1")));
                info.row();
                info.label(() -> XUnitHandler.getUnitCap(player.team()).getUnitCount(2) + " / " + XUnitHandler.getUnitCap(player.team()).getUnitCap(2)).color(Color.lightGray);
                info.image(Core.atlas.find(XilionJavaMod.name("ui-tier2")));
            }).top().left();
        });
    }
}
