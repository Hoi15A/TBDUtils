package computer.austins.tbdutils.util.tab

import computer.austins.tbdutils.util.Chat
import computer.austins.tbdutils.util.config.ConfigManager

import org.bukkit.entity.Player

import kotlin.random.Random

object TabListManager {
    private var header = "<newline>                    <tbdcolour><b>TBD SMP: Season 3               "
    private var footer = ""

    fun populateTabList(player : Player) {
        val quotesList = ConfigManager.getQuotesConfig().getStringList("quotes")
        footer = if(quotesList.isEmpty()) {
            "<gray><i>'Quote not found.'<newline>"
        } else {
            val quote = quotesList[Random.nextInt(quotesList.size)]
            "<gray><i>'$quote'\n"
        }
        player.sendPlayerListHeaderAndFooter(Chat.formatMessage(header, false), Chat.formatMessage(footer, false))
    }
}