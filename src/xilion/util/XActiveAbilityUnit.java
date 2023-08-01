package xilion.util;

public interface XActiveAbilityUnit {
    XActiveAbility activeAbility = null;

    default XActiveAbility getActiveAbility(){
        return  activeAbility;
    }
}
