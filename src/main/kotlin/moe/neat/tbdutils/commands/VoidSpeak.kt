package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.Argument
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import github.scarsz.discordsrv.DiscordSRV
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player

class VoidSpeak : BaseCommand {
    private val mm = MiniMessage.miniMessage()

    @CommandMethod("voidsay [text]")
    @CommandPermission("tbdutils.command.voidsay.speak")
    fun voidSay(sender: Player, @Argument("text") text: Array<String>) {
        val msgText = mm.escapeTags(text.joinToString(" "))
        val message = mm.deserialize("<font:alt><gradient:#d10aaf:#8e0ad1>$msgText</gradient></font>")

        DiscordSRV.getPlugin().processChatMessage(sender, "somehow translate this to moon runes", DiscordSRV.getPlugin().mainChatChannel, false)
    }

}