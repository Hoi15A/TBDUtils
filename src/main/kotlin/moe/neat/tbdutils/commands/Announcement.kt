package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.*

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.title.Title

import org.bukkit.Bukkit
import org.bukkit.Instrument
import org.bukkit.Note

import java.time.Duration

@Suppress("unused")
class Announcement : BaseCommand {
    @CommandMethod("announcement <text>")
    @CommandDescription("Displays title text to all players online")
    @CommandPermission("tbdutils.command.announcement")
    fun announcement(@Argument("text") text: Array<String>, @Flag("restart") isRestart: Boolean) {
        for(players in Bukkit.getServer().onlinePlayers) {
            players.playNote(players.location, Instrument.PLING, Note.sharp(1, Note.Tone.F))

            if(isRestart) {
                players.sendMessage(Component.text("Server Restarting: ")
                    .color(NamedTextColor.RED)
                    .decoration(TextDecoration.BOLD, true)
                    .append(Component.text(text.joinToString(" "))
                        .color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, false)
                    )
                )

                players.showTitle(Title.title(
                    Component.text("Server Restarting").color(NamedTextColor.RED),
                    Component.text(text.joinToString(" ")),
                    Title.Times.of(Duration.ofSeconds(1.toLong()),
                        Duration.ofSeconds(6.toLong()),
                        Duration.ofSeconds(1.toLong()))
                    )
                )
            }
            else {
                players.sendMessage(Component.text("Announcement: ")
                    .color(NamedTextColor.YELLOW)
                    .decoration(TextDecoration.BOLD, true)
                    .append(Component.text(text.joinToString(" "))
                        .color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, false)
                    )
                )

                players.showTitle(Title.title(
                    Component.text("Announcement").color(NamedTextColor.YELLOW),
                    Component.text(text.joinToString(" ")),
                    Title.Times.of(Duration.ofSeconds(1.toLong()),
                        Duration.ofSeconds(6.toLong()),
                        Duration.ofSeconds(1.toLong()))
                    )
                )
            }
        }
    }
}
