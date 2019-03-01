package ph.sym.adda.view.secondscreen

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second_screen.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import ph.sym.adda.R
import ph.sym.adda.eventbus.TurnOffAddaEvent
import ph.sym.adda.util.activityutil.ActivityUtil
import ph.sym.adda.view.viewmodel.ScreensViewModel


class SecondScreenActivity: AppCompatActivity() {

    var viewModel: ScreensViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second_screen)

        viewModel = ScreensViewModel()
        viewModel?.turnOnAdda(this)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
        manageViews()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    private fun manageViews() {
        auto_sms_reply_switch.setOnCheckedChangeListener { _, isChecked ->
            viewModel?.storeAutoReplyState(this, isChecked)
        }

        adda_button_off.setOnClickListener {
            viewModel?.turnOffAdda(this)
            startMainScreen()
        }

        auto_sms_reply_switch.isChecked = viewModel?.getAutoReplyState(this) as Boolean
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onTurnOffAddaEvent(event: TurnOffAddaEvent?) {
        if (event != null) {
            startMainScreen()
        }
        EventBus.getDefault().removeStickyEvent(TurnOffAddaEvent::class.java)
    }

    override fun onBackPressed() {
        finish()
    }

    private fun startMainScreen() {
        ActivityUtil.startMainScreenActivityClrTsk(this)
        finish()
    }
}