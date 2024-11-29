package com.cobr.ezadb.actions

import com.cobr.ezadb.platform.Notifier
import com.cobr.ezadb.platform.log.Logger
import com.cobr.ezadb.requirements.Requirements
import com.cobr.ezadb.requirements.label
import com.github.michaelbull.result.*
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent

abstract class AnAppAction<R : Requirements> : AnAction() {

    abstract val log: Logger

    abstract val action: R.() -> Result<String, String>

    abstract fun createRequirements(event: AnActionEvent): R

    override fun actionPerformed(event: AnActionEvent) {
        val requirements = createRequirements(event)

        requirements.check()
            .mapError { it.label }
            .andThen { action.invoke(requirements) }
            .onSuccess(Notifier::info)
            .onFailure(Notifier::error)
    }
}

