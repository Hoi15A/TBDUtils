package computer.austins.tbdutils.collapse

import computer.austins.tbdutils.plugin
import computer.austins.tbdutils.util.Chat
import computer.austins.tbdutils.util.Formatting
import computer.austins.tbdutils.util.block.BlockManager
import computer.austins.tbdutils.util.cooldown.CooldownManager
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound

import net.kyori.adventure.text.Component
import net.kyori.adventure.title.Title

import org.bukkit.Bukkit
import org.bukkit.Particle
import org.bukkit.scheduler.BukkitRunnable
import java.time.Duration

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

object CollapseTask {
    private val eventTaskMap = mutableMapOf<Int, BukkitRunnable>()
    private var currentEventTaskId = 0
    private var timeLeftTicks = 0
    private var timeLeftSecs = 0
    private var timerState = false

    fun eventTask() {
        val eventTaskRunnable = object : BukkitRunnable() {
            var timeElapsedTicks = 0
            var timeElapsedSecs = 0
            override fun run() {
                for(player in Bukkit.getOnlinePlayers()) {
                    player.sendActionBar(Component.text("Time Remaining: ${String.format("%02d:%02d", timeLeftSecs / 60, timeLeftSecs % 60)} | Time Elapsed: ${String.format("%02d:%02d:%02d", timeElapsedSecs / 3600, timeElapsedSecs / 60, timeElapsedSecs % 60)} | Local Time: ${LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))} | ${Collapse.getEventState()}"))
                }
                if(Collapse.getEventState() == CollapseEventState.IDLE) {
                    // IDLE
                }
                if(Collapse.getEventState() == CollapseEventState.PRE_EVENT_TEASER) {
                    if(LocalDateTime.now().format(DateTimeFormatter.ofPattern("mm")).toInt() % 2 == 0) {
                        if(CooldownManager.canPreTeaserRun(this)) {
                            for(player in Bukkit.getOnlinePlayers()) {
                                player.playSound(player.location, "tbd.event.pre.border_move", 1.0f, 1.0f)
                                player.sendMessage(Formatting.allTags.deserialize("<gray><italic>Otherworldly voices linger around you..."))
                            }
                            Bukkit.getWorlds()[0].worldBorder.warningDistance = Int.MAX_VALUE
                            Bukkit.getWorlds()[0].worldBorder.setSize(Bukkit.getWorlds()[0].worldBorder.size - 1.0, TimeUnit.SECONDS, 3L)
                            object: BukkitRunnable() {
                                override fun run() {
                                    Bukkit.getWorlds()[0].worldBorder.warningDistance = 5
                                }
                            }.runTaskLater(plugin, 100L)
                        }
                    }
                }
                if(Collapse.getEventState() == CollapseEventState.PRE_EVENT) {
                    for(player in Bukkit.getOnlinePlayers()) {
                        if(!BlockManager.isPlayerInPreTeaserArea(player)) {
                            player.teleport(BlockManager.getPreTeaserAreaRetainYawPitch(player))
                            player.playSound(Sound.sound(Key.key("tbd.event.rift.use"), Sound.Source.VOICE, 8.0f, 1.0f))
                            player.spawnParticle(Particle.FIREWORK, player.location, 100, 0.0, 0.0, 0.0)
                            player.spawnParticle(Particle.ENCHANTED_HIT, player.location, 100, 0.0, 0.0, 0.0)
                            player.spawnParticle(Particle.SOUL, player.location, 100, 0.0, 0.0, 0.0)
                            player.showTitle(
                                Title.title(
                                    Formatting.allTags.deserialize("\uD001"),
                                    Formatting.allTags.deserialize(""),
                                    Title.Times.times(
                                        Duration.ofSeconds(0L),
                                        Duration.ofMillis(100L),
                                        Duration.ofMillis(100L)
                                    )
                                )
                            )
                        }
                    }
                    if(timeElapsedSecs % 30 == 0 && timeElapsedTicks == 0) {
                        for(player in Bukkit.getOnlinePlayers()) {
                            player.playSound(Sound.sound(Key.key("tbd.event.pre.border_move"), Sound.Source.VOICE, 1.0f, 1.0f))
                        }
                    }
                    // TODO: Teleport into waiting area and play sounds/ambience
                }
                if(Collapse.getEventState() == CollapseEventState.EVENT) {
                    // TODO: Begin event
                }
                if(Collapse.getEventState() == CollapseEventState.POST_EVENT) {
                    // TODO
                }
                timeElapsedTicks++
                if(timeElapsedTicks >= 20) {
                    timeElapsedTicks = 0
                    timeElapsedSecs++
                }
            }
        }
        eventTaskRunnable.runTaskTimer(plugin, 0L, 1L)
        currentEventTaskId = eventTaskRunnable.taskId
        eventTaskMap[eventTaskRunnable.taskId] = eventTaskRunnable
    }

    fun stopEventTask() {
        eventTaskMap.remove(currentEventTaskId)?.cancel()
        for(strayTasks in eventTaskMap) {
            strayTasks.value.cancel()
        }
        eventTaskMap.clear()
    }

    fun setTimerState(boolean: Boolean) {
        if(this.timerState == boolean) return
        this.timerState = boolean
    }

    fun setTimeLeft(int: Int) {
        this.timeLeftSecs = int
        this.timeLeftTicks = 0
    }

    fun getTimeLeft(): Int {
        return this.timeLeftSecs
    }

    fun getEventTaskMap() : Map<Int, BukkitRunnable> {
        return this.eventTaskMap
    }
}