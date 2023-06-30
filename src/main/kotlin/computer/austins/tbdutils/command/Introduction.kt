package computer.austins.tbdutils.command

import computer.austins.tbdutils.plugin
import computer.austins.tbdutils.task.EventTask

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
                EventTask.isDowntimeActive = false
                for(player in Bukkit.getOnlinePlayers()) {
                    EventTask.stopDowntimeLoop(player)
                    EventTask.startIntroLoop(player, plugin)
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
                    EventTask.stopIntroLoop(player)
                }
            }
        }
    }
}

@Suppress("unused")
class Downtime : BaseCommand() {
    override fun register(commandManager: PaperCommandManager<CommandSender>) {
        commandManager.buildAndRegister("intro") {
            permission = "tbdutils.command.special"
            commandDescription("Special command for interaction with server events.")

            literal(
                "toggle_downtime",
                ArgumentDescription.of("Toggles downtime.")
            )

            handler {
                if(EventTask.isDowntimeActive) {
                    it.sender.sendMessage("Disabled downtime phase.")
                    EventTask.isDowntimeActive = false
                    for(player in Bukkit.getOnlinePlayers()) {
                        EventTask.stopDowntimeLoop(player)
                    }
                } else {
                    it.sender.sendMessage("Enabled downtime phase.")
                    EventTask.isDowntimeActive = true
                    Bukkit.dispatchCommand(it.sender, "gamerule doDaylightCycle false")
                    Bukkit.dispatchCommand(it.sender, "time set 0")
                    Bukkit.dispatchCommand(it.sender, "gamerule doWeatherCycle false")
                    Bukkit.dispatchCommand(it.sender, "gamerule doInsomnia false")
                    for(player in Bukkit.getOnlinePlayers()) {
                        EventTask.startDowntimeLoop(player, plugin)
                    }
                }
            }
        }
    }
}