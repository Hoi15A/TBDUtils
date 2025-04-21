package computer.austins.tbdutils.command

@Suppress("unused")
class NoxesiumCommand {/*
    override fun register(commandManager: PaperCommandManager<CommandSender>) {
        commandManager.buildAndRegister("noxesium") {
            permission = "tbdutils.command.noxesium.player_head"
            commandDescription("Responds with provided arguments")

            literal("head", argumentDescription("Required literal."))
            argument(argumentDescription("Player of who's head to get.")) {
                PlayerArgument.of("player")
            }

            handler {
                val arg = it.get<Player>("player")
                it.sender.sendMessage(Component.text("${arg.name}'s head: ", NamedTextColor.YELLOW).append(Noxesium.buildSkullComponent(arg.uniqueId, false, 0, 0, 1.0f)))
            }
        }

        commandManager.buildAndRegister("noxesium") {
            permission = "tbdutils.command.noxesium.get_protocol"
            commandDescription("Responds with provided arguments")

            literal("protocol", argumentDescription("Required literal."))
            argument(argumentDescription("Player of who's protocol version of Noxesium to get.")) {
                PlayerArgument.of("player")
            }

            handler {
                val arg = it.get<Player>("player")
                val protocol = Noxesium.getNoxesiumUsers()[arg.uniqueId]
                if(protocol != null) {
                    it.sender.sendMessage(Component.text("${arg.name}'s Noxesium protocol is ", NamedTextColor.YELLOW).append(Component.text("$protocol", NamedTextColor.GOLD)).append(Component.text(".", NamedTextColor.YELLOW)))
                } else {
                    it.sender.sendMessage(Component.text("${arg.name} is not registered as a Noxesium user.", NamedTextColor.YELLOW))
                }
            }
        }
    }*/
}