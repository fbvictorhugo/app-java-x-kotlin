package net.fbvictorhugo.k.barreirasanitaria.ui;

import android.content.Context;
import android.widget.Toast;

internal object UtilDialog {

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}