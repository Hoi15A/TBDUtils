package moe.neat.tbdutils.events

import moe.neat.tbdutils.Plugin
import moe.neat.tbdutils.commands.Vanish

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

@Suppress("unused")
class PlayerJoin : Listener {
    @EventHandler
    private fun onPlayerJoin(e: PlayerJoinEvent) {
        val player = e.player
        val vanishedPlayers = Vanish.vanishedPlayers.map { id -> Bukkit.getPlayer(id)!! }
        vanishedPlayers.forEach { pl ->
            player.hidePlayer(Plugin.plugin, pl)
        }

        player.resetTitle()
    }
}