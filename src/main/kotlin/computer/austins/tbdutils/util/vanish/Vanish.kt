package computer.austins.tbdutils.util.vanish

import computer.austins.tbdutils.plugin
import computer.austins.tbdutils.util.Chat
import computer.austins.tbdutils.util.pathfinding.Pathfinding
import net.kyori.adventure.audience.Audience

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.Bukkit
import org.bukkit.GameMode

import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector

import java.util.*

class Vanish(player : Player) {
    private val vanishPlayer : Player
    private val vanishUUID : UUID

    init {
        vanishPlayer = player
        vanishUUID = vanishPlayer.uniqueId

        VanishManager.vanish(vanishPlayer, vanishUUID)
        for(onlinePlayer in Bukkit.getOnlinePlayers()) {
            onlinePlayer.hidePlayer(plugin, vanishPlayer)
        }
        Chat.broadcastAdmin("<notifcolour>${vanishPlayer.name} <reset>has vanished.", true)
        Chat.messageAudience(
            Audience.audience(Bukkit.getOnlinePlayers()),
            "<tbdcolour>[<red>-<tbdcolour>] <notifcolour><name><reset> left the game.",
            false,
            Placeholder.component("name", vanishPlayer.name())
        )
        vanishPlayer.allowFlight = true
        vanishPlayer.velocity = Vector(0.0, 1.25, 0.0)
        vanishPlayer.isFlying = true

        Pathfinding.clearNearbyTargets(player)

        object : BukkitRunnable() {
            override fun run() {
                if(VanishManager.isVanished(vanishUUID)) {
                    vanishPlayer.sendActionBar(
                        Component.text("You are currently ", NamedTextColor.WHITE)
                            .append(Component.text("VANISHED", TextColor.fromHexString("#a43beb"))
                                .append(Component.text(".", NamedTextColor.WHITE))
                            )
                        )
                } else {
                    vanishPlayer.sendActionBar(Component.text(""))
                    for(onlinePlayer in Bukkit.getOnlinePlayers()) {
                        onlinePlayer.showPlayer(plugin, vanishPlayer)
                    }
                    Chat.broadcastAdmin("<notifcolour>${player.name} <reset>has un-vanished.", true)
                    Chat.messageAudience(
                        Audience.audience(Bukkit.getOnlinePlayers()),
                        "<tbdcolour>[<green>+<tbdcolour>] <notifcolour><name><reset> joined the game.",
                        false,
                        Placeholder.component("name", vanishPlayer.name())
                    )
                    if(vanishPlayer.gameMode == GameMode.SURVIVAL || vanishPlayer.gameMode == GameMode.ADVENTURE) {
                        vanishPlayer.allowFlight = false
                        vanishPlayer.isFlying = false
                    }
                    this.cancel()
                }
            }
        }.runTaskTimer(plugin, 0L, 5L)
    }
}