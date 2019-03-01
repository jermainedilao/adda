package ph.sym.adda.util.activityutil

import android.content.Context
import android.content.Intent
import ph.sym.adda.view.mainscreen.MainScreenActivity
import ph.sym.adda.view.secondscreen.SecondScreenActivity


class ActivityUtil {
    companion object {
        fun startMainScreenActivityClrTsk(context: Context) {
            val intent = Intent(context, MainScreenActivity::class.java)
            context.startActivity(intent)
        }

        fun startSecondScreenActivityClrTsk(context: Context) {
            val intent = Intent(context, SecondScreenActivity::class.java)
            context.startActivity(intent)
        }
    }
}