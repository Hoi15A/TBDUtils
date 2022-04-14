package moe.neat.tbdutils.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.entity.Player
import java.io.InputStream

@Serializable
data class Pixel(val r: Int, val g: Int, val b: Int, val a: Int)
@Serializable
data class ImageData(val name: String, val width: Int, val height: Int, val image: List<List<Pixel>>)


@OptIn(ExperimentalSerializationApi::class)
class ParticleImage(inputStream: InputStream) {
    private val imageData: ImageData

    init {
        imageData = Json.decodeFromStream(inputStream)
    }

    fun displayForPlayer(player: Player) {
        for (x in imageData.image.indices) {
            for (z in imageData.image[x].indices) {
                val pixel = imageData.image[x][z]

                if (pixel.a > 0) {
                    player.world.spawnParticle(
                        Particle.REDSTONE,
                        player.location.x + x.toDouble() / 10,
                        player.location.y,
                        player.location.z + z.toDouble() / 10,
                        1,
                        Particle.DustOptions(Color.fromRGB(pixel.r, pixel.g, pixel.b), 1f)
                    )
                }
            }
        }
    }
}
