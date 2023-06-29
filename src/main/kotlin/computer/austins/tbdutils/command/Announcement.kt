package computer.austins.tbdutils.command

import computer.austins.tbdutils.util.Chat

import cloud.commandframework.arguments.standard.IntegerArgument
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.kotlin.extension.argumentDescription
import cloud.commandframework.kotlin.extension.buildAndRegister
import cloud.commandframework.paper.PaperCommandManager

import org.bukkit.command.CommandSender

@Suppress("unused")
class Announcement : BaseCommand() {
    override fun register(commandManager: PaperCommandManager<CommandSender>) {
        commandManager.buildAndRegister("announce") {
            permission = "tbdutils.command.announce"
            commandDescription("Broadcasts the provided text to all players.")

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
        commandManager.buildAndRegister("announcerestart") {
            permission = "tbdutils.command.announce"
            commandDescription("Broadcasts a server restart announcement to all players.")

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