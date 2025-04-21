package computer.austins.tbdutils.util.vanish

import org.bukkit.entity.Player

import java.util.UUID

object VanishManager {
    private val vanishInstances = mutableMapOf<UUID, Player>()

    fun vanish(player : Player, uuid : UUID) = vanishInstances.set(uuid, player)

    fun unvanish(uuid : UUID) = vanishInstances.remove(uuid)

    fun isVanished(uuid : UUID) : Boolean = vanishInstances.contains(uuid)

    fun removeAll() {
        vanishInstances.values.forEach { it.remove() }
        vanishInstances.clear()
    }

    fun getInstances() : Map<UUID, Player> {
        return vanishInstances
    }
}