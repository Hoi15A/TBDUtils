package computer.austins.tbdutils.command

import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.kotlin.extension.argumentDescription
import cloud.commandframework.kotlin.extension.buildAndRegister
import cloud.commandframework.paper.PaperCommandManager
import computer.austins.tbdutils.util.Chat
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

private const val SHRUG = "¯\\_(ツ)_/¯"

@Suppress("unused")
class Shrug : BaseCommand() {
    override fun register(commandManager: PaperCommandManager<CommandSender>) {
        commandManager.buildAndRegister("shrug") {
            permission = "tbdutils.command.shrug"
            commandDescription("Adds a shrug emote to your message")
            senderType<Player>()

            argument(argumentDescription("Message content")) {
                StringArgument.optional("message", StringArgument.StringMode.GREEDY)
            }

            handler {
                val arg = it.getOrDefault("message", "")
                Chat.globalChat(it.sender as Player, Component.text("$arg $SHRUG".trim()))
            }
        }
    }
}