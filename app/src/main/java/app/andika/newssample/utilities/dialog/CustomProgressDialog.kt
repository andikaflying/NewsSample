package app.andika.newssample.utilities.dialog

import android.app.Activity
import android.app.Dialog
import androidx.appcompat.app.AlertDialog
import app.andika.newssample.R

class CustomProgressDialog(activity: Activity) {
    private val TAG = CustomProgressDialog::class.java.name
    lateinit var builder: AlertDialog.Builder
    lateinit var dialog: Dialog

    init {
        builder = androidx.appcompat.app.AlertDialog.Builder(activity)
        builder.setView(R.layout.progress_dialog)
        dialog = builder.create()
        dialog.setCancelable(false)
    }

    public fun setDialog(show: Boolean) {
        if (show) {
            dialog.show()
        } else {
            dialog.dismiss()
        }
    }
}