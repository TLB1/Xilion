package xilion.blockTypes;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.geom.Geometry;
import mindustry.gen.Building;
import mindustry.io.versions.Save1;
import mindustry.world.Block;
import mindustry.world.Tile;
import xilion.util.Binaryc;

public class XNotGate extends Block  {
    public TextureRegion onRegion, offRegion;
    public XNotGate(String name) {
        super(name);
        rotate = true;
        update = false; //Logic updating done separately: updateState() from Binaryc
    }

    @Override
    public void load() {
        super.load();
        onRegion = Core.atlas.find(name + "-on");
        offRegion = Core.atlas.find(name + "-off");
    }

    public class XNotGateBuild extends Building implements Binaryc {


        @Override
        public void draw() {
            Draw.rect(this.block.region, this.x, this.y);
            if(outputState) Draw.rect(onRegion, this.x, this.y, this.drawrot());
            else Draw.rect(offRegion, this.x, this.y, this.drawrot());
        }

        boolean outputState = false;
        @Override
        public boolean outputState() {
            return outputState;
        }

        @Override
        public void updateState(){
            boolean oldState = outputState;
            int backside = (rotation + 2) % 2;
            Tile other = tile.nearby(Geometry.d4x(backside), Geometry.d4y(backside));
            if(other != null && other.build != null && !(other.build instanceof Binaryc)) {
                outputState = true;

            }
            assert other.build != null;
            outputState = ((Binaryc)(other.build)).outputState();
            if(oldState != outputState){
                //Update facing tile build when this value has changed
                Tile facingTile = tile.nearby(Geometry.d4x(rotation), Geometry.d4y(rotation));
                if(facingTile != null && facingTile.build != null && !(facingTile.build instanceof Binaryc)) {
                    ((Binaryc) (facingTile.build)).updateState();
                }
            }
        }
    }
}
