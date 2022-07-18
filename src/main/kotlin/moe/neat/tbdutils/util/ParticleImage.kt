package moe.neat.tbdutils.util

import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.nio.PngReader
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.entity.Player
import java.io.InputStream

class ParticleImage(inputStream: InputStream) {
    private val imageData: ImmutableImage

    init {
        imageData = ImmutableImage.loader()
            .withImageReaders(listOf(PngReader())).fromStream(inputStream)

    }

    fun displayForPlayer(player: Player) {

        imageData.pixels().forEach { pixel ->

            if (pixel.alpha() > 0) {
                player.world.spawnParticle(
                    Particle.REDSTONE,
                    player.location.x - imageData.width / 2 / 10 + pixel.x.toDouble() / 10,
                    player.location.y,
                    player.location.z - imageData.height / 2 / 10 + pixel.y.toDouble() / 10,
                    1,
                    Particle.DustOptions(Color.fromRGB(pixel.red(), pixel.green(), pixel.blue()), 1f)
                )
            }
        }
    }
}
