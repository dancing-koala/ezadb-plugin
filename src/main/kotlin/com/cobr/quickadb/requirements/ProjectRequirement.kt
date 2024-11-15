package com.cobr.quickadb.requirements

import com.github.michaelbull.result.toResultOr
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.Project

interface ProjectRequirement {
    class Impl(private val event: AnActionEvent) : ProjectRequirement {
        override lateinit var project: Project

        override fun RequirementsScope.requireProject() {
            project = event.getData(PlatformDataKeys.PROJECT)
                .toResultOr { RequirementError.NoProject }
                .bind()
        }
    }

    var project: Project

    fun RequirementsScope.requireProject()
}

