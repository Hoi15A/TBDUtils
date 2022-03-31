package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.Argument
import cloud.commandframework.annotations.CommandDescription
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

@Suppress("unused")
class GiveCustomItem : BaseCommand {
    @CommandMethod("customitem <item>")
    @CommandDescription("Gives the executor the custom item specified")
    @CommandPermission("tbdutils.command.customitem")
    fun echo(sender: CommandSender, @Argument("item") item: CustomItems) {
        val player: Player = sender as Player
        if(item == CustomItems.ROCKET_LAUNCHER) {
            val rocketLauncher = ItemStack(Material.GOLDEN_SHOVEL)
            rocketLauncher.itemMeta.displayName(Component.text("Rocket Launcher").color(NamedTextColor.GOLD))
            rocketLauncher.itemMeta.isUnbreakable = true
            rocketLauncher.itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE)
            player.inventory.addItem(rocketLauncher)
        } else if(item == CustomItems.LIGHTNING_WAND) {
            sender.sendMessage(Component.text("This item is not implemented yet!").color(NamedTextColor.RED))
        } else if(item == CustomItems.TELEPORT_BOW) {
            sender.sendMessage(Component.text("This item is not implemented yet!").color(NamedTextColor.RED))
        }
    }

    enum class CustomItems {
        ROCKET_LAUNCHER,
        LIGHTNING_WAND,
        TELEPORT_BOW
    }
}
