package xilion.blockTypes;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.util.Time;
import mindustry.Vars;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.environment.Floor;

public class XVirusWall extends Wall {
    TextureRegion deadTexture;

    public XVirusWall(String name) {
        super(name);
        update = true;
        saveData = true;

    }

    @Override
    public void load() {
        super.load();
        deadTexture = Core.atlas.find(name+"-dead");
    }

    public class VirisWallBuild extends WallBuild{
        float timer = 0f;

        @Override
        public void onDestroyed() {
            this.tile.data = (byte) 0x00;
            super.onDestroyed();
        }

        @Override
        public void updateTile() {

            super.updateTile();
            if(this.tile.data != (byte) 0xFF){
            if(timer > 120){
                timer = timer - 120;
                if(Math.random() > 0.50){
                 if(   !(Vars.world.tile(this.tile.x, this.tile.y +1).block() instanceof Wall) && !(Vars.world.tile(this.tile.x, this.tile.y -1).block() instanceof Floor) && !(Vars.world.tile(this.tile.x+1, this.tile.y).block() instanceof Floor) && !(Vars.world.tile(this.tile.x - 1, this.tile.y).block() instanceof Floor)){
                     this.tile.data = (byte) 0xFF;
                 }

                    switch ((int)Math.nextDown((Math.random()*4))){

                        case 0 -> {
                            if(Vars.world.tile(this.tile.x, this.tile.y +1).block() instanceof Wall){Vars.world.tiles.get(this.tile.x, this.tile.y +1).setBlock(this.block, this.team);}
                        }
                        case 1 -> {
                            if(Vars.world.tile(this.tile.x, this.tile.y -1).block() instanceof Wall){Vars.world.tiles.get(this.tile.x, this.tile.y -1).setBlock(this.block, this.team);}
                        }
                        case 2 -> {
                            if(Vars.world.tile(this.tile.x+1, this.tile.y).block() instanceof Wall){Vars.world.tiles.get(this.tile.x + 1, this.tile.y).setBlock(this.block, this.team);}
                        }
                        case 3 -> {
                            if(Vars.world.tile(this.tile.x-1, this.tile.y).block() instanceof Wall){Vars.world.tiles.get(this.tile.x - 1, this.tile.y).setBlock(this.block, this.team);}
                        }
                    }
                }
            }
            timer += Time.delta;
            }
        }

        @Override
        public void update() {
            super.update();

        }

        @Override
        public void draw() {
            if (this.tile.data != (byte) 0xFF) {
                Draw.rect(this.block.region, this.x, this.y);
            }else{
                Draw.rect(deadTexture, this.x, this.y);
            }

        }
    }
}
