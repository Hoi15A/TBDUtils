package computer.austins.tbdutils.command

import computer.austins.tbdutils.plugin
import computer.austins.tbdutils.task.IntroductionTask

import cloud.commandframework.ArgumentDescription
import cloud.commandframework.extra.confirmation.CommandConfirmationManager
import cloud.commandframework.kotlin.extension.buildAndRegister
import cloud.commandframework.paper.PaperCommandManager

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

@Suppress("unused")
class Introduction : BaseCommand() {
    override fun register(commandManager: PaperCommandManager<CommandSender>) {
        commandManager.buildAndRegister("intro") {
            permission = "tbdutils.command.special"
            commandDescription("Special command for interaction with server events.")
            meta(CommandConfirmationManager.META_CONFIRMATION_REQUIRED, true)

            literal(
                "start",
                ArgumentDescription.of("Starts event.")
            )

            handler {
                it.sender.sendMessage("Starting introduction for all online players.")
                for(player in Bukkit.getOnlinePlayers()) {
                    IntroductionTask().startIntroLoop(player, plugin)
                }
            }
        }
    }
}

@Suppress("unused")
class IntroDebug : BaseCommand() {
    override fun register(commandManager: PaperCommandManager<CommandSender>) {
        commandManager.buildAndRegister("intro") {
            permission = "tbdutils.command.special"
            commandDescription("Special command for interaction with server events.")

            literal(
                "stop",
                ArgumentDescription.of("Force stops intro event for all online.")
            )

            handler {
                it.sender.sendMessage("Force stopped all ongoing introductions.")
                for(player in Bukkit.getOnlinePlayers()) {
                    IntroductionTask().stopIntroLoop(player)
                }
            }
        }
    }
}