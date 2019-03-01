package ph.sym.adda.util.ringer

import android.content.Context
import android.media.AudioManager
import ph.sym.adda.util.AddaLogger


class RingerUtil {
    companion object {
        val TAG = "RingerUtil"

        fun setRingerMode(context: Context, isSilent: Boolean) {
            try {
                val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

                var ringerMode: Int?
                if (isSilent) {
                    ringerMode = AudioManager.RINGER_MODE_SILENT
                } else {
                    ringerMode = AudioManager.RINGER_MODE_NORMAL
                }

                audioManager.ringerMode = ringerMode
            } catch (e: Exception) {
                AddaLogger.e(TAG, "error", e)
            }
        }
    }
}