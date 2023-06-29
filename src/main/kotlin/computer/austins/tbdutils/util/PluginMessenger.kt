package computer.austins.tbdutils.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration

import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener

import java.io.UnsupportedEncodingException

import java.nio.charset.Charset
import java.util.*

@Suppress("unused")
class PluginMessenger : PluginMessageListener {
    override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray) {
        try {
            val brand = String(message, Charset.defaultCharset()).substring(1)
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            val component = Component.text(player.name, TextColor.fromHexString("#d4008a"))
                                .append(Component.text(" joined using ", NamedTextColor.WHITE))
                                    .append(Component.text(brand, TextColor.fromHexString("#d4008a")))
                                        .append(Component.text(".", NamedTextColor.WHITE))
            if(brand.lowercase() == "vanilla") {
                component.append(Component.text(" (Could be OptiFine)", NamedTextColor.DARK_GRAY, TextDecoration.ITALIC)
                    .append(Component.text(".", NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false)))
            }
            Chat.broadcastDev(component, true)
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
    }
}