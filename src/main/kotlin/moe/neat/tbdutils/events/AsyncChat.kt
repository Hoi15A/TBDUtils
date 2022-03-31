package moe.neat.tbdutils.events

import io.papermc.paper.event.player.AsyncChatEvent
import moe.neat.tbdutils.util.AprilFools
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

@Suppress("unused")
class AsyncChat : Listener {
    @EventHandler
    private fun onAsyncChat(e: AsyncChatEvent) {
        AprilFools.asyncChatEvent(e)
    }
}
