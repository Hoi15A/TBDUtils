package computer.austins.tbdutils.command

import computer.austins.tbdutils.util.Notification
import computer.austins.tbdutils.util.Sounds

import io.papermc.paper.command.brigadier.CommandSourceStack

import net.kyori.adventure.title.Title.Times

import org.incendo.cloud.annotations.Argument
import org.incendo.cloud.annotations.Command
import org.incendo.cloud.annotations.CommandDescription
import org.incendo.cloud.annotations.Permission
import org.incendo.cloud.annotations.processing.CommandContainer
import org.incendo.cloud.processors.confirmation.annotation.Confirmation

import java.time.Duration

@Suppress("unused", "unstableApiUsage")
@CommandContainer
class Announcement {
    @Command("announce <text>")
    @CommandDescription("Changes the value of the specified setting.")
    @Permission("tbdutils.cmd.announce")
    @Confirmation
    fun announce(css: CommandSourceStack, @Argument("text") text: Array<String>) {
        Notification.announceServer("<yellow><b>Announcement<reset>", text.joinToString(" "))
    }

    @Command("announcerestart <time>")
    @CommandDescription("Changes the value of the specified setting.")
    @Permission("burb.cmd.admin_chat")
    @Confirmation
    fun announceRestart(css: CommandSourceStack, @Argument("time") time: Int) {
        Notification.announceServer(
            "<red><b>Server Restarting<reset>",
            "In $time minute${if (time > 1) "s" else ""}.",
            Sounds.RESTART_ANNOUNCEMENT,
            Times.times(
                Duration.ofSeconds(1.toLong()),
                Duration.ofSeconds(3.toLong()),
                Duration.ofSeconds(1.toLong())
            )
        )
    }
}
