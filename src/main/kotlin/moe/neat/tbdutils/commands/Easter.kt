package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.Argument
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import de.tr7zw.nbtapi.NBTItem
import moe.neat.tbdutils.util.EasterScoreboard
import moe.neat.tbdutils.util.LocationArrayDataType
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player

@Suppress("unused")
class Easter : BaseCommand {
    val mm = MiniMessage.miniMessage()

    @CommandMethod("tag-egg")
    @CommandPermission("tbdutils.command.easter.tagegg")
    fun tagEgg(sender: Player) {
        if (sender.inventory.itemInMainHand.type == Material.PLAYER_HEAD) {
            val nItem = NBTItem(sender.inventory.itemInMainHand)
            nItem.setBoolean("isEasterEgg", true)
            val item = nItem.item
            item.lore(listOf(Component.text("Easter egg").color(NamedTextColor.AQUA)))
            sender.inventory.setItemInMainHand(item)
        }
    }

    @CommandMethod("clear-egg-data <player>")
    @CommandPermission("tbdutils.command.easter.cleareggdata")
    fun clearEggData(sender: Player, @Argument("player") player: Player) {
        sender.sendMessage(Component.text("Cleared ${player.name}'s egg data").color(NamedTextColor.BLUE))
        player.persistentDataContainer.set(
            NamespacedKey.fromString("tbd:easter_eggs_collected")!!,
            LocationArrayDataType(),
            arrayOf()
        )
        EasterScoreboard.setPlayerEggs(player, 0)
    }

    @CommandMethod("egg-scoreboard")
    @CommandPermission("tbdutils.command.easter.getscoreboard")
    fun getEggScoreboard(sender: Player) {
        val scores = EasterScoreboard.getAllScores()
        var scoreboardText = "TBD EASTER EGG SCOREBOARD\n\nName: Count\n\n"

        scores.forEach { (name, score) ->
            scoreboardText += "$name: $score\n"
        }

        sender.sendMessage(mm.deserialize("<click:copy_to_clipboard:'$scoreboardText'><color:#4c76f5><u>Click here to copy the scoreboard to your clipboard!<u></color></click>"))
    }
}
