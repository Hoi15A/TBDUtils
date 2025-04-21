package computer.austins.tbdutils.messenger

import computer.austins.tbdutils.logger
import computer.austins.tbdutils.util.Noxesium
import computer.austins.tbdutils.util.NoxesiumChannel

import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener

import java.nio.ByteBuffer

class NoxesiumMessenger : PluginMessageListener {
    override fun onPluginMessageReceived(channel : String, player : Player, message : ByteArray) {
        logger.info("Channel: $channel | Player: ${player.name} | Message: ${parseIntByteArray(message)}")
        if(channel == NoxesiumChannel.NOXESIUM_V1_CLIENT_INFORMATION_CHANNEL.channel || channel == NoxesiumChannel.NOXESIUM_V2_CLIENT_INFORMATION_CHANNEL.channel) {
            Noxesium.addNoxesiumUser(player, parseIntByteArray(message))
        }
    }

    private fun parseIntByteArray(bytes : ByteArray) : Int {
        // Could quite possibly be destroyed if Daniel changes how information is send from Noxesium.
        return bytes[1].toInt()
    }
}