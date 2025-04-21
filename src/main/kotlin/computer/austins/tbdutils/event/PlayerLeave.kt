package computer.austins.tbdutils.event

import computer.austins.tbdutils.util.Chat
import computer.austins.tbdutils.util.Noxesium
import computer.austins.tbdutils.util.disguise.DisguiseManager
import computer.austins.tbdutils.util.vanish.VanishManager

import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

@Suppress("unused")
class PlayerLeave : Listener {
    @EventHandler
    private fun onPlayerLeave(e: PlayerQuitEvent) {
        if(DisguiseManager.isDisguised(e.player.uniqueId)) {
            DisguiseManager.undisguise(e.player.uniqueId)
        }
        if(VanishManager.isVanished(e.player.uniqueId)) {
            VanishManager.unvanish(e.player.uniqueId)
        } else {
            e.quitMessage(
                Chat.formatMessage(
                    "<tbdcolour>[<red>-<tbdcolour>] <notifcolour><name><reset> left the game.",
                    false,
                    Placeholder.component("name", e.player.name())
                )
            )
        }
        Noxesium.removeNoxesiumUser(e.player)
    }
}