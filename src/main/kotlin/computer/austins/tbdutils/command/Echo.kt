package computer.austins.tbdutils.command

import io.papermc.paper.command.brigadier.CommandSourceStack

import org.incendo.cloud.annotations.Command
import org.incendo.cloud.annotations.Permission
import org.incendo.cloud.annotations.processing.CommandContainer

@Suppress("unused", "unstableApiUsage")
@CommandContainer
/** Dummy command **/
class Echo {
    @Command("echo <text>")
    @Permission("burb.cmd.echo")
    fun echo(css: CommandSourceStack, text: Array<String>) {
        css.sender.sendMessage(text.joinToString(" "))
    }
}
