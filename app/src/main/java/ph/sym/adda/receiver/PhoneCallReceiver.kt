package ph.sym.adda.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.telephony.PhoneStateListener
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import ph.sym.adda.R
import ph.sym.adda.util.AddaLogger
import ph.sym.adda.util.addastate.AddaPrefUtil
import ph.sym.adda.util.phonecallutil.SmsUtil
import java.lang.reflect.Method


class PhoneCallReceiver: BroadcastReceiver() {
    val TAG = "PhoneCallReceiver"

    // https://stackoverflow.com/a/23151161/5285687
    override fun onReceive(context: Context?, intent: Intent?) {
        val telephonyManager = context?.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        telephonyManager.listen(object : PhoneStateListener() {
            override fun onCallStateChanged(state: Int, incomingNumber: String?) {
                super.onCallStateChanged(state, incomingNumber)

                val shouldExecute = AddaPrefUtil.getAddaState(context)
                        && incomingNumber?.isNotBlank() as Boolean
                        && state == TelephonyManager.CALL_STATE_RINGING

                if (shouldExecute) {
                    AddaLogger.d(TAG, incomingNumber ?: "")
                    endCall(telephonyManager)

                    if (AddaPrefUtil.getAutoReplyState(context) && !SmsUtil.flag) {
                        SmsUtil.flag = true
                        sendSms(context, incomingNumber)
                    }
                }
            }
        }, PhoneStateListener.LISTEN_CALL_STATE)
    }

    private fun endCall(telephonyManager: TelephonyManager) {
        val clss = Class.forName(telephonyManager.javaClass.name)
        val method: Method = clss.getDeclaredMethod("getITelephony")
        method.isAccessible = true

        val telephonyInterface = method.invoke(telephonyManager)

        val telephonyInterfaceClass = Class.forName(telephonyInterface.javaClass.name)
        val methodEndCall = telephonyInterfaceClass.getDeclaredMethod("endCall")

        methodEndCall.invoke(telephonyInterface)
    }

    private fun sendSms(context: Context, incomingNumber: String?) {
        AddaLogger.d(TAG, "sendSms")

        val smsManager = SmsManager.getDefault()
        val msg = context.getString(R.string.adda_sms_message)
        smsManager.sendTextMessage(incomingNumber, "", msg, null, null)

        object : CountDownTimer(5000, 1000) {
            override fun onFinish() {
                SmsUtil.flag = false
            }

            override fun onTick(p0: Long) {
            }
        }.start()
    }
}