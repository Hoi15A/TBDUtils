package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.Argument
import cloud.commandframework.annotations.CommandDescription
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import moe.neat.tbdutils.Plugin
import moe.neat.tbdutils.util.Hypixel
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

/**
 * @property sender Player that represents some other players last conversation partner
 * @property timeStamp Time of when the last message occurred as a timestamp in seconds.
 */
private data class LastMessager(val sender: UUID, val timeStamp: Long)

private val HYPIXEL = Hypixel(Plugin.plugin.config.getString("hypixelApiKey")!!)

/**
 * Defines all command methods related to Messages in the plugin
 *
 * @property lastConversationPartner Stores a [LastMessager] for a given UUID representing a player
 */
@Suppress("unused")
@CommandPermission("tbdutils.command.msg")
class Message : BaseCommand {
    private val lastConversationPartner = mutableMapOf<UUID, LastMessager>()
    private val mm = MiniMessage.miniMessage()

    /**
     * Command that allows players to send a direct message to another player.
     * Replacement for the vanilla /msg command in order to allow for replies to function.
     *
     * @param sender Player running the command
     * @param recipient Player that should receive the message
     * @param text The message that should be sent
     */
    @CommandMethod("msg|w|m|tell|explain|beg <player> <text>")
    @CommandDescription("Send somebody a private message")
    fun msg(sender: Player, @Argument("player") recipient: Player, @Argument("text") text: Array<String>) {
        if (sender == recipient) {
            System.currentTimeMillis() / 1000
            sender.sendMessage(
                mm.deserialize("<i>You -> <yellow>Yourself</yellow>: ${mm.stripTags(text.joinToString(" "))}</i>")
            )
        } else {
            sendMessage(sender, recipient, text)
        }
    }

    /**
     * Command that allows the player to respond to previous messages, including their own.
     * Timestamps of the last player talked to are stored in [lastConversationPartner]
     *
     * @param sender Player running the command
     * @param text Message to reply with
     */
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

    /**
     * Custom implementation of the Hypixel boop command.
     * Is functionally the same as [msg] just with different formatting.
     *
     * @param sender Player running the command
     * @param recipient Player that should receive a boop
     */
    @CommandMethod("boop <player>")
    @CommandDescription("Boop a player")
    fun boop(sender: Player, @Argument("player") recipient: Player) {
        val sendRank = HYPIXEL.getHypixelDisplayName(sender)
        val recRank = HYPIXEL.getHypixelDisplayName(recipient)

        recipient.playSound(Sound.sound(Key.key("minecraft:entity.axolotl.idle_air"), Sound.Source.MASTER, 1f, 1f))

        sender.sendMessage(
            mm.deserialize("<light_purple>To</light_purple> ${mm.serialize(recRank)}<gray>:</gray> <b><light_purple>Boop!</light_purple></b>")
        )

        recipient.sendMessage(
            mm.deserialize("<light_purple>From</light_purple> ${mm.serialize(sendRank)}<gray>:</gray> <b><light_purple>Boop!</light_purple></b>")
        )

        lastConversationPartner[recipient.uniqueId] = LastMessager(sender.uniqueId, System.currentTimeMillis())
        lastConversationPartner[sender.uniqueId] = LastMessager(recipient.uniqueId, System.currentTimeMillis())
    }

    /**
     * Send a direct message from one player to another. Then also set [lastConversationPartner] for both players
     * in order for [reply] to function.
     *
     * @param sender Player sending the message
     * @param recipient Player receiving the message
     * @param text
     */
    private fun sendMessage(sender: Player, recipient: Player, text: Array<String>) {
        val message = mm.stripTags(text.joinToString(" "))

        sender.sendMessage(
            mm.deserialize("<i><yellow>You</yellow> -> ${mm.serialize(recipient.displayName())}: $message</i>")
        )

        recipient.sendMessage(
            mm.deserialize("<i>${mm.serialize(sender.displayName())} -> <yellow>You</yellow>: $message</i>")
        )

        lastConversationPartner[recipient.uniqueId] = LastMessager(sender.uniqueId, System.currentTimeMillis())
        lastConversationPartner[sender.uniqueId] = LastMessager(recipient.uniqueId, System.currentTimeMillis())
    }

    companion object {
        private const val REPLY_TIMEOUT_SECONDS = 60 * 60 * 1 // 1 Hour
    }
}