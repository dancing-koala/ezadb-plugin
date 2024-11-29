package com.cobr.ezadb.settings

import com.intellij.ide.util.PropertiesComponent

class Preferences(
    private val propertiesComponent: PropertiesComponent
) {
    companion object {
        private const val LAST_TARGET_SERIAL_KEY =
            "com.cobr.ezadb.settings.LAST_TARGET_SERIAL"
    }

    val lastTargetSerial: String?
        get() = propertiesComponent.getValue(LAST_TARGET_SERIAL_KEY)

    fun saveTargetSerial(target: String) {
        propertiesComponent.setValue(LAST_TARGET_SERIAL_KEY, target)
    }

    fun clearTargetSerial() {
        propertiesComponent.setValue(LAST_TARGET_SERIAL_KEY, null)
    }
}
