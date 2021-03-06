package net.fbvictorhugo.k.barreirasanitaria.utils

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import net.fbvictorhugo.k.barreirasanitaria.R

object UtilDialog {

    fun showToast(context: Context, mensagem: String) {
        Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show()
    }

    fun showDialogSimNao(
        context: Context,
        mensagem: String,
        onPositiveClickListener: DialogInterface.OnClickListener
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setTitle(R.string.titulo_atencao)
        builder.setMessage(mensagem)
        builder.setPositiveButton(R.string.texto_btn_sim, onPositiveClickListener)
        builder.setNegativeButton(R.string.texto_btn_nao, null)
        builder.create().show()
    }

    fun showDialogOK(
        context: Context,
        mensagem: String,
        onOkClickListener: DialogInterface.OnClickListener? = null
    ) {
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setTitle(R.string.titulo_atencao)
        builder.setMessage(mensagem)
        builder.setPositiveButton(R.string.texto_btn_ok, onOkClickListener)
        builder.create().show()
    }

}
