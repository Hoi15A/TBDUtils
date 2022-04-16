package moe.neat.tbdutils.events

import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent

import kotlin.math.cos
import kotlin.math.sin


@Suppress("unused")
class PlayerUseAspectOfTheVoid : Listener {
    private val teleportSound: Sound = Sound.sound(Key.key("entity.enderman.teleport"), Sound.Source.MASTER, 1f, 1f)
    private val etherwarpSound: Sound = Sound.sound(Key.key("entity.elder_guardian.ambient"), Sound.Source.MASTER, 1f, 2f)

    private val failSound: Sound = Sound.sound(Key.key("entity.enderman.teleport"), Sound.Source.MASTER, 1f, 0f)
    private val failMessage: Component = Component.text("You do not have the ability to wield this item.").color(NamedTextColor.RED)

    @EventHandler
    private fun onPlayerUseAspectOfTheVoid(e : PlayerInteractEvent) {
        if(e.player.hasPermission("tbdutils.customitems.use") && e.player.inventory.itemInMainHand.type == Material.DIAMOND_SHOVEL && e.player.inventory.itemInMainHand.itemMeta.displayName() == Component.text("Aspect of the Void").color(TextColor.fromHexString("#992af5")).decoration(TextDecoration.ITALIC, false)) {
            if(e.player.isSneaking) {
                if (e.action == Action.RIGHT_CLICK_AIR) {
                    onTeleport(e.player, 80)
                } else if (e.action == Action.RIGHT_CLICK_BLOCK) {
                    onTeleport(e.player, 80)
                }
            } else {
                if (e.action == Action.RIGHT_CLICK_AIR) {
                    onTeleport(e.player, 12)
                } else if (e.action == Action.RIGHT_CLICK_BLOCK) {
                    onTeleport(e.player, 12)
                }
            }
        }
        if(!e.player.hasPermission("tbdutils.customitems.use") && e.player.inventory.itemInMainHand.type == Material.DIAMOND_SHOVEL && e.player.inventory.itemInMainHand.itemMeta.displayName() == Component.text("Aspect of the Void").color(TextColor.fromHexString("#992af5")).decoration(TextDecoration.ITALIC, false)) {
            e.player.sendMessage(failMessage)
            e.player.playSound(failSound)
        }
    }

    private fun onTeleport(player : Player, maxDistance: Int) {
        val block: Block = player.getTargetBlock(null, maxDistance)
        val location: Location = block.location
        val pitch = player.eyeLocation.pitch
        val yaw = player.eyeLocation.yaw
        location.add(0.5, 1.0, 0.5)
        location.yaw = yaw
        location.pitch = pitch
        player.teleport(location)
        player.playSound(teleportSound)

        if(maxDistance == 80) {
            player.playSound(etherwarpSound, Sound.Emitter.self())
            val sphereLoc = Location(player.location.world, player.location.x, player.location.y, player.location.z)
            val r = 1.5
            run {
                var phi = 0.0
                while (phi <= Math.PI) {
                    val y = r * cos(phi) + 1.5
                    var theta = 0.0
                    while (theta <= 2 * Math.PI) {
                        val x = r * cos(theta) * sin(phi)
                        val z = r * sin(theta) * sin(phi)
                        sphereLoc.add(x, y, z)
                        sphereLoc.world.spawnParticle(Particle.SPELL_WITCH, sphereLoc, 1, 0.0, 0.0, 0.0, 0.0)
                        sphereLoc.subtract(x, y, z)
                        theta += Math.PI / 30
                    }
                    phi += Math.PI / 15
                }
            }
        }
    }

    @EventHandler
    private fun onBlockBreak(e : BlockBreakEvent) {
        if(e.player.inventory.itemInMainHand.type == Material.DIAMOND_SHOVEL && e.player.inventory.itemInMainHand.itemMeta.displayName() == Component.text("Aspect of the Void").color(TextColor.fromHexString("#992af5")).decoration(TextDecoration.ITALIC, false)) {
            e.isCancelled = true
        }
    }

    @EventHandler
    private fun onInteract(e : PlayerInteractEvent) {
        if(e.player.inventory.itemInMainHand.type == Material.DIAMOND_SHOVEL || e.player.inventory.itemInOffHand.type == Material.DIAMOND_SHOVEL && e.player.inventory.itemInMainHand.itemMeta.displayName() == Component.text("Aspect of the Void").color(TextColor.fromHexString("#992af5")).decoration(TextDecoration.ITALIC, false) && e.player.inventory.itemInOffHand.itemMeta.displayName() == Component.text("Aspect of the Void").color(TextColor.fromHexString("#992af5")).decoration(TextDecoration.ITALIC, false)) {
            e.isCancelled = true
        }
    }
}