package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.Argument
import cloud.commandframework.annotations.CommandDescription
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

import org.bukkit.command.CommandSender

@Suppress("unused")
class VisualDialogue : BaseCommand {
    private val failSound: Sound = Sound.sound(Key.key("entity.enderman.teleport"), Sound.Source.MASTER, 1f, 0f)
    private val failMessage: Component = Component.text("This is currently disabled!").color(NamedTextColor.RED)

    @CommandMethod("visualdialogue <option>")
    @CommandDescription("Toggles the visual dialogue for Byrtrum")
    @CommandPermission("tbdutils.command.visualdialogue")
    fun toggleVisualDialogue(sender: CommandSender, @Argument("option") option : VisualDialogueOption) {
        if(sender.name != "Byrtrum") {
            sender.sendMessage(Component.text("You cannot execute this command.").color(NamedTextColor.RED))
        } else {
            when(option) {
                VisualDialogueOption.TOGGLE -> {
                    visualDialogue = if (visualDialogue) {
                        sender.sendMessage(Component.text("Disabled visual dialogue for Byrtrum!").color(NamedTextColor.RED))
                        false
                    } else {
                        sender.sendMessage(Component.text("Enabled visual dialogue for Byrtrum!").color(NamedTextColor.GREEN))
                        true
                    }
                }
                VisualDialogueOption.COLOUR -> {
                    sender.sendMessage(failMessage)
                    sender.playSound(failSound)
                }
            }
        }
    }

    companion object {
        var visualDialogue = false
    }

    enum class VisualDialogueOption {
        TOGGLE,
        COLOUR
    }
}