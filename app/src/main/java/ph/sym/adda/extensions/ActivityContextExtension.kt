package ph.sym.adda.extensions

import android.content.Context
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import ph.sym.adda.R

fun Context.toastShort(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showAlertDialog(message: String, onClickListener: View.OnClickListener) {
    val alertDialog = AlertDialog.Builder(this)
            .setMessage(message)
            .setNeutralButton(getString(R.string.okay), { _, _ ->
                onClickListener.onClick(null)
            }).create()
    alertDialog.show()
}
