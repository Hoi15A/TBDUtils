package moe.neat.tbdutils.util

import com.destroystokyo.paper.event.player.PlayerClientOptionsChangeEvent
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.inventory.MainHand

object AprilFools {
    private val mm = MiniMessage.miniMessage()
    private val leftHandMessages = listOf(
        "Honestly they should just be /kicked and come back with the hand on the right side of the screen",
        "Somebody tell them they are holding their tool wrong",
        "I don't know what's up with them but they are kinda sus",
        "They are also incredibly unbased",
        "Then everyone died of cringe",
        "Unfortunately...",
        "Laugh"
    )

    fun playerJoinEvent(e: PlayerJoinEvent) {
        demoScreen(e.player)
    }

    fun playerClientOptionsChangeEvent(e: PlayerClientOptionsChangeEvent) {
        if (e.hasMainHandChanged() && e.mainHand == MainHand.LEFT) {
            leftHand(e.player)
        }
    }

    fun asyncChatEvent(e: AsyncChatEvent) {
        val plainTxt = PlainTextComponentSerializer.plainText().serialize(e.originalMessage())

        when ((0..1000).random()) {
            in 0..49 -> e.message(e.originalMessage().append(Component.text(" OwO")))
            in 50..99 -> e.message(Component.text(plainTxt.split(" ").shuffled().joinToString(" ")))
            in 100..149 -> e.message(Component.text(plainTxt.reversed()))
            in 150..199 -> e.message(mm.deserialize("<rainbow>${plainTxt}</rainbow>"))
            in 200..299 -> e.message(mm.deserialize("<hover:show_text:'made you look'><color:#d4e7f7>${plainTxt}</color></hover>"))
            1000 -> e.message(e.originalMessage().append(Component.text(" also I think austin is such an incredibly based individual.")))
        }
    }

    private fun demoScreen(player: Player) {
        player.showDemoScreen()
        player.sendMessage(mm.deserialize(
            "<click:open_url:'https://rb.gy/enaq3a'><red>Due to an unforseen issue your minecraft account has been put into demo mode.\n" +
                "<yellow><b>Click here</b></yellow> to resolve this issue.</red></click>"
        ))
    }

    private fun leftHand(player: Player) {
        val text = leftHandMessages.random().replace("%", player.name)
        Bukkit.getServer().onlinePlayers.forEach { it.sendMessage(Component.text(text).color(NamedTextColor.YELLOW)) }
    }
}
