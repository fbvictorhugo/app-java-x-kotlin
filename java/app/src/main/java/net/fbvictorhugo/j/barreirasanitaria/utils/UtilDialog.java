package net.fbvictorhugo.j.barreirasanitaria.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import net.fbvictorhugo.j.barreirasanitaria.R;

import androidx.appcompat.app.AlertDialog;

public class UtilDialog {

    public static void showToast(Context context, String mensagem) {
        Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();
    }

    public static void showDialogSimNao(final Context context, String mensagem, DialogInterface.OnClickListener onPositiveClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(R.string.titulo_atencao);
        builder.setMessage(mensagem);
        builder.setPositiveButton(R.string.texto_btn_sim, onPositiveClickListener);
        builder.setNegativeButton(R.string.texto_btn_nao, null);
        builder.create().show();
    }

    public static void showDialogOK(final Context context, String mensagem,
                                    DialogInterface.OnClickListener onOkClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(R.string.titulo_atencao);
        builder.setMessage(mensagem);
        builder.setPositiveButton(R.string.texto_btn_ok, onOkClickListener);
        builder.create().show();
    }

    /**
     * overload do método showDialogOK , parametro  DialogInterface.OnClickListener padrão nulo.
     *
     * @param context  Contexto
     * @param mensagem Mensagem exibida no Dialog
     */
    public static void showDialogOK(final Context context, String mensagem) {
        showDialogOK(context, mensagem, null);
    }

}
