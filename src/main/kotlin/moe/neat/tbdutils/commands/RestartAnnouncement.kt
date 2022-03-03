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
class RestartAnnouncement : BaseCommand {
    @CommandMethod("announcerestart <time>")
    @CommandDescription("Displays restart title text to all players online")
    @CommandPermission("tbdutils.command.announcerestart")
    fun announcement(@Argument("time") time: UInt) {
        for(players in Bukkit.getServer().onlinePlayers) {
            players.playNote(players.location, Instrument.PLING, Note.sharp(1, Note.Tone.F))

            players.sendMessage(Component.text("Server restarting: ")
                .color(NamedTextColor.RED)
                .decoration(TextDecoration.BOLD, true)
                .append(Component.text("In $time minute(s).")
                    .color(NamedTextColor.WHITE).decoration(TextDecoration.BOLD, false)
                )
            )

            players.showTitle(Title.title(
                Component.text("Server restarting").color(NamedTextColor.RED),
                Component.text("In $time minute(s)"),
                Title.Times.of(Duration.ofSeconds(1.toLong()),
                    Duration.ofSeconds(3.toLong()),
                    Duration.ofSeconds(1.toLong()))
                )
            )
        }
    }
}
