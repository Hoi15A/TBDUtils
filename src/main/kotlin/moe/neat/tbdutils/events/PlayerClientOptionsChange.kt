package moe.neat.tbdutils.events

import com.destroystokyo.paper.event.player.PlayerClientOptionsChangeEvent
import moe.neat.tbdutils.util.AprilFools
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

@Suppress("unused")
class PlayerClientOptionsChange : Listener {
    @EventHandler
    private fun onPlayerClientOptionsChange(e: PlayerClientOptionsChangeEvent) {
        AprilFools.playerClientOptionsChangeEvent(e)
    }
}