package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.CommandDescription
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

@Suppress("unused")
class PlayTime : BaseCommand {
    private val mm = MiniMessage.miniMessage()

    private val objectiveName = "hc_playTimeShow"
    private val manager = Bukkit.getScoreboardManager()
    private val scoreboard = manager.mainScoreboard
    private var objective = scoreboard.getObjective(objectiveName)

    @CommandMethod("getplaytime")
    @CommandDescription("Gets all entries of play-time of all players.")
    @CommandPermission("tbdutils.command.getplaytime")
    fun getAllPLayTime(sender: CommandSender) {
        sender.sendMessage(Component.text("Attempting to get everyone's play-time...").color(NamedTextColor.GRAY))
        try{
            sender.sendMessage(mm.deserialize("<click:copy_to_clipboard:'${getAllScores()}'><color:#fefefe><u>Click here to copy all play-times to your clipboard!<u></color></click>"))
        } catch(e : Exception) {
            sender.sendMessage(Component.text("An error occurred when trying to get everyone's play-time.").color(NamedTextColor.RED))
        }
    }

    private fun getAllScores() : Map<String, Int> {
        val entries = objective?.scoreboard?.entries
        var loopCounter = 0
        val map = mutableMapOf<String, Int>()
        Bukkit.getOfflinePlayers().forEach {
            if (objective?.getScore(entries?.toTypedArray()?.get(loopCounter).toString())?.isScoreSet == true) {
                map[it.name!!] = objective!!.getScore(entries?.toTypedArray()?.get(loopCounter).toString()).score
                loopCounter++
            }
        }

        return map.toList().sortedBy { (_, value) -> value }.reversed().toMap()
    }
}