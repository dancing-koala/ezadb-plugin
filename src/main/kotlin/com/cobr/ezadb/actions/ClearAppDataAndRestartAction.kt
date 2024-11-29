package com.cobr.ezadb.actions

import com.cobr.ezadb.commands.CommandFactory
import com.cobr.ezadb.commands.chainCommands
import com.cobr.ezadb.platform.log.Logger
import com.cobr.ezadb.platform.log.logger
import com.cobr.ezadb.requirements.ProjectAppRequirements
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