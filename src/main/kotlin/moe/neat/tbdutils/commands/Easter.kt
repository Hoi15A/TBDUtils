package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import de.tr7zw.nbtapi.NBTItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.persistence.PersistentDataType

@Suppress("unused")
class Easter : BaseCommand {

    @CommandMethod("toggle-egg-place")
    @CommandPermission("tbdutils.command.easter.toggle-egg-placing")
    fun toggleEggPlacing(sender: Player) {
        var placeMode = sender.persistentDataContainer.getOrDefault(NamespacedKey.fromString("tbd:easter_toggle_egg_place")!!, PersistentDataType.INTEGER, 0)
        placeMode = if (placeMode == 0) 1 else 0

        sender.persistentDataContainer.set(NamespacedKey.fromString("tbd:easter_toggle_egg_place")!!, PersistentDataType.INTEGER, placeMode)

        sender.sendMessage("Egg placement ${if (placeMode == 0) "disabled" else "enabled"}!")
    }

    @CommandMethod("tag-egg")
    @CommandPermission("tbdutils.command.unobtanium")
    fun tagEgg(sender: Player) {
        if (sender.inventory.itemInMainHand.type == Material.PLAYER_HEAD) {
            val nItem = NBTItem(sender.inventory.itemInMainHand)
            nItem.setBoolean("isEasterEgg", true)
            val item = nItem.item
            item.lore(listOf(Component.text("Easter egg").color(NamedTextColor.AQUA)))
            sender.inventory.setItemInMainHand(item)
        }
    }
}
