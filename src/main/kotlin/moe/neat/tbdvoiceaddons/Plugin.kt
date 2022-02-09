package moe.neat.tbdvoiceaddons

import de.maxhenkel.voicechat.api.BukkitVoicechatService
import moe.neat.tbdvoiceaddons.voice.TBDVoiceChatPlugin
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

@Suppress("UNUSED")
class Plugin : JavaPlugin() {
    override fun onEnable() {
        Bukkit.getLogger().info("Initialising")
        saveDefaultConfig()
        server.servicesManager.load(BukkitVoicechatService::class.java)?.registerPlugin(TBDVoiceChatPlugin())
    }
}