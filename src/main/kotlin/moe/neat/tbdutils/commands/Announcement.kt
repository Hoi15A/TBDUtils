package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.Argument
import cloud.commandframework.annotations.CommandDescription
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title

import org.bukkit.Bukkit

import org.bukkit.command.CommandSender
import java.time.Duration

@Suppress("unused")
class Announcement : BaseCommand {
    @CommandMethod("announcement <text>")
    @CommandDescription("Displays title text to all players online")
    @CommandPermission("tbdutils.command.announcement")
    fun announcement(sender: CommandSender, @Argument("text") text: Array<String>) {
        for(players in Bukkit.getServer().onlinePlayers) {
            players.showTitle(Title.title(
                Component.text("Announcement").color(NamedTextColor.YELLOW),
                Component.text(text.joinToString(" ")),
                Title.Times.of(Duration.ofSeconds(0.5.toLong()),
                    Duration.ofSeconds(4.toLong()),
                    Duration.ofSeconds(0.5.toLong()))
                )
            )
        }
    }
}
