package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.Argument
import cloud.commandframework.annotations.CommandDescription
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import org.bukkit.command.CommandSender

@Suppress("unused")
class Echo : BaseCommand {
    @CommandMethod("echo <text>")
    @CommandDescription("Echooooooooooooooooooo echoooo echooooo")
    @CommandPermission("tbdutils.command.echo")
    fun echo(sender: CommandSender, @Argument("text") text: Array<String>) {
        sender.sendMessage(text.joinToString(" "))
    }
}