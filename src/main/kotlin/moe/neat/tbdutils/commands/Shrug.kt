package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.Argument
import cloud.commandframework.annotations.CommandDescription
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission

import org.bukkit.entity.Player

@Suppress("unused")
class Shrug : BaseCommand {
    /**
     * Command to append shrug to a message.
     * As this uses [Player.chat] there are some odd quirks like `/shrug /kick` working. This is considered a "feature".
     *
     * @param sender Player sending the shrug
     * @param text Optional text to prepend to the shrug
     */
    @CommandMethod("shrug [text]")
    @CommandDescription(SHRUG)
    @CommandPermission("tbdutils.command.shrug")
    fun shrug(sender: Player, @Argument("text") text: Array<String>?) {
        if (text == null) {
            sender.chat(SHRUG)
        } else {
            sender.chat("${text.joinToString(" ")} $SHRUG")
        }

    }

    companion object {
        private const val SHRUG = "¯\\_(ツ)_/¯"
    }
}