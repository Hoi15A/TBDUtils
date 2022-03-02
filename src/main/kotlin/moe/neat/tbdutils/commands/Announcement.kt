package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.Argument
import cloud.commandframework.annotations.CommandDescription
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
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
    fun announcement(@Argument("text") text: Array<String>) {
        for(players in Bukkit.getServer().onlinePlayers) {
            players.playNote(players.location, Instrument.PLING, Note.sharp(0, Note.Tone.F))

            players.sendMessage(Component.text("Announcement: ")
                .color(NamedTextColor.YELLOW)
                .append(Component.text(text.joinToString(" "))
                    .color(NamedTextColor.WHITE))
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
