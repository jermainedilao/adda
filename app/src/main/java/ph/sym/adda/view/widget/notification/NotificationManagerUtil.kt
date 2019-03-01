package ph.sym.adda.view.widget.notification

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import ph.sym.adda.R


class NotificationManagerUtil {
    companion object {
        val NOTIFICATION_ID = 1921;
        val REQUEST_ID_NOTIFICATION = 9991;

        fun showNotification(context: Context) {
            val bigTextStyle = NotificationCompat.BigTextStyle()
                    .bigText(context.getString(R.string.adda_notification_description))

            val builder = NotificationCompat.Builder(context)
                    .setStyle(bigTextStyle)
                    .setOngoing(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setPriority(Notification.PRIORITY_MAX)
                    .addAction(buildStopAction(context))

            builder.setAutoCancel(false)

            val notification = builder.build()
            val notificationManagerCompat = NotificationManagerCompat.from(context)

            dismissNotification(context)
            notificationManagerCompat.notify(NOTIFICATION_ID, notification)
        }

        fun dismissNotification(context: Context) {
            NotificationManagerCompat.from(context).cancel(NOTIFICATION_ID)
        }

        private fun buildStopAction(context: Context): NotificationCompat.Action? {
            val intent = Intent(context, StopNotificationService::class.java)

            val pendingIntent = PendingIntent.getService(
                    context,
                    REQUEST_ID_NOTIFICATION,
                    intent,
                    PendingIntent.FLAG_ONE_SHOT
            )

            return NotificationCompat.Action.Builder(
                    0,
                    context.getString(R.string.turn_adda_mode_off),
                    pendingIntent
            ).build()
        }
    }
}