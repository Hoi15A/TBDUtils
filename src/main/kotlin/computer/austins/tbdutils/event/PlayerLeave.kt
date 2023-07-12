package computer.austins.tbdutils.event

import computer.austins.tbdutils.util.Chat
import computer.austins.tbdutils.util.Noxesium

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

@Suppress("unused")
class PlayerLeave : Listener {
    @EventHandler
    private fun onPlayerLeave(e: PlayerQuitEvent) {
        e.quitMessage(
            Chat.formatMessage(
                "<tbdcolour><name><reset> left the game.",
                false,
                Placeholder.component("name", e.player.name())
            )
        )
        Noxesium.removeNoxesiumUser(e.player)
    }
}