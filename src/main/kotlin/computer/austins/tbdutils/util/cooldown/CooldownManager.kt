package computer.austins.tbdutils.util.cooldown

import org.bukkit.scheduler.BukkitRunnable

import java.util.*

object CooldownManager {
    private var preTeaserEventFireCooldowns = HashMap<BukkitRunnable, Long>()
    private const val preTeaserEventCooldownTime = 60000 // 1 minute
    fun canPreTeaserRun(runnable : BukkitRunnable) : Boolean {
        return if(!preTeaserEventFireCooldowns.containsKey(runnable) || System.currentTimeMillis() - preTeaserEventFireCooldowns[runnable]!! > preTeaserEventCooldownTime) {
            preTeaserEventFireCooldowns[runnable] = System.currentTimeMillis()
            true
        } else {
            false
        }
    }
}