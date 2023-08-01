package xilion.entities;

import arc.util.Nullable;
import mindustry.entities.pattern.ShootAlternate;

public class XMirrorShootAlternate extends ShootAlternate {
    //Shots should be a multiple of 2!
    @Override
    public void shoot(int totalShots, BulletHandler handler, @Nullable Runnable barrelIncrementer){
        for(int i = 0; i < shots/2; i++){
            float index = ((totalShots + i + barrelOffset) % barrels) - (barrels-1)/2f;
            handler.shoot(index * spread+1.5f, 0, 0f, firstShotDelay + shotDelay * i);
            handler.shoot(-index * spread-1.5f, 0, 0f, firstShotDelay + shotDelay * i);
            if(barrelIncrementer != null) barrelIncrementer.run();
        }
    }
}
