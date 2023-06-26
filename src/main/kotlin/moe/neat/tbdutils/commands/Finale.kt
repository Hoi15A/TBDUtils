package moe.neat.tbdutils.commands

import moe.neat.tbdutils.Plugin
import moe.neat.tbdutils.util.DowntimeMusicLoop

import cloud.commandframework.annotations.CommandDescription
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import cloud.commandframework.annotations.Confirmation

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.MiniMessage

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

import java.util.*

@Suppress("unused")
class Finale : BaseCommand {
    private val mm = MiniMessage.miniMessage()
    @CommandMethod("finale get_items")
    @CommandDescription("Grants the user Keith's item set.")
    @CommandPermission("tbdutils.command.finale")
    fun finaleItems(sender : Player) {
        giveKeithItems(sender)
        sender.sendMessage(mm.deserialize("<gold>You received a set of Keith's items, time for <red>carnage<gold>."))
    }

    @CommandMethod("finale toggle visual_dialogue")
    @CommandDescription("Toggles visual dialogue.")
    @CommandPermission("tbdutils.command.finale")
    fun finaleToggleDialogue(sender : Player) {
        toggleDialogue(sender)
    }

    @CommandMethod("finale remove_effects")
    @CommandDescription("Removes all potion effects for the executor.")
    @CommandPermission("tbdutils.command.finale")
    fun finaleRemoveEffects(sender : Player) {
        for(activeEffects in sender.activePotionEffects) {
            sender.removePotionEffect(activeEffects.type)
        }
        sender.sendMessage(mm.deserialize("<red>You removed all your active potion effects."))
    }

    @CommandMethod("finale toggle downtime")
    @CommandDescription("Toggles server downtime.")
    @CommandPermission("tbdutils.command.finale")
    @Confirmation
    fun finaleToggleDowntime(sender : Player) {
        toggleDowntime(sender)
    }

    private fun toggleDowntime(player : Player) {
        if(isDowntimeActive) {
            isDowntimeActive = false
            player.sendMessage(mm.deserialize("<red>You disabled the downtime phase."))
        } else {
            isDowntimeActive = true
            player.sendMessage(mm.deserialize("<green>You enabled the downtime phase."))
            visualDialogue = false
            for(onlinePlayers in Bukkit.getOnlinePlayers()) {
                DowntimeMusicLoop().startMusicLoop(onlinePlayers, Plugin.plugin)
            }
        }
    }

    private fun toggleDialogue(player : Player) {
        if(!player.isOp) {
            player.sendMessage(Component.text("You cannot execute this command.").color(NamedTextColor.RED))
        } else {
            visualDialogue = if(visualDialogue) {
                player.sendMessage(mm.deserialize("<gold>You toggled visual dialogue <red>off<gold>."))
                false
            } else {
                player.sendMessage(mm.deserialize("<gold>You toggled visual dialogue <green>on<gold>."))
                true
            }
        }
    }

    private fun giveKeithItems(player : Player) {
        val godWeapon = ItemStack(Material.BLAZE_ROD, 1)
        val godWeaponMeta: ItemMeta = godWeapon.itemMeta
        godWeaponMeta.displayName(Component.text("Keith's Will").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false))
        godWeaponMeta.isUnbreakable = true
        godWeaponMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES)
        godWeaponMeta.addEnchant(Enchantment.DAMAGE_ALL, 100, true)
        godWeapon.itemMeta = godWeaponMeta
        player.inventory.addItem(godWeapon)

        val mainWeapon = ItemStack(Material.NETHERITE_SWORD, 1)
        val mainWeaponMeta: ItemMeta = mainWeapon.itemMeta
        mainWeaponMeta.displayName(Component.text("Blade of Magma").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false))
        mainWeaponMeta.isUnbreakable = true
        mainWeaponMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES)
        mainWeaponMeta.addEnchant(Enchantment.DAMAGE_ALL, 10, true)
        mainWeapon.itemMeta = mainWeaponMeta
        player.inventory.addItem(mainWeapon)

        val subWeapon = ItemStack(Material.BOW, 1)
        val subWeaponMeta: ItemMeta = subWeapon.itemMeta
        subWeaponMeta.displayName(Component.text("Molten Mortar").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false))
        subWeaponMeta.isUnbreakable = true
        subWeaponMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES)
        subWeaponMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true)
        subWeaponMeta.addEnchant(Enchantment.ARROW_DAMAGE, 8, true)
        subWeaponMeta.addEnchant(Enchantment.ARROW_FIRE, 1, true)
        subWeapon.itemMeta = subWeaponMeta
        player.inventory.addItem(subWeapon)

        val magmaWeapon = ItemStack(Material.MAGMA_CREAM, 1)
        val magmaWeaponMeta: ItemMeta = magmaWeapon.itemMeta
        magmaWeaponMeta.displayName(Component.text("Magma Melter").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false))
        magmaWeaponMeta.isUnbreakable = true
        magmaWeaponMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES)
        magmaWeaponMeta.addEnchant(Enchantment.DAMAGE_ALL, 10, true)
        magmaWeaponMeta.addEnchant(Enchantment.FIRE_ASPECT, 5, true)
        magmaWeapon.itemMeta = magmaWeaponMeta
        player.inventory.addItem(magmaWeapon)

        val godPowerCharm = ItemStack(Material.RED_DYE, 1)
        val godPowerCharmMeta: ItemMeta = godPowerCharm.itemMeta
        godPowerCharmMeta.displayName(Component.text("Molten Secret Stone").color(NamedTextColor.DARK_RED).decoration(TextDecoration.ITALIC, false))
        godPowerCharmMeta.isUnbreakable = true
        godPowerCharmMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_DESTROYS)
        godPowerCharm.itemMeta = godPowerCharmMeta
        player.inventory.addItem(godPowerCharm)

        player.inventory.addItem(ItemStack(Material.ARROW, 64))
    }

    companion object {
        var visualDialogue = false
        var isDowntimeActive = false
    }
}