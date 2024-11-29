package com.cobr.ezadb.platform

import com.android.tools.idea.gradle.project.sync.GradleSyncState
import com.intellij.openapi.project.Project

fun isGradleSyncInProgress(project: Project): Boolean {
    return try {
        GradleSyncState.getInstance(project).isSyncInProgress
    } catch (t: Throwable) {
        Notifier.info("Couldn't determine if a gradle sync is in progress")
        true
    }
}