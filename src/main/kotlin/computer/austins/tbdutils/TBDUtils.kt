package computer.austins.tbdutils

import cloud.commandframework.execution.CommandExecutionCoordinator
import cloud.commandframework.extra.confirmation.CommandConfirmationManager
import cloud.commandframework.meta.CommandMeta
import cloud.commandframework.paper.PaperCommandManager

import computer.austins.tbdutils.command.BaseCommand
import computer.austins.tbdutils.util.PluginMessenger
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

import org.reflections.Reflections

import java.util.*
import java.util.concurrent.TimeUnit

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
        setupCommandConfirmation(commandManager)

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

    private fun setupCommandConfirmation(commandManager : PaperCommandManager<CommandSender>) {
        try {
            val confirmationManager : CommandConfirmationManager<CommandSender> = CommandConfirmationManager(
                30L, TimeUnit.SECONDS,
                { context -> context.commandContext.sender.sendMessage(
                    Component.text("Confirm command ", NamedTextColor.RED).append(
                        Component.text("'/${context.command}' ", NamedTextColor.GREEN)).append(Component.text("by running ", NamedTextColor.RED)).append(
                        Component.text("'/confirm' ", NamedTextColor.YELLOW)).append(Component.text("to execute.", NamedTextColor.RED))) },
                { sender -> sender.sendMessage(Component.text("You do not have any pending commands.", NamedTextColor.RED)) }
            )
            confirmationManager.registerConfirmationProcessor(commandManager)

            commandManager.command(commandManager.commandBuilder("confirm")
                .meta(CommandMeta.DESCRIPTION, "Confirm a pending command.")
                .handler(confirmationManager.createConfirmationExecutionHandler())
                .permission("tbdutils.confirm"))

        } catch (e : Exception) {
            logger.severe("Failed to initialize command confirmation manager.")
            return
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