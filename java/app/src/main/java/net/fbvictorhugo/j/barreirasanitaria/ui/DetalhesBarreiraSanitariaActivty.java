package net.fbvictorhugo.j.barreirasanitaria.ui;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import net.fbvictorhugo.j.barreirasanitaria.R;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.DAOFactory;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.IBarreiraSanitariaDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.TabelasDataBase;
import net.fbvictorhugo.j.barreirasanitaria.data.model.BarreiraSanitaria;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class DetalhesBarreiraSanitariaActivty extends AppCompatActivity {

    private boolean isModoCadastro;
    private TextInputEditText mEdtNome;
    private TextInputEditText mEdtDescricao;
    private TextInputEditText mEdtEndereco;
    private TextInputEditText mEdtBairro;
    private TextInputEditText mEdtCidade;
    private TextInputEditText mEdtEstado;
    private AppCompatButton mBtnSalvar;
    private IBarreiraSanitariaDAO mBarreiraSanitariaDAO;
    private TextInputLayout mBarreiraInputlayoutNome;
    private TextInputLayout mBarreiraInputlayoutCidade;
    private TextInputLayout mBarreiraInputlayoutEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_barreira_sanitaria);
        isModoCadastro = getIntent().getBooleanExtra(Constantes.EXTRA_MODO_CADASTRO, true);

        mBarreiraSanitariaDAO = (IBarreiraSanitariaDAO) DAOFactory.getInstance().getDataSource(TabelasDataBase.BARREIRA_SANITARIA);

        findViews();
        configuraActionBar(getSupportActionBar());
        //configuraDadosTela();
        configuraClickListeners();
    }

    void configuraActionBar(ActionBar supportActionBar) {
        if (supportActionBar != null) {
            if (isModoCadastro) {
                supportActionBar.setTitle(R.string.title_activity_cadastro_barreira_sanitaria);
            }
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void findViews() {
        mEdtNome = findViewById(R.id.barreira_edt_nome);
        mEdtDescricao = findViewById(R.id.barreira_edt_descricao);
        mEdtEndereco = findViewById(R.id.barreira_edt_endereco);
        mEdtBairro = findViewById(R.id.barreira_edt_bairro);
        mEdtCidade = findViewById(R.id.barreira_edt_cidade);
        mEdtEstado = findViewById(R.id.barreira_edt_estado);
        mBtnSalvar = findViewById(R.id.barreira_btn_salvar);

        mBarreiraInputlayoutNome = findViewById(R.id.barreira_inputlayout_nome);
        mBarreiraInputlayoutCidade = findViewById(R.id.barreira_inputlayout_cidade);
        mBarreiraInputlayoutEstado = findViewById(R.id.barreira_inputlayout_estado);
    }

    private void configuraClickListeners() {
        mBtnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBtnSalvar();
            }
        });
    }

    private void onClickBtnSalvar() {

        boolean valido = verificaFormularioValido();

        if (valido) {
            BarreiraSanitaria barreiraSanitaria = populaModelo();

            try {
                String mensagem;
                if (isModoCadastro) {
                    mBarreiraSanitariaDAO.inserir(barreiraSanitaria);
                    mensagem = getResources().getString(R.string.msg_cadastrado_com_sucesso);
                } else {
                    mBarreiraSanitariaDAO.atualizar(barreiraSanitaria);
                    mensagem = getResources().getString(R.string.msg_alterado_com_sucesso);
                }

                UtilDialog.showDialogOK(this, mensagem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
            } catch (Exception e) {
                UtilDialog.showDialogAlerta(this, getResources().getString(R.string.msg_erro_generico));
            }
        }
    }

    private boolean verificaFormularioValido() {
        mBarreiraInputlayoutNome.setError("");
        mBarreiraInputlayoutCidade.setError("");
        mBarreiraInputlayoutEstado.setError("");

        boolean isFormularioValido = true;
        if (mEdtNome.getText().toString().isEmpty()) {
            isFormularioValido = false;
            mBarreiraInputlayoutNome.setError(getResources().getString(R.string.msg_erro_campo_obrigatorio));
        }
        if (mEdtCidade.getText().toString().isEmpty()) {
            isFormularioValido = false;
            mBarreiraInputlayoutCidade.setError(getResources().getString(R.string.msg_erro_campo_obrigatorio));
        }

        if (mEdtEstado.getText().toString().isEmpty()) {
            isFormularioValido = false;
            mBarreiraInputlayoutEstado.setError(getResources().getString(R.string.msg_erro_campo_obrigatorio));
        }

        return isFormularioValido;
    }

    private BarreiraSanitaria populaModelo() {
        BarreiraSanitaria barreira = new BarreiraSanitaria();
        barreira.setNome(mEdtNome.getText().toString());
        barreira.setDescricao(mEdtDescricao.getText().toString());
        barreira.setEndereco(mEdtEndereco.getText().toString());
        barreira.setBairro(mEdtBairro.getText().toString());
        barreira.setCidade(mEdtCidade.getText().toString());
        barreira.setEstado(mEdtEstado.getText().toString());
        return barreira;
    }

}
