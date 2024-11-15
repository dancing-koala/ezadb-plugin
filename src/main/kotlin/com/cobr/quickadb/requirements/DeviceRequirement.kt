package com.cobr.quickadb.requirements

import com.android.ddmlib.AndroidDebugBridge
import com.android.ddmlib.IDevice
import com.cobr.quickadb.devices.getDevice
import com.github.michaelbull.result.toResultOr
import com.intellij.openapi.project.Project

interface DeviceRequirement {
    class Impl : DeviceRequirement {
        override lateinit var device: IDevice

        override fun RequirementsScope.requireDevice(
            project: Project,
            bridge: AndroidDebugBridge,
        ) {
            device = getDevice(project, bridge)
                .toResultOr { RequirementError.NoDevice }
                .bind()
        }
    }

    var device: IDevice

    fun RequirementsScope.requireDevice(
        project: Project,
        bridge: AndroidDebugBridge
    )
}