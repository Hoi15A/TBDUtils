package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.CommandDescription
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

import org.bukkit.command.CommandSender

@Suppress("unused")
class ToggleVisualDialogue : BaseCommand {
    @CommandMethod("togglevisualdialogue")
    @CommandDescription("Toggles the visual dialogue for Byrtrum")
    @CommandPermission("tbdutils.command.togglevisualdialogue")
    fun toggleVisualDialogue(sender: CommandSender) {
        if(sender.name != "Byrtrum") {
            sender.sendMessage(Component.text("You cannot execute this command.").color(NamedTextColor.RED))
        } else {
            visualDialogue = if(visualDialogue) {
                sender.sendMessage(Component.text("Disabled visual dialogue for Byrtrum!").color(NamedTextColor.RED))
                false
            } else {
                sender.sendMessage(Component.text("Enabled visual dialogue for Byrtrum!").color(NamedTextColor.GREEN))
                true
            }
        }
    }

    companion object {
        var visualDialogue = false
    }
}