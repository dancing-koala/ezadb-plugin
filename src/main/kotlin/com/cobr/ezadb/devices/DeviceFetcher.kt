package com.cobr.ezadb.devices

import com.android.ddmlib.AndroidDebugBridge
import com.android.ddmlib.IDevice
import com.cobr.ezadb.MainService
import com.cobr.ezadb.platform.Notifier
import com.intellij.openapi.components.service
import com.intellij.openapi.project.Project

fun getDevice(
    project: Project,
    bridge: AndroidDebugBridge,
): IDevice? {
    val devices = bridge.devices.sortedBy { it.serialNumber }

    if (devices.isEmpty()) {
        Notifier.info("No devices found.")
        return null
    }

    val preferences = project.service<MainService>().preferences
    val lastTargetSerial = preferences.lastTargetSerial

    if (devices.size == 1) {
        val device = devices.first()

        if (device.serialNumber != lastTargetSerial) {
            preferences.clearTargetSerial()
        }

        return device
    }

    val lastTarget = lastTargetSerial?.let { serial ->
        devices.firstOrNull { it.serialNumber == serial }
    }

    if (lastTarget != null) {
        return lastTarget
    }

    if (lastTargetSerial != null) {
        preferences.clearTargetSerial()
    }

    val result = TargetDeviceDialog(project, devices, preferences)
        .showAndGetResult()

    return result.also {
        it ?: Notifier.info("No device selected.")
    }
}