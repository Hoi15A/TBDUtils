package computer.austins.tbdutils.command

import computer.austins.tbdutils.collapse.Collapse
import computer.austins.tbdutils.collapse.CollapseEventState
import computer.austins.tbdutils.collapse.CollapseTask

import computer.austins.tbdutils.util.Chat
import computer.austins.tbdutils.util.block.BlockManager

import io.papermc.paper.command.brigadier.CommandSourceStack
import org.incendo.cloud.annotations.Argument

import org.incendo.cloud.annotations.Command
import org.incendo.cloud.annotations.Permission
import org.incendo.cloud.annotations.processing.CommandContainer

@Suppress("unused", "unstableApiUsage")
@CommandContainer
class EventCommand {
    @Command("event next_state")
    @Permission("tbdutils.cmd.event")
    fun eventNextState(css: CommandSourceStack) {
        Chat.broadcastDev("<notifcolour>${css.sender.name} <white>pushed the event to its <tbdcolour>next state<white>.", false)
        Collapse.nextEventState()
    }

    @Command("event set_state <state>")
    @Permission("tbdutils.cmd.event")
    fun eventSetState(css: CommandSourceStack, state: CollapseEventState) {
        if(Collapse.getEventState() != state) {
            Chat.broadcastDev("<notifcolour>${css.sender.name} <white>set the event state to <tbdcolour>${state}<white>.", false)
            Collapse.setEventState(state)
        }
    }

    @Command("event auto_launch <boolean>")
    @Permission("tbdutils.cmd.event")
    fun eventSetAutoLaunch(css: CommandSourceStack, @Argument("boolean") boolean: Boolean) {
        Chat.broadcastDev("<notifcolour>${css.sender.name} <white>set event auto-launch to <tbdcolour>${boolean}<white>.", false)
        if(Collapse.getEventAutoLaunch() != boolean) {
            Collapse.setEventAutoLaunch(boolean)
        }
    }

    @Command("event start")
    @Permission("tbdutils.cmd.event")
    fun eventStart(css: CommandSourceStack) {
        if(CollapseTask.getEventTaskMap().isEmpty()) {
            Chat.broadcastDev("<notifcolour>${css.sender.name} <white>started the event using its existing state.", false)
            CollapseTask.eventTask()
        }
    }

    @Command("event stop")
    @Permission("tbdutils.cmd.event")
    fun eventStop(css: CommandSourceStack) {
        if(CollapseTask.getEventTaskMap().isNotEmpty()) {
            Chat.broadcastDev("<notifcolour>${css.sender.name} <white>stopped the currently active event.", false)
            CollapseTask.stopEventTask()
        }
    }

    @Command("event debug pre_teaser corners")
    @Permission("tbdutils.cmd.event")
    fun eventDebugPreTeaserCorners(css: CommandSourceStack) {
        if(CollapseTask.getEventTaskMap().isNotEmpty()) {
            BlockManager.debugPreTeaserAreaCorners()
        }
    }
}