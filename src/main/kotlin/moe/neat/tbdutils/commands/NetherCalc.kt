package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.Argument
import cloud.commandframework.annotations.CommandMethod
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player

@Suppress("unused")
class NetherCalc : BaseCommand {

    @CommandMethod("nether [location]")
    fun netherCalc(sender: Player, @Argument("location") location: Location?) {
        val loc = location ?: sender.location

        when (sender.world.environment) {
            World.Environment.NORMAL -> {
                sender.sendMessage(
                    Component.text("To link from the ")
                        .append(Component.text("overworld").color(NamedTextColor.GREEN))
                        .append(Component.text(" at "))
                        .append(Component.text("${loc.blockX} ${loc.blockY} ${loc.blockZ}").color(NamedTextColor.BLUE))
                        .append(Component.text("\nBuild a portal in the "))
                        .append(Component.text("nether").color(NamedTextColor.RED))
                        .append(Component.text(" at "))
                        .append(Component.text("${loc.blockX / 8} ${loc.blockY} ${loc.blockZ / 8}").color(NamedTextColor.BLUE))
                )
            }
            World.Environment.NETHER -> {
                sender.sendMessage(
                    Component.text("To link from the ")
                        .append(Component.text("nether").color(NamedTextColor.RED))
                        .append(Component.text(" at "))
                        .append(Component.text("${loc.blockX} ${loc.blockY} ${loc.blockZ}").color(NamedTextColor.BLUE))
                        .append(Component.text("\nBuild a portal in the "))
                        .append(Component.text("overworld").color(NamedTextColor.GREEN))
                        .append(Component.text(" at "))
                        .append(Component.text("${loc.blockX * 8} ${loc.blockY} ${loc.blockZ * 8}").color(NamedTextColor.BLUE))
                )
            }
            World.Environment.THE_END -> sender.sendMessage(Component.text("Not quite sure what you are trying to do there buddy, this is the end, but you do you.").color(NamedTextColor.RED))
            World.Environment.CUSTOM -> sender.sendMessage(Component.text("Where even are you what...\nWell whatever you are doing I dont know where you'd link to so you are on your own.").color(NamedTextColor.RED))
        }
    }
}
