package computer.austins.tbdutils.util

import computer.austins.tbdutils.logger
import computer.austins.tbdutils.util.Formatting.allTags
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.title.Title
import net.kyori.adventure.title.Title.Times
import org.bukkit.Bukkit
import java.time.Duration

object Notification {
    fun announceServer(
        title: String, subtitle: String, sound: Sound = Sounds.Admin.SERVER_ANNOUNCEMENT, times: Times = Times.times(
            Duration.ofSeconds(1.toLong()),
            Duration.ofSeconds(6.toLong()),
            Duration.ofSeconds(1.toLong())
        )
    ) {
        logger.info("Announcement: $subtitle")

        val online = Audience.audience(Bukkit.getOnlinePlayers())
        online.sendMessage(allTags.deserialize("$title: $subtitle"))
        online.playSound(sound)
        online.showTitle(
            Title.title(
                allTags.deserialize(title),
                allTags.deserialize(subtitle),
                times
            )
        )
    }
}
