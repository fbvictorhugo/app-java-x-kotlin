package net.fbvictorhugo.j.barreirasanitaria.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import net.fbvictorhugo.j.barreirasanitaria.R;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.BarreiraSanitariaDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.DAOFactory;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.QuestionarioDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.TabelasDataBase;
import net.fbvictorhugo.j.barreirasanitaria.data.model.Questionario;
import net.fbvictorhugo.j.barreirasanitaria.utils.Constantes;
import net.fbvictorhugo.j.barreirasanitaria.utils.UtilDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;

public class QuestionarioActivity extends AppCompatActivity {
    private AppCompatTextView mTxtNomePessoa;
    private AppCompatTextView mTxtNomeBarreira;
    private AppCompatCheckBox mCheckViagemExterior;
    private AppCompatCheckBox mCheckFebre;
    private AppCompatCheckBox mCheckCoriza;
    private AppCompatCheckBox mCheckCancaco;
    private AppCompatCheckBox mCheckTosse;
    private AppCompatCheckBox mCheckDorGarganta;
    private AppCompatCheckBox mCheckFaltaAr;
    private AppCompatCheckBox mCheckContatoEnfermos;
    private TextInputEditText mEdtObservacoe;
    private AppCompatButton mBtnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionario);

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

        mCheckViagemExterior = findViewById(R.id.questionario_check_pergunta_viagem_exterior);
        mCheckFebre = findViewById(R.id.questionario_check_pergunta_febre);
        mCheckCoriza = findViewById(R.id.questionario_check_pergunta_coriza);
        mCheckTosse = findViewById(R.id.questionario_check_pergunta_tosse);
        mCheckCancaco = findViewById(R.id.questionario_check_pergunta_cancaco);
        mCheckDorGarganta = findViewById(R.id.questionario_check_dor_garganta);
        mCheckFaltaAr = findViewById(R.id.questionario_check_pergunta_falta_ar);
        mCheckContatoEnfermos = findViewById(R.id.questionario_check_contato_com_enfermos);

        mEdtObservacoe = findViewById(R.id.questionario_edt_observacoes);
        mBtnSalvar = findViewById(R.id.questionario_btn_salvar);
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
        mBtnSalvar.setOnClickListener(view -> onClickBtnSalvar());
    }

    private void onClickBtnSalvar() {
        try {

            QuestionarioDAO questionarioDAO = (QuestionarioDAO) DAOFactory.getInstance().getDataSource(TabelasDataBase.QUESTIONARIO);
            final Questionario questionario = populaModelo();

            questionarioDAO.inserir(questionario);

            UtilDialog.showDialogOK(this, getResources().getString(R.string.questionarioa_salvo), (dialogInterface, i) -> finish());

        } catch (Exception e) {
            e.printStackTrace();
            UtilDialog.showDialogOK(this, getResources().getString(R.string.msg_erro_generico));
        }
    }

    private Questionario populaModelo() {

        Questionario questionario = new Questionario();
        questionario.setBarreiraId(getIntent().getLongExtra(Constantes.EXTRA_ID_BARREIRA, 0));
        questionario.setPessoaId(getIntent().getLongExtra(Constantes.EXTRA_ID_PESSOA, 0));
        questionario.setViagemExterior(mCheckViagemExterior.isChecked());
        questionario.setSintomaFebre(mCheckFebre.isChecked());
        questionario.setSintomaCoriza(mCheckCoriza.isChecked());
        questionario.setSintomaTosse(mCheckTosse.isChecked());
        questionario.setSintomaCancaco(mCheckCancaco.isChecked());
        questionario.setSintomaDorGarganta(mCheckDorGarganta.isChecked());
        questionario.setSintomaFaltaAr(mCheckFaltaAr.isChecked());
        questionario.setSintomaContatoComEnfermos(mCheckContatoEnfermos.isChecked());

        questionario.setObservacoes(mEdtObservacoe.getText().toString());
        questionario.setDataResposta(new Date());

        return questionario;
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
