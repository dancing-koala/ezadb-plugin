package com.cobr.ezadb.actions

import com.cobr.ezadb.requirements.ProjectAppRequirements
import com.intellij.openapi.actionSystem.AnActionEvent

abstract class ProjectAppAction : AnAppAction<ProjectAppRequirements>() {
    override fun createRequirements(
        event: AnActionEvent
    ): ProjectAppRequirements = ProjectAppRequirements(event)
}