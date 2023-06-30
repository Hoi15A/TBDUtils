package computer.austins.tbdutils.messenger

import computer.austins.tbdutils.util.Noxesium
import computer.austins.tbdutils.util.NoxesiumChannel

import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener

import java.nio.ByteBuffer

class NoxesiumMessenger : PluginMessageListener {
    override fun onPluginMessageReceived(channel: String, player: Player, message: ByteArray) {
        if(channel == NoxesiumChannel.NOXESIUM_CLIENT_INFORMATION_CHANNEL.channel) {
            Noxesium.addNoxesiumUser(player, parseIntByteArray(message))
        }
    }

    private fun parseIntByteArray(rawByteArray : ByteArray) : Int {
        return ByteBuffer.wrap(rawByteArray).int
    }
}