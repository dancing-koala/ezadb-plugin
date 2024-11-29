package com.cobr.ezadb.requirements

import com.android.ddmlib.IDevice
import com.cobr.ezadb.commands.CommandFactory
import com.github.michaelbull.result.mapError

interface AppInstalledRequirement {
    class Impl : AppInstalledRequirement {
        override fun RequirementsScope.requireAppInstalled(
            applicationId: String,
            device: IDevice
        ) {
            CommandFactory.packageVersionName(applicationId)
                .runOn(device)
                .mapError { RequirementError.AppNotInstalled }
                .bind()
        }
    }

    fun RequirementsScope.requireAppInstalled(
        applicationId: String,
        device: IDevice
    )
}