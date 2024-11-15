package com.cobr.quickadb.requirements

import com.android.ddmlib.IDevice
import com.cobr.quickadb.commands.CommandFactory
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