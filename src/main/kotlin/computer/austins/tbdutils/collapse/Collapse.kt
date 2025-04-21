package computer.austins.tbdutils.collapse

import computer.austins.tbdutils.util.config.ConfigManager
import computer.austins.tbdutils.util.config.TBDConfig
import computer.austins.tbdutils.util.event.EventBoss
import org.bukkit.Bukkit

object Collapse {
    private var eventState = CollapseEventState.IDLE
    private var eventAutoLaunch = false

    init {
        val configEventState = ConfigManager.getConfig(TBDConfig.EVENT).getString("event.state")
        val configEventAutoLaunch = ConfigManager.getConfig(TBDConfig.EVENT).getBoolean("event.autolaunch")
        if(configEventState != null) {
            setEventStateFromConfig(configEventState)
            autoLaunchEventFromConfig(configEventAutoLaunch)
        } else {
            ConfigManager.getConfig(TBDConfig.EVENT).set("event.state", eventState.stateName)
            ConfigManager.getConfig(TBDConfig.EVENT).set("event.autolaunch", eventAutoLaunch)
            ConfigManager.saveConfig(ConfigManager.getFile(TBDConfig.EVENT), ConfigManager.getConfig(TBDConfig.EVENT))
            ConfigManager.reloadConfig(TBDConfig.EVENT)
        }
    }

    fun setEventState(newEventState : CollapseEventState) {
        if(newEventState == CollapseEventState.EVENT && EventBoss.getEventBoss() == null) return
        this.eventState = newEventState
        ConfigManager.getConfig(TBDConfig.EVENT).set("event.state", eventState.stateName)
        ConfigManager.saveConfig(ConfigManager.getFile(TBDConfig.EVENT), ConfigManager.getConfig(TBDConfig.EVENT))
        ConfigManager.reloadConfig(TBDConfig.EVENT)

        if(this.eventState == CollapseEventState.EVENT) {
            for(player in Bukkit.getOnlinePlayers()) {
                player.playSound(player.location, "tbd.event.boss", 0.5f, 1.0f)
            }
        }
    }

    fun getEventState() : CollapseEventState {
        return this.eventState
    }

    fun setEventAutoLaunch(newAutoLaunch: Boolean) {
        this.eventAutoLaunch = newAutoLaunch
        ConfigManager.getConfig(TBDConfig.EVENT).set("event.autolaunch", eventAutoLaunch)
        ConfigManager.saveConfig(ConfigManager.getFile(TBDConfig.EVENT), ConfigManager.getConfig(TBDConfig.EVENT))
        ConfigManager.reloadConfig(TBDConfig.EVENT)
    }

    fun getEventAutoLaunch() : Boolean {
        return this.eventAutoLaunch
    }

    fun nextEventState() {
        when(eventState) {
            CollapseEventState.IDLE -> {
                setEventState(CollapseEventState.PRE_EVENT_TEASER)
            }
            CollapseEventState.PRE_EVENT_TEASER -> {
                setEventState(CollapseEventState.PRE_EVENT)
            }
            CollapseEventState.PRE_EVENT -> {
                setEventState(CollapseEventState.EVENT)
            }
            CollapseEventState.EVENT -> {
                setEventState(CollapseEventState.POST_EVENT)
            }
            CollapseEventState.POST_EVENT -> {
                setEventState(CollapseEventState.IDLE)
            }
        }
    }

    private fun setEventStateFromConfig(stateName : String) {
        for(state in CollapseEventState.entries) {
            if(state.stateName == stateName) {
                setEventState(state)
                break
            }
        }
    }

    private fun autoLaunchEventFromConfig(autoLaunch: Boolean) {
        if(autoLaunch && CollapseTask.getEventTaskMap().isEmpty()) CollapseTask.eventTask()
    }
}

enum class CollapseEventState(val stateName : String) {
    IDLE("idle"),
    PRE_EVENT_TEASER("pre_event_teaser"),
    PRE_EVENT("pre_event"),
    EVENT("event"),
    POST_EVENT("post_event")
}