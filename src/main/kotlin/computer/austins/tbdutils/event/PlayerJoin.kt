package computer.austins.tbdutils.event

import computer.austins.tbdutils.util.Chat
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

@Suppress("unused")
class PlayerJoin : Listener {
    @EventHandler
    private fun onPlayerJoin(e: PlayerJoinEvent) {
        e.joinMessage(
            Chat.formatMessage(
                "<tbdcolour><name><reset> joined the game.",
                false,
                Placeholder.component("name", e.player.name())
            )
        )
    }
}
