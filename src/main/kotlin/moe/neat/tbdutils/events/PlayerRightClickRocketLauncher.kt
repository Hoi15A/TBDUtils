package moe.neat.tbdutils.events

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
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
class PlayerRightClickRocketLauncher : Listener {
    private lateinit var fireball: Fireball
    private lateinit var creeper: Creeper

    @EventHandler
    private fun onPlayerRightClickRocketLauncher(e: PlayerInteractEvent) {
        if (e.player.inventory.itemInMainHand.type == Material.GOLDEN_SHOVEL && e.player.inventory.itemInMainHand.itemMeta.displayName() == Component.text("Rocket Launcher").color(NamedTextColor.GOLD)) {
            if (e.action == Action.RIGHT_CLICK_AIR) {
                onLaunch(e.player)
            } else if (e.action == Action.RIGHT_CLICK_BLOCK) {
                onLaunch(e.player)
            }
        }
    }

    private fun onLaunch(player: Player) {
        fireball = player.launchProjectile(Fireball::class.java, player.location.direction.multiply(0.75))
        fireball.setIsIncendiary(false)
        fireball.yield = 0f
        fireball.customName(Component.text("rocket"))
        fireball.velocity = player.location.direction

        moe.neat.tbdutils.Plugin.plugin.let {
            object : BukkitRunnable() {
                override fun run() {
                    if (fireball.isDead) {
                        val fireballDeathLoc: Location = fireball.location.clone()
                        fireballDeathLoc.y += 2.5
                        creeper = fireball.world.spawn(fireballDeathLoc, Creeper::class.java)
                        creeper.explode()
                        cancel()
                    }
                }
            }.runTaskTimer(it, 0L, 2L)
        }
    }

    @EventHandler
    fun onFireballHit(e: EntityDamageByEntityEvent) {
        if(e.damager == fireball || e.damager == creeper) {
            e.damage = 0.0
        }
    }

    @EventHandler
    fun onFireballExplode(e: EntityExplodeEvent) {
        if(e.entity == fireball) {
            e.isCancelled = true
        }
    }
}