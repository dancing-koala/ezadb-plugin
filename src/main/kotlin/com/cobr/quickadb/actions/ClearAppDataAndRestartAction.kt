package com.cobr.quickadb.actions

import com.cobr.quickadb.commands.CommandFactory
import com.cobr.quickadb.commands.chainCommands
import com.cobr.quickadb.platform.log.Logger
import com.cobr.quickadb.platform.log.logger
import com.cobr.quickadb.requirements.ProjectAppRequirements
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.mapEither

class ClearAppDataAndRestartAction : ProjectAppAction() {

    override val log: Logger by logger()

    override val action: ProjectAppRequirements.() -> Result<String, String> = {
        chainCommands(
            device,
            CommandFactory.wipeData(applicationId),
            CommandFactory.startApp(androidProject, device, applicationId)
        ).mapEither(
            success = { "Success: wiped and restarted <$applicationId>" },
            failure = { "Failure: could not wipe or restart <$applicationId>" },
        )
    }
}