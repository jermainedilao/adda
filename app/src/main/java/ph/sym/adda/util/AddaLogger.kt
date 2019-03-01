package ph.sym.adda.util

import android.util.Log


class AddaLogger {
    companion object {
        fun d(tag: String, message: String) {
            Log.d(tag, message)
        }

        fun e(tag: String, message: String, throwable: Throwable) {
            Log.e(tag, message, throwable)
        }
    }
}