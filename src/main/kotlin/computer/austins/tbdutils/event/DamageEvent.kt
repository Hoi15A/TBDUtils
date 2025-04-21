package computer.austins.tbdutils.event

import computer.austins.tbdutils.util.vanish.VanishManager

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent

@Suppress("unused")
class DamageEvent : Listener {
    @EventHandler
    private fun onDamage(e : EntityDamageEvent) {
        if(e.entity is Player) {
            val damaged = e.entity as Player
            e.isCancelled = VanishManager.isVanished(damaged.uniqueId)
        }
    }

    @EventHandler
    private fun onDamageByEntity(e : EntityDamageByEntityEvent) {
        if(e.entity is Player) {
            val damaged = e.entity as Player
            e.isCancelled = VanishManager.isVanished(damaged.uniqueId)
        }
        if(e.damager is Player) {
            val damager = e.damager as Player
            e.isCancelled = VanishManager.isVanished(damager.uniqueId)
        }
    }
}