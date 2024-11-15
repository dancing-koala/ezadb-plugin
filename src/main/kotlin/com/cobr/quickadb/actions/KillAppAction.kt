package com.cobr.quickadb.actions

import com.cobr.quickadb.platform.log.Logger
import com.cobr.quickadb.platform.log.logger
import com.cobr.quickadb.requirements.ProjectAppRequirements
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result

class KillAppAction : ProjectAppAction() {

    override val log: Logger by logger()

    override val action: ProjectAppRequirements.() -> Result<String, String> = {
        device.forceStop(applicationId)
        Ok("Success: stopped <$applicationId>")
    }
}