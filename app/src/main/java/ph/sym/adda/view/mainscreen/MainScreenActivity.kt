package ph.sym.adda.view.mainscreen

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main_screen.*
import org.greenrobot.eventbus.EventBus
import ph.sym.adda.R
import ph.sym.adda.eventbus.TurnOffAddaEvent
import ph.sym.adda.extensions.showAlertDialog
import ph.sym.adda.util.AddaLogger
import ph.sym.adda.util.PermissionsUtil
import ph.sym.adda.util.activityutil.ActivityUtil
import ph.sym.adda.view.viewmodel.ScreensViewModel

class MainScreenActivity : AppCompatActivity() {

    private val TAG = "MainScreenActivity"
    private val REQUEST_CODE_PERMISSIONS = 1000

    private var viewModel: ScreensViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        // to make sure this event is removed
        EventBus.getDefault().removeStickyEvent(TurnOffAddaEvent::javaClass)

        viewModel = ScreensViewModel()

        if (viewModel?.getAddaState(this) as Boolean) {
            startSecondScreen()
        }
    }

    override fun onStart() {
        super.onStart()

        if (!PermissionsUtil.hasCompletePermissions(this)) {
            AddaLogger.d(TAG, "hasCompletePermissions is FALSE")
            ActivityCompat.requestPermissions(
                    this,
                    PermissionsUtil.getNonGrantedPermissionsList(this).toTypedArray(),
                    REQUEST_CODE_PERMISSIONS
            )
        } else if (shouldStartDoNotDisturbActivity()) {
            startDoNotDisturbAccessActivity()
        } else {
            AddaLogger.d(TAG, "hasCompletePermissions is TRUE")
            manageViews()
        }
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_CODE_PERMISSIONS -> {
                val map: MutableMap<String, Int> = mutableMapOf()
                map[PermissionsUtil.PERMISSION_READ_PHONE_STATE] = PackageManager.PERMISSION_GRANTED
                map[PermissionsUtil.PERMISSION_SEND_SMS] = PackageManager.PERMISSION_GRANTED
                map[PermissionsUtil.PERMISSION_CALL_PHONE] = PackageManager.PERMISSION_GRANTED
                map[PermissionsUtil.PERMISSION_NOTIFICATION_POLICY] = PackageManager.PERMISSION_GRANTED

                if (grantResults.isNotEmpty()) {
                    for ((index, _) in permissions.withIndex()) {
                        map[permissions[index]] = grantResults[index]
                    }
                }

                if (map[PermissionsUtil.PERMISSION_READ_PHONE_STATE] == PackageManager.PERMISSION_GRANTED
                        && map[PermissionsUtil.PERMISSION_SEND_SMS] == PackageManager.PERMISSION_GRANTED
                        && map[PermissionsUtil.PERMISSION_CALL_PHONE] == PackageManager.PERMISSION_GRANTED
                        && map[PermissionsUtil.PERMISSION_NOTIFICATION_POLICY] == PackageManager.PERMISSION_GRANTED) {

                    if (shouldStartDoNotDisturbActivity()) {
                        startDoNotDisturbAccessActivity()
                    } else {
                        manageViews()
                    }
                } else {
                    // not all permissions were granted, do something
                }
            }
        }
    }

    private fun shouldStartDoNotDisturbActivity(): Boolean {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N && !notificationManager.isNotificationPolicyAccessGranted
    }

    private fun startDoNotDisturbAccessActivity() {
        showAlertDialog(getString(R.string.do_not_disturb_access_message), View.OnClickListener {
            val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
            startActivity(intent)
        })
    }

    private fun manageViews() {
        adda_button_on.setOnClickListener {
            startSecondScreen()
        }
    }

    private fun startSecondScreen() {
        ActivityUtil.startSecondScreenActivityClrTsk(this)
        finish()
    }
}
