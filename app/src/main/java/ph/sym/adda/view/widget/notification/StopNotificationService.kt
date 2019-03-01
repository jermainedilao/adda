package ph.sym.adda.view.widget.notification

import android.app.IntentService
import android.content.Intent
import android.support.v4.app.NotificationManagerCompat
import org.greenrobot.eventbus.EventBus
import ph.sym.adda.eventbus.TurnOffAddaEvent
import ph.sym.adda.util.addastate.AddaPrefUtil
import ph.sym.adda.util.ringer.RingerUtil


class StopNotificationService : IntentService("StopNotificationService") {
    override fun onHandleIntent(intent: Intent?) {
        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.cancel(NotificationManagerUtil.NOTIFICATION_ID)

        AddaPrefUtil.storeAddaState(this, false)
        RingerUtil.setRingerMode(this, false)

        val intent = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
        sendBroadcast(intent)

        EventBus.getDefault().postSticky(TurnOffAddaEvent())
    }
}