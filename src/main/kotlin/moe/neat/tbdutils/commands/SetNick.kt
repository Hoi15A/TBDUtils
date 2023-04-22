package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.*
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.entity.Player

class SetNick : BaseCommand {

    @CommandMethod("setnick <player> <nick>")
    @CommandDescription("Allows nick modification.")
    @CommandPermission("tbdutils.command.setnick")
    fun nick(sender : Player, @Argument("player") player : Player, @Argument("nick") nick : String) {
        try {
            if (nick.length > 16) {
                sender.sendMessage(Component.text("Oh nonononono... bad! That name is too long.", NamedTextColor.RED))
                return
            }

            val previousName = player.name
            sender.sendMessage(Component.text("Attempting to change nick from $previousName to ${nick}...").color(NamedTextColor.GRAY))
            setNick(player, nick)
            sender.sendMessage(Component.text("Successfully changed nick from $previousName to ${nick}.").color(NamedTextColor.GREEN))
        } catch(e : Exception) {
            sender.sendMessage(Component.text("An error occurred when attempting to change a player's nick.", NamedTextColor.RED))
            e.printStackTrace()
        }
    }

    private fun setNick(player : Player, nick : String) {
        player.playerProfile = Bukkit.createProfileExact(player.uniqueId, nick)
        player.playerListName(Component.text(nick))
    }
}