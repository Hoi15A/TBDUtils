package computer.austins.tbdutils.util

import computer.austins.tbdutils.command.lastConversationPartner
import computer.austins.tbdutils.logger

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import net.kyori.adventure.title.Title

import org.bukkit.Bukkit
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player

import java.time.Duration
import java.util.*

@Suppress("unused")
object Chat {
    private val globalMiniMessage = MiniMessage.builder()
        .tags(Noxesium.skullResolver())
        .tags(TagResolver.builder()
            .resolver(StandardTags.color())
            .resolver(StandardTags.decorations())
            .build()
        ).build()
    private val adminMiniMessage = MiniMessage.builder()
        .tags(Noxesium.skullResolver())
        .tags(TagResolver.builder()
            .resolver(StandardTags.defaults())
            .build()
        ).build()

    private const val DEV_PREFIX = "\uD002"
    private const val ADMIN_PREFIX = "\uD003"

    fun globalChat(player : Player, rawComponent : Component) {
        val message = PlainTextComponentSerializer.plainText().serialize(rawComponent)
        val noxAudience = Audience.audience(Bukkit.getOnlinePlayers().filter{ p : Player -> Noxesium.isNoxesiumUser(p) })
        val nonNoxAudience = Audience.audience(Bukkit.getOnlinePlayers().filter{ p : Player -> !Noxesium.isNoxesiumUser(p) })

        noxAudience.sendMessage(Noxesium.buildSkullComponent(player.uniqueId, false, 0, 0, 1.0f)
            .append(adminMiniMessage.deserialize("${tbdColour()}${player.name}<reset>: ")
                .append(if(player.hasPermission("tbdutils.admin")) {
                        adminMiniMessage.deserialize(message)
                    } else {
                        globalMiniMessage.deserialize(message)
                    }
                )
            )
        )
        nonNoxAudience.sendMessage(adminMiniMessage.deserialize("${tbdColour()}${player.name}<reset>: ")
            .append(if(player.hasPermission("tbdutils.admin")) {
                    adminMiniMessage.deserialize(message)
                } else {
                    globalMiniMessage.deserialize(message)
                }
            )
        )
    }

    fun broadcast(rawString : String) {
        logger.info("Announcement: $rawString")
        Bukkit.broadcast(
            adminMiniMessage.deserialize("<yellow><b>Announcement:<reset> ")
                .append(adminMiniMessage.deserialize(rawString))
        )
        val online = Audience.audience(Bukkit.getOnlinePlayers())
        online.playSound(Sounds.Admin.SERVER_ANNOUNCEMENT)
        online.showTitle(Title.title(
            adminMiniMessage.deserialize("<yellow><b>Announcement"),
            adminMiniMessage.deserialize(rawString),
                Title.Times.times(
                    Duration.ofSeconds(1.toLong()),
                    Duration.ofSeconds(6.toLong()),
                    Duration.ofSeconds(1.toLong())
                )
            )
        )
    }

    fun broadcastRestart(time : Int) {
        logger.info("Server Restarting: In $time minute${if (time > 1) "s" else ""}.")
        Bukkit.broadcast(adminMiniMessage.deserialize("<red><b>Server Restarting:<reset> In $time minute${if (time > 1) "s" else ""}."))
        val online = Audience.audience(Bukkit.getOnlinePlayers())
        online.playSound(Sounds.Admin.RESTART_ANNOUNCEMENT)
        online.showTitle(Title.title(
            adminMiniMessage.deserialize("<red><b>Server Restarting"),
            Component.text("In $time minute${if (time > 1) "s" else ""}."),
                Title.Times.times(
                    Duration.ofSeconds(1.toLong()),
                    Duration.ofSeconds(3.toLong()),
                    Duration.ofSeconds(1.toLong())
                )
            )
        )
    }

    fun broadcastAdmin(component : Component, isSilent : Boolean) {
        val admin = Audience.audience(Bukkit.getOnlinePlayers()).filterAudience { (it as Player).hasPermission("tbdutils.admin") }
        admin.sendMessage(
            Component.text("$ADMIN_PREFIX ")
                .append(component)
        )
        if(!isSilent) { admin.playSound(Sounds.Admin.ADMIN_MESSAGE) }
    }

    fun broadcastDev(component : Component, isSilent : Boolean) {
        val dev = Audience.audience(Bukkit.getOnlinePlayers()).filterAudience { (it as Player).hasPermission("tbdutils.dev") }
        dev.sendMessage(
            Component.text("$DEV_PREFIX ")
                .append(component)
        )
        if(!isSilent) { dev.playSound(Sounds.Admin.ADMIN_MESSAGE) }
    }

    fun privateMessage(sender : Player, recipient: Player, message : String) {
        sender.sendMessage(
            globalMiniMessage.deserialize("<i>${tbdColour()}You<white> -> <name>: $message</i>", Placeholder.component("name", recipient.displayName()))
        )

        recipient.sendMessage(
            globalMiniMessage.deserialize("<i><name> -> ${tbdColour()}You<white>: $message</i>", Placeholder.component("name", sender.displayName()))
        )

        lastConversationPartner[recipient.uniqueId] = LastMessager(sender.uniqueId, System.currentTimeMillis())
        lastConversationPartner[sender.uniqueId] = LastMessager(recipient.uniqueId, System.currentTimeMillis())
    }

    fun replyReceiverOffline(sender : Player, offlinePlayer : OfflinePlayer) {
        sender.sendMessage(globalMiniMessage.deserialize("<i>${tbdColour()}${offlinePlayer.name}<gray> is not online :pensive:</gray></i>"))
    }

    fun replyLonely(sender : Player) {
        sender.sendMessage(globalMiniMessage.deserialize("<i><gray>Nobody has sent you a message in a while, but don't worry we still love you <3 -</gray> ${tbdColour()}TBDUtils</i>"))
    }

    fun joinMessage(player : Player) : Component {
        return adminMiniMessage.deserialize("${tbdColour()}${player.name}<reset> joined the game.")
    }

    fun quitMessage(player : Player) : Component {
        return adminMiniMessage.deserialize("${tbdColour()}${player.name}<reset> left the game.")
    }

    private fun tbdColour() : String {
        return "<#ff9ced>"
    }

    private fun getRankColour(player : Player) : String {
        return if(player.hasPermission("tbdutils.admin")) { "<dark_red>" } else { "<#ff9ced>" }
    }
}

data class LastMessager(val sender: UUID, val timeStamp: Long)