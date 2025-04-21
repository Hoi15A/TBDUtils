package computer.austins.tbdutils.util

import computer.austins.tbdutils.plugin
import computer.austins.tbdutils.util.disguise.DisguiseManager.getDisguise
import computer.austins.tbdutils.util.disguise.DisguiseManager.isDisguised

import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.GameMode
import org.bukkit.entity.AreaEffectCloud
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scoreboard.Team

import java.util.*

object CustomNametag {
    private val playerNametags = mutableMapOf<UUID, AreaEffectCloud>()
    private val nametagTasks = mutableMapOf<UUID, BukkitRunnable>()

    private val invisibleTags = Bukkit.getScoreboardManager().mainScoreboard.registerNewTeam("invisibleTags-${UUID.randomUUID()}")

    fun buildNametag(player: Player) {
        invisibleTags.addPlayer(player)

        val nametag : AreaEffectCloud = player.world.spawn(player.location, AreaEffectCloud::class.java)
        nametag.duration = Int.MAX_VALUE
        nametag.radius = 0F
        nametag.waitTime = 0
        nametag.color = Color.BLACK
        nametag.customName(Formatting.allTags.deserialize(player.getDisguise().first))

        playerNametags[player.uniqueId] = nametag
        player.addPassenger(nametag)
        runNametagTask(player, player.uniqueId, nametag)

        nametag.isCustomNameVisible = true
    }

    fun destroyNametag(uuid: UUID) {
        playerNametags[uuid]?.remove()
        playerNametags.remove(uuid)
    }

    fun getNametag(player: Player): AreaEffectCloud? {
        return playerNametags[player.uniqueId]
    }

    private fun runNametagTask(player: Player, uuid: UUID, nametag : AreaEffectCloud) {
        val nametagTask = object : BukkitRunnable() {
            override fun run() {
                if(player.isDisguised()) {
                    nametag.customName(Formatting.allTags.deserialize(player.getDisguise().first))
                } else {
                    nametag.customName(Formatting.allTags.deserialize("<white>${player.name}"))
                }
                if(!player.passengers.contains(nametag)) {
                    player.addPassenger(nametag)
                }
                if(player.world != nametag.world) {
                    nametag.teleport(player)
                }
                if(player.isSneaking) {
                    nametag.isSneaking = true
                } else {
                    nametag.isSneaking = false
                }
                if(player.isInvisible || player.gameMode == GameMode.SPECTATOR || player.isDead) {
                    nametag.isCustomNameVisible = false
                } else {
                    nametag.isCustomNameVisible = true
                }
                if(!player.isOnline) {
                    cancelNametagTask(uuid)
                }
            }
        }
        nametagTask.runTaskTimer(plugin, 0L, 1L)
        nametagTasks[player.uniqueId] = nametagTask
    }

    fun cancelNametagTask(uuid: UUID) {
        destroyNametag(uuid)
        nametagTasks[uuid]?.cancel()
        nametagTasks.remove(uuid)
    }

    fun setup() {
        invisibleTags.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER)
    }

    fun destroy() {
        invisibleTags.unregister()
        for(world in Bukkit.getWorlds()) {
            for(aec in world.getEntitiesByClass(AreaEffectCloud::class.java)) {
                aec.remove()
            }
        }
    }
}