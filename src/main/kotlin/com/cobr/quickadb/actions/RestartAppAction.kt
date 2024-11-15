package com.cobr.quickadb.actions

import com.cobr.quickadb.commands.CommandFactory
import com.cobr.quickadb.platform.log.Logger
import com.cobr.quickadb.platform.log.logger
import com.cobr.quickadb.requirements.ProjectAppRequirements
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapEither

class RestartAppAction : ProjectAppAction() {

    override val log: Logger by logger()

    override val action: ProjectAppRequirements.() -> Result<String, String> = {
        device.forceStop(applicationId)

        CommandFactory.startApp(androidProject, device, applicationId)
            .runOn(device)
            .mapEither(
                success = { "Success: restarted <$applicationId>" },
                failure = { "Failure: could not restart <$applicationId>" }
            )
    }
}