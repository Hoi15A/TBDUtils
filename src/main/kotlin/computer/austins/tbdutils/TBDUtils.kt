package computer.austins.tbdutils

import cloud.commandframework.execution.CommandExecutionCoordinator
import cloud.commandframework.paper.PaperCommandManager

import computer.austins.tbdutils.command.BaseCommand
import computer.austins.tbdutils.util.PluginMessenger

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

import org.reflections.Reflections

import java.util.*

@Suppress("unused")
class TBDUtils : JavaPlugin() {
    override fun onEnable() {
        registerCommands()
        registerEvents()
        registerPluginMessegers()
        logger.info("TBDUtils enabled.")
    }

    private fun registerCommands() {
        logger.info("Registering commands.")
        val commandManager: PaperCommandManager<CommandSender> = try {
            PaperCommandManager.createNative(
                this,
                CommandExecutionCoordinator.simpleCoordinator()
            )
        } catch (e: Exception) {
            logger.severe("Failed to initialize the command manager.")
            server.pluginManager.disablePlugin(this)
            return
        }

        commandManager.registerAsynchronousCompletions()
        commandManager.registerBrigadier()

        commandManager.commandSuggestionProcessor { context, strings ->
            var input: String = if (context.inputQueue.isEmpty()) {
                ""
            } else {
                context.inputQueue.peek()
            }
            input = input.lowercase(Locale.getDefault())
            val suggestions: MutableList<String> = LinkedList()
            for (suggestion in strings) {
                val suggestionLower = suggestion.lowercase(Locale.getDefault())
                if (suggestionLower.startsWith(input)) {
                    suggestions.add(suggestion)
                }
            }
            suggestions
        }

        val reflections = Reflections("computer.austins.tbdutils.command")
        val commands = reflections.getSubTypesOf(BaseCommand::class.java)
        commands.forEach {
            it.getConstructor().newInstance().register(commandManager)
        }
    }

    private fun registerEvents() {
        logger.info("Registering event listeners.")
        val reflections = Reflections("computer.austins.tbdutils.event")
        val listeners = reflections.getSubTypesOf(Listener::class.java)
        listeners.forEach { listener: Class<out Listener> ->
            val instance = listener.getConstructor().newInstance()
            server.pluginManager.registerEvents(instance, this)
        }
    }

    private fun registerPluginMessegers() {
        logger.info("Registering plugin messegers.")
        messenger.registerIncomingPluginChannel(this, "minecraft:brand", PluginMessenger())
    }

}

val plugin = Bukkit.getPluginManager().getPlugin("TBDUtils")!!
val messenger = Bukkit.getMessenger()
val logger = plugin.logger
