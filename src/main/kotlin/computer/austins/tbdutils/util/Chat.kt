package computer.austins.tbdutils.util

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.entity.Player

object Chat {
    fun broadcast(component: Component) {
        Bukkit.broadcast(Component.text("BROADCAST: ").append(component)) // TODO: formatting
    }

    fun adminBroadcast(component: Component) {
        Audience.audience(Bukkit.getOnlinePlayers()).filterAudience {
            (it as Player).hasPermission("tbdutils.admin")
        }.sendMessage(Component.text("ADMIN: ").append(component)) // TODO: formatting
    }

    // TODO: figure out how /msg /r and just regular chat should be done and make functions

    fun joinMessage(player: Player) {
        Bukkit.broadcast(Component.text("${player.name} joined with ${player.clientBrandName}").color(NamedTextColor.RED)) // TODO formatting
    }

    fun leaveMessage(player: Player) {
        // TODO: Everything
    }

}