package com.cobr.quickadb.actions

import com.cobr.quickadb.requirements.ProjectAppRequirements
import com.intellij.openapi.actionSystem.AnActionEvent

abstract class ProjectAppAction : AnAppAction<ProjectAppRequirements>() {
    override fun createRequirements(
        event: AnActionEvent
    ): ProjectAppRequirements = ProjectAppRequirements(event)
}