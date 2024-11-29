package com.cobr.ezadb.commands

import com.android.ddmlib.IDevice
import com.cobr.ezadb.commands.receivers.DumbReceiver
import com.cobr.ezadb.platform.log.logger
import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

data class ShellCommand(
    val shell: String,
    val timoutSeconds: Long = 15L,
    val successCheck: ((receiver: DumbReceiver) -> Boolean)? = null,
) : Command {
    companion object {
        private const val SUCCESS_OUTPUT = "Success" //$NON-NLS-1$

        @Suppress("RegExpRedundantEscape")
        private val FAILURE_PATTERN =
            Pattern.compile("Failure\\s+\\[(.*)\\]") //$NON-NLS-1$
    }

    private val log by logger()

    override fun runOn(device: IDevice): Result<List<String>, RuntimeException> {
        log.log("execute: $device > $shell")

        val receiver = DumbReceiver()

        device.executeShellCommand(
            shell,
            receiver,
            timoutSeconds,
            TimeUnit.SECONDS
        )

        return result(receiver).also { log.log("execute: result = $it") }
    }

    private fun defaultSuccessCheck(receiver: DumbReceiver): Boolean {
        var errorMessage: String? = null

        receiver.adbOutputLines.forEach { line ->
            if (line.isNotEmpty()) {
                errorMessage = if (line.startsWith(SUCCESS_OUTPUT)) {
                    null
                } else {
                    val m = FAILURE_PATTERN.matcher(line)
                    if (m.matches()) {
                        m.group(1)
                    } else {
                        "Unknown failure"
                    }
                }
            }
        }

        log.error("defaultSuccessCheck: errorMessage = $errorMessage")

        return errorMessage == null
    }

    private fun result(receiver: DumbReceiver): Result<List<String>, RuntimeException> {
        val check = successCheck ?: ::defaultSuccessCheck

        return if (check.invoke(receiver)) {
            Ok(receiver.adbOutputLines)
        } else {
            Err(
                RuntimeException(
                    buildString {
                        append("Command result: ")
                        append(receiver.adbOutputLines.joinToString("\n"))
                    }
                )
            )
        }
    }
}

