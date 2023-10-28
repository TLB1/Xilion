package xilion.content;

import mindustry.game.Schematic;
import mindustry.game.Schematics;

public class XSchematics {
    public static Schematic baseExplorer;

    public static void load(){
        baseExplorer = Schematics.readBase64("bXNjaAF4nGNgZmBmZmDJS8xNZeB1SixOVXCtKMjJL0otYuBOSS1OLsosKMnMz2NgYGDLSUxKzSlmYIqOZWQQqcjMAQrrJgNV6qbCdDAwMIIQkAAAAQMWqQ==");
    }
}
