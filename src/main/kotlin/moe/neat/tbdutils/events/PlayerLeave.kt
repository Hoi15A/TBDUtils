package moe.neat.tbdutils.events

import moe.neat.tbdutils.commands.Vanish
import net.kyori.adventure.text.Component

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

@Suppress("unused")
class PlayerLeave : Listener {
    @EventHandler
    private fun onPlayerQuit(e: PlayerQuitEvent)
    {
        if(Vanish.vanishedPlayers.contains(e.player.uniqueId)) {
            Vanish.vanishedPlayers.remove(e.player.uniqueId)
            e.quitMessage(Component.text(""))
        }
    }
}