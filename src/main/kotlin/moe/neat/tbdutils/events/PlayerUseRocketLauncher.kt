package moe.neat.tbdutils.events

import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.*
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityExplodeEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitRunnable

@Suppress("unused")
class PlayerUseRocketLauncher : Listener {
    private lateinit var fireball: Fireball
    private lateinit var creeper: Creeper

    private val failSound: Sound = Sound.sound(Key.key("entity.enderman.teleport"), Sound.Source.MASTER, 1f, 0f)
    private val failMessage: Component = Component.text("You do not have the ability to wield this item.").color(NamedTextColor.RED)

    @EventHandler
    private fun onPlayerUseRocketLauncher(e : PlayerInteractEvent) {
        if(e.player.hasPermission("tbdutils.customitems.use") && e.player.inventory.itemInMainHand.type == Material.GOLDEN_HORSE_ARMOR && e.player.inventory.itemInMainHand.itemMeta.displayName() == Component.text("Rocket Launcher").color(TextColor.fromHexString("#faac05")).decoration(TextDecoration.ITALIC, false)) {
            if (e.action == Action.RIGHT_CLICK_AIR) {
                onLaunch(e.player)
            } else if (e.action == Action.RIGHT_CLICK_BLOCK) {
                onLaunch(e.player)
            }
        }
        if(!e.player.hasPermission("tbdutils.customitems.use") && e.player.inventory.itemInMainHand.type == Material.GOLDEN_HORSE_ARMOR && e.player.inventory.itemInMainHand.itemMeta.displayName() == Component.text("Rocket Launcher").color(TextColor.fromHexString("#faac05")).decoration(TextDecoration.ITALIC, false)) {
            e.player.sendMessage(failMessage)
            e.player.playSound(failSound)
        }
    }

    private fun onLaunch(player : Player) {
        fireball = player.launchProjectile(Fireball::class.java, player.location.direction.multiply(2.75))
        fireball.setIsIncendiary(false)
        fireball.yield = 0f
        fireball.customName(Component.text("rocket"))
        fireball.velocity = player.location.direction

        moe.neat.tbdutils.Plugin.plugin.let {
            object : BukkitRunnable() {
                override fun run() {
                    if(fireball.isDead) {
                        val fireballDeathLoc: Location = fireball.location.clone()
                        fireballDeathLoc.y += 1.75
                        creeper = fireball.world.spawn(fireballDeathLoc, Creeper::class.java)
                        creeper.explode()
                        cancel()
                    }
                }
            }.runTaskTimer(it, 0L, 1L)
        }
    }

    @EventHandler
    private fun onFireballHit(e : EntityDamageByEntityEvent) {
        if(e.damager == fireball || e.damager == creeper) {
            e.damage = 0.0
        }
    }

    @EventHandler
    private fun onFireballExplode(e : EntityExplodeEvent) {
        if(e.entity == fireball) {
            e.isCancelled = true
        }
    }

    @EventHandler
    private fun onInteract(e : PlayerInteractEvent) {
        if(e.player.inventory.itemInMainHand.type == Material.GOLDEN_HORSE_ARMOR && e.player.inventory.itemInMainHand.itemMeta.displayName() == Component.text("Rocket Launcher").color(TextColor.fromHexString("#faac05")).decoration(TextDecoration.ITALIC, false)) {
            e.isCancelled = true
        }
    }
}