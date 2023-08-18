package xilion.blockTypes;

import mindustry.world.blocks.distribution.Duct;
import xilion.content.XBlocks;

public class XPipe extends Duct {
    public XPipe(String name) {
        super(name);
    }
    @Override
    public void init() {
        super.init();
        bridgeReplacement = XBlocks.pipeBridge;
    }
}
