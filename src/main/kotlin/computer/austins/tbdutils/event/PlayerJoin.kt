package computer.austins.tbdutils.event

import computer.austins.tbdutils.util.Chat
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

@Suppress("unused")
class PlayerJoin : Listener {
    @EventHandler
    private fun onPlayerJoin(e: PlayerJoinEvent) {
        e.joinMessage(null)
        Chat.joinMessage(e.player)
    }
}
