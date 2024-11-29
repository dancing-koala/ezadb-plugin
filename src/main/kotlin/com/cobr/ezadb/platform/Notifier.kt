package com.cobr.ezadb.platform

import com.cobr.ezadb.platform.log.logger
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType

object Notifier {
    private val log by logger()

    private val INFO by lazy {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("ezADB [log]")
    }

    private val ERRORS by lazy {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("ezADB [log]")
    }

    fun info(message: String) =
        sendNotification(message, NotificationType.INFORMATION, INFO)

    fun error(message: String) =
        sendNotification(message, NotificationType.ERROR, ERRORS)

    @Suppress("DialogTitleCapitalization")
    private fun sendNotification(
        message: String,
        notificationType: NotificationType,
        notificationGroup: NotificationGroup
    ) {
        log.log("sendNotification: [$notificationType] $message")

        notificationGroup.createNotification(
            "ezADB",
            message,
            notificationType,
        ).notify(null)
    }
}