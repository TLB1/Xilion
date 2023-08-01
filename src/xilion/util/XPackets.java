package xilion.util;

import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.gen.Unit;
import mindustry.io.TypeIO;
import mindustry.net.Packet;

public class XPackets {
    public static class ActiveAbilityUse extends Packet {
        public Unit unit;

        @Override
        public void write(Writes buffer){
            TypeIO.writeUnit(buffer, unit);
        }

        @Override
        public void read(Reads buffer){
            unit = TypeIO.readUnit(buffer);
        }
    }
    public static class ActiveAbilityTrigger extends Packet {
        public Unit unit;

        @Override
        public void write(Writes buffer){
            TypeIO.writeUnit(buffer, unit);
        }

        @Override
        public void read(Reads buffer){
            unit = TypeIO.readUnit(buffer);
        }
    }
}
