package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.Argument
import cloud.commandframework.annotations.CommandDescription
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import cloud.commandframework.annotations.Regex
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.Instrument
import org.bukkit.Note
import java.time.Duration

/**
 * Defines all command methods related to announcements.
 */
@Suppress("unused")
class Announce : BaseCommand {
    private val mm = MiniMessage.miniMessage()

    @CommandMethod("announce <text>")
    @CommandDescription("Displays title text to all players online")
    @CommandPermission("tbdutils.command.announce")
    fun announce(@Argument("text") text: Array<String>) {
        for (player in Bukkit.getServer().onlinePlayers) {
            player.playNote(player.location, Instrument.PLING, Note.sharp(1, Note.Tone.F))
            player.sendMessage(
                mm.deserialize("<b><yellow>Announcement:</yellow></b> ${mm.stripTags(text.joinToString(" "))}")
            )

            player.showTitle(
                Title.title(
                    Component.text("Announcement").color(NamedTextColor.YELLOW),
                    Component.text(text.joinToString(" ")),
                    Title.Times.times(
                        Duration.ofSeconds(1.toLong()),
                        Duration.ofSeconds(6.toLong()),
                        Duration.ofSeconds(1.toLong())
                    )
                )
            )
        }
    }

    @CommandMethod("announcerestart <time>")
    @CommandDescription("Displays restart title text to all players online")
    @CommandPermission("tbdutils.command.announcerestart")
    fun announceRestart(@Argument("time") @Regex("\\d+") time: UInt) {
        for (player in Bukkit.getServer().onlinePlayers) {
            player.playNote(player.location, Instrument.PLING, Note.sharp(1, Note.Tone.F))

            player.sendMessage(
                mm.deserialize("<b><red>Server restarting:</red></b> In $time minute${if (time > 1u) "s" else ""}.")
            )

            player.showTitle(
                Title.title(
                    Component.text("Server restarting").color(NamedTextColor.RED),
                    Component.text("In $time minute(s)"),
                    Title.Times.times(
                        Duration.ofSeconds(1.toLong()),
                        Duration.ofSeconds(3.toLong()),
                        Duration.ofSeconds(1.toLong())
                    )
                )
            )
        }
    }
}
