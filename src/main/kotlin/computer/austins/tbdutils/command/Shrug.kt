package computer.austins.tbdutils.command

private const val SHRUG = "¯\\_(ツ)_/¯"

@Suppress("unused")
class Shrug  {/*
    override fun register(commandManager: PaperCommandManager<CommandSender>) {
        commandManager.buildAndRegister("shrug") {
            permission = "tbdutils.command.shrug"
            commandDescription("Adds a shrug emote to your message")
            senderType<Player>()

            argument(argumentDescription("Message content")) {
                StringArgument.optional("message", StringArgument.StringMode.GREEDY)
            }

            handler {
                val arg = it.getOrDefault("message", "")
                Chat.globalChat(it.sender as Player, Component.text("$arg $SHRUG".trim()))
            }
        }
    }*/
}