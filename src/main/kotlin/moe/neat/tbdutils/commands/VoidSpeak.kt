package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.Argument
import cloud.commandframework.annotations.CommandDescription
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import github.scarsz.discordsrv.DiscordSRV
import moe.neat.tbdutils.util.GalacticAlphabet
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Bukkit
import org.bukkit.entity.Player

@Suppress("unused")
class VoidSpeak : BaseCommand {
    private val mm = MiniMessage.miniMessage()

    @CommandMethod("voidsay|v <text>")
    @CommandDescription("Say a message in \"voidspeak\" aka the galactic alphabet")
    @CommandPermission("tbdutils.command.voidsay")
    fun voidSay(sender: Player, @Argument("text") text: Array<String>) {
        var msgText = mm.stripTags(text.joinToString(" "))
        val messageReadable = mm.deserialize("\\<${sender.name}> <hover:show_text:'${msgText.replace("'","\\'")}'><font:alt><gradient:#d10aaf:#8e0ad1>$msgText</gradient></font></hover>")
        val messageNonReadable = mm.deserialize("\\<${sender.name}> <font:alt><gradient:#d10aaf:#8e0ad1>$msgText</gradient></font>")

        Bukkit.getServer().onlinePlayers.forEach { player ->
            if (player.hasPermission("tbdutils.command.voidsay")) {
                player.sendMessage(messageReadable)
            } else {
                player.sendMessage(messageNonReadable)
            }
        }

        msgText = msgText.replace("||", "|\u200B|")
        DiscordSRV.getPlugin().processChatMessage(sender, "<a:enchantment_tbd:957610364340686888>: ${GalacticAlphabet.translateToGalactic(msgText)}\n<:translate_tbd:957610363518586895>: ||${msgText}||", DiscordSRV.getPlugin().mainChatChannel, false)
    }

}