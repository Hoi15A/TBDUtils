package computer.austins.tbdutils.event

import computer.austins.tbdutils.task.IntroductionTask
import computer.austins.tbdutils.util.Chat
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

@Suppress("unused")
class PlayerLeave : Listener {
    @EventHandler
    private fun onPlayerLeave(e: PlayerQuitEvent) {
        e.quitMessage(Chat.quitMessage(e.player))
        IntroductionTask().stopIntroLoop(e.player)
    }
}