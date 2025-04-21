package computer.austins.tbdutils.util.disguise

import org.bukkit.Bukkit
import org.bukkit.entity.Player

import java.util.*

object DisguiseManager {
    private val disguiseInstances = mutableMapOf<Pair<UUID, Player>, Pair<String, String>>()

    fun disguise(player: Player, uuid: UUID, disguiseName: String, disguiseSkinName: String) = disguiseInstances.set(Pair(uuid, player), Pair(disguiseName, disguiseSkinName))

    fun undisguise(uuid: UUID) = disguiseInstances.remove(Pair(uuid, Bukkit.getPlayer(uuid)))

    fun isDisguised(uuid: UUID) : Boolean = disguiseInstances.contains(Pair(uuid, Bukkit.getPlayer(uuid)))

    fun Player.isDisguised(): Boolean = disguiseInstances.contains(Pair(this.uniqueId, this))

    fun Player.getDisguise(): Pair<String, String> = disguiseInstances.getValue(Pair(this.uniqueId, this))

    fun removeAll() {
        disguiseInstances.clear()
    }

    fun getInstances() : Map<Pair<UUID, Player>, Pair<String, String>> {
        return disguiseInstances
    }
}