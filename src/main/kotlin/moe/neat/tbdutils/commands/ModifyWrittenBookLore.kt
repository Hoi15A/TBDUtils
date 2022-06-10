package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.Argument
import cloud.commandframework.annotations.CommandDescription
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import de.tr7zw.nbtapi.NBTItem
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

@Suppress("unused")
class ModifyWrittenBookLore : BaseCommand {
    private val failSound: Sound = Sound.sound(Key.key("entity.enderman.teleport"), Sound.Source.MASTER, 1f, 0f)
    private val failMessage: Component = Component.text("You cannot use this command on that item!").color(NamedTextColor.RED)

    @CommandMethod("modifyauthor <text>")
    @CommandDescription("Modifies the Author of the written book currently held by the user.")
    @CommandPermission("tbdutils.command.modifyauthor")
    fun modifyAuthor(sender: Player, @Argument("text") text: Array<String>) {
        if (sender.inventory.itemInMainHand.type == Material.WRITTEN_BOOK) {
            val bookNbt = NBTItem(sender.inventory.itemInMainHand)
            bookNbt.setString("originalAuthor", sender.name)
            bookNbt.setString("author", text.joinToString(" "))
            val newBook: ItemStack = bookNbt.item
            newBook.lore(
                listOf(
                    Component.text("Author modified").color(NamedTextColor.DARK_GRAY)
                        .decoration(TextDecoration.ITALIC, false)
                )
            )
            sender.inventory.setItemInMainHand(newBook)

        } else {
            sender.sendMessage(failMessage)
            sender.playSound(failSound)
        }
    }
}