package com.cobr.quickadb.requirements

import com.android.ddmlib.AndroidDebugBridge
import com.github.michaelbull.result.toResultOr

interface ADBRequirement {
    class Impl : ADBRequirement {
        override lateinit var bridge: AndroidDebugBridge

        override fun RequirementsScope.requireADB() {
            bridge = AndroidDebugBridge.getBridge()
                .toResultOr { RequirementError.NoADB }
                .bind()
        }
    }

    var bridge: AndroidDebugBridge

    fun RequirementsScope.requireADB()
}