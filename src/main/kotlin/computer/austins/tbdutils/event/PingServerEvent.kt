package computer.austins.tbdutils.event

import com.destroystokyo.paper.event.server.PaperServerListPingEvent

import computer.austins.tbdutils.util.vanish.VanishManager
import org.bukkit.event.EventHandler

import org.bukkit.event.Listener

@Suppress("unused")
class PingServerEvent : Listener {
    @EventHandler
    private fun onServerPing(e : PaperServerListPingEvent) {
        for(vanished in VanishManager.getInstances()) {
            if(e.playerSample.contains(vanished.value.playerProfile)) {
                e.playerSample.remove(vanished.value.playerProfile)
                e.numPlayers -= 1
            }
        }
    }
}