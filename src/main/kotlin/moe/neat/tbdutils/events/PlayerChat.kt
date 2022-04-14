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

@Suppress("unused","deprecation")
class PlayerChat : Listener {
    @EventHandler
    private fun onChat(e : PlayerChatEvent) {
        //TODO: Add a toggle for this
        if (e.player.name == "Byrtrum" && e.player.gameMode != GameMode.SPECTATOR) {
            val spawnDialogueLocX = e.player.location.x
            val spawnDialogueLocY = e.player.location.y + 1.25
            val spawnDialogueLocZ = e.player.location.z
            val spawnDialogueLoc = Location(e.player.world, spawnDialogueLocX, spawnDialogueLocY, spawnDialogueLocZ)
            val dialogue = e.player.location.world.spawn(spawnDialogueLoc, ArmorStand::class.java)
            dialogue.isInvisible = true
            dialogue.isSmall = true
            dialogue.isInvulnerable = true
            dialogue.setGravity(false)
            dialogue.addScoreboardTag("dialogue")
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
        if(e.rightClicked.scoreboardTags.contains("dialogue"))
        {
            e.isCancelled = true
        }
    }
}