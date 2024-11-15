package com.cobr.quickadb.requirements

import com.github.michaelbull.result.Result
import com.github.michaelbull.result.binding
import com.intellij.openapi.actionSystem.AnActionEvent

class ProjectAppRequirements(
    event: AnActionEvent
) : Requirements,
    ProjectRequirement by ProjectRequirement.Impl(event),
    AndroidProjectRequirement by AndroidProjectRequirement.Impl(),
    ADBRequirement by ADBRequirement.Impl(),
    DeviceRequirement by DeviceRequirement.Impl(),
    NoGradleSyncRequirement by NoGradleSyncRequirement.Impl(),
    CurrentApplicationIdRequirement by CurrentApplicationIdRequirement.Impl(),
    AppInstalledRequirement by AppInstalledRequirement.Impl() {

    override fun check(): Result<Unit, RequirementError> = binding {
        requireProject()
        requireNoGradleSync(project)
        requireAndroidProject(project)
        requireADB()
        requireDevice(project, bridge)
        requireCurrentApplicationId(androidProject)
        requireAppInstalled(applicationId, device)
    }
}