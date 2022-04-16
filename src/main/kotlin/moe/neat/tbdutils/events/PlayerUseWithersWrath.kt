package moe.neat.tbdutils.events

import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.Particle

import org.bukkit.entity.Player
import org.bukkit.entity.WitherSkull
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

@Suppress("unused")
class PlayerUseWithersWrath : Listener {
    private val shootSound: Sound = Sound.sound(Key.key("entity.wither.shoot"), Sound.Source.MASTER, 1f, 1f)

    private val failSound: Sound = Sound.sound(Key.key("entity.enderman.teleport"), Sound.Source.MASTER, 1f, 0f)
    private val failMessage: Component = Component.text("You do not have the ability to wield this item.").color(NamedTextColor.RED)

    @EventHandler
    private fun onUseWithersWrath(e : PlayerInteractEvent) {
        if(e.player.hasPermission("tbdutils.customitems.use") && e.player.inventory.itemInMainHand.type == Material.WITHER_ROSE && e.player.inventory.itemInMainHand.itemMeta.displayName() == Component.text("Wither's Wrath").color(TextColor.fromHexString("#292929")).decoration(TextDecoration.ITALIC, false)) {
            if (e.action == Action.RIGHT_CLICK_AIR || e.action == Action.RIGHT_CLICK_BLOCK) {
                shootSkull(e.player)
            }
        }
        if(!e.player.hasPermission("tbdutils.customitems.use") && e.player.inventory.itemInMainHand.type == Material.WITHER_ROSE && e.player.inventory.itemInMainHand.itemMeta.displayName() == Component.text("Wither's Wrath").color(TextColor.fromHexString("#292929")).decoration(TextDecoration.ITALIC, false)) {
            e.player.sendMessage(failMessage)
            e.player.playSound(failSound)
        }
    }

    private fun shootSkull(player : Player) {
        val witherSkull: WitherSkull = player.launchProjectile(WitherSkull::class.java)
        player.world.playSound(shootSound, Sound.Emitter.self())
        witherSkull.world.spawnParticle(Particle.EXPLOSION_LARGE, witherSkull.location, 1, 0.0, 0.0, 0.0, 0.0)
        witherSkull.world.spawnParticle(Particle.SPELL_WITCH, witherSkull.location, 15, 0.75, 0.75, 0.75, 0.15)
    }

    @EventHandler
    private fun onInteract(e : PlayerInteractEvent) {
        if(e.player.inventory.itemInMainHand.type == Material.WITHER_ROSE && e.player.inventory.itemInMainHand.itemMeta.displayName() == Component.text("Wither's Wrath").color(TextColor.fromHexString("#292929")).decoration(TextDecoration.ITALIC, false)) {
            e.isCancelled = true
        }
    }
}