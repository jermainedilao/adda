package ph.sym.adda.util.addastate

import android.content.Context
import android.content.SharedPreferences


class AddaPrefUtil {
    companion object {
        val PREF_ADDA = "pref:adda"
        val PREF_ADDA_STATE = "pref:adda_state"
        val PREF_AUTO_REPLY_STATE = "pref:auto_reply_state"

        fun storeAddaState(context: Context, state: Boolean) {
            val editor = getEditor(context)
            editor.putBoolean(PREF_ADDA_STATE, state)
            editor.apply()
        }

        fun getAddaState(context: Context): Boolean {
            return getSharedPreferences(context).getBoolean(PREF_ADDA_STATE, false)
        }

        fun storeAutoReplyState(context: Context, state: Boolean) {
            val editor = getEditor(context)
            editor.putBoolean(PREF_AUTO_REPLY_STATE, state)
            editor.apply()
        }

        fun getAutoReplyState(context: Context): Boolean {
            return getSharedPreferences(context).getBoolean(PREF_AUTO_REPLY_STATE, false)
        }

        private fun getEditor(context: Context): SharedPreferences.Editor {
            return getSharedPreferences(context).edit()
        }

        private fun getSharedPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences(PREF_ADDA, Context.MODE_PRIVATE)
        }
    }
}