package computer.austins.tbdutils.command

import cloud.commandframework.arguments.standard.IntegerArgument
import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.extra.confirmation.CommandConfirmationManager
import cloud.commandframework.kotlin.extension.argumentDescription
import cloud.commandframework.kotlin.extension.buildAndRegister
import cloud.commandframework.paper.PaperCommandManager

import computer.austins.tbdutils.util.Notification
import computer.austins.tbdutils.util.Sounds

import net.kyori.adventure.title.Title.Times

import org.bukkit.command.CommandSender

import java.time.Duration

@Suppress("unused")
class Announcement : BaseCommand() {
    override fun register(commandManager: PaperCommandManager<CommandSender>) {
        commandManager.buildAndRegister("announce") {
            permission = "tbdutils.command.announce"
            commandDescription("Broadcasts the provided text to all players.")

            meta(CommandConfirmationManager.META_CONFIRMATION_REQUIRED, true)

            argument(argumentDescription("Text")) {
                StringArgument.greedy("text")
            }

            handler {
                val arg = it.get<String>("text")
                Notification.announceServer("<yellow><b>Announcement<reset>", arg)
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
                val time = it.get<Int>("time")
                Notification.announceServer(
                    "<red><b>Server Restarting<reset>",
                    "In $time minute${if (time > 1) "s" else ""}.",
                    Sounds.RESTART_ANNOUNCEMENT,
                    Times.times(
                        Duration.ofSeconds(1.toLong()),
                        Duration.ofSeconds(3.toLong()),
                        Duration.ofSeconds(1.toLong())
                    )
                )
            }
        }
    }
}
