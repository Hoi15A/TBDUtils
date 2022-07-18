package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import moe.neat.tbdutils.particles.Blossom
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player

@Suppress("unused")
class BlossomCommand : BaseCommand {
    private val mm = MiniMessage.miniMessage()

    @CommandMethod("blossom")
    @CommandPermission("tbdutils.command.blossom")
    fun toggleBlossom(sender: Player) {
        if (Blossom.toggleActive(sender)) {
            sender.sendMessage(mm.deserialize("<color:#ff52c8>❀ Enabled cherry blossom particles ❀</color>"))
        } else {
            sender.sendMessage(mm.deserialize("<color:#ff52c8>❀ Disabled cherry blossom particles ❀</color>"))
        }
    }

}
