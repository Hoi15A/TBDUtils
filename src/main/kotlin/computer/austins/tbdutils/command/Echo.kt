package computer.austins.tbdutils.command

import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.kotlin.extension.argumentDescription
import cloud.commandframework.kotlin.extension.buildAndRegister
import cloud.commandframework.paper.PaperCommandManager
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender

@Suppress("unused")
class Echo : BaseCommand() {
    override fun register(commandManager: PaperCommandManager<CommandSender>) {
        commandManager.buildAndRegister("echo") {
            permission = "tbdutils.command.echo"
            commandDescription("Responds with provided arguments")

            argument(argumentDescription("Text")) {
                StringArgument.greedy("text")
            }

            handler {
                val arg = it.get<String>("text")
                it.sender.sendMessage(Component.text(arg))
            }
        }
    }
}
