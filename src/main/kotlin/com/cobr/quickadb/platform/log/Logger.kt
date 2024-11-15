package com.cobr.quickadb.platform.log

interface Logger {
    fun error(message: String)
    fun fatal(message: String): Nothing
    fun log(message: String)
    fun warning(message: String)
}