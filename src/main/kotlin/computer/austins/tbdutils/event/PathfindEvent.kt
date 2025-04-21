package computer.austins.tbdutils.event

import computer.austins.tbdutils.util.vanish.VanishManager

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityTargetEvent

@Suppress("unused")
class PathfindEvent : Listener {
    @EventHandler
    private fun onPathfind(e : EntityTargetEvent) {
        if(e.target != null) {
            if(e.target is Player) {
                val targetedPlayer = e.target as Player
                if(VanishManager.isVanished(targetedPlayer.uniqueId)) {
                    e.target = null
                    e.isCancelled = true
                }
            }
        }
    }
}