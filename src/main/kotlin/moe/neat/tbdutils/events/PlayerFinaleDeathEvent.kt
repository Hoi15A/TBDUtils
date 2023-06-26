package moe.neat.tbdutils.events

import net.kyori.adventure.text.Component
import net.kyori.adventure.title.Title

import org.bukkit.GameMode
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

import java.time.Duration

@Suppress("unused")
class PlayerFinaleDeathEvent : Listener {
    @EventHandler
    private fun onPostRespawn(e : PlayerDeathEvent) {
        e.player.showTitle(Title.title(Component.text("\uD000"), Component.text(""), Title.Times.times(Duration.ofSeconds(0), Duration.ofSeconds(1), Duration.ofSeconds(1))))
        e.player.gameMode = GameMode.SPECTATOR
        e.drops.clear()
        e.newExp = 0
        e.newLevel = 0
        e.player.inventory.clear()
        e.isCancelled = true
    }
}