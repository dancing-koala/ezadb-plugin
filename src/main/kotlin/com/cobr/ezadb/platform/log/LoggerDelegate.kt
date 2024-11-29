package com.cobr.ezadb.platform.log

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class LoggerDelegate(name: String) : ReadWriteProperty<Any?, Logger> {

    private var logger: Logger = SimpleLogger(name)

    override fun getValue(thisRef: Any?, property: KProperty<*>): Logger =
        logger

    override fun setValue(
        thisRef: Any?,
        property: KProperty<*>,
        value: Logger
    ) {
        logger = value
    }
}

inline fun <reified T : Any> T.logger(
    customName: String? = null
): LoggerDelegate = if (customName != null) {
    LoggerDelegate(customName)
} else {
    LoggerDelegate(T::class.java.simpleName)
}
