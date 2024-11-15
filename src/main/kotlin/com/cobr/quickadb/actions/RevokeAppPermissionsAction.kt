package com.cobr.quickadb.actions

import com.android.ddmlib.IDevice
import com.cobr.quickadb.commands.CommandFactory
import com.cobr.quickadb.platform.log.Logger
import com.cobr.quickadb.platform.log.logger
import com.cobr.quickadb.requirements.ProjectAppRequirements
import com.github.michaelbull.result.*
import kotlin.collections.fold

class RevokeAppPermissionsAction : ProjectAppAction() {

    override val log: Logger by logger()

    override val action: ProjectAppRequirements.() -> Result<String, String> = {
        CommandFactory.dumpSysPackage(applicationId, "permission")
            .runOn(device)
            .map(::extractGrantedPermissions)
            .flatMap { runRevokePermissionCommands(it, applicationId, device) }
            .mapEither(
                success = { "Success: revoked permissions for <$applicationId>" },
                failure = { "Failure: could not revoke permissions for <$applicationId>" }
            )
    }

    private fun extractGrantedPermissions(data: List<String>): List<String> =
        data.asSequence()
            .filter { it.contains("granted=true") }
            .mapNotNull { it.trim().split(":").firstOrNull() }
            .toList()

    private fun runRevokePermissionCommands(
        permissions: List<String>,
        applicationId: String,
        device: IDevice
    ): Result<Unit, RuntimeException> {
        val initialValue: Result<Unit, RuntimeException> = Ok(Unit)

        return permissions.fold(initialValue) { acc, permission ->
            acc.andThen {
                CommandFactory
                    .revokePermission(applicationId, permission)
                    .runOn(device)
                    .map {}
            }
        }
    }
}