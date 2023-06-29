package computer.austins.tbdutils.task

import computer.austins.tbdutils.util.Sounds

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.title.Title

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

class IntroductionTask {
    private val introLoopMap = mutableMapOf<UUID, BukkitRunnable>()

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
                        player.teleport(Location(player.world, 140.5, 200.0, 970.5))
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
        introLoopMap.remove(player.uniqueId)?.cancel()
    }
}