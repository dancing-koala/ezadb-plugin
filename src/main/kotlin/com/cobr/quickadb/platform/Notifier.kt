package com.cobr.quickadb.platform

import com.cobr.quickadb.platform.log.logger
import com.intellij.notification.NotificationGroup
import com.intellij.notification.NotificationGroupManager
import com.intellij.notification.NotificationType

object Notifier {
    private val log by logger()

    private val INFO by lazy {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("QuickADB [log]")
    }

    private val ERRORS by lazy {
        NotificationGroupManager.getInstance()
            .getNotificationGroup("QuickADB [log]")
    }

    fun info(message: String) =
        sendNotification(message, NotificationType.INFORMATION, INFO)

    fun error(message: String) =
        sendNotification(message, NotificationType.ERROR, ERRORS)

    private fun sendNotification(
        message: String,
        notificationType: NotificationType,
        notificationGroup: NotificationGroup
    ) {
        log.log("sendNotification: [$notificationType] $message")

        notificationGroup.createNotification(
            "QuickADB",
            message,
            notificationType,
        ).notify(null)
    }
}