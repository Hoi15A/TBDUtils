package moe.neat.tbdutils.events

import moe.neat.tbdutils.particles.Blossom
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerToggleSneakEvent

class PlayerToggleSneak : Listener {
    private val blossom = Blossom()

    @EventHandler
    private fun onPlayerToggleSneak(e: PlayerToggleSneakEvent) {
        blossom.sneakEvent(e.isSneaking, e.player)
    }
}