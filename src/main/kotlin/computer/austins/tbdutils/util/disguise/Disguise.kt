package computer.austins.tbdutils.util.disguise

import com.destroystokyo.paper.profile.PlayerProfile
import com.destroystokyo.paper.profile.ProfileProperty
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import computer.austins.tbdutils.plugin
import computer.austins.tbdutils.util.Chat
import computer.austins.tbdutils.util.CustomNametag
import computer.austins.tbdutils.util.pathfinding.Pathfinding
import computer.austins.tbdutils.util.vanish.VanishManager
import net.kyori.adventure.audience.Audience

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import org.bukkit.Bukkit
import org.bukkit.GameMode

import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import java.io.InputStreamReader
import java.net.URL

import java.util.*

class Disguise(player: Player, disguiseName: String, disguiseSkinName: String?) {
    private val disguisePlayer : Player = player
    private val disguiseUUID : UUID = disguisePlayer.uniqueId
    private val disguiseSkinNameParsed : String = disguiseSkinName ?: disguisePlayer.name

    init {
        DisguiseManager.disguise(disguisePlayer, disguiseUUID, disguiseName, disguiseSkinNameParsed)
        setPlayerSkin(player, disguiseSkinNameParsed)
        CustomNametag.buildNametag(disguisePlayer)
        Chat.broadcastAdmin("<notifcolour>${disguisePlayer.name} <reset>has disguised as $disguiseName using $disguiseSkinName's skin.", true)

        object : BukkitRunnable() {
            override fun run() {
                if(DisguiseManager.isDisguised(disguiseUUID)) {
                    disguisePlayer.sendActionBar(
                        Component.text("You are currently ", NamedTextColor.WHITE)
                            .append(Component.text("DISGUISED", TextColor.fromHexString("#a43beb"))
                                .append(Component.text(".", NamedTextColor.WHITE))
                            )
                    )
                } else {
                    disguisePlayer.sendActionBar(Component.text(""))
                    setPlayerSkin(disguisePlayer, disguisePlayer.name)
                    CustomNametag.destroyNametag(disguisePlayer.uniqueId)
                    Chat.broadcastAdmin("<notifcolour>${player.name} <reset>has un-disguised.", true)
                    this.cancel()
                }
            }
        }.runTaskTimer(plugin, 0L, 5L)
    }

    private fun setPlayerSkin(playerToChange : Player, playerSkinToGrabName : String) {
        try {
            val uuid = URL("https://api.mojang.com/users/profiles/minecraft/${playerSkinToGrabName}")
            val userReader = InputStreamReader(uuid.openStream())
            val id = JsonParser.parseReader(userReader).asJsonObject.get("id").asString
            val profile: PlayerProfile = Bukkit.createProfile(playerToChange.uniqueId, id)
            val mojang = URL("https://sessionserver.mojang.com/session/minecraft/profile/$id?unsigned=false")
            val reader = InputStreamReader(mojang.openStream())
            val textureProperty : JsonObject = JsonParser.parseReader(reader).asJsonObject.get("properties").asJsonArray.get(0).asJsonObject
            if (textureProperty.get("value").asString != null && textureProperty.get("signature").asString != null) {
                val texture = textureProperty.get("value").asString
                val signature = textureProperty.get("signature").asString
                profile.clearProperties()
                profile.setProperty(ProfileProperty("textures", texture, signature))
                playerToChange.playerProfile = profile
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}