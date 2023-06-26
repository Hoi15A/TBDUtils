package moe.neat.tbdutils.events

import moe.neat.tbdutils.commands.Finale
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

@Suppress("unused")
class PlayerDamagePlayerEvent : Listener {
    @EventHandler
    private fun onOneHitKill(e : EntityDamageByEntityEvent) {
        if(e.entity is Player && e.damager is Player) {
            val deadPlayer = e.entity as Player
            val killer = e.damager as Player
            if(Finale.isDowntimeActive) {
                e.isCancelled = true
            } else {
                if(killer.inventory.itemInMainHand.type == Material.BLAZE_ROD && killer.inventory.itemInMainHand.itemMeta.displayName() == Component.text("Keith's Will").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false)) {
                    deadPlayer.world.strikeLightningEffect(deadPlayer.location)
                }
            }
        }
    }
}