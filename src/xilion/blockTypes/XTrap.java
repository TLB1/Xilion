package xilion.blockTypes;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.util.Log;
import arc.util.Time;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.gen.Bullet;
import mindustry.graphics.BlockRenderer;
import mindustry.type.Category;
import mindustry.world.Block;
import mindustry.world.Build;
import mindustry.world.Tile;
import mindustry.world.meta.BuildVisibility;

import java.beans.Visibility;

import static mindustry.Vars.state;

public class XTrap extends Block {
    public boolean canStopActivating = false;

    public float activationTime = 30f;
    public XTrap(String name) {
        super(name);
        category = Category.turret;
        update = true;
        fillsTile = false;
        health = 500;
        drawTeamOverlay = false;
        hasShadow = false;
        buildVisibility = BuildVisibility.shown;
        targetable = false;
        drawCracks = false;
        hideDetails = true;
    }

    @Override
    public int minimapColor(Tile tile) {
        return tile.floor().mapColor.rgba();
    }

    public class XTrapBuild extends Building {
        float activationTimer = 0f;

        public boolean isActive(){
            return activationTimer > 0f;
        }
        public boolean detonates(){
            return activationTimer > activationTime;
        }
        public boolean shouldActivate(){
            return false; // needs to be overwritten in child class
        }
        public float detonationProgress(){
            if(activationTime == 0f) return 0f;
            return activationTimer / activationTime;
        }
        public void drawDetonationProgress(){

        }
        public void detonate(){
            //Add cool/unique stuff in child class
            kill();
        }
        @Override
        public void draw() {
           if(Vars.player != null &&(Vars.player.team() == team ||isActive())){
               Draw.rect(this.block.region, this.x, this.y, this.drawrot());
               Draw.color((state.rules.fog && isVisible()) ? Color.white : BlockRenderer.blendShadowColor);
               Log.info((state.rules.fog && isVisible()));
           }

        }

        @Override
        public void updateTile() {
            if(shouldActivate() || (!canStopActivating && isActive())){
                activationTimer += Time.delta;
            } else if (canStopActivating) {
                activationTimer = 0f;
            }
            if(detonates()){
                detonate();
            }
            if(isActive()){
                drawDetonationProgress();
            }
        }

        @Override
        public void read(Reads read) {
            super.read(read);
           // activationTimer = read.f();
        }

        @Override
        public void write(Writes write) {
            super.write(write);
           // write.f(activationTimer);
        }

        @Override
        public boolean collide(Bullet other) {
            return false;
        }
    }
}
