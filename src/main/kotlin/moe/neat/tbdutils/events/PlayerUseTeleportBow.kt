package moe.neat.tbdutils.events

import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Location

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.ProjectileHitEvent

@Suppress("unused")
class PlayerUseTeleportBow : Listener {
    private val teleportSound: Sound = Sound.sound(Key.key("entity.enderman.teleport"), Sound.Source.MASTER, 1f, 1f)

    @EventHandler
    fun onProjectileHit(e : ProjectileHitEvent) {
        if (e.entity.shooter is Player) {
            val player: Player = e.entity.shooter as Player
            if (player.hasPermission("tbdutils.customitems.use") && player.inventory.itemInMainHand.type == Material.BOW && player.inventory.itemInMainHand.itemMeta.displayName() == Component.text("Teleport Bow").color(TextColor.fromHexString("#5524c7")).decoration(TextDecoration.ITALIC, false)) {
                if (e.hitBlock == null) {
                    e.hitEntity?.let { player.teleport(it) }
                } else if (e.hitEntity == null) {
                    val teleportXLoc: Double = e.hitBlock!!.location.x
                    val teleportYLoc: Double = e.hitBlock!!.location.y + 1.0
                    val teleportZLoc: Double = e.hitBlock!!.location.z
                    val teleportYawLoc: Float = player.location.yaw
                    val teleportPitchLoc: Float = player.location.pitch
                    val teleportLoc = Location(player.world, teleportXLoc, teleportYLoc, teleportZLoc, teleportYawLoc, teleportPitchLoc)
                    player.teleport(teleportLoc)
                }
                player.playSound(teleportSound)
                e.entity.remove()
            }
        }
    }

    @EventHandler
    private fun onInteract(e : BlockBreakEvent) {
        if(e.player.inventory.itemInMainHand.type == Material.BOW && e.player.inventory.itemInMainHand.itemMeta.displayName() == Component.text("Teleport Bow").color(TextColor.fromHexString("#5524c7")).decoration(TextDecoration.ITALIC, false)) {
            e.isCancelled = true
        }
    }
}