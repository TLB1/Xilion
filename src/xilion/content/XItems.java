package xilion.content;

import arc.graphics.Color;
import mindustry.type.Item;
import mindustry.type.Liquid;

public class XItems {
    public static Liquid ammonia, methane;
    public static Item germanium, erythrite, cobalt, boron, boronCarbide;
    public void load() {
        ammonia = new Liquid("ammonia", Color.valueOf("e4ca5a")){{
            gas = true;
            barColor = Color.valueOf("e4ca5a");
            explosiveness = 0.5f;
            flammability = 0.5f;
        }};
        methane = new Liquid("methane", Color.valueOf("e69d62")){{
            gas = true;
            barColor = Color.valueOf("e69d62");
            explosiveness = 0.5f;
            flammability = 1f;
        }};
        germanium = new Item("germanium", Color.valueOf("bcbdcb")){{
            hardness = 3;
            cost = 1.2f;
            healthScaling = 0.6f;
        }};
        erythrite = new Item("erythrite", Color.valueOf("8a3a50")){{
            hardness = 3;
            cost = 1.8f;
            healthScaling = 0.7f;
        }};
        cobalt = new Item("cobalt", Color.valueOf("5e729f")){{
            hardness = 3;
            cost = 1.4f;
            healthScaling = 0.7f;
        }};
        boron = new Item("boron", Color.valueOf("8e8372")){{
            hardness = 4;
            cost = 1.6f;
            healthScaling = 1.0f;
        }};
        boronCarbide = new Item("boron-carbide", Color.valueOf("97794d")){{
            cost = 1.4f;
            healthScaling = 1.3f;
        }};

    }
}
