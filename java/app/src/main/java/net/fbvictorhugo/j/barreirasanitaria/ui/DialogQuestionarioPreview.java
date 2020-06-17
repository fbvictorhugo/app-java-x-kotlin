package net.fbvictorhugo.j.barreirasanitaria.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import net.fbvictorhugo.j.barreirasanitaria.R;
import net.fbvictorhugo.j.barreirasanitaria.data.model.Questionario;
import net.fbvictorhugo.j.barreirasanitaria.utils.Constantes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.DialogFragment;

public class DialogQuestionarioPreview extends DialogFragment {

    private final Questionario mQuestionarioAnterior;
    private final String mNomePessoa;
    private final String mNomeBarreiraAnterior;

    public DialogQuestionarioPreview(Questionario questionarioAnterior,
                                     String nomePessoa, String nomeBarreiraAnterior
    ) {
        mQuestionarioAnterior = questionarioAnterior;
        mNomePessoa = nomePessoa;
        mNomeBarreiraAnterior = nomeBarreiraAnterior;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_perguntas_questionario, null);
        int padding = getResources().getDimensionPixelOffset(R.dimen.activity_margin);
        view.setPadding(padding, 0, padding, 0);


        builder.setView(view)
                .setTitle(R.string.titulo_historico)
                .setMessage(mNomePessoa + " passou pela barreira " + mNomeBarreiraAnterior + " em " + formataDataHistorico(mQuestionarioAnterior.getDataResposta()))
                .setPositiveButton(R.string.texto_btn_ok, null);

        configuraDadosTela(view, mQuestionarioAnterior);


        return builder.create();
    }

    private void configuraDadosTela(View view, Questionario questionarioAnterior) {
        configuraCheckBox(
                view.findViewById(R.id.questionario_check_pergunta_viagem_exterior),
                questionarioAnterior.isViagemExterior()
        );
        configuraCheckBox(
                view.findViewById(R.id.questionario_check_pergunta_febre),
                questionarioAnterior.isSintomaFebre()
        );
        configuraCheckBox(
                view.findViewById(R.id.questionario_check_pergunta_coriza),
                questionarioAnterior.isSintomaCoriza()
        );
        configuraCheckBox(
                view.findViewById(R.id.questionario_check_pergunta_tosse),
                questionarioAnterior.isSintomaTosse()
        );
        configuraCheckBox(
                view.findViewById(R.id.questionario_check_pergunta_cancaco),
                questionarioAnterior.isSintomaCancaco()
        );
        configuraCheckBox(
                view.findViewById(R.id.questionario_check_pergunta_paladar),
                questionarioAnterior.isSintomaPerdaPaladar()
        );
        configuraCheckBox(
                view.findViewById(R.id.questionario_check_pergunta_falta_ar),
                questionarioAnterior.isSintomaFaltaAr()
        );
        configuraCheckBox(
                view.findViewById(R.id.questionario_check_contato_com_enfermos),
                questionarioAnterior.isSintomaContatoComEnfermos()
        );
    }

    private void configuraCheckBox(AppCompatCheckBox checkbox, boolean checked) {
        checkbox.setChecked(checked);
        checkbox.setClickable(false);
    }

    private String formataDataHistorico(Date dataResposta) {
        if (dataResposta != null) {
            return new SimpleDateFormat(Constantes.FORMATO_DATA_HISTORICO, Locale.getDefault()).format(dataResposta);
        } else {
            return "";
        }
    }
}
