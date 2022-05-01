package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.Argument
import cloud.commandframework.annotations.CommandDescription
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

@Suppress("unused")
class GiveCustomItem : BaseCommand {
    @CommandMethod("customitem <item>")
    @CommandDescription("Gives the executor the custom item specified")
    @CommandPermission("tbdutils.command.customitem")
    fun customItem(sender: Player, @Argument("item") item: CustomItems) {
        when(item) {
            CustomItems.ROCKET_LAUNCHER -> {
                createRocketLauncher(sender)
            }
            CustomItems.LIGHTNING_WAND -> {
                createLightningWand(sender)
            }
            CustomItems.TELEPORT_BOW -> {
                createTeleportBow(sender)
            }
            CustomItems.ASPECT_OF_THE_VOID -> {
                createTeleportSpoon(sender)
            }
            CustomItems.WITHERS_WRATH -> {
                createWithersWrath(sender)
            }
        }
    }

    private fun createRocketLauncher(player : Player) {
        val rocketLauncher = ItemStack(Material.GOLDEN_HORSE_ARMOR)
        val rocketLauncherMeta: ItemMeta = rocketLauncher.itemMeta
        rocketLauncherMeta.displayName(Component.text("Rocket Launcher").color(TextColor.fromHexString("#faac05")).decoration(TextDecoration.ITALIC, false))
        rocketLauncherMeta.isUnbreakable = true
        rocketLauncherMeta.addEnchant(Enchantment.MENDING, 1, false)
        rocketLauncherMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES)
        rocketLauncher.itemMeta = rocketLauncherMeta
        player.inventory.addItem(ItemStack(rocketLauncher))
        player.sendMessage(Component.text("Received ${CustomItems.ROCKET_LAUNCHER}").color(NamedTextColor.GREEN))
    }

    private fun createLightningWand(player : Player) {
        val lightningWand = ItemStack(Material.BLAZE_ROD)
        val lightningWandMeta: ItemMeta = lightningWand.itemMeta
        lightningWandMeta.displayName(Component.text("Lightning Wand").color(TextColor.fromHexString("#78acff")).decoration(TextDecoration.ITALIC, false))
        lightningWandMeta.addEnchant(Enchantment.MENDING, 1, false)
        lightningWandMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        lightningWand.itemMeta = lightningWandMeta
        player.inventory.addItem(ItemStack(lightningWand))
        player.sendMessage(Component.text("Received ${CustomItems.LIGHTNING_WAND}").color(NamedTextColor.GREEN))
    }

    private fun createTeleportBow(player : Player) {
        val teleportBow = ItemStack(Material.BOW)
        val teleportBowMeta: ItemMeta = teleportBow.itemMeta
        teleportBowMeta.displayName(Component.text("Teleport Bow").color(TextColor.fromHexString("#5524c7")).decoration(TextDecoration.ITALIC, false))
        teleportBowMeta.isUnbreakable = true
        teleportBowMeta.addEnchant(Enchantment.MENDING, 1, false)
        teleportBowMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE)
        teleportBow.itemMeta = teleportBowMeta
        player.inventory.addItem(ItemStack(teleportBow))
        player.sendMessage(Component.text("Received ${CustomItems.TELEPORT_BOW}").color(NamedTextColor.GREEN))
    }

    private fun createTeleportSpoon(player : Player) {
        val teleportSpoon = ItemStack(Material.DIAMOND_SHOVEL)
        val teleportSpoonMeta: ItemMeta = teleportSpoon.itemMeta
        teleportSpoonMeta.displayName(Component.text("Aspect of the Void").color(TextColor.fromHexString("#992af5")).decoration(TextDecoration.ITALIC, false))
        teleportSpoonMeta.isUnbreakable = true
        teleportSpoonMeta.addEnchant(Enchantment.MENDING, 1, false)
        teleportSpoonMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES)
        teleportSpoon.itemMeta = teleportSpoonMeta
        player.inventory.addItem(ItemStack(teleportSpoon))
        player.sendMessage(Component.text("Received ${CustomItems.ASPECT_OF_THE_VOID}").color(NamedTextColor.GREEN))
    }

    private fun createWithersWrath(player : Player) {
        val withersWrath = ItemStack(Material.WITHER_ROSE)
        val withersWrathMeta: ItemMeta = withersWrath.itemMeta
        withersWrathMeta.displayName(Component.text("Wither's Wrath").color(TextColor.fromHexString("#292929")).decoration(TextDecoration.ITALIC, false))
        withersWrathMeta.addEnchant(Enchantment.MENDING, 1, false)
        withersWrathMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS)
        withersWrath.itemMeta = withersWrathMeta
        player.inventory.addItem(ItemStack(withersWrath))
        player.sendMessage(Component.text("Received ${CustomItems.WITHERS_WRATH}").color(NamedTextColor.GREEN))
    }

    enum class CustomItems {
        ROCKET_LAUNCHER,
        LIGHTNING_WAND,
        TELEPORT_BOW,
        ASPECT_OF_THE_VOID,
        WITHERS_WRATH
    }
}