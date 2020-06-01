package net.fbvictorhugo.j.barreirasanitaria.ui;

import android.content.Context;
import android.widget.Toast;

class UtilDialog {

    static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
