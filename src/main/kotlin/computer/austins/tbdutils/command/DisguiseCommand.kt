package computer.austins.tbdutils.command

import computer.austins.tbdutils.util.disguise.Disguise
import computer.austins.tbdutils.util.disguise.DisguiseManager
import computer.austins.tbdutils.util.disguise.DisguiseManager.isDisguised

import io.papermc.paper.command.brigadier.CommandSourceStack

import org.bukkit.entity.Player
import org.incendo.cloud.annotations.Argument
import org.incendo.cloud.annotations.Command
import org.incendo.cloud.annotations.Permission
import org.incendo.cloud.annotations.processing.CommandContainer

@Suppress("unused", "unstableApiUsage")
@CommandContainer
class DisguiseCommand {
    @Command("disguise <disguiseName> <disguiseSkin>")
    @Permission("tbdutils.cmd.disguise")
    fun disguise(css: CommandSourceStack, @Argument("disguiseName") disguiseName: String, @Argument("disguiseSkin") disguiseSkin: String) {
        if(css.sender is Player) {
            val player = css.sender as Player
            if(!player.isDisguised()) {
                Disguise(player, disguiseName, disguiseSkin)
            } else {
                css.sender.sendMessage("You are already disguised.")
            }
        }
    }

    @Command("undisguise")
    @Permission("tbdutils.cmd.disguise")
    fun undisguise(css: CommandSourceStack) {
        if(css.sender is Player) {
            val player = css.sender as Player
            if(player.isDisguised()) {
                DisguiseManager.undisguise(player.uniqueId)
            } else {
                css.sender.sendMessage("You are not disguised.")
            }
        }
    }
}