package computer.austins.tbdutils.command

import cloud.commandframework.paper.PaperCommandManager
import org.bukkit.command.CommandSender

abstract class BaseCommand {
    abstract fun register(commandManager: PaperCommandManager<CommandSender>)
}
