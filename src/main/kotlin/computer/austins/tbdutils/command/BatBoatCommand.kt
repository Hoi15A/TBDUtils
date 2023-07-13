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
            commandDescription("Spawns a Bat Boat at the player's location")

            argument(argumentDescription("Spawns the Bat Boat and attaches the sender as a passenger")) {
                BooleanArgument.builder<CommandSender>("attach").asOptionalWithDefault("false").build()
            }

            handler {
                val attach = it.get<Boolean>("attach")

                if (it.sender !is Player) return@handler
                val sender = it.sender as Player

                if (BatBoatManager.contains(sender.uniqueId)) {
                    Chat.messageAudience(
                        sender,
                        "<red>You have an existing Bat Boat; wait for it to be removed first.",
                        true
                    )
                    return@handler
                }

                BatBoat(sender, attach)
            }
        }

        commandManager.buildAndRegister("forcebatboat") {
            permission = "tbdutils.command.batboat.spawn.force"
            commandDescription("Spawns a Bat Boat at the player's location")

            argument(argumentDescription("The player to spawn the Bat Boat for")) {
                PlayerArgument.of("player")
            }

            argument(argumentDescription("Spawns the Bat Boat and attaches the player as a passenger")) {
                BooleanArgument.builder<CommandSender>("attach").asOptionalWithDefault("false").build()
            }

            handler {
                val player = it.get<Player>("player")
                val attach = it.get<Boolean>("attach")

                if (BatBoatManager.contains(player.uniqueId)) {
                    Chat.messageAudience(
                        it.sender,
                        "<red>${player.name} has an existing Bat Boat; remove it first.",
                        true
                    )
                    return@handler
                }

                BatBoat(player, attach)
            }
        }

        commandManager.buildAndRegister("smitebats") {
            permission = "tbdutils.command.batboat.admin"
            commandDescription("Removes a subset of Bat Boats")

            argument(argumentDescription("The player to forcefully remove the instance, if applicable")) {
                PlayerArgument.builder<CommandSender>("player").asOptional().build()
            }

            handler {
                val player = it.getOptional<Player>("player").getOrNull()

                if (player != null) {
                    BatBoatManager.remove(player.uniqueId)
                    Chat.messageAudience(
                        it.sender,
                        "<green>The Bat Boat instance for ${player.name} has been removed if it existed.",
                        true
                    )
                    return@handler
                }

                BatBoatManager.removeAll()
                Chat.messageAudience(it.sender, "<green>Bat Boat instances have been cleared.", true)
            }
        }
    }
}
