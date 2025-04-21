package computer.austins.tbdutils.util.pathfinding

import org.bukkit.entity.Mob
import org.bukkit.entity.Player

object Pathfinding {
    fun clearNearbyTargets(player : Player) {
        val nearbyEntities = player.getNearbyEntities(20.0, 20.0, 20.0).filterIsInstance<Mob>()
        for(entity in nearbyEntities) {
            entity.target = null
        }
    }
}