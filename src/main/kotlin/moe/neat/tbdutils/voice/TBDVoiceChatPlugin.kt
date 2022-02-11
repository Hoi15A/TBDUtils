package moe.neat.tbdutils.voice

import de.maxhenkel.voicechat.api.VoicechatApi
import de.maxhenkel.voicechat.api.VoicechatPlugin
import de.maxhenkel.voicechat.api.events.EventRegistration
import de.maxhenkel.voicechat.api.events.PlayerConnectedEvent
import de.maxhenkel.voicechat.api.events.PlayerDisconnectedEvent
import org.bukkit.Bukkit

class TBDVoiceChatPlugin : VoicechatPlugin {
    private val tabList = TabList()

    override fun getPluginId(): String {
        return "TBDVoiceChatPlugin"
    }

    override fun initialize(api: VoicechatApi?) {
        Bukkit.getLogger().info("Initialising Voicechat Plugin")
    }

    override fun registerEvents(registration: EventRegistration?) {
        registration?.registerEvent(PlayerConnectedEvent::class.java, this::playerConnectedEvent)
        registration?.registerEvent(PlayerDisconnectedEvent::class.java, this::playerDisconnectedEvent)
    }

    private fun playerConnectedEvent(event: PlayerConnectedEvent) {
        tabList.updatePlayer(event.connection.player.uuid, true)
    }

    private fun playerDisconnectedEvent(event: PlayerDisconnectedEvent) {
        tabList.updatePlayer(event.playerUuid, false)
    }
}
