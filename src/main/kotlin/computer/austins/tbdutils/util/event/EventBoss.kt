package computer.austins.tbdutils.util.event

import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player

object EventBoss {
    private var eventBossPlayer: Player? = null

    fun setEventBoss(player: Player) {
        this.eventBossPlayer = player
    }

    fun getEventBoss(): Player? {
        return eventBossPlayer
    }

    fun setBossMaxHealth() {
        if(eventBossPlayer != null) {
            eventBossPlayer!!.getAttribute(Attribute.GENERIC_MAX_HEALTH)!!.baseValue = 100.0
            eventBossPlayer!!.health = 100.0
            eventBossPlayer!!.foodLevel = Int.MAX_VALUE
        }
    }
}