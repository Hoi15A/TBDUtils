package moe.neat.tbdvoiceaddons

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Bukkit
import java.util.*

private val CONFIG = Bukkit.getPluginManager().getPlugin("TBDVoiceAddons")?.config
private val ICON = Component.text(CONFIG?.getString("voiceIcon")!! + " ")
                            .color(TextColor.color(CONFIG.getInt("voiceIconColour")))

class TabList {
    fun updatePlayer(uuid: UUID, connected: Boolean) {
        val player = Bukkit.getPlayer(uuid)
        if (connected) {
            player?.playerListName(
                Component.text()
                    .append(ICON)
                    .append(player.teamDisplayName())
                    .build()
            )
        } else {
            player?.playerListName(player.teamDisplayName())
        }
    }
}
