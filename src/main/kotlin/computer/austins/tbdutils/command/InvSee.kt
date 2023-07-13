package computer.austins.tbdutils.command

import cloud.commandframework.bukkit.parsers.PlayerArgument
import cloud.commandframework.kotlin.extension.argumentDescription
import cloud.commandframework.kotlin.extension.buildAndRegister
import cloud.commandframework.paper.PaperCommandManager

import computer.austins.tbdutils.logger
import computer.austins.tbdutils.util.Chat
import computer.austins.tbdutils.util.Sounds

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

@Suppress("unused")
class InvSee : BaseCommand() {
    override fun register(commandManager: PaperCommandManager<CommandSender>) {
        commandManager.buildAndRegister("invsee") {
            permission = "tbdutils.command.invsee"
            commandDescription("Responds with provided arguments")

            argument(argumentDescription("Player of whose inventory is to be viewed.")) {
                PlayerArgument.of("player")
            }
            flag("isEChest", emptyArray(), argumentDescription("Defines whether the player's inventory to be viewed should be their Ender Chest."))

            handler {
                if(it.sender is Player) {
                    val sender = it.sender as Player
                    val player = it.get<Player>("player")
                    val isEChest = it.flags().isPresent("isEChest")

                    if(sender == player) {
                        Chat.messageAudience(sender, "<red><prefix:warning>You cannot view your own ${if(isEChest) "<light_purple>Ender Chest" else "<yellow>Inventory"}<red>, you fool.", false)
                        sender.playSound(Sounds.ACTION_FAIL)
                    } else {
                        if(isEChest) {
                            Chat.broadcastAdmin("<notifcolour>${sender.name}<reset> is now viewing <notifcolour>${player.name}<reset>'s <light_purple>Ender Chest<reset>.", false)
                            sender.openInventory(player.enderChest)
                        } else {
                            Chat.broadcastAdmin("<notifcolour>${sender.name}<reset> is now viewing <notifcolour>${player.name}<reset>'s <yellow>Inventory<reset>.", false)
                            sender.openInventory(player.inventory)
                        }
                    }
                } else {
                    logger.warning("Only players are able to utilise the /invsee command.")
                }
            }
        }
    }
}