package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.Argument
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player

@Suppress("unused")
class NetherCalc : BaseCommand {
    @CommandMethod("portal [location]")
    @CommandPermission("tbdutils.command.portal")
    fun netherCalc(sender: Player, @Argument("location") location: Location?) {
        val loc = location ?: sender.location

        when (sender.world.environment) {
            World.Environment.NORMAL -> {
                sender.sendMessage(
                    Component.text("Build a portal in the ")
                        .append(Component.text("overworld").color(TextColor.color(0x42BB3C)))
                        .append(Component.text(" at "))
                        .append(Component.text("${loc.blockX} ${loc.blockZ}").color(TextColor.color(0x5FBFF9)))
                        .append(Component.text(" and a portal in the "))
                        .append(Component.text("nether").color(TextColor.color(0xE5446D)))
                        .append(Component.text(" at "))
                        .append(Component.text("${loc.blockX / 8} ${loc.blockZ / 8}").color(TextColor.color(0x5FBFF9)))
                        .append(Component.text(" to link them together."))
                )
            }
            World.Environment.NETHER -> {
                sender.sendMessage(
                    Component.text("Build a portal in the ")
                        .append(Component.text("nether").color(TextColor.color(0xE5446D)))
                        .append(Component.text(" at "))
                        .append(Component.text("${loc.blockX} ${loc.blockZ}").color(TextColor.color(0x5FBFF9)))
                        .append(Component.text(" and a portal in the "))
                        .append(Component.text("overworld").color(TextColor.color(0x42BB3C)))
                        .append(Component.text(" at "))
                        .append(Component.text("${loc.blockX * 8} ${loc.blockZ * 8}").color(TextColor.color(0x5FBFF9)))
                        .append(Component.text(" to link them together."))
                )
            }
            World.Environment.THE_END -> sender.sendMessage(Component.text("Not quite sure what you are trying to do there friend, this is the end, but you do you.").color(NamedTextColor.RED))
            World.Environment.CUSTOM -> sender.sendMessage(Component.text("Where even are you what...\nWell whatever you are doing I dont know where you'd link to so you are on your own.").color(NamedTextColor.RED))
        }
    }
}
