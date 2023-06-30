package computer.austins.tbdutils.task

import computer.austins.tbdutils.util.Sounds

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.title.Title

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

import java.time.Duration
import java.util.*

import kotlin.time.DurationUnit

object EventTask {
    private val introLoopMap = mutableMapOf<UUID, BukkitRunnable>()
    private val downtimeLoopMap = mutableMapOf<UUID, BukkitRunnable>()
    var isDowntimeActive = false

    fun startIntroLoop(player : Player, plugin : Plugin) {
        val bukkitRunnable = object: BukkitRunnable() {
            var introTime = 0
            override fun run() {
                    if(introTime % 3 == 0 && introTime < 10) {
                        player.addPotionEffect(PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Int.MAX_VALUE, 255, false, false))
                        player.playSound(Sounds.IntroEvent.HEARTBEAT)
                        player.showTitle(Title.title(
                            Component.text("\uD000"),
                            Component.text(""),
                            Title.Times.times(
                                Duration.ofSeconds(0.toLong()),
                                Duration.ofSeconds(5.toLong()),
                                Duration.ofSeconds(0.toLong())
                                )
                            )
                        )
                    }
                    if(introTime == 10) {
                        if(player.name == "Byrt") {
                            Bukkit.dispatchCommand(player, "gamerule doDaylightCycle true")
                            Bukkit.dispatchCommand(player, "gamerule doWeatherCycle true")
                            Bukkit.dispatchCommand(player, "gamerule doInsomnia true")
                        }
                        player.playSound(Sounds.IntroEvent.INTRO_MUSIC)
                        player.showTitle(Title.title(
                            Component.text("\uD001"),
                            Component.text(""),
                            Title.Times.times(
                                Duration.ofSeconds(0.toLong()),
                                Duration.ofSeconds(2.toLong()),
                                Duration.ofSeconds(1.toLong())
                                )
                            )
                        )
                        player.teleport(Location(player.world, 140.5, 256.0, 970.5))
                        player.addPotionEffect(PotionEffect(PotionEffectType.SLOW_FALLING, Int.MAX_VALUE, 255, false, false))
                    }
                    if(introTime == 15) {
                        player.showTitle(Title.title(
                            Component.text("TBD SMP Season 3", TextColor.fromHexString("#ff9ced")),
                            Component.text("A new world awaits..."),
                            Title.Times.times(
                                Duration.ofSeconds(1.toLong()),
                                Duration.ofSeconds(5.toLong()),
                                Duration.ofSeconds(1.toLong())
                                )
                            )
                        )
                    }
                    if(introTime >= 10 && player.location.block.getRelative(BlockFace.DOWN).type != Material.AIR) {
                        stopIntroLoop(player)
                    }
                    introTime++
                }
            }
        bukkitRunnable.runTaskTimer(plugin, 0L, 20L)
        introLoopMap[player.uniqueId] = bukkitRunnable
    }

    fun stopIntroLoop(player : Player) {
        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE)
        player.removePotionEffect(PotionEffectType.SLOW_FALLING)
        player.saturation = 0.0f
        player.gameMode = GameMode.SURVIVAL
        introLoopMap.remove(player.uniqueId)?.cancel()
    }

    fun startDowntimeLoop(player : Player, plugin : Plugin) {
        player.showTitle(Title.title(Component.text("\uD000"), Component.text(""), Title.Times.times(Duration.ofSeconds(0), Duration.ofSeconds(1), Duration.ofSeconds(1))))
        player.gameMode = GameMode.ADVENTURE
        for(activeEffects in player.activePotionEffects) {
            player.removePotionEffect(activeEffects.type)
        }
        player.addPotionEffect(PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, kotlin.time.Duration.INFINITE.toInt(DurationUnit.SECONDS), 255, false, false))
        player.addPotionEffect(PotionEffect(PotionEffectType.SATURATION, kotlin.time.Duration.INFINITE.toInt(DurationUnit.SECONDS), 0, false, false))
        //TELEPORT LOC player.teleport(Location(Bukkit.getWorld("SMP2_the_end"), 0.5, 71.0, -489.5, -180.0f, 15.0f))

        val bukkitRunnable = object: BukkitRunnable() {
            var musicTimer = 0
            override fun run() {
                player.sendActionBar(Component.text("TBD SMP - Season 3: ").append(Component.text("Starting Soon...", NamedTextColor.RED)))
                if(musicTimer == 1) {
                    player.playSound(Sounds.IntroEvent.DOWNTIME_MUSIC)
                }
                if(musicTimer == 191) {
                    musicTimer = -1
                }
                musicTimer++
            }
        }
        bukkitRunnable.runTaskTimer(plugin, 0L, 20L)
        downtimeLoopMap[player.uniqueId] = bukkitRunnable
    }

    fun stopDowntimeLoop(player : Player) {
        player.removePotionEffect(PotionEffectType.SATURATION)
        player.stopSound(Sounds.IntroEvent.DOWNTIME_MUSIC)
        downtimeLoopMap.remove(player.uniqueId)?.cancel()
    }
}