package computer.austins.tbdutils.command

import computer.austins.tbdutils.util.Chat
import computer.austins.tbdutils.util.LastMessager

import cloud.commandframework.arguments.standard.StringArgument
import cloud.commandframework.bukkit.parsers.PlayerArgument
import cloud.commandframework.kotlin.extension.argumentDescription
import cloud.commandframework.kotlin.extension.buildAndRegister
import cloud.commandframework.paper.PaperCommandManager

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

import java.util.*

@Suppress("unused")
class Message : BaseCommand() {
    override fun register(commandManager: PaperCommandManager<CommandSender>) {
        commandManager.buildAndRegister("msg",
            argumentDescription("Sends a private message to the specified player"),
            arrayOf("w","m","tell","explain","beg")
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
                Chat.privateMessage(sender, recipient, message)
            }
        }
    }
}

@Suppress("unused")
class Reply : BaseCommand() {
    override fun register(commandManager: PaperCommandManager<CommandSender>) {
        commandManager.buildAndRegister("reply",
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
                    if(offlinePlayer.isOnline) {
                        Chat.privateMessage(sender, offlinePlayer.player!!, message)
                    } else {
                        Chat.replyReceiverOffline(sender, offlinePlayer)
                    }
                } else {
                    Chat.replyLonely(sender)
                }
            }
        }
    }
}

val lastConversationPartner = mutableMapOf<UUID, LastMessager>()
private const val REPLY_TIMEOUT_SECONDS = 60 * 60 * 1