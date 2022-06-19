package moe.neat.tbdutils.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player
import java.util.*
import net.hypixel.api.HypixelAPI
import net.hypixel.api.apache.ApacheHttpClient
import net.hypixel.api.reply.PlayerReply

/**
 * Utilities relating to Hypixel using the [HypixelAPI]
 *
 * @param apiKey Apikey obtained with /api on Hypixel
 */
class Hypixel(apiKey: String) {
    private val api = HypixelAPI(ApacheHttpClient(UUID.fromString(apiKey)))
    private val playerCache = mutableMapOf<UUID, PlayerReply.Player>()
    private val mm = MiniMessage.miniMessage()

    private fun getPlayer(player: UUID): PlayerReply.Player {
        if (playerCache[player] == null) {
            playerCache[player] = api.getPlayerByUuid(player).get().player
        }
        return playerCache[player]!!
    }

    /**
     * Get the formatted display name of a player on Hypixel.
     *
     * @param player The player for which to fetch the display name
     * @return Hypixel styled component
     *
     * @see <a href="https://github.com/HypixelDev/PublicAPI/wiki/Common-Questions#how-do-i-get-a-players-rank-prefix">Available Ranks on Hypixel</a>
     */
    fun getHypixelDisplayName(player: Player): Component {
        val defaultComponent = Component.text(player.name).color(NamedTextColor.GRAY)
        val apiPlayer = getPlayer(player.uniqueId)

        return when (apiPlayer.highestRank) {
            "VIP" -> Component.text("[VIP] ${player.name}").color(NamedTextColor.GREEN)
            "VIP_PLUS" -> mm.deserialize("<green>[VIP<gold>+</gold>] ${player.name}</green>")
            "MVP" -> Component.text("[MVP] ${player.name}").color(NamedTextColor.AQUA)
            "MVP_PLUS" -> {
                val plusColor = apiPlayer.selectedPlusColor
                mm.deserialize("<aqua>[MVP<${plusColor}>+</${plusColor}>] ${player.name}</aqua>")
            }
            "SUPERSTAR" -> {
                val tagColor = apiPlayer.superstarTagColor
                val plusColor = apiPlayer.selectedPlusColor
                mm.deserialize("<${tagColor}>[MVP<${plusColor}>++</${plusColor}>] ${player.name}</${tagColor}>")
            }
            "YOUTUBE" -> mm.deserialize("<red>[</red><white>YOUTUBE</white><red>] ${player.name}</red>")
            "GAME_MASTER" -> Component.text("[GM] ${player.name}").color(NamedTextColor.DARK_GREEN)
            "ADMIN" -> Component.text("[ADMIN] ${player.name}").color(NamedTextColor.RED)
            else -> defaultComponent
        }
    }
}