package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.*
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.entity.Player

@Suppress("unused")
class InvSee : BaseCommand {
    @CommandMethod("invsee <player>")
    @CommandDescription("Allows the executing player to view the specified player's inventory or ender chest contents.")
    @CommandPermission("tbdutils.command.invsee")
    fun invSee(sender: Player, @Argument("player") toInvSee: Player, @Flag("echest") isEchest: Boolean) {
        val invText = if (isEchest) "ender chest" else "inventory"

        if(toInvSee == sender) {
            sender.sendMessage(Component.text("You can already see your $invText, nice try though.").color(NamedTextColor.RED))
        } else {

            Bukkit.getOnlinePlayers()
                .filter { player -> player.hasPermission("tbdutils.command.invsee") && !player.equals(sender) }
                .forEach { player ->
                    player.sendMessage(
                        sender.displayName()
                            .append(Component.text(" is viewing "))
                            .append(toInvSee.displayName())
                            .append(Component.text("'s $invText"))
                            .color(NamedTextColor.BLUE)
                    )
                }

            sender.sendMessage(
                Component.text("You have opened ")
                    .append(toInvSee.displayName())
                    .append(Component.text("'s $invText"))
                    .color(NamedTextColor.GREEN)
            )

            if (isEchest) {
                sender.openInventory(toInvSee.enderChest)
            } else {
                sender.openInventory(toInvSee.inventory)
            }
        }
    }
}