package computer.austins.tbdutils.command

import cloud.commandframework.arguments.standard.BooleanArgument
import cloud.commandframework.bukkit.parsers.PlayerArgument
import cloud.commandframework.kotlin.extension.argumentDescription
import cloud.commandframework.kotlin.extension.buildAndRegister
import cloud.commandframework.paper.PaperCommandManager
import computer.austins.tbdutils.util.batboat.BatBoat
import computer.austins.tbdutils.util.batboat.BatBoatManager
import computer.austins.tbdutils.util.Chat
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import kotlin.jvm.optionals.getOrNull

@Suppress("unused")
class BatBoatCommand : BaseCommand() {
    override fun register(commandManager: PaperCommandManager<CommandSender>) {
        commandManager.buildAndRegister("batboat") {
            permission = "tbdutils.command.batboat.spawn.self"
            commandDescription("Spawns a BatBoat at the player's location")

            argument(argumentDescription("Spawns the BatBoat and attaches the sender as a passenger")) {
                BooleanArgument.builder<CommandSender>("attach").asOptionalWithDefault("false").build()
            }

            handler {
                val attach = it.get<Boolean>("attach")

                if (it.sender !is Player) return@handler
                val sender = it.sender as Player

                if (BatBoatManager.contains(sender.uniqueId)) {
                    Chat.messageAudience(
                        sender,
                        "<red>You have an existing BatBoat; wait for it to be removed first.",
                        true
                    )
                    return@handler
                }

                BatBoat(sender, attach)
            }
        }

        commandManager.buildAndRegister("forcebatboat") {
            permission = "tbdutils.command.batboat.spawn.force"
            commandDescription("Spawns a BatBoat at the player's location")

            argument(argumentDescription("The player to spawn the BatBoat for")) {
                PlayerArgument.of("player")
            }

            argument(argumentDescription("Spawns the BatBoat and attaches the player as a passenger")) {
                BooleanArgument.builder<CommandSender>("attach").asOptionalWithDefault("false").build()
            }

            handler {
                val player = it.get<Player>("player")
                val attach = it.get<Boolean>("attach")

                if (BatBoatManager.contains(player.uniqueId)) {
                    Chat.messageAudience(
                        it.sender,
                        "<red>${player.name} has an existing BatBoat; remove it first.",
                        true
                    )
                    return@handler
                }

                BatBoat(player, attach)
            }
        }

        commandManager.buildAndRegister("smitebats") {
            permission = "tbdutils.command.batboat.admin"
            commandDescription("Removes a subset of BatBoats")

            argument(argumentDescription("The player to forcefully remove the instance, if applicable")) {
                PlayerArgument.builder<CommandSender>("player").asOptional().build()
            }

            handler {
                val player = it.getOptional<Player>("player").getOrNull()

                if (player != null) {
                    BatBoatManager.remove(player.uniqueId)
                    Chat.messageAudience(
                        it.sender,
                        "<green>The BatBoat instance for ${player.name} has been removed if it existed.",
                        true
                    )
                    return@handler
                }

                BatBoatManager.removeAll()
                Chat.messageAudience(it.sender, "<green>BatBoat instances have been cleared.", true)
            }
        }
    }
}
