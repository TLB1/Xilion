package xilion.util;

import arc.util.Log;
import mindustry.Vars;
import mindustry.gen.Call;
import mindustry.gen.Unit;

public class XPacketHelper {
    public static void init() {
        Vars.net.handleClient(XPackets.ActiveAbilityTrigger.class, packet -> {
            if (packet.unit.type instanceof XActiveAbilityUnit aUnit) {
                XActiveAbility a = aUnit.getActiveAbility();
                a.triggeredAbility(packet.unit);
                Log.info("Client shows ability");
            }
        });

        Vars.net.handleServer(XPackets.ActiveAbilityUse.class, (con , packet)-> {
            if (packet.unit.type instanceof XActiveAbilityUnit aUnit) {
                XActiveAbility a = aUnit.getActiveAbility();
                if(!a.abilityReady()) return;
                Log.info("Server approved");
                a.triggeredAbility(packet.unit);
                Server.ActivateAbility(packet.unit);
            }
        });
    }
    public static class Client{
        public static void TryActiveAbilityUse(Unit unit){
            Log.info("Requested Ability Use");
            //if(!Vars.net.client()) return;
            XPackets.ActiveAbilityUse packet = new XPackets.ActiveAbilityUse();
            packet.unit = unit;
            Vars.net.send(packet, true);
        }
    }
    public static class Server{
        public static void ActivateAbility(Unit unit){

            if(/*!Vars.net.server() &&*/ unit == null) return;
            XPackets.ActiveAbilityTrigger packet = new XPackets.ActiveAbilityTrigger();
            packet.unit = unit;
            //TODO: fix Calls
            //Call.clientPacketReliable(unit.id);
            
            Vars.net.send(packet, true);

        }
    }

}


