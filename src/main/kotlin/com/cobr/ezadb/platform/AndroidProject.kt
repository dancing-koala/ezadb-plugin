package com.cobr.ezadb.platform

import com.android.ddmlib.IDevice
import com.android.tools.idea.model.AndroidModel
import com.android.tools.idea.projectsystem.AndroidModuleSystem
import com.android.tools.idea.projectsystem.androidProjectType
import com.android.tools.idea.projectsystem.getHolderModule
import com.android.tools.idea.run.activity.ActivityLocator.ActivityLocatorException
import com.android.tools.idea.run.activity.DefaultActivityLocator
import com.android.tools.idea.util.androidFacet
import com.intellij.facet.ProjectFacetManager
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.roots.ui.configuration.ChooseModulesDialog
import com.intellij.openapi.util.ThrowableComputable
import com.intellij.util.ui.UIUtil
import org.jetbrains.android.facet.AndroidFacet
import java.awt.Component
import java.awt.Dimension
import javax.swing.JTable

class AndroidProject(
    private val project: Project
) {

    private val projectFacetManager = ProjectFacetManager.getInstance(project)

    private val facet: AndroidFacet?
        get() = projectFacetManager
            .getFacets(AndroidFacet.ID)
            .let { getFacet(it) }

    val applicationId: String
        get() = facet?.let { AndroidModel.get(it)?.applicationId } ?: ""

    @Throws(ActivityLocatorException::class)
    fun getDefaultActivityName(device: IDevice): String =
        ApplicationManager.getApplication()
            .runReadAction(ThrowableComputable<String, ActivityLocatorException?> {
                DefaultActivityLocator(facet!!).getQualifiedActivityName(device)
            })

    private fun getFacet(facets: List<AndroidFacet>): AndroidFacet? {
        val appFacets = facets
            .map { it.module.getHolderModule() }
            .filter { it.androidProjectType() == AndroidModuleSystem.Type.TYPE_APP }
            .mapNotNull { it.androidFacet }
            .distinct()

        return if (appFacets.size > 1) {
            showDialogForFacets(project, appFacets)
        } else {
            appFacets.first()
        }
    }

    private fun showDialogForFacets(
        project: Project,
        facets: List<AndroidFacet>
    ): AndroidFacet? {
        val modules = facets.map { it.module }

        val selectedModule = showDialog(project, modules)
            ?: return null

        return facets[modules.indexOf(selectedModule)]
    }

    private fun showDialog(
        project: Project,
        modules: List<Module>,
    ): Module? {
        with(ChooseModulesDialog(project, modules, "Choose Module", "")) {
            setSingleSelectionMode()
            getSizeForTableContainer(preferredFocusedComponent)?.let {
                // Set the height to 0 to allow the dialog to resize itself to fit the content.
                setSize(it.width, 0)
            }
            return showAndGetResult().firstOrNull()
        }
    }

    // Fix an issue where the modules dialog is not wide enough to display the whole module name.
    // This code is lifted from com.intellij.openapi.ui.impl.DialogWrapperPeerImpl.MyDialog.getSizeForTableContainer
    private fun getSizeForTableContainer(component: Component?): Dimension? {
        component ?: return null

        val tables = UIUtil.uiTraverser(component).filter(JTable::class.java)

        if (tables.isEmpty) {
            return null
        }

        val size = component.preferredSize

        tables.forEach { table ->
            val tableSize = table.preferredSize
            size.width = size.width.coerceAtLeast(tableSize.width)
        }

        size.width = size.width.coerceIn(600, 1000)

        return size
    }

    override fun toString(): String = "AndroidProject(project=${project.name})"
}