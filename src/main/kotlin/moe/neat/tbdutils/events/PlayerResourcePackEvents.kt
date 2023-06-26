package moe.neat.tbdutils.events

import moe.neat.tbdutils.Plugin
import moe.neat.tbdutils.commands.Finale
import moe.neat.tbdutils.util.DowntimeMusicLoop

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerResourcePackStatusEvent

@Suppress("unused")
class PlayerResourcePackEvents : Listener {
    @EventHandler
    private fun onLoadPack(e : PlayerResourcePackStatusEvent) {
        if(e.status == PlayerResourcePackStatusEvent.Status.SUCCESSFULLY_LOADED) {
            if(Finale.isDowntimeActive) {
                DowntimeMusicLoop().startMusicLoop(e.player, Plugin.plugin)
            }
        }
    }
}