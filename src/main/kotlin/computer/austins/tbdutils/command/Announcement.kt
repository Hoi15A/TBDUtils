package computer.austins.tbdutils.command

import computer.austins.tbdutils.util.Chat

import cloud.commandframework.arguments.standard.IntegerArgument
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.extra.confirmation.CommandConfirmationManager
import cloud.commandframework.kotlin.extension.argumentDescription
import cloud.commandframework.kotlin.extension.buildAndRegister
import cloud.commandframework.paper.PaperCommandManager

import org.bukkit.command.CommandSender

@Suppress("unused")
class Announcement : BaseCommand() {
    override fun register(commandManager: PaperCommandManager<CommandSender>) {
        commandManager.buildAndRegister("announce",
            argumentDescription("Broadcasts the provided text to all players.")
        ) {
            permission = "tbdutils.command.announce"

            meta(CommandConfirmationManager.META_CONFIRMATION_REQUIRED, true)

            argument(argumentDescription("Text")) {
                StringArgument.greedy("text")
            }

            handler {
                val arg = it.get<String>("text")
                Chat.broadcast(arg)
            }
        }
    }
}

@Suppress("unused")
class RestartAnnouncement : BaseCommand() {
    override fun register(commandManager: PaperCommandManager<CommandSender>) {
        commandManager.buildAndRegister("announcerestart"
            , argumentDescription("Broadcasts a server restart announcement to all players.")
        ) {
            permission = "tbdutils.command.announce"

            argument(argumentDescription("Time")) {
                IntegerArgument.builder<CommandSender>("time").withMin(1).withMax(Int.MAX_VALUE).build()
            }

            handler {
                val arg = it.get<Int>("time")
                Chat.broadcastRestart(arg)
            }
        }
    }

}