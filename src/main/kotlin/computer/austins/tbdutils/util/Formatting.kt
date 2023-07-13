package computer.austins.tbdutils.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags

object Formatting {
    /** Prefix enum for allowing MiniMessage usage of the <prefix:NAME> tag in messages. **/
    private enum class Prefix(val prefixName: String, val value: String) {
        DEV_PREFIX("dev", "퀂 "),
        ADMIN_PREFIX("admin", "퀃 "),
        NO_PREFIX("", ""),
        WARNING_PREFIX("warning", "⚠ ");

        companion object {
            fun ofName(str: String): Prefix {
                for(p in Prefix.values()) {
                    if (p.prefixName == str) return p
                }
                return NO_PREFIX
            }
        }
    }

    private val TBD_COLOUR = TagResolver.resolver("tbdcolour", Tag.styling(TextColor.color(255, 156, 237)))
    private val NOTIFICATION_COLOUR = TagResolver.resolver("notifcolour", Tag.styling(TextColor.color(219, 0, 96)))

    val allTags = MiniMessage.builder()
        .tags(
            TagResolver.builder()
                .resolver(StandardTags.defaults())
                .resolver(Noxesium.skullResolver())
                .resolver(TBD_COLOUR)
                .resolver(NOTIFICATION_COLOUR)
                .resolver(prefix())
                .build()
        )
        .build()

    val restrictedTags = MiniMessage.builder()
        .tags(
            TagResolver.builder()
                .resolver(StandardTags.color())
                .resolver(StandardTags.decorations())
                .resolver(StandardTags.rainbow())
                .resolver(StandardTags.reset())
                .resolver(Noxesium.skullResolver())
                .resolver(TBD_COLOUR)
                .resolver(NOTIFICATION_COLOUR)
                .build()
        )
        .build()

    /** Builds a prefix tag. **/
    private fun prefix() : TagResolver {
        return TagResolver.resolver("prefix") { args, _ ->
            val prefixName = args.popOr("Name not supplied.")
            Tag.inserting(
                Component.text(Prefix.ofName(prefixName.toString()).value)
            )
        }
    }
}

