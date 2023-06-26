package moe.neat.tbdutils.events

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration

import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

import kotlin.time.Duration
import kotlin.time.DurationUnit

@Suppress("unused")
class PlayerInventoryInteractionEvent : Listener {
    @EventHandler
    private fun onCharmUse(e : PlayerInteractEvent) {
        if(e.action.isRightClick && e.player.inventory.itemInMainHand.type == Material.RED_DYE && e.player.inventory.itemInMainHand.itemMeta.displayName() == Component.text("Molten Secret Stone").color(NamedTextColor.DARK_RED).decoration(TextDecoration.ITALIC, false)) {
            e.player.inventory.setItemInMainHand(null)
            e.player.sendMessage(Component.text("YOU INGESTED A SECRET STONE AND GAINED IMMENSE POWER!", NamedTextColor.DARK_RED, TextDecoration.BOLD))
            e.player.addPotionEffect(PotionEffect(PotionEffectType.HEALTH_BOOST, Duration.INFINITE.toInt(DurationUnit.SECONDS), 99,false, false))
            e.player.addPotionEffect(PotionEffect(PotionEffectType.REGENERATION, Duration.INFINITE.toInt(DurationUnit.SECONDS), 2,false, false))
            e.player.addPotionEffect(PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Duration.INFINITE.toInt(DurationUnit.SECONDS), 2,false, false))
            e.player.addPotionEffect(PotionEffect(PotionEffectType.FIRE_RESISTANCE, Duration.INFINITE.toInt(DurationUnit.SECONDS), 1,false, false))
            e.player.addPotionEffect(PotionEffect(PotionEffectType.INCREASE_DAMAGE, Duration.INFINITE.toInt(DurationUnit.SECONDS), 4,false, false))
            e.player.health = e.player.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.value
        }
    }
}