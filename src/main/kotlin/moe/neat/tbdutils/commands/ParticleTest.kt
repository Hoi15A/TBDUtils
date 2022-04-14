package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import moe.neat.tbdutils.Plugin
import moe.neat.tbdutils.util.ParticleImage
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import java.io.File
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


@Suppress("unused")
class ParticleTest : BaseCommand {
    private val particleImage: ParticleImage
    init {
        val resource = javaClass.classLoader.getResourceAsStream("particles/sprite.json")
        particleImage = ParticleImage(resource!!)
    }

    @CommandMethod("testparticle")
    @CommandPermission("tbdutils.command.unobtanium")
    fun testParticle(sender: Player) {
        var counter = 0
        object : BukkitRunnable () {
            override fun run() {
                if (counter == 20) this.cancel()
                // spawnParticles(sender, counter)
                spawnImageParticle(sender)
                counter++
            }
        }.runTaskTimer(Plugin.plugin, 20, 10)

    }

    private fun spawnImageParticle(player: Player) {
        particleImage.displayForPlayer(player)
    }

    private fun spawnParticles(player: Player, c: Int) {
        // spawns if for that player only
        player.spawnParticle(
            Particle.DUST_COLOR_TRANSITION,
            player.location.x,
            player.location.y + 0.1,
            player.location.z,
            1,
            Particle.DustTransition(Color.fromRGB(54, 33, 2), Color.fromRGB(36, 22, 2), 1F)
        )

        for (i in 1..100) {
            spawnBrownParticleAt(player, i/2.0, sin(i*1.0)*0.5)
            spawnBrownParticleAt(player, -i/10.0, 0.0)
            spawnBrownParticleAt(player, 0.0, i/10.0)
            spawnBrownParticleAt(player, 0.0, -i/10.0)
        }

        val radius = c
        for (i in 0..(2*PI*100).toInt()) {
            spawnBrownParticleAt(player, radius*sin(i/100.0), radius*cos(i/100.0))
        }
    }

    private fun spawnBrownParticleAt(player: Player, xOffset: Double, zOffset: Double) {
        player.world.spawnParticle(
            Particle.DUST_COLOR_TRANSITION,
            player.location.x + xOffset,
            player.location.y + 0.1,
            player.location.z + zOffset,
            1,
            Particle.DustTransition(Color.fromRGB(54, 33, 2), Color.fromRGB(36, 22, 2), 1F)
        )
    }
}