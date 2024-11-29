package com.cobr.ezadb.requirements

import com.cobr.ezadb.platform.AndroidProject
import com.github.michaelbull.result.toResultOr
import com.intellij.openapi.project.Project

interface AndroidProjectRequirement {

    class Impl : AndroidProjectRequirement {

        override lateinit var androidProject: AndroidProject

        override fun RequirementsScope.requireAndroidProject(project: Project) {
            androidProject = AndroidProject(project)
                .toResultOr { RequirementError.NoAndroidProject }
                .bind()
        }
    }

    var androidProject: AndroidProject

    fun RequirementsScope.requireAndroidProject(project: Project)
}
