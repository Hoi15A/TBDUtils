package computer.austins.tbdutils.event

import computer.austins.tbdutils.util.Chat

import io.papermc.paper.event.player.AsyncChatEvent

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

@Suppress("unused")
class PlayerGlobalChatEvent : Listener {
    @EventHandler
    private fun onChat(e : AsyncChatEvent) {
        Chat.globalChat(e.player, e.message())
        e.isCancelled = true
    }
}