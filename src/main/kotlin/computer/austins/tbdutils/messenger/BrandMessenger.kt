package computer.austins.tbdutils.messenger

import computer.austins.tbdutils.util.Chat

import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener

import java.io.UnsupportedEncodingException

import java.nio.charset.Charset
import java.util.*

@Suppress("unused")
class BrandMessenger : PluginMessageListener {
    override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray) {
        try {
            val brand = String(message, Charset.defaultCharset()).substring(1)
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            val rawString = "<#db0060>${player.name}<white> joined using <#db0060>$brand<white>."
            if(brand.lowercase() == "vanilla") {
                rawString.plus(" <dark_gray><i>(Could be OptiFine)")
            }
            Chat.broadcastDev(rawString, true)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
    }
}