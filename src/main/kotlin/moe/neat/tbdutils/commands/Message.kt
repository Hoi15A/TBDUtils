package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.Argument
import cloud.commandframework.annotations.CommandDescription
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

private data class LastSender(val sender: UUID, val timeStamp: Long)

@Suppress("unused")
@CommandPermission("tbdutils.command.msg")
class Message : BaseCommand {
    private val lastMessageReceivedFrom = mutableMapOf<UUID, LastSender>()

    @CommandMethod("msg|w|tell|explain|beg <player> <text>")
    @CommandDescription("Send somebody a private message")
    fun msg(sender: Player, @Argument("player") recipient: Player, @Argument("text") text: Array<String>) {
        if (sender == recipient) {
            System.currentTimeMillis() / 1000
            sender.sendMessage(
                Component.text().decorate(TextDecoration.ITALIC)
                    .append(Component.text("You"))
                    .append(ARROW)
                    .append(Component.text("Yourself").color(NamedTextColor.YELLOW))
                    .append(COLON)
                    .append(Component.text(text.joinToString(" ")))
            )
        } else {
            sendMessage(sender, recipient, text)
        }
    }

    @CommandMethod("r|reply <text>")
    @CommandDescription("Reply to the last private message you received")
    fun reply(sender: Player, @Argument("text") text: Array<String>) {
        val lastSender = lastMessageReceivedFrom[sender.uniqueId]
        if (lastSender != null && System.currentTimeMillis() - lastSender.timeStamp <= REPLY_TIMEOUT_SECONDS * 1000) {

            val offlinePlayer = Bukkit.getOfflinePlayer(lastSender.sender)
            if (offlinePlayer.isOnline) {
                sendMessage(sender, offlinePlayer.player!!, text)
            } else {
                sender.sendMessage(
                    Component.text("${offlinePlayer.name} is not online :pensive:")
                        .decorate(TextDecoration.ITALIC)
                        .color(NamedTextColor.GRAY)
                )
            }
        } else {
            sender.sendMessage(
                Component.text("Nobody has sent you a message in a while, but don't worry I still love you <3 - Austin")
                .decorate(TextDecoration.ITALIC)
                .color(NamedTextColor.GRAY)
            )
        }
    }

    private fun sendMessage(sender: Player, recipient: Player, text: Array<String>) {
        sender.sendMessage(
            Component.text().decorate(TextDecoration.ITALIC)
                .append(Component.text("You").color(NamedTextColor.YELLOW))
                .append(ARROW)
                .append(recipient.displayName())
                .append(COLON)
                .append(Component.text(text.joinToString(" ")))
        )

        recipient.sendMessage(
            sender.displayName().decorate(TextDecoration.ITALIC)
                .append(ARROW)
                .append(Component.text("You").color(NamedTextColor.YELLOW))
                .append(COLON)
                .append(Component.text(text.joinToString(" ")))
        )

        lastMessageReceivedFrom[recipient.uniqueId] = LastSender(sender.uniqueId, System.currentTimeMillis())
    }

    companion object {
        private val ARROW = Component.text(" -> ")
        private val COLON = Component.text(": ")
        private const val REPLY_TIMEOUT_SECONDS = 60 * 60 * 1 // 1 Hour
    }
}