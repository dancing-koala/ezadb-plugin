package com.cobr.quickadb.commands.receivers

import com.android.ddmlib.MultiLineReceiver

class DumbReceiver : MultiLineReceiver() {

    val adbOutputLines: MutableList<String> = ArrayList()

    override fun processNewLines(lines: Array<String>) {
        adbOutputLines.addAll(lines)
    }

    override fun isCancelled() = false
}
