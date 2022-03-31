package moe.neat.tbdutils.events

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.util.BlockIterator

@Suppress("unused")
class PlayerUseLightningWand : Listener {
    @EventHandler
    private fun onPlayerUseLightningWand(e : PlayerInteractEvent) {
        if (e.player.hasPermission("tbdutils.customitems.use") && e.player.inventory.itemInMainHand.type == Material.BLAZE_ROD && e.player.inventory.itemInMainHand.itemMeta.displayName() == Component.text("Lightning Wand").color(TextColor.fromHexString("#78acff")).decoration(TextDecoration.ITALIC, false)) {
            if (e.action == Action.RIGHT_CLICK_AIR) {
                onStrike(e.player)
            } else if (e.action == Action.RIGHT_CLICK_BLOCK) {
                onStrike(e.player)
            }
        }
    }

    private fun onStrike(player : Player) {
        val blocksToAdd = BlockIterator(player.eyeLocation, 0.0, 80)
        while (blocksToAdd.hasNext()) {
            val loc: Location = blocksToAdd.next().location
            if (loc.block.type.isSolid) {
                val world: World = player.world
                world.strikeLightningEffect(loc)
                break
            } else {
                val world: World = player.world
                world.spawnParticle(Particle.FIREWORKS_SPARK, loc, 1, 0.0, 0.0, 0.0, 0.05)
            }
        }
    }

    @EventHandler
    private fun onInteract(e: PlayerInteractEvent) {
        if(e.player.inventory.itemInMainHand.type == Material.BLAZE_ROD && e.player.inventory.itemInMainHand.itemMeta.displayName() == Component.text("Lightning Wand").color(TextColor.fromHexString("#78acff")).decoration(TextDecoration.ITALIC, false)) {
            e.isCancelled = true
        }
    }
}