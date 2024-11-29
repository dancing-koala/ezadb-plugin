package com.cobr.ezadb.actions

import com.cobr.ezadb.platform.log.Logger
import com.cobr.ezadb.platform.log.logger
import com.cobr.ezadb.requirements.ProjectAppRequirements
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

class UninstallAppAction : ProjectAppAction() {

    override val log: Logger by logger()

    override val action: ProjectAppRequirements.() -> Result<String, String> = {
        device.uninstallPackage(applicationId)
            ?.let(::Err)
            ?: Ok("Success: uninstalled <$applicationId>")
    }
}