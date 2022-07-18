package moe.neat.tbdutils.particles

import moe.neat.tbdutils.Plugin
import moe.neat.tbdutils.util.ParticleImage
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class Blossom {

    private val particleImage: ParticleImage
    private val activeParticles = mutableSetOf<UUID>()

    init {
        val resource = javaClass.classLoader.getResourceAsStream("particles/blossom.png")

        println(resource)
        particleImage = ParticleImage(resource!!)
    }

    fun sneakEvent(sneaking: Boolean, player: Player) {
        if (sneaking && !player.isFlying) {
            if (activeParticles.size == 0) {
                object : BukkitRunnable() {
                    override fun run() {
                        if (activeParticles.size < 1) this.cancel()

                        for (p in Bukkit.getOnlinePlayers().filter { activeParticles.contains(it.uniqueId) && enabledPlayers.contains(it.uniqueId) }) {
                            particleImage.displayForPlayer(p)
                        }
                    }
                }.runTaskTimer(Plugin.plugin, 0, 10)
            }

            activeParticles.add(player.uniqueId)
        } else {
            activeParticles.remove(player.uniqueId)
        }
    }

    companion object {
        private val enabledPlayers = mutableSetOf<UUID>()

        fun toggleActive(player: Player): Boolean {
            if (enabledPlayers.contains(player.uniqueId)) {
                enabledPlayers.remove(player.uniqueId)
                return false
            } else {
                enabledPlayers.add(player.uniqueId)
                return true
            }
        }
    }
}
