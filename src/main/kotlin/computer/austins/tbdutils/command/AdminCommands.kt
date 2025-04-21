package computer.austins.tbdutils.command

import computer.austins.tbdutils.util.Chat
import io.papermc.paper.command.brigadier.CommandSourceStack

import org.bukkit.entity.Player
import org.incendo.cloud.annotations.Argument
import org.incendo.cloud.annotations.Command
import org.incendo.cloud.annotations.Permission
import org.incendo.cloud.annotations.processing.CommandContainer

@Suppress("unused", "unstableApiUsage")
@CommandContainer
class AdminCommands {
    @Command("ac <text>")
    @org.incendo.cloud.annotations.CommandDescription("Changes the value of the specified setting.")
    @Permission("tbdutils.cmd.chat")
    fun adminChat(css: CommandSourceStack, @Argument("text") text: Array<String>) {
        if(css.sender is Player) {
            val player = css.sender as Player
            Chat.broadcastAdmin("<skull:${player.name}><dark_red>${player.name}<white>: ${text.joinToString(" ")}", false)
        }
    }

    @Command("dc <text>")
    @org.incendo.cloud.annotations.CommandDescription("Changes the value of the specified setting.")
    @Permission("tbdutils.cmd.chat")
    fun devChat(css: CommandSourceStack, @Argument("text") text: Array<String>) {
        if(css.sender is Player) {
            val player = css.sender as Player
            Chat.broadcastDev("<skull:${player.name}><gold>${player.name}<white>: ${text.joinToString(" ")}", false)
        }
    }
}