package computer.austins.tbdutils.util

import computer.austins.tbdutils.logger

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.tag.Tag
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

import org.bukkit.Bukkit
import org.bukkit.entity.Player

import java.util.*

object Noxesium {
    /** Map of online users playing with Noxesium installed, and the protocol of their version of Noxesium. **/
    private val noxesiumUsers = mutableMapOf<UUID, Int>()

    /** Adds the requested player with their respective protocol version of Noxesium to the noxesiumUsers map. **/
    fun addNoxesiumUser(player : Player, protocolVersion : Int) {
        logger.info("${player.name} is on Noxesium protocol $protocolVersion.")
        if(protocolVersion >= NOXESIUM_MINIMUM_PROTOCOL) {
            noxesiumUsers[player.uniqueId] = protocolVersion
            logger.info("Passed ${player.name} as a Noxesium user.")
        } else {
            logger.warning("Unable to pass ${player.name} as Noxesium user as their protocol version is $protocolVersion when it should be $NOXESIUM_MINIMUM_PROTOCOL or higher.")
        }
    }

    /** Removes the requested player from the noxesiumUsers map. **/
    fun removeNoxesiumUser(player : Player) {
        noxesiumUsers.remove(player.uniqueId)
        logger.info("Removed ${player.name} from Noxesium users.")
    }

    /** Returns the noxesiumUsers map. **/
    fun getNoxesiumUsers() : Map<UUID, Int> {
        return noxesiumUsers
    }

    /** Returns whether an online player is in the noxesiumUsers list or not. **/
    fun isNoxesiumUser(player : Player) : Boolean {
        return noxesiumUsers.containsKey(player.uniqueId)
    }

    /**
    * Noxesium skull component builder that allows the displaying of player heads in chat for those that are utilising Noxesium.
    * @params
    * uuid: UUID of player's skull to build.
    * isGrayscale: Decide whether the skull should be coloured or grayscale.
    * advance: Moves skull horizontally in non-chat UIs.
    * ascent: Moves skull vertically.
    * scale: Scales the skull anchored by its top-left corner.
    * Standard coloured skull params may look like: (uuid, false, 0, 0, 1.0)
    * */
    fun buildSkullComponent(uuid : UUID, isGrayscale : Boolean, advance : Int, ascent : Int, scale : Float) : Component {
        return Component.translatable("%nox_uuid%$uuid,$isGrayscale,$advance,$ascent,$scale", "").color(NamedTextColor.WHITE)
    }

    /** Resolves any MiniMessage <skull:NAME> tags used in messages. **/
    fun skullResolver() : TagResolver {
        return TagResolver.resolver("skull") { args, _ ->
            val rawName = args.popOr("Name not supplied.")
            Tag.inserting(buildSkullComponent(Bukkit.getPlayerUniqueId(rawName.toString())!!, false, 0, 0, 1.0f))
        }
    }
}

@Suppress("unused")
/** Channels are only server-bound. **/
enum class NoxesiumChannel(val channel : String) {
    NOXESIUM_V1_CLIENT_INFORMATION_CHANNEL("noxesium-v1:client_info"),
    NOXESIUM_V1_CLIENT_SETTINGS_CHANNEL("noxesium-v1:client_settings"),
    NOXESIUM_V2_CLIENT_INFORMATION_CHANNEL("noxesium-v2:client_info"),
    NOXESIUM_V2_CLIENT_SETTINGS_CHANNEL("noxesium-v2:client_settings")
}

/** Minimum protocol version required of the Noxesium mod. **/
const val NOXESIUM_MINIMUM_PROTOCOL = 2