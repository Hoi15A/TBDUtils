package moe.neat.tbdutils.commands

import cloud.commandframework.annotations.Argument
import cloud.commandframework.annotations.CommandMethod
import cloud.commandframework.annotations.CommandPermission
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.Player

@Suppress("unused")
class NetherCalc : BaseCommand {
    private val mm = MiniMessage.miniMessage()

    @CommandMethod("portal [location]")
    @CommandPermission("tbdutils.command.portal")
    fun netherCalc(sender: Player, @Argument("location") location: Location?) {
        val loc = location ?: sender.location
        when (sender.world.environment) {
            World.Environment.NORMAL -> {

                sender.sendMessage(mm.deserialize(
                    "Build a portal in the <#42BB3C>overworld</#42BB3C> at " +
                            "<#5FBFF9>${loc.blockX} ${loc.blockZ}</#5FBFF9> " +
                            "and a portal in the <#E5446D>nether</#E5446D> " +
                            "at <#5FBFF9>${loc.blockX / 8} ${loc.blockZ / 8}</#5FBFF9> " +
                            "to link them together."
                ))
            }
            World.Environment.NETHER -> {
                sender.sendMessage(mm.deserialize(
                    "Build a portal in the <#E5446D>nether</#E5446D> at " +
                            "<#5FBFF9>${loc.blockX} ${loc.blockZ}</#5FBFF9> " +
                            "and a portal in the <#42BB3C>overworld</#42BB3C> " +
                            "at <#5FBFF9>${loc.blockX * 8} ${loc.blockZ * 8}</#5FBFF9> " +
                            "to link them together."
                ))
            }
            World.Environment.THE_END -> sender.sendMessage(Component.text("Not quite sure what you are trying to do there friend, this is the end, but you do you.").color(NamedTextColor.RED))
            World.Environment.CUSTOM -> sender.sendMessage(Component.text("Where even are you what...\nWell whatever you are doing I don't know where you'd link to so you are on your own.").color(NamedTextColor.RED))
        }
    }
}
