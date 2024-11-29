package com.cobr.ezadb.platform.log

class SimpleLogger(private val name: String) : Logger {

    override fun error(message: String) = println("[ERR] $name: $message")

    override fun fatal(message: String): Nothing {
        throw Exception("[FATAL] $name: $message")
    }

    override fun log(message: String) = println("[INFO] $name: $message")

    override fun warning(message: String) = println("[WARN] $name: $message")
}

