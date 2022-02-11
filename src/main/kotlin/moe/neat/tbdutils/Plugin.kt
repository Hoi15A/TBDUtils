package moe.neat.tbdutils

import de.maxhenkel.voicechat.api.BukkitVoicechatService
import moe.neat.tbdutils.voice.TBDVoiceChatPlugin
import org.bukkit.plugin.java.JavaPlugin

@Suppress("UNUSED")
class Plugin : JavaPlugin() {
    override fun onEnable() {
        saveDefaultConfig()
        server.servicesManager.load(BukkitVoicechatService::class.java)?.registerPlugin(TBDVoiceChatPlugin())
    }
}