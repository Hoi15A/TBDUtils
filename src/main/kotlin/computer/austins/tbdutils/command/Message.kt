package computer.austins.tbdutils.command

import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.bukkit.parsers.PlayerArgument
import cloud.commandframework.kotlin.extension.argumentDescription
import cloud.commandframework.kotlin.extension.buildAndRegister
import cloud.commandframework.paper.PaperCommandManager
import computer.austins.tbdutils.util.Chat
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

@Suppress("unused")
class Message : BaseCommand() {
    override fun register(commandManager: PaperCommandManager<CommandSender>) {
        commandManager.buildAndRegister(
            "msg",
            argumentDescription("Sends a private message to the specified player"),
            arrayOf("w", "m", "tell", "explain", "beg")
        ) {
            permission = "tbdutils.command.message"

            argument(argumentDescription("Player")) {
                PlayerArgument.of("recipient")
            }
            argument(argumentDescription("Message")) {
                StringArgument.greedy("message")
            }

            handler {
                val message = it.get<String>("message")
                val recipient = it.get<Player>("recipient")
                val sender = it.sender as Player
                privateMessage(sender, recipient, message)
            }
        }
    }
}

@Suppress("unused")
class Reply : BaseCommand() {
    override fun register(commandManager: PaperCommandManager<CommandSender>) {
        commandManager.buildAndRegister(
            "reply",
            argumentDescription("Reply to the latest private message you received."),
            arrayOf("r")
        ) {
            permission = "tbdutils.command.reply"

            argument(argumentDescription("Message")) {
                StringArgument.greedy("message")
            }

            handler {
                val message = it.get<String>("message")
                val sender = it.sender as Player
                val lastSender = lastConversationPartner[sender.uniqueId]
                if (lastSender != null && System.currentTimeMillis() - lastSender.timeStamp <= REPLY_TIMEOUT_SECONDS * 1000) {

                    val offlinePlayer = Bukkit.getOfflinePlayer(lastSender.sender)
                    if (offlinePlayer.isOnline) {
                        privateMessage(sender, offlinePlayer.player!!, message)
                    } else {
                        replyReceiverOffline(sender, offlinePlayer)
                    }
                } else {
                    replyLonely(sender)
                }
            }
        }
    }
}

private fun privateMessage(sender: Player, recipient: Player, message: String) {
    Chat.messageAudience(
        sender,
        "<i><tbdcolour>You<white> -> <name>: $message</i>",
        true,
        Placeholder.component("name", recipient.displayName())
    )

    Chat.messageAudience(
        recipient,
        "<i><name> -> <tbdcolour>You<white>: $message</i>",
        true,
        Placeholder.component("name", sender.displayName())
    )

    lastConversationPartner[recipient.uniqueId] = LastMessager(sender.uniqueId, System.currentTimeMillis())
    lastConversationPartner[sender.uniqueId] = LastMessager(recipient.uniqueId, System.currentTimeMillis())
}

private fun replyReceiverOffline(sender: Player, offlinePlayer: OfflinePlayer) {
    Chat.messageAudience(sender, "<i><tbdcolour>${offlinePlayer.name}<gray> is not online :pensive:</gray></i>", false)
}

private fun replyLonely(sender: Player) {
    Chat.messageAudience(
        sender,
        "<i><gray>Nobody has sent you a message in a while, but don't worry we still love you <3 -</gray> <tbdcolour>TBDUtils</i>",
        false
    )
}

val lastConversationPartner = mutableMapOf<UUID, LastMessager>()
private const val REPLY_TIMEOUT_SECONDS = 60 * 60 * 1

data class LastMessager(val sender: UUID, val timeStamp: Long)