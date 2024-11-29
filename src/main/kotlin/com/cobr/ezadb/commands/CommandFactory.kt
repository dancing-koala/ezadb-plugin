package com.cobr.ezadb.commands

import com.android.ddmlib.IDevice
import com.cobr.ezadb.commands.receivers.DumbReceiver
import com.cobr.ezadb.platform.AndroidProject

object CommandFactory {

    private val notEmptySuccessCheck: ((receiver: DumbReceiver) -> Boolean) = {
        it.adbOutputLines.isNotEmpty()
    }

    private fun pipeGrep(term: String): String = " | grep '$term'"

    private fun startActivity(
        packageName: String,
        activityName: String,
        withDebugger: Boolean
    ): String {
        val debuggerFlag = if (withDebugger) "-D " else ""
        return "am start $debuggerFlag-n $packageName/$activityName"
    }

    fun dumpSysPackage(applicationId: String, grep: String? = null): Command =
        ShellCommand(
            shell = "dumpsys package $applicationId ${grep?.let(::pipeGrep) ?: ""}",
            successCheck = notEmptySuccessCheck
        )

    fun packageVersionName(applicationId: String): Command =
        dumpSysPackage(applicationId, grep = "versionName")

    fun startApp(
        androidProject: AndroidProject,
        device: IDevice,
        applicationId: String,
        withDebugger: Boolean = false
    ): Command {
        val activityName = androidProject
            .getDefaultActivityName(device)

        return ShellCommand(
            shell = startActivity(applicationId, activityName, withDebugger),
            successCheck = notEmptySuccessCheck
        )
    }

    fun wipeData(applicationId: String): Command =
        ShellCommand(shell = "pm clear $applicationId")

    fun revokePermission(applicationId: String, permission: String): Command =
        ShellCommand(
            shell = "pm revoke $applicationId $permission",
            successCheck = { true }
        )
}