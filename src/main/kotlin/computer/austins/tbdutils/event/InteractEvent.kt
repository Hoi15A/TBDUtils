package computer.austins.tbdutils.event

import computer.austins.tbdutils.util.vanish.VanishManager

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

@Suppress("unused")
class InteractEvent : Listener {
    @EventHandler
    private fun onInteract(e : PlayerInteractEvent) {
        e.isCancelled = VanishManager.isVanished(e.player.uniqueId)
    }
}