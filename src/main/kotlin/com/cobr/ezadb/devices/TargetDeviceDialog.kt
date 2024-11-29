package com.cobr.ezadb.devices

import com.android.ddmlib.IDevice
import com.cobr.ezadb.settings.Preferences
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.dsl.builder.panel
import javax.swing.JComponent

class TargetDeviceDialog(
    project: Project,
    private val devices: List<IDevice>,
    private val preferences: Preferences
) : DialogWrapper(project, true) {

    private var selectedDevice: IDevice? = null
    private var useSameTarget: Boolean = false

    init {
        title = "Target Device"
        init()
    }

    override fun createCenterPanel(): JComponent {
        return panel {
            buttonsGroup("Devices") {
                devices.forEach { device ->
                    row {
                        val text = with(device) { "$serialNumber ($name)" }
                        radioButton(text)
                            .onChanged {
                                if (it.isSelected) {
                                    selectedDevice = device
                                }
                            }
                    }
                }
            }
            row {
                checkBox("Use this device for next actions")
                    .applyToComponent { isSelected = useSameTarget }
                    .onChanged { useSameTarget = it.isSelected }
            }
        }
    }

    fun showAndGetResult(): IDevice? {
        if (devices.isEmpty()) {
            return null
        }

        return if (showAndGet()) {
            selectedDevice?.also {
                if (useSameTarget) {
                    preferences.saveTargetSerial(it.serialNumber)
                }
            }
        } else {
            null
        }
    }
}