package xilion.util;

import arc.util.Log;
import arc.util.Time;
import mindustry.gen.Unit;

public class XActiveAbility {

    //Count The number of uses
    public int usesLeft = 0;
    //cooldown in game-ticks
    public float cooldown = 0f;

    public XActiveAbilityType abilityType;

    public XActiveAbility(XActiveAbilityType abilityType){
        this.abilityType = abilityType;
        if(abilityType.hasUseLimit) usesLeft = abilityType.maxUses;
    }
    public boolean abilityReady(){
        return  cooldown == 0f && (!abilityType.hasUseLimit || usesLeft > 0);
    }
    //progress from 0-1, 1: Ability ready
    public float abilityProgress(){
        return (abilityType.cooldownTime - cooldown) / abilityType.cooldownTime;
    }
    public void triggerAbility(Unit unit) {
        Log.info("ability checks...");
        if(!abilityReady()) return;
        //if(Vars.net.client() Vars.net.server()){
            Log.info("May be client, tried to send packets");
            //TODO: send packet to server to request ability use
            XPacketHelper.Client.TryActiveAbilityUse(unit);
        //}
    }
    public void triggeredAbility(Unit unit){
        if(!abilityReady()) return;
        cooldown = abilityType.cooldownTime;
        if(abilityType.hasUseLimit) usesLeft--;
        abilityType.onUse(unit);
    }

    //Only call once each game-tick!
    public void update(){
       if(!abilityReady()){
           cooldown = Math.max(cooldown - Time.delta, 0f);
       }
    }
}
