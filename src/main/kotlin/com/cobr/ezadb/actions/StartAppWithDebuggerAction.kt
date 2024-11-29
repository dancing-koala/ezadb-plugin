package com.cobr.ezadb.actions

import com.cobr.ezadb.commands.CommandFactory
import com.cobr.ezadb.platform.log.Logger
import com.cobr.ezadb.platform.log.logger
import com.cobr.ezadb.requirements.ProjectAppRequirements
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapEither

class StartAppWithDebuggerAction : ProjectAppAction() {

    override val log: Logger by logger()

    override val action: ProjectAppRequirements.() -> Result<String, String> = {
        CommandFactory.startApp(androidProject, device, applicationId, true)
            .runOn(device)
            .mapEither(
                success = { "Success: started <$applicationId>" },
                failure = { "Failure: could not start <$applicationId>" }
            )
    }
}