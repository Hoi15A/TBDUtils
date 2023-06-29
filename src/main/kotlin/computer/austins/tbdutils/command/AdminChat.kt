package computer.austins.tbdutils.command

import computer.austins.tbdutils.util.Chat

import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.kotlin.extension.argumentDescription
import cloud.commandframework.kotlin.extension.buildAndRegister
import cloud.commandframework.paper.PaperCommandManager

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

import org.bukkit.command.CommandSender

@Suppress("unused")
class AdminChat : BaseCommand() {
    override fun register(commandManager: PaperCommandManager<CommandSender>) {
        commandManager.buildAndRegister("ac") {
            permission = "tbdutils.command.admin_chat"
            commandDescription("Sends the provided text to all online admins.")

            argument(argumentDescription("Text")) {
                StringArgument.greedy("text")
            }

            handler {
                val arg = it.get<String>("text")
                Chat.broadcastAdmin(Component.text(it.sender.name, NamedTextColor.DARK_RED)
                    .append(Component.text(": $arg", NamedTextColor.WHITE)), false)
            }
        }
    }
}

@Suppress("unused")
class DevChat : BaseCommand() {
    override fun register(commandManager: PaperCommandManager<CommandSender>) {
        commandManager.buildAndRegister("dc") {
            permission = "tbdutils.command.dev_chat"
            commandDescription("Sends the provided text to all online devs.")

            argument(argumentDescription("Text")) {
                StringArgument.greedy("text")
            }

            handler {
                val arg = it.get<String>("text")
                Chat.broadcastDev(Component.text(it.sender.name, NamedTextColor.GOLD)
                    .append(Component.text(": $arg", NamedTextColor.WHITE)), false)
            }
        }
    }
}