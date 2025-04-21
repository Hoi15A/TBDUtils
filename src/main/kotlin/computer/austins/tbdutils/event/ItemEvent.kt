package computer.austins.tbdutils.event

import computer.austins.tbdutils.util.vanish.VanishManager

import org.bukkit.event.EventHandler

import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerAttemptPickupItemEvent
import org.bukkit.event.player.PlayerDropItemEvent

@Suppress("unused")
class ItemEvent : Listener {
    @EventHandler
    private fun onItemPickup(e : PlayerAttemptPickupItemEvent) {
        e.isCancelled = VanishManager.isVanished(e.player.uniqueId)
    }

    @EventHandler
    private fun onItemDrop(e : PlayerDropItemEvent) {
        e.isCancelled = VanishManager.isVanished(e.player.uniqueId)
    }
}