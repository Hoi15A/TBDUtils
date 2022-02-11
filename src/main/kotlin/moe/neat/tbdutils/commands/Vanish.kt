package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.CommandDescription
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import moe.neat.tbdutils.Plugin

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

import java.util.*

@Suppress("unused")
class Vanish : BaseCommand {
    @CommandMethod("vanish")
    @CommandDescription("Allows admins to vanish (does not hide from server list, discord or command completions)")
    @CommandPermission("tbdutils.command.vanish")
    fun vanish(sender: Player) {
        if (!vanishedPlayers.contains(sender.uniqueId)) {
            for (players in Bukkit.getServer().onlinePlayers) {
                players.hidePlayer(Plugin.plugin, sender)

                players.sendMessage(
                    Component.text("${sender.name} left the game")
                        .color(NamedTextColor.YELLOW)
                )

                if (players.hasPermission("tbdutils.command.vanish")) {
                    players.sendMessage(
                        sender.displayName()
                            .append(Component.text(" has just vanished!"))
                            .color(NamedTextColor.GREEN))
                }
            }

            vanishedPlayers.add(sender.uniqueId)
            vanishedActionBarText(sender)
            sender.sendMessage(Component.text("You are now vanished!").color(NamedTextColor.GREEN))

        } else {
            for (players in Bukkit.getServer().onlinePlayers) {
                players.showPlayer(Plugin.plugin, sender)

                players.sendMessage(
                    Component.text("${sender.name} joined the game")
                        .color(NamedTextColor.YELLOW)
                )

                if (players.hasPermission("tbdutils.command.vanish")) {
                    players.sendMessage(
                        sender.displayName()
                            .append(Component.text(" has just un-vanished!"))
                            .color(NamedTextColor.GREEN)
                    )
                }
            }

            vanishedPlayers.remove(sender.uniqueId)
            vanishedActionBarText(sender)
            sender.sendMessage(Component.text("You are no longer vanished!").color(NamedTextColor.GREEN))
        }
    }

    private fun vanishedActionBarText(player: Player) {
        Plugin.plugin.let {
            object : BukkitRunnable() {
                override fun run() {
                    if (vanishedPlayers.contains(player.uniqueId)) {
                        player.sendActionBar(
                            Component.text("You are currently ")
                                .append(Component.text("VANISHED").color(NamedTextColor.RED))
                        )
                    } else {
                        cancel()
                    }
                }
            }.runTaskTimer(it, 20, 20)
        }
    }

    companion object {
        val vanishedPlayers = mutableListOf<UUID>()
    }
}