package moe.neat.tbdutils.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title

import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable

import java.time.Duration
import java.util.*

import kotlin.time.DurationUnit

@Suppress("unused")
class DowntimeMusicLoop {
    private val musicLoopMap = mutableMapOf<UUID, BukkitRunnable>()

    fun startMusicLoop(player : Player, plugin : Plugin) {
        player.showTitle(Title.title(Component.text("\uD000"), Component.text(""), Title.Times.times(Duration.ofSeconds(0), Duration.ofSeconds(1), Duration.ofSeconds(1))))
        player.gameMode = GameMode.ADVENTURE

        for(activeEffects in player.activePotionEffects) {
            player.removePotionEffect(activeEffects.type)
        }

        player.addPotionEffect(PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, kotlin.time.Duration.INFINITE.toInt(DurationUnit.SECONDS) , 255, false, false))
        player.addPotionEffect(PotionEffect(PotionEffectType.SATURATION, kotlin.time.Duration.INFINITE.toInt(DurationUnit.SECONDS) , 255, false, false))
        player.inventory.clear()
        player.exp = 0.0f
        player.level = 0
        player.teleport(Location(Bukkit.getWorld("SMP2_the_end"), 0.5, 71.0, -489.5, -180.0f, 15.0f))

        val bukkitRunnable = object: BukkitRunnable() {
            var musicTimer = 0
            override fun run() {
                player.sendActionBar(Component.text("TBD SMP - Season 3: ").append(Component.text("Coming Soon...", NamedTextColor.RED)))
                if(musicTimer == 0) {
                    player.playSound(player.location, "event.downtime.loop", 0.75f, 1f)
                }
                if(musicTimer == 191) {
                    musicTimer = -1
                }
                musicTimer++
            }
        }
        bukkitRunnable.runTaskTimer(plugin, 0L, 20L)
        musicLoopMap[player.uniqueId] = bukkitRunnable
    }

    fun removeFromMusicLoop(player : Player) {
        musicLoopMap.remove(player.uniqueId)?.cancel()
    }
}