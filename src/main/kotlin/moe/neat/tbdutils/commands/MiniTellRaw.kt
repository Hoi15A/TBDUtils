package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.Argument
import cloud.commandframework.annotations.CommandDescription
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import cloud.commandframework.bukkit.arguments.selector.MultiplePlayerSelector
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.command.CommandSender

@Suppress("unused")
class MiniTellRaw : BaseCommand {
    private val mm = MiniMessage.miniMessage()

    @CommandMethod("mm <players> <message>")
    @CommandDescription("Send a minimessage formatted message to people")
    @CommandPermission("tbdutils.command.mm")
    fun miniTellRaw(@Argument("players") players: MultiplePlayerSelector, @Argument("message") text: Array<String>) {
        val audience = players.players.stream().collect(Audience.toAudience())
        audience.sendMessage(mm.deserialize(text.joinToString(" ")))
    }
}