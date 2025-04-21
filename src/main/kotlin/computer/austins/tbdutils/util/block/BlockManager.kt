package computer.austins.tbdutils.util.block

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player

object BlockManager {
    private val preTeaserMin = Location(Bukkit.getWorlds()[0], 130.5, 230.0, 950.5)
    private val preTeaserMax = Location(Bukkit.getWorlds()[0], 150.5, 270.0, 990.5)

    private val preTeaserArea = Location(Bukkit.getWorlds()[0], 140.5, 256.0, 970.5)

    fun isPlayerInPreTeaserArea(player: Player): Boolean {
        return player.location.x in preTeaserMin.x..preTeaserMax.x
                && player.location.y in preTeaserMin.y..preTeaserMax.y
                && player.location.z in preTeaserMin.z..preTeaserMax.z
    }

    fun getPreTeaserAreaRetainYawPitch(player: Player): Location {
        return Location(preTeaserArea.world, preTeaserArea.x, preTeaserArea.y, preTeaserArea.z, player.yaw, player.pitch)
    }

    fun debugPreTeaserAreaCorners() {
        preTeaserMin.block.type = Material.BLUE_CONCRETE
        preTeaserMax.block.type = Material.RED_CONCRETE
    }
}