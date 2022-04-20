package moe.neat.tbdutils.util

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType

class LocationDataType : PersistentDataType<PersistentDataContainer, Location> {
    private val keyWorld = NamespacedKey.fromString("tbd:location_world")!!
    private val keyX = NamespacedKey.fromString("tbd:location_x")!!
    private val keyY = NamespacedKey.fromString("tbd:location_y")!!
    private val keyZ = NamespacedKey.fromString("tbd:location_z")!!


    override fun getPrimitiveType(): Class<PersistentDataContainer> {
        return PersistentDataContainer::class.java
    }

    override fun getComplexType(): Class<Location> {
        return Location::class.java
    }

    override fun fromPrimitive(primitive: PersistentDataContainer, context: PersistentDataAdapterContext): Location {
        return Location(
            Bukkit.getWorld(primitive.get(keyWorld, PersistentDataType.STRING)!!),
            primitive.get(keyX, PersistentDataType.DOUBLE)!!,
            primitive.get(keyY, PersistentDataType.DOUBLE)!!,
            primitive.get(keyZ, PersistentDataType.DOUBLE)!!
        )
    }

    override fun toPrimitive(complex: Location, context: PersistentDataAdapterContext): PersistentDataContainer {
        val container =  context.newPersistentDataContainer()
        container.set(keyWorld, PersistentDataType.STRING, complex.world.name)
        container.set(keyX, PersistentDataType.DOUBLE, complex.x)
        container.set(keyY, PersistentDataType.DOUBLE, complex.y)
        container.set(keyZ, PersistentDataType.DOUBLE, complex.z)
        return container
    }
}