package moe.neat.tbdutils.util

import io.github.reflxction.hypixelapi.HypixelAPI
import io.github.reflxction.hypixelapi.player.HypixelPlayer
import io.github.reflxction.hypixelapi.player.PlayerRank
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player
import java.util.UUID

/**
 * Utilities relating to Hypixel using the [HypixelAPI]
 *
 * @param apiKey Apikey obtained with /api on Hypixel
 */
class Hypixel(apiKey: String) {
    private val api = HypixelAPI.create(apiKey)
    private val playerCache = mutableMapOf<UUID, HypixelPlayer>()
    private val mm = MiniMessage.miniMessage()

    private fun getPlayer(player: UUID): HypixelPlayer {
        if (playerCache[player] == null) {
            playerCache[player] = api.getPlayer(player)
        }
        return playerCache[player]!!
    }

    /**
     * Get the formatted display name of a player on Hypixel.
     *
     * @param player The player for which to fetch the display name
     * @return Hypixel styled component
     */
    fun getHypixelDisplayName(player: Player): Component {
        return when (getPlayer(player.uniqueId).newPackageRank!!) {
            PlayerRank.DEFAULT -> Component.text(player.name).color(NamedTextColor.GRAY)
            PlayerRank.VIP -> Component.text("[VIP] ${player.name}").color(NamedTextColor.GREEN)
            PlayerRank.VIP_PLUS -> mm.deserialize("<green>[VIP<gold>+</gold>] ${player.name}</green>")
            PlayerRank.MVP -> Component.text("[MVP] ${player.name}").color(NamedTextColor.AQUA)
            PlayerRank.MVP_PLUS -> mm.deserialize("<aqua>[MVP<red>+</red>] ${player.name}</aqua>")
        }
    }
}