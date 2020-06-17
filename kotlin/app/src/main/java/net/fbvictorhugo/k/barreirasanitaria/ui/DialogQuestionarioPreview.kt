package net.fbvictorhugo.k.barreirasanitaria.ui

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.fragment.app.DialogFragment
import net.fbvictorhugo.k.barreirasanitaria.R
import net.fbvictorhugo.k.barreirasanitaria.data.model.Questionario
import net.fbvictorhugo.k.barreirasanitaria.extension.toString
import net.fbvictorhugo.k.barreirasanitaria.utils.Constantes.FORMATO_DATA_HISTORICO

class DialogQuestionarioPreview(
    private val questionarioAnterior: Questionario,
    private val nomePessoa: String,
    private val nomeBarreiraAnterior: String
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.layout_perguntas_questionario, null)
            val padding = resources.getDimensionPixelOffset(R.dimen.activity_margin)
            view.setPadding(padding, 0, padding, 0)

            builder.setView(view)
                .setTitle(R.string.titulo_historico)
                .setMessage(
                    "$nomePessoa passou pela barreira $nomeBarreiraAnterior em ${questionarioAnterior.dataResposta.toString(
                        FORMATO_DATA_HISTORICO
                    )}"
                )
                .setPositiveButton(R.string.texto_btn_ok, null)

            configuraDadosTela(view, questionarioAnterior)

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    fun configuraDadosTela(
        view: View,
        questionarioAnterior: Questionario
    ) {
        configuraCheckBox(
            view.findViewById(R.id.questionario_check_pergunta_viagem_exterior),
            questionarioAnterior.viagemExterior
        )
        configuraCheckBox(
            view.findViewById(R.id.questionario_check_pergunta_febre),
            questionarioAnterior.sintomaFebre
        )
        configuraCheckBox(
            view.findViewById(R.id.questionario_check_pergunta_coriza),
            questionarioAnterior.sintomaCoriza
        )
        configuraCheckBox(
            view.findViewById(R.id.questionario_check_pergunta_tosse),
            questionarioAnterior.sintomaTosse
        )
        configuraCheckBox(
            view.findViewById(R.id.questionario_check_pergunta_cancaco),
            questionarioAnterior.sintomaCancaco
        )
        configuraCheckBox(
            view.findViewById(R.id.questionario_check_pergunta_paladar),
            questionarioAnterior.sintomaPerdaPaladar
        )
        configuraCheckBox(
            view.findViewById(R.id.questionario_check_pergunta_falta_ar),
            questionarioAnterior.sintomaFaltaAr
        )
        configuraCheckBox(
            view.findViewById(R.id.questionario_check_contato_com_enfermos),
            questionarioAnterior.sintomaContatoComEnfermos
        )
    }

    private fun configuraCheckBox(checkbox: AppCompatCheckBox, checked: Boolean) {
        checkbox.isChecked = checked
        checkbox.isClickable = false
    }

}