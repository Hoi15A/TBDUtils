package computer.austins.tbdutils

import computer.austins.tbdutils.messenger.BrandMessenger
import computer.austins.tbdutils.messenger.NoxesiumMessenger
import computer.austins.tbdutils.util.NoxesiumChannel
import computer.austins.tbdutils.util.batboat.BatBoatManager
import computer.austins.tbdutils.util.config.ConfigManager
import computer.austins.tbdutils.util.CustomNametag

import io.papermc.paper.command.brigadier.CommandSourceStack

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor

import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

import org.incendo.cloud.annotations.AnnotationParser
import org.incendo.cloud.description.CommandDescription
import org.incendo.cloud.execution.ExecutionCoordinator
import org.incendo.cloud.paper.PaperCommandManager
import org.incendo.cloud.processors.cache.SimpleCache
import org.incendo.cloud.processors.confirmation.ConfirmationConfiguration
import org.incendo.cloud.processors.confirmation.ConfirmationManager
import org.incendo.cloud.processors.confirmation.annotation.ConfirmationBuilderModifier

import org.reflections.Reflections

import java.lang.Exception
import java.time.Duration
import java.util.function.Consumer

@Suppress("unused", "unstableApiUsage")
class TBDUtils : JavaPlugin() {
    private lateinit var commandManager: PaperCommandManager<CommandSourceStack>
    private lateinit var annotationParser: AnnotationParser<CommandSourceStack>
    override fun onEnable() {
        setupCommands()
        setupEventListeners()
        setupPluginMessageListener()
        ConfigManager.setup()
        CustomNametag.setup()
        logger.info("TBDUtils enabled.")
    }

    override fun onDisable() {
        BatBoatManager.removeAll()
        CustomNametag.destroy()
        logger.info("TBDUtils disabled.")
    }

    private fun setupCommands() {
        logger.info("Registering commands.")
        commandManager = PaperCommandManager.builder()
            .executionCoordinator(ExecutionCoordinator.simpleCoordinator())
            .buildOnEnable(this)

        annotationParser = AnnotationParser(commandManager, CommandSourceStack::class.java)
        annotationParser.parseContainers()

        setupCommandConfirmation()
    }

    private fun setupCommandConfirmation() {
        logger.info("Setting up command confirmation.")
        val confirmationConfig = ConfirmationConfiguration.builder<CommandSourceStack>()
            .cache(SimpleCache.of())
            .noPendingCommandNotifier { css ->
                css.sender.sendMessage(
                    Component.text(
                        "You do not have any pending commands.",
                        NamedTextColor.RED
                    )
                ) }
            .confirmationRequiredNotifier { css, ctx ->
                css.sender.sendMessage(
                    Component.text("Confirm command ", NamedTextColor.RED).append(
                        Component.text("'/${ctx.commandContext()}' ", NamedTextColor.GREEN)
                    ).append(Component.text("by running ", NamedTextColor.RED)).append(
                        Component.text("'/confirm' ", NamedTextColor.YELLOW)
                    ).append(Component.text("to execute.", NamedTextColor.RED))
                ) }
            .expiration(Duration.ofSeconds(30))
            .build()

        val confirmationManager = ConfirmationManager.confirmationManager(confirmationConfig)
        commandManager.registerCommandPostProcessor(confirmationManager.createPostprocessor())

        commandManager.command(
            commandManager.commandBuilder("confirm")
                .handler(confirmationManager.createExecutionHandler())
                .commandDescription(CommandDescription.commandDescription("Confirm a pending command."))
                .permission("tbdutils.confirm")
                .build()
        )
        ConfirmationBuilderModifier.install(annotationParser)
    }

    private fun setupEventListeners() {
        logger.info("Registering events.")
        val reflections = Reflections("computer.austins.tbdutils.event")
        val listeners = reflections.getSubTypesOf(Listener::class.java)

        listeners.forEach(Consumer { listener : Class<out Listener> ->
            try {
                val instance = listener.getConstructor().newInstance()
                server.pluginManager.registerEvents(instance, this)
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }
        )
    }

    private fun setupPluginMessageListener() {
        logger.info("Registering plugin messengers.")
        messenger.registerIncomingPluginChannel(this, "minecraft:brand", BrandMessenger())
        messenger.registerIncomingPluginChannel(this, NoxesiumChannel.NOXESIUM_V1_CLIENT_INFORMATION_CHANNEL.channel, NoxesiumMessenger())
        messenger.registerIncomingPluginChannel(this, NoxesiumChannel.NOXESIUM_V2_CLIENT_INFORMATION_CHANNEL.channel, NoxesiumMessenger())
    }
}

val plugin = Bukkit.getPluginManager().getPlugin("TBDUtils")!!
val messenger = Bukkit.getMessenger()
val logger = plugin.logger