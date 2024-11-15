package com.cobr.quickadb.requirements

import com.cobr.quickadb.platform.isGradleSyncInProgress
import com.github.michaelbull.result.Err
import com.intellij.openapi.project.Project

interface NoGradleSyncRequirement {
    class Impl : NoGradleSyncRequirement {

        @Suppress("IMPLICIT_NOTHING_TYPE_ARGUMENT_IN_RETURN_POSITION")
        override fun RequirementsScope.requireNoGradleSync(project: Project) {
            if (isGradleSyncInProgress(project)) {
                Err(RequirementError.GradleSyncRunning).bind()
            }
        }
    }

    fun RequirementsScope.requireNoGradleSync(project: Project)
}