package moe.neat.tbdutils.util

import com.destroystokyo.paper.ClientOption
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.MainHand
import java.util.*

object AprilFools {
    private val mm = MiniMessage.miniMessage()
    private val demoCooldown = mutableMapOf<UUID, Long>()
    private val leftHandMessages = listOf(
        "% joined the game, but is left-handed...",
        "% joined holding their tool wrongly",
        "Something about % is kinda cursed, either way they have joined",
        "% joined being incredibly unbased",
        "% joined and everyone died of cringe",
        "Unfortunately, % is here and is left-handed"
    )

    fun playerJoinEvent(e: PlayerJoinEvent) {
        if (isDemoCooldownOver(e.player.uniqueId)) demoScreen(e.player)
        leftHand(e)
    }

    fun asyncChatEvent(e: AsyncChatEvent) {
        if ((0..100).random() <= 1) {
            e.message(e.originalMessage().append(Component.text(" OwO")))
        }
    }

    private fun demoScreen(player: Player) {
        player.showDemoScreen()
        player.sendMessage(mm.deserialize(
            "<click:open_url:'https://rb.gy/enaq3a'><red>Due to an unforseen issue your minecraft account has been put into demo mode.\n" +
                "<yellow><b>Click here</b></yellow> to resolve this issue.</red></click>"
        ))
    }

    private fun isDemoCooldownOver(uuid: UUID): Boolean {
        val now = System.currentTimeMillis()

        if (!demoCooldown.containsKey(uuid)) {
            demoCooldown[uuid] = now
            return true
        }

        return now - demoCooldown[uuid]!! > 60 * 60 * 1000
    }

    private fun leftHand(e: PlayerJoinEvent) {
        val text = leftHandMessages.random().replace("%", e.player.name)

        if (e.player.getClientOption(ClientOption.MAIN_HAND) == MainHand.LEFT) {
            e.joinMessage(Component.text(text).color(NamedTextColor.YELLOW))
        }
    }

}
