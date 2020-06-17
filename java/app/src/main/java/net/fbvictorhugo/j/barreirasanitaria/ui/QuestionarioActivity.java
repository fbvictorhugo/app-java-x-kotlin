package net.fbvictorhugo.j.barreirasanitaria.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import net.fbvictorhugo.j.barreirasanitaria.R;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.BarreiraSanitariaDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.DAOFactory;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.QuestionarioDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.TabelasDataBase;
import net.fbvictorhugo.j.barreirasanitaria.data.model.Questionario;
import net.fbvictorhugo.j.barreirasanitaria.utils.Constantes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

public class QuestionarioActivity extends AppCompatActivity {
    private AppCompatTextView mTxtNomePessoa;
    private AppCompatTextView mTxtNomeBarreira;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionario);

//        intent.getLongExtra(Constantes.EXTRA_ID_BARREIRA,0)
//        intent.getStringExtra(Constantes.EXTRA_NOME_BARREIRA)
//        intent.getLongExtra(Constantes.EXTRA_ID_PESSOA,0)
//        intent.getStringExtra(Constantes.EXTRA_NOME_PESSOA)

        findViews();
        configuraActionBar(getSupportActionBar());
        configuraDadosTela();
        configuraClickListeners();

        verificaOutrosQuestionarios(getIntent().getLongExtra(Constantes.EXTRA_ID_PESSOA, 0));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews() {
        mTxtNomeBarreira = findViewById(R.id.questionario_txt_nome_barreira);
        mTxtNomePessoa = findViewById(R.id.questionario_txt_nome_pessoa);
    }

    void configuraActionBar(ActionBar supportActionBar) {
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private void configuraDadosTela() {
        mTxtNomeBarreira.setText(getIntent().getStringExtra(Constantes.EXTRA_NOME_BARREIRA));
        mTxtNomePessoa.setText(getIntent().getStringExtra(Constantes.EXTRA_NOME_PESSOA));
    }

    private void configuraClickListeners() {

    }

    private void verificaOutrosQuestionarios(long idPessoa) {
        QuestionarioDAO questionarioDAO = (QuestionarioDAO) DAOFactory.getInstance().getDataSource(TabelasDataBase.QUESTIONARIO);
        Questionario questionarioAnterior = questionarioDAO.procurarUltimoQuestionarioRespondidoPorPessoa(idPessoa);

        if (questionarioAnterior != null) {
            String mensagem = String.format(getResources().getString(R.string.msg_historico_passagem_), formataDataHistorico(questionarioAnterior.getDataResposta()));

            Snackbar make = Snackbar.make(findViewById(R.id.questionario_cordinator_layout), mensagem, Snackbar.LENGTH_INDEFINITE);
            make.setAction(R.string.txt_btn_ver, exibirDialogQuestionarioListener(questionarioAnterior));
            make.show();
        }
    }

    private View.OnClickListener exibirDialogQuestionarioListener(Questionario questionarioAnterior) {
        return view -> {
            BarreiraSanitariaDAO barreiraDAO = (BarreiraSanitariaDAO) DAOFactory.getInstance().getDataSource(TabelasDataBase.BARREIRA_SANITARIA);
            String nomeBarreiraAnterior = barreiraDAO.procurarNomeBarreira(questionarioAnterior.getBarreiraId());

            new DialogQuestionarioPreview(questionarioAnterior, mTxtNomePessoa.getText().toString(), nomeBarreiraAnterior).show(getSupportFragmentManager(), "DIALOG_QUESTIONARIO_PREVIEW");
        };
    }

    private String formataDataHistorico(Date dataResposta) {
        if (dataResposta != null) {
            return new SimpleDateFormat(Constantes.FORMATO_DATA_HISTORICO, Locale.getDefault()).format(dataResposta);
        } else {
            return "";
        }
    }
}
