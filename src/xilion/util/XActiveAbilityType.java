package xilion.util;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import arc.util.Log;
import mindustry.gen.Unit;
import xilion.XilionJavaMod;

public abstract class XActiveAbilityType {
    // Whether it can only be activated a certain number of times
    boolean hasUseLimit = false;

    //Max uses if hasUseLimit = true;
    int maxUses = 1;
    //internal name
    public String name;
    //Visible name
    public String displayName;
    //Visible description
    public String description;


    public TextureRegion icon;
    //Cooldown time in ticks (60 per second)
    public float cooldownTime = 600f;

    public void load(){
        icon = Core.assets.get(name+"-icon");
        displayName = Core.bundle.get("active-ability." + XilionJavaMod.MOD_NAME + "-" + name + ".name");
        description = Core.bundle.get("active-ability." + XilionJavaMod.MOD_NAME + "-" + name + ".description");
    }
    public void setCooldownTime(float seconds){
        cooldownTime = seconds * 60f;
    }
    public XActiveAbilityType(String name){
        this.name = name;
    }

    public void onUse(Unit unit){
        Log.info(unit.type()+" used "+ name);
    }
}
