package computer.austins.tbdutils.command

import computer.austins.tbdutils.util.vanish.Vanish
import computer.austins.tbdutils.util.vanish.VanishManager

import io.papermc.paper.command.brigadier.CommandSourceStack

import org.bukkit.entity.Player

import org.incendo.cloud.annotations.Command
import org.incendo.cloud.annotations.Permission
import org.incendo.cloud.annotations.processing.CommandContainer

@Suppress("unused", "unstableApiUsage")
@CommandContainer
class VanishCommand {
    @Command("vanish")
    @Permission("tbdutils.vanish")
    fun vanish(css: CommandSourceStack) {
        val player = css.sender as Player
        if(!VanishManager.isVanished(player.uniqueId)) {
            Vanish(player)
        } else {
            VanishManager.unvanish(player.uniqueId)
        }
    }
}