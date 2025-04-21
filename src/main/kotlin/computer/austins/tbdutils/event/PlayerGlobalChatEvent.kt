package computer.austins.tbdutils.event

import computer.austins.tbdutils.util.Chat
import computer.austins.tbdutils.util.Sounds
import computer.austins.tbdutils.util.vanish.VanishManager

import io.papermc.paper.event.player.AsyncChatEvent

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

@Suppress("unused")
class PlayerGlobalChatEvent : Listener {
    @EventHandler
    private fun onChat(e: AsyncChatEvent) {
        if(VanishManager.isVanished(e.player.uniqueId)) {
            Chat.messageAudience(e.player, "<red><prefix:warning>You cannot chat while <#a43beb>Vanished<red>.", false)
            e.player.playSound(Sounds.ACTION_FAIL)
        } else {
            Chat.globalChat(e.player, e.message())
        }
        e.isCancelled = true
    }
}
