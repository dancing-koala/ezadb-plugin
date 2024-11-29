package com.cobr.ezadb.actions

import com.cobr.ezadb.platform.log.Logger
import com.cobr.ezadb.platform.log.logger
import com.cobr.ezadb.requirements.ProjectAppRequirements
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

class KillAppAction : ProjectAppAction() {

    override val log: Logger by logger()

    override val action: ProjectAppRequirements.() -> Result<String, String> = {
        device.forceStop(applicationId)
        Ok("Success: stopped <$applicationId>")
    }
}