package moe.neat.tbdutils.util

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object EasterScoreboard {
    private val mm = MiniMessage.miniMessage()

    private const val objectiveName = "tbdutils_eggs_found"
    private val manager = Bukkit.getScoreboardManager()
    private val scoreboard = manager.mainScoreboard
    private val objective = scoreboard.getObjective(objectiveName) ?: scoreboard.registerNewObjective(objectiveName, "dummy",
        mm.deserialize("<gradient:dark_green:yellow>Easter Eggs Found</gradient>"))

    fun setPlayerEggs(player: Player, count: Int) {
        objective.getScore(player).score = count
    }
}
