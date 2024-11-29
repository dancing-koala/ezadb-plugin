package com.cobr.ezadb

import com.cobr.ezadb.settings.Preferences
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.components.Service
import com.intellij.openapi.project.Project
import kotlinx.coroutines.CoroutineScope

@Service(Service.Level.PROJECT)
class MainService(
    private val project: Project,
    unusedScope: CoroutineScope
) {
    val preferences: Preferences by lazy {
        Preferences(PropertiesComponent.getInstance(project))
    }
}