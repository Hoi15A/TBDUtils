package computer.austins.tbdutils.util

import computer.austins.tbdutils.util.Formatting.allTags
import computer.austins.tbdutils.util.Formatting.restrictedTags

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer

import org.bukkit.Bukkit
import org.bukkit.entity.Player

object Chat {

    fun globalChat(player: Player, rawComponent: Component) {
        val message = PlainTextComponentSerializer.plainText().serialize(rawComponent)

        val noxAudience = Audience.audience(Bukkit.getOnlinePlayers().filter{ p : Player -> Noxesium.isNoxesiumUser(p) })
        val nonNoxAudience = Audience.audience(Bukkit.getOnlinePlayers().filter{ p : Player -> !Noxesium.isNoxesiumUser(p) })

        noxAudience.sendMessage(Noxesium.buildSkullComponent(player.uniqueId, false, 0, 0, 1.0f)
            .append(allTags.deserialize("<tbdcolour>${player.name}<reset>: ")
                .append(if(player.hasPermission("tbdutils.group.admin")) {
                        allTags.deserialize(message)
                    } else {
                        restrictedTags.deserialize(message)
                    }
                )
            )
        )
        nonNoxAudience.sendMessage(allTags.deserialize("<tbdcolour>${player.name}<reset>: ")
            .append(if(player.hasPermission("tbdutils.group.admin")) {
                    allTags.deserialize(message)
                } else {
                    restrictedTags.deserialize(message)
                }
            )
        )
    }

    fun broadcastAdmin(component: Component, isSilent: Boolean) {
        val admin = Audience.audience(Bukkit.getOnlinePlayers())
            .filterAudience { (it as Player).hasPermission("tbdutils.group.admin") }
        admin.sendMessage(
            allTags.deserialize("<prefix:admin>").append(component)
        )
        if (!isSilent) {
            admin.playSound(Sounds.Admin.ADMIN_MESSAGE)
        }
    }

    fun broadcastDev(component: Component, isSilent: Boolean) {
        val dev = Audience.audience(Bukkit.getOnlinePlayers())
            .filterAudience { (it as Player).hasPermission("tbdutils.group.dev") }
        dev.sendMessage(
            allTags.deserialize("<prefix:dev>").append(component)
        )
        if (!isSilent) {
            dev.playSound(Sounds.Admin.ADMIN_MESSAGE)
        }
    }

    fun messageAudience(recipient: Audience, message: String, restricted: Boolean, vararg placeholders: TagResolver) {
        val resolvers = mutableListOf<TagResolver>()
        for (p in placeholders) {
            resolvers.add(p)
        }

        recipient.sendMessage(formatMessage(message, restricted, TagResolver.resolver(resolvers)))
    }

    fun formatMessage(message: String, restricted: Boolean, vararg placeholders: TagResolver): Component {
        val resolvers = mutableListOf<TagResolver>()
        for(p in placeholders) {
            resolvers.add(p)
        }

        return if (restricted) {
            restrictedTags.deserialize(message, TagResolver.resolver(resolvers))
        } else {
            allTags.deserialize(message, TagResolver.resolver(resolvers))
        }
    }
}
