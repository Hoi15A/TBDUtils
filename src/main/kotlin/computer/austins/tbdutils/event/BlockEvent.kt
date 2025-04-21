package computer.austins.tbdutils.event

import computer.austins.tbdutils.util.vanish.VanishManager
import org.bukkit.event.EventHandler

import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent

@Suppress("unused")
class BlockEvent : Listener {
    @EventHandler
    private fun onBlockBreak(e : BlockBreakEvent) {
        e.isCancelled = VanishManager.isVanished(e.player.uniqueId)
    }

    @EventHandler
    private fun onBlockPlace(e : BlockPlaceEvent) {
        e.isCancelled = VanishManager.isVanished(e.player.uniqueId)
    }
}