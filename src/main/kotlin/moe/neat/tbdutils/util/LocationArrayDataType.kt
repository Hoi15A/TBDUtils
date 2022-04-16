package moe.neat.tbdutils.util

import org.bukkit.Location
import org.bukkit.persistence.PersistentDataAdapterContext
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType

class LocationArrayDataType : PersistentDataType<Array<PersistentDataContainer>, Array<Location>> {
    override fun getPrimitiveType(): Class<Array<PersistentDataContainer>> {
        return Array<PersistentDataContainer>::class.java
    }

    override fun getComplexType(): Class<Array<Location>> {
        return Array<Location>::class.java
    }

    override fun fromPrimitive(
        primitive: Array<PersistentDataContainer>,
        context: PersistentDataAdapterContext
    ): Array<Location> {
        return Array(primitive.size) {
            LocationDataType().fromPrimitive(primitive[it], context)
        }
    }

    override fun toPrimitive(
        complex: Array<Location>,
        context: PersistentDataAdapterContext
    ): Array<PersistentDataContainer> {
        return Array(complex.size) {
            LocationDataType().toPrimitive(complex[it], context)
        }
    }
}