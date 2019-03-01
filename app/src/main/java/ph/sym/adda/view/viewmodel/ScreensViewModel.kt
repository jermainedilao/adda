package ph.sym.adda.view.viewmodel

import android.content.Context
import ph.sym.adda.util.addastate.AddaPrefUtil
import ph.sym.adda.util.ringer.RingerUtil
import ph.sym.adda.view.widget.notification.NotificationManagerUtil


class ScreensViewModel {

    var backCount = 0

    fun turnOnAdda(context: Context) {
        showNotification(context, true)
        storeAddaState(context, true)
        setRingerMode(context, true)
    }

    fun turnOffAdda(context: Context) {
        showNotification(context, false)
        storeAddaState(context, false)
        setRingerMode(context, false)
    }

    private fun storeAddaState(context: Context, state: Boolean) {
        AddaPrefUtil.storeAddaState(context, state)
    }

    fun getAddaState(context: Context): Boolean {
        return AddaPrefUtil.getAddaState(context)
    }

    fun storeAutoReplyState(context: Context, state: Boolean) {
        AddaPrefUtil.storeAutoReplyState(context, state)
    }

    fun getAutoReplyState(context: Context): Boolean {
        return AddaPrefUtil.getAutoReplyState(context)
    }

    fun onBackPressed() {
        backCount++
    }

    fun shouldExit(): Boolean {
        return backCount > 1
    }

    private fun setRingerMode(context: Context, isSilent: Boolean) {
        RingerUtil.setRingerMode(context, isSilent)
    }

    private fun showNotification(context: Context, isShow: Boolean) {
        if (isShow) {
            NotificationManagerUtil.showNotification(context)
        } else {
            NotificationManagerUtil.dismissNotification(context)
        }
    }
}