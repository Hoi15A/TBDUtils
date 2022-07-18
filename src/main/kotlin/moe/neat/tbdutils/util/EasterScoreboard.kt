package moe.neat.tbdutils.util

import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object EasterScoreboard {
    private val mm = MiniMessage.miniMessage()

    private const val objectiveName = "tbdutils_eggs_found"
    private val manager = Bukkit.getScoreboardManager()
    private val scoreboard = manager.mainScoreboard
    private var objective = scoreboard.getObjective(objectiveName)

    init {
        if (objective == null) {
            objective = scoreboard.registerNewObjective(
                objectiveName, "dummy",
                mm.deserialize("<gradient:dark_green:yellow>Easter Eggs Found</gradient>")
            )
        }
    }

    fun setPlayerEggs(player: Player, count: Int) {
        objective?.getScore(player)!!.score = count
    }

    fun getAllScores() : Map<String, Int> {
        val map = mutableMapOf<String, Int>()
        Bukkit.getOfflinePlayers().forEach {
            if (objective?.getScore(it)?.isScoreSet == true) {
                map[it.name!!] = objective?.getScore(it)!!.score
            }
        }

        return map.toList().sortedBy { (_, value) -> value }.reversed().toMap()
    }
}
