package net.fbvictorhugo.j.barreirasanitaria.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import net.fbvictorhugo.j.barreirasanitaria.R;

import androidx.appcompat.app.AlertDialog;

class UtilDialog {

    static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    static void showDialogSimNao(final Context context, String mensagem, DialogInterface.OnClickListener onPositiveClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.titulo_atencao);
        builder.setMessage(mensagem);
        builder.setPositiveButton(R.string.texto_sim, onPositiveClickListener);
        builder.setNegativeButton(R.string.texto_nao, null);
        builder.create().show();
    }

}
