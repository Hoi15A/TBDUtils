package moe.neat.tbdutils

import cloud.commandframework.annotations.AnnotationParser
import cloud.commandframework.execution.CommandExecutionCoordinator
import cloud.commandframework.extra.confirmation.CommandConfirmationManager
import cloud.commandframework.meta.CommandMeta
import cloud.commandframework.meta.SimpleCommandMeta
import cloud.commandframework.paper.PaperCommandManager

import de.maxhenkel.voicechat.api.BukkitVoicechatService

import moe.neat.tbdutils.commands.BaseCommand
import moe.neat.tbdutils.voice.TBDVoiceChatPlugin

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.reflections.Reflections

import java.util.*
import java.util.concurrent.TimeUnit
import java.util.function.Consumer

@Suppress("unused")
class Plugin : JavaPlugin() {

    override fun onEnable() {
        saveDefaultConfig()
        server.servicesManager.load(BukkitVoicechatService::class.java)?.registerPlugin(TBDVoiceChatPlugin())
        setupCommands()
        setupEventListeners()
    }

    private fun setupCommands() {
        val commandManager: PaperCommandManager<CommandSender> = try {
            PaperCommandManager.createNative(
                this,
                CommandExecutionCoordinator.simpleCoordinator()
            )
        } catch (e: Exception) {
            logger.severe("Failed to initialize the command manager")
            server.pluginManager.disablePlugin(this)
            return
        }

        commandManager.registerAsynchronousCompletions()
        commandManager.registerBrigadier()
        setupCommandConfirmation(commandManager)

        // Thanks broccolai <3 https://github.com/broccolai/tickets/commit/e8c227abc298d1a34094708a24601d006ec25937
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


        val reflections = Reflections("moe.neat.tbdutils.commands")
        val commands = reflections.getSubTypesOf(BaseCommand::class.java)

        val annotationParser = AnnotationParser(
            commandManager,
            CommandSender::class.java
        ) { SimpleCommandMeta.empty() }

        commands.forEach { command ->
            run {
                val instance = command.getConstructor().newInstance()
                annotationParser.parse(instance)
            }
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
                .permission("cheesehunt.confirm"))

        } catch (e : Exception) {
            logger.severe("Failed to initialize command confirmation manager.")
            return
        }
    }

    private fun setupEventListeners() {
        val reflections = Reflections("moe.neat.tbdutils.events")
        val listeners = reflections.getSubTypesOf(Listener::class.java)

        listeners.forEach(Consumer { listener: Class<out Listener> ->
            try {
                val instance = listener.getConstructor().newInstance()
                server.pluginManager.registerEvents(instance, this)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        })
    }

    companion object {
        val plugin: Plugin get() = Bukkit.getPluginManager().getPlugin("TBDUtils") as Plugin
    }
}