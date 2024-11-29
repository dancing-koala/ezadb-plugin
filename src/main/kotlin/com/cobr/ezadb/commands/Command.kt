package com.cobr.ezadb.commands

import com.android.ddmlib.IDevice
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.andThen

interface Command {
    fun runOn(device: IDevice): Result<List<String>, RuntimeException>
}

fun chainCommands(
    device: IDevice,
    vararg commands: Command
): Result<List<String>, RuntimeException> =
    commands.fold(Ok(emptyList())) { acc, item ->
        acc.andThen { item.runOn(device) }
    }
