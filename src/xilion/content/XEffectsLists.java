package xilion.content;

import mindustry.content.StatusEffects;
import mindustry.type.StatusEffect;

public class XEffectsLists {

    //Immunities for classes
    public static final StatusEffect[] attackClassImmune = new StatusEffect[3];

    static {
        attackClassImmune[0] = StatusEffects.sapped;
        attackClassImmune[1] = StatusEffects.burning;
        attackClassImmune[2] = StatusEffects.melting;
    }
}
