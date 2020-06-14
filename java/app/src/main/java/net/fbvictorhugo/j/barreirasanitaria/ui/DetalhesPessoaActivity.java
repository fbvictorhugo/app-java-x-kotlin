package net.fbvictorhugo.j.barreirasanitaria.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import net.fbvictorhugo.j.barreirasanitaria.R;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.DAOFactory;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.PessoaDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.TabelasDataBase;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.dummy.DummyPessoaDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.model.Pessoa;
import net.fbvictorhugo.j.barreirasanitaria.utils.Constantes;
import net.fbvictorhugo.j.barreirasanitaria.utils.UtilDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import static net.fbvictorhugo.j.barreirasanitaria.utils.Constantes.FORMATO_DATA_NASCIMENTO;

public class DetalhesPessoaActivity extends AppCompatActivity {

    private boolean isModoCadastro;
    private TextInputEditText mEdtNome;
    private TextInputEditText mEdtDocumento;
    private TextInputEditText mEdtDataNascimento;
    private TextInputEditText mEdtTelefone;
    private TextInputEditText mEdtBairro;
    private TextInputEditText mEdtCidade;
    private TextInputEditText mEdtEstado;
    private TextInputLayout mInputlayoutNome;
    private TextInputLayout mInputlayoutDocumento;
    private TextInputLayout mInputlayoutDataNascimento;
    private TextInputLayout mInputlayoutTelefone;
    private TextInputLayout mInputlayoutCidade;
    private TextInputLayout mInputlayoutEstado;
    private AppCompatButton mBtnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_pessoa);
        isModoCadastro = getIntent().getBooleanExtra(Constantes.EXTRA_MODO_CADASTRO, true);

        findViews();
        configuraActionBar(getSupportActionBar());
        configuraClickListeners();
        configuraDadosTela();
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
        mEdtNome = findViewById(R.id.pessoa_edt_nome);
        mEdtDocumento = findViewById(R.id.pessoa_edt_documento);
        mEdtDataNascimento = findViewById(R.id.pessoa_edt_data_nascimento);
        mEdtTelefone = findViewById(R.id.pessoa_edt_telefone);
        mEdtBairro = findViewById(R.id.pessoa_edt_bairro);
        mEdtCidade = findViewById(R.id.pessoa_edt_cidade);
        mEdtEstado = findViewById(R.id.pessoa_edt_estado);
        mInputlayoutNome = findViewById(R.id.pessoa_inputlayout_nome);
        mInputlayoutDocumento = findViewById(R.id.pessoa_inputlayout_documento);
        mInputlayoutDataNascimento = findViewById(R.id.pessoa_inputlayout_data_nascimento);
        mInputlayoutTelefone = findViewById(R.id.pessoa_inputlayout_telefone);
        mInputlayoutCidade = findViewById(R.id.pessoa_inputlayout_cidade);
        mInputlayoutEstado = findViewById(R.id.pessoa_inputlayout_estado);
        mBtnSalvar = findViewById(R.id.pessoa_btn_salvar);
    }

    void configuraActionBar(ActionBar supportActionBar) {
        if (supportActionBar != null) {
            if (isModoCadastro) {
                supportActionBar.setTitle(R.string.title_activity_cadastro_pessoa);
            }
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private void configuraDadosTela() {
        if (isModoCadastro) {
            mEdtDocumento.setText(getIntent().getStringExtra(Constantes.EXTRA_NUMERO_DOCUMENTO));
        }
    }

    private void configuraClickListeners() {
        mBtnSalvar.setOnClickListener(view -> onClickBtnSalvar());
    }

    private void onClickBtnSalvar() {
        PessoaDAO mPessoaDAO = (DummyPessoaDAO) DAOFactory.getInstance().getDataSource(TabelasDataBase.PESSOA);

        if (verificaFormularioValido()) {
            final Pessoa pessoa = populaModelo();
            try {
                String mensagem;
                if (isModoCadastro) {
                    mPessoaDAO.inserir(pessoa);
                    mensagem = getResources().getString(R.string.msg_cadastrado_com_sucesso);
                } else {
                    mPessoaDAO.atualizar(pessoa);
                    mensagem = getResources().getString(R.string.msg_alterado_com_sucesso);
                }

                UtilDialog.showDialogOK(this, mensagem, (dialogInterface, i) -> {

                    Intent intent = new Intent();
                    intent.putExtra(Constantes.EXTRA_NUMERO_DOCUMENTO, pessoa.getNumeroDocumento());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                });
            } catch (Exception e) {
                UtilDialog.showDialogOK(this, getResources().getString(R.string.msg_erro_generico));
            }
        }
    }

    private boolean verificaFormularioValido() {
        mInputlayoutNome.setError("");
        mInputlayoutDocumento.setError("");
        mInputlayoutDataNascimento.setError("");
        mInputlayoutTelefone.setError("");
        mInputlayoutCidade.setError("");
        mInputlayoutEstado.setError("");

        boolean isFormularioValido = true;

        if (mEdtNome.getText().toString().isEmpty()) {
            isFormularioValido = false;
            mInputlayoutNome.setError(getResources().getString(R.string.msg_erro_campo_obrigatorio));
        }

        if (mEdtDocumento.getText().toString().isEmpty()) {
            isFormularioValido = false;
            mInputlayoutDocumento.setError(getResources().getString(R.string.msg_erro_campo_obrigatorio));
        }

        if (mEdtDataNascimento.getText().toString().isEmpty()) {
            isFormularioValido = false;
            mInputlayoutDataNascimento.setError(getResources().getString(R.string.msg_erro_campo_obrigatorio));
        } else {
            Date dataNascimento = formataDataNascimento(mEdtDataNascimento.getText().toString());
            if (dataNascimento == null) {
                isFormularioValido = false;
                String msgErroDataNascimento = String.format(getResources().getString(R.string.msg_erro_data_invalida_), FORMATO_DATA_NASCIMENTO);
                mInputlayoutDataNascimento.setError(msgErroDataNascimento);
            }
        }

        if (mEdtTelefone.getText().toString().isEmpty()) {
            isFormularioValido = false;
            mInputlayoutTelefone.setError(getResources().getString(R.string.msg_erro_campo_obrigatorio));
        }

        if (mEdtCidade.getText().toString().isEmpty()) {
            isFormularioValido = false;
            mInputlayoutCidade.setError(getResources().getString(R.string.msg_erro_campo_obrigatorio));
        }

        if (mEdtEstado.getText().toString().isEmpty()) {
            isFormularioValido = false;
            mInputlayoutEstado.setError(getResources().getString(R.string.msg_erro_campo_obrigatorio));
        }
        return isFormularioValido;
    }

    private Pessoa populaModelo() {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(mEdtNome.getText().toString());
        pessoa.setNumeroDocumento(formataNumeroDocumento(mEdtDocumento.getText().toString()));
        pessoa.setDataNascimento(formataDataNascimento(mEdtDataNascimento.getText().toString()));
        pessoa.setTelefone(formataNumeroTelefone(mEdtTelefone.getText().toString()));
        pessoa.setBairro(mEdtBairro.getText().toString());
        pessoa.setCidade(mEdtCidade.getText().toString());
        pessoa.setEstado(mEdtEstado.getText().toString());
        return pessoa;
    }

    private long formataNumeroDocumento(String texto) {
        return Long.parseLong(texto);
    }

    private Date formataDataNascimento(String texto) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMATO_DATA_NASCIMENTO, Locale.getDefault());
        try {
            return sdf.parse(texto);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private long formataNumeroTelefone(String texto) {
        return Long.parseLong(texto);
    }

}
