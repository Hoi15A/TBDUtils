package moe.neat.tbdutils.events

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.GameMode

import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerArmorStandManipulateEvent
import org.bukkit.event.player.PlayerChatEvent
import org.bukkit.scheduler.BukkitRunnable

@Suppress("unused")
class PlayerChat : Listener {
    @EventHandler
    private fun onChat(e : PlayerChatEvent) {
        if (e.player.name == "Byrtrum" && e.player.gameMode != GameMode.SPECTATOR) {
            val dialogue = e.player.location.world.spawn(e.player.location, ArmorStand::class.java)
            dialogue.addScoreboardTag("dialogue")
            dialogue.isInvulnerable = true
            dialogue.isInvisible = true
            dialogue.isSmall = true
            dialogue.setGravity(false)
            dialogue.customName(Component.text(e.message)
                .decoration(TextDecoration.BOLD, true))
            dialogue.isCustomNameVisible = true

            var i = 0
            moe.neat.tbdutils.Plugin.plugin.let {
                object : BukkitRunnable() {
                    override fun run() {
                        val playerLocX = e.player.location.x
                        val playerLocY = e.player.location.y + 1.25
                        val playerLocZ = e.player.location.z
                        val playerLoc = Location(e.player.world, playerLocX, playerLocY, playerLocZ)
                        dialogue.teleport(playerLoc)
                        i++
                        if(i == 80) {
                            dialogue.remove()
                        }
                    }
                }.runTaskTimer(it, 0L, 1L)
            }
        }
    }

    @EventHandler
    private fun playerInteractWithDialogue(e : PlayerArmorStandManipulateEvent) {
        e.isCancelled = true
    }
}