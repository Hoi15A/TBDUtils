package computer.austins.tbdutils.util.batboat

import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

/** A manager singleton to manage the running instances of a [BatBoat]. */
object BatBoatManager {
    /** Maps a player's UUID to their [BatBoat] instance. */
    private val instances = ConcurrentHashMap<UUID, BatBoat>()

    /** Registers an [instance], assigning it to a [uuid]. */
    fun registerInstance(uuid: UUID, instance: BatBoat) =
        instances.set(uuid, instance)

    /** Unregisters the [BatBoat] instance associated with the given [uuid], if it exists. */
    fun unregisterInstance(uuid: UUID) = instances.remove(uuid)

    /** Determines whether a [BatBoat] instance exists for the given [uuid]. */
    fun contains(uuid: UUID): Boolean =
        instances.containsKey(uuid)

    /** Removes the [BatBoat] instance of given [uuid] if it exists. */
    fun remove(uuid: UUID) {
        instances[uuid]?.remove()
        instances.remove(uuid)
    }

    /** Removes all [BatBoat] instances. */
    fun removeAll() {
        instances.values.forEach { it.remove() }
        instances.clear()
    }
}