package com.cobr.ezadb.actions

import com.intellij.ide.actions.QuickSwitchSchemeAction
import com.intellij.openapi.actionSystem.ActionManager
import com.intellij.openapi.actionSystem.DataContext
import com.intellij.openapi.actionSystem.DefaultActionGroup
import com.intellij.openapi.project.Project
import kotlin.reflect.KClass

class ListAllAction : QuickSwitchSchemeAction() {

    override fun fillActions(
        project: Project?,
        group: DefaultActionGroup,
        dataContext: DataContext
    ) {
        project ?: return

        val manager = ActionManager.getInstance()

        listOf(
            UninstallAppAction::class,
            KillAppAction::class,
            StartAppAction::class,
            RestartAppAction::class,
            ClearAppDataAction::class,
            ClearAppDataAndRestartAction::class,
            RevokeAppPermissionsAction::class,
        ).forEach { group.addAction(manager, it) }

        group.addSeparator()

        listOf(
            StartAppWithDebuggerAction::class,
            RestartAppWithDebuggerAction::class,
        ).forEach { group.addAction(manager, it) }
    }

    private fun <T : AnAppAction<*>> DefaultActionGroup.addAction(
        actionManager: ActionManager,
        actionClass: KClass<T>
    ) = actionClass.qualifiedName
        ?.let(actionManager::getAction)
        ?.let(::addAction)
}