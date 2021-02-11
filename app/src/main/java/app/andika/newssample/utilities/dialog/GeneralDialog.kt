package app.andika.newssample.utilities.dialog

import android.app.Activity
import androidx.appcompat.app.AlertDialog

object GeneralDialog {

    public fun displayNetworkErrorDialog(activity: Activity) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Network Error")
        builder.setMessage("Unable to contact the server")
        builder.show()
    }
}
