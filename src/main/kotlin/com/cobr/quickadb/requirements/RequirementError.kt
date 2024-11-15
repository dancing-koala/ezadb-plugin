package com.cobr.quickadb.requirements

sealed interface RequirementError {
    data object NoProject : RequirementError
    data object NoAndroidProject : RequirementError
    data object NoADB : RequirementError
    data object NoDevice : RequirementError
    data object GradleSyncRunning : RequirementError
    data object NoApplicationId : RequirementError
    data object AppNotInstalled : RequirementError
}

val RequirementError.label: String
    get() = when (this) {
        RequirementError.GradleSyncRunning -> "Gradle Sync running, please retry later."
        RequirementError.NoADB -> "No AndroidDebugBridge found."
        RequirementError.NoAndroidProject -> "No Android project found."
        RequirementError.NoApplicationId -> "No application id found in project."
        RequirementError.NoDevice -> "No connected device found."
        RequirementError.NoProject -> "No project found."
        RequirementError.AppNotInstalled -> "App not installed."
    }