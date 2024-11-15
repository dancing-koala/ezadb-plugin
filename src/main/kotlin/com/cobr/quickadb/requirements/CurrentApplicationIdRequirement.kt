package com.cobr.quickadb.requirements

import com.cobr.quickadb.platform.AndroidProject
import com.github.michaelbull.result.toResultOr

interface CurrentApplicationIdRequirement {
    class Impl : CurrentApplicationIdRequirement {
        override lateinit var applicationId: String

        override fun RequirementsScope.requireCurrentApplicationId(
            androidProject: AndroidProject
        ) {
            applicationId =
                androidProject.applicationId.takeIf { it.isNotBlank() }
                    .toResultOr { RequirementError.NoApplicationId }
                    .bind()
        }
    }

    var applicationId: String

    fun RequirementsScope.requireCurrentApplicationId(
        androidProject: AndroidProject
    )
}