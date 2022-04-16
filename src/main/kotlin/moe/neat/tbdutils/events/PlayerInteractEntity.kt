package moe.neat.tbdutils.events

import de.tr7zw.nbtapi.NBTItem
import moe.neat.tbdutils.util.LocationArrayDataType
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.ArmorStand
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.inventory.EquipmentSlot


@Suppress("unused")
class PlayerInteractEntity : Listener {

    @EventHandler
    private fun onInteractEntity(e: PlayerInteractAtEntityEvent) {
        val entity = e.rightClicked
        if (entity is ArmorStand) {
            e.player.sendMessage("right clicked armor stand")

            val headItem = entity.getItem(EquipmentSlot.HEAD)
            if (headItem.type != Material.AIR) {
                val nItem = NBTItem(headItem)
                if (nItem.getBoolean("isEasterEgg")) {
                    e.player.sendMessage("Easter egg")

                    val data = e.player.persistentDataContainer.getOrDefault(
                        NamespacedKey.fromString("tbd:easter_eggs_collected")!!,
                        LocationArrayDataType(),
                        arrayOf()
                    )

                    var isDuplicate = false
                    for (loc in data) {
                        if (e.rightClicked.location == loc) {
                            isDuplicate = true
                            break
                        }
                    }

                    if (!isDuplicate) {
                        val newData = data.toMutableList()
                        newData.add(e.rightClicked.location)

                        e.player.persistentDataContainer.set(
                            NamespacedKey.fromString("tbd:easter_eggs_collected")!!,
                            LocationArrayDataType(),
                            newData.toTypedArray()
                        )
                    }
                }
            }
        }
    }
}
