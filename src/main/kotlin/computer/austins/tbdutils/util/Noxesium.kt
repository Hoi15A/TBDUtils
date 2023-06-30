package computer.austins.tbdutils.util

import computer.austins.tbdutils.logger

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

import org.bukkit.Bukkit
import org.bukkit.entity.Player

import java.util.*

object Noxesium {
    private val noxesiumUsers = mutableMapOf<UUID, Int>()

    fun addNoxesiumUser(player : Player, protocolVersion : Int) {
        if(protocolVersion == NOXESIUM_LATEST_PROTOCOL) {
            noxesiumUsers[player.uniqueId] = protocolVersion
            logger.info("Passed ${player.name} as a Noxesium user.")
        } else {
            logger.warning("Unable to pass ${player.name} as Noxesium user as their protocol version is $protocolVersion when it should be $NOXESIUM_LATEST_PROTOCOL.")
        }
    }

    fun removeNoxesiumUser(player : Player) {
        noxesiumUsers.remove(player.uniqueId)
        logger.info("Removed ${player.name} from Noxesium users.")
    }

    fun getNoxesiumUsers() : Map<UUID, Int> {
        return noxesiumUsers
    }

    fun isNoxesiumUser(player : Player) : Boolean {
        return noxesiumUsers.containsKey(player.uniqueId)
    }

    /**
    * @params
    * uuid: UUID of player's skull to build.
    * isGrayscale: Decide whether the skull should be coloured or grayscale.
    * advance: Moves skull horizontally in non-chat UIs.
    * ascent: Moves skull vertically.
    * scale: Scales the skull anchored by its top-left corner.
    * Standard coloured skull params may look like: (uuid, false, 0, 0, 1.0)
    * */
    fun buildSkullComponent(uuid : UUID, isGrayscale : Boolean, advance : Int, ascent : Int, scale : Float) : Component {
        return Component.score("%NCPH%$uuid,$isGrayscale,$advance,$ascent,$scale", "")
    }

    fun skullResolver() : TagResolver {
        return TagResolver.resolver("skull") { args, _ ->
            val rawName = args.popOr("Name not supplied.")
            Tag.inserting(buildSkullComponent(Bukkit.getPlayerUniqueId(rawName.toString())!!, false, 0, 0, 1.0f))
        }
    }
}

@Suppress("unused")
enum class NoxesiumChannel(val channel : String) {
    NOXESIUM_CLIENT_INFORMATION_CHANNEL("noxesium:client_information"),
    NOXESIUM_CLIENT_SETTINGS_CHANNEL("noxesium:client_settings"),
    NOXESIUM_SERVER_RULE_CHANNEL("noxesium:server_rules"),
    NOXESIUM_RESET_CHANNEL("noxesium:reset")
}

const val NOXESIUM_LATEST_PROTOCOL = 2