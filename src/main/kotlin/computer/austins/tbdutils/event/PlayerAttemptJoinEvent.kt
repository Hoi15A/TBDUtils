package computer.austins.tbdutils.event

import computer.austins.tbdutils.logger
import computer.austins.tbdutils.util.Chat

import net.kyori.adventure.text.Component

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent

@Suppress("unused")
class PlayerAttemptJoinEvent : Listener {
    @EventHandler
    private fun onAttemptJoin(e : AsyncPlayerPreLoginEvent) {
        if(!Bukkit.getWhitelistedPlayers().contains(Bukkit.getOfflinePlayer(e.uniqueId))) {
            Chat.broadcastAdmin(Component.text("<red>Player '<yellow>${e.name}<red>' <dark_gray><i>(${e.uniqueId})<reset> <red>attempted to join but is not whitelisted."), true)
            logger.info("Player '${e.name}' (${e.uniqueId}) attempted to join but is not whitelisted.")
        }
    }
}