package moe.neat.tbdutils.events

import de.tr7zw.nbtapi.NBTItem
import github.scarsz.discordsrv.DiscordSRV
import github.scarsz.discordsrv.dependencies.jda.api.EmbedBuilder
import moe.neat.tbdutils.util.EasterScoreboard
import moe.neat.tbdutils.util.LocationArrayDataType
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.*
import org.bukkit.entity.ArmorStand
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractAtEntityEvent
import org.bukkit.inventory.EquipmentSlot


@Suppress("unused")
class PlayerInteractEntity : Listener {
    private val mm = MiniMessage.miniMessage()

    @EventHandler
    private fun onInteractEntity(e: PlayerInteractAtEntityEvent) {
        val entity = e.rightClicked
        if (entity is ArmorStand) {

            val headItem = entity.getItem(EquipmentSlot.HEAD)
            if (headItem.type != Material.AIR) {
                val nItem = NBTItem(headItem)
                if (nItem.getBoolean("isEasterEgg")) {

                    val data = e.player.persistentDataContainer.getOrDefault(
                        NamespacedKey.fromString("tbd:easter_eggs_collected")!!,
                        LocationArrayDataType(),
                        arrayOf()
                    )

                    var isDuplicate = false
                    for (loc in data) {
                        if (e.rightClicked.location.x == loc.x &&
                            e.rightClicked.location.y == loc.y &&
                            e.rightClicked.location.z == loc.z
                        ) {
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

                        val eggLocation = e.rightClicked.location
                        eggLocation.y = eggLocation.y + 1.5

                        e.player.world.spawnParticle(
                            Particle.VILLAGER_HAPPY,
                            eggLocation,
                            25,
                            0.5,
                            0.5,
                            0.5
                        )

                        e.player.world.playSound(
                            eggLocation,
                            Sound.ENTITY_PLAYER_LEVELUP,
                            1f,
                            1f
                        )

                        Bukkit.getServer().sendMessage(
                            mm.deserialize("<color:blue>${e.player.name}</color><gradient:dark_green:yellow> found an easter egg!</gradient>")
                        )

                        val embed = EmbedBuilder()
                            .setColor(0xf898c0)
                            .setAuthor(
                                "${e.player.name} found an easter egg!",
                                null,
                                "https://crafatar.com/avatars/${e.player.uniqueId}?overlay=true"
                            )
                            .build()
                        DiscordSRV.getPlugin().mainTextChannel.sendMessageEmbeds(embed).queue()

                        EasterScoreboard.setPlayerEggs(e.player, data.size + 1)
                    } else {
                        e.player.sendMessage(mm.deserialize("<gradient:red:gold>You've already found this egg!</gradient>"))
                    }
                }
            }
        }
    }
}
