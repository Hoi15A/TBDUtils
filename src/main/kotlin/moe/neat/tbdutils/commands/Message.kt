package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.Argument
import cloud.commandframework.annotations.CommandDescription
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

private data class LastMessager(val sender: UUID, val timeStamp: Long)

@Suppress("unused")
@CommandPermission("tbdutils.command.msg")
class Message : BaseCommand {
    private val lastConversationPartner = mutableMapOf<UUID, LastMessager>()
    private val mm = MiniMessage.miniMessage()

    @CommandMethod("msg|w|m|tell|explain|beg <player> <text>")
    @CommandDescription("Send somebody a private message")
    fun msg(sender: Player, @Argument("player") recipient: Player, @Argument("text") text: Array<String>) {
        if (sender == recipient) {
            System.currentTimeMillis() / 1000
            sender.sendMessage(
                mm.deserialize("<i>You -> <yellow>Yourself</yellow>: ${text.joinToString(" ")}</i>")
            )
        } else {
            sendMessage(sender, recipient, text)
        }
    }

    @CommandMethod("r|reply <text>")
    @CommandDescription("Reply to the last private message you received")
    fun reply(sender: Player, @Argument("text") text: Array<String>) {
        val lastSender = lastConversationPartner[sender.uniqueId]
        if (lastSender != null && System.currentTimeMillis() - lastSender.timeStamp <= REPLY_TIMEOUT_SECONDS * 1000) {

            val offlinePlayer = Bukkit.getOfflinePlayer(lastSender.sender)
            if (offlinePlayer.isOnline) {
                sendMessage(sender, offlinePlayer.player!!, text)
            } else {
                sender.sendMessage(
                    mm.deserialize("<i><gray>${offlinePlayer.name} is not online :pensive:</gray></i>")
                )
            }
        } else {
            sender.sendMessage(
                mm.deserialize("<i><gray>Nobody has sent you a message in a while, but don't worry I still love you <3 - Austin</gray></i>")
            )
        }
    }

    @CommandMethod("boop <player>")
    fun boop(sender: Player, @Argument("player") recipient: Player) {
        sender.sendMessage(
            mm.deserialize("<light_purple>To</light_purple> ${mm.serialize(recipient.displayName())}: <b><light_purple>Boop!</light_purple></b>")
        )

        recipient.sendMessage(
            mm.deserialize("<light_purple>From</light_purple> ${mm.serialize(sender.displayName())}: <b><light_purple>Boop!</light_purple></b>")
        )

        lastConversationPartner[recipient.uniqueId] = LastMessager(sender.uniqueId, System.currentTimeMillis())
        lastConversationPartner[sender.uniqueId] = LastMessager(recipient.uniqueId, System.currentTimeMillis())
    }

    private fun sendMessage(sender: Player, recipient: Player, text: Array<String>) {

        sender.sendMessage(
            mm.deserialize("<i><yellow>You</yellow> -> ${mm.serialize(recipient.displayName())}: ${text.joinToString(" ")}</i>")
        )

        recipient.sendMessage(
            mm.deserialize("<i>${mm.serialize(sender.displayName())} -> <yellow>You</yellow>: ${text.joinToString(" ")}</i>")
        )

        lastConversationPartner[recipient.uniqueId] = LastMessager(sender.uniqueId, System.currentTimeMillis())
        lastConversationPartner[sender.uniqueId] = LastMessager(recipient.uniqueId, System.currentTimeMillis())
    }

    companion object {
        private const val REPLY_TIMEOUT_SECONDS = 60 * 60 * 1 // 1 Hour
    }
}