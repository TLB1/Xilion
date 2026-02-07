package xilion.content;

import arc.Core;
import arc.assets.AssetDescriptor;
import arc.assets.loaders.SoundLoader;
import arc.audio.Sound;
import mindustry.Vars;


public class XSounds {
    public static Sound attackMissileHeavy = new Sound();
    public static Sound dashAbilitySound = new Sound();
    public static Sound shootBigFlame = new Sound();
    public static Sound shootShock = new Sound();
    public static Sound shootMissileLight = new Sound();
    public static Sound explosionMissileLight = new Sound();

    public XSounds() {

    }


    public void load() {
        attackMissileHeavy = loadSound("attackMissileHeavy");
        dashAbilitySound = loadSound("dashAbilitySound");
        shootShock = loadSound("shootShock");
        shootBigFlame = loadSound("shootBigFlame");
        shootMissileLight = loadSound("shootMissileLight");
        explosionMissileLight = loadSound("explosionMissileLight");
    }


    private static Sound loadSound(String soundName) {
        if (!Vars.headless) {
            String name = "sounds/" + soundName;
            String path = Vars.tree.get(name + ".ogg").exists() ? name + ".ogg" : name + ".mp3";

            Sound sound = new Sound();

            AssetDescriptor<?> desc = Core.assets.load(path, Sound.class, new SoundLoader.SoundParameter(sound));
            desc.errored = Throwable::printStackTrace;
            return sound;
        } else return new Sound();
    }
}
