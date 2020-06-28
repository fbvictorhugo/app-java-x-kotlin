package net.fbvictorhugo.j.barreirasanitaria.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import net.fbvictorhugo.j.barreirasanitaria.R;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.DAOFactory;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.PessoaDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.TabelasDataBase;
import net.fbvictorhugo.j.barreirasanitaria.data.model.Pessoa;
import net.fbvictorhugo.j.barreirasanitaria.ui.adapter.PessoasRecyclerAdapter;
import net.fbvictorhugo.j.barreirasanitaria.utils.Constantes;
import net.fbvictorhugo.j.barreirasanitaria.utils.UtilDialog;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static net.fbvictorhugo.j.barreirasanitaria.utils.Constantes.RESULT_TELA_DETALHES;

public class PesquisaPessoasActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FloatingActionButton mFabCadastroPessoa;
    private PessoasRecyclerAdapter mPessoasRecyclerAdapter;
    private AppCompatTextView mTxtNomeBarreira;
    private AppCompatTextView mTxtListaVazia;
    private TextInputEditText mEdtPesquisa;
    private AppCompatImageButton mBtnPesquisar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa_pessoas);

        findViews();

        configuraActionBar(getSupportActionBar());
        configuraDadosTela();

        configuraRecyclerView();
        configuraClickListeners();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_TELA_DETALHES && data != null) {
                long numDocumento = data.getLongExtra(Constantes.EXTRA_NUMERO_DOCUMENTO, 0);
                if (numDocumento > 0) {
                    mEdtPesquisa.setText(String.valueOf(numDocumento));
                }
            }
            mBtnPesquisar.callOnClick();
        }
        super.onActivityResult(requestCode, resultCode, data);
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
        mRecyclerView = findViewById(R.id.pesquisa_pessoas_recyclerview);
        mFabCadastroPessoa = findViewById(R.id.pesquisa_pessoas_fab);
        mTxtNomeBarreira = findViewById(R.id.pesquisa_pessoas_txt_nome_barreira);
        mTxtListaVazia = findViewById(R.id.pesquisa_pessoas_txt_lista_vazia);
        mEdtPesquisa = findViewById(R.id.pesquisa_pessoas_edt_pesquisar);
        mBtnPesquisar = findViewById(R.id.pesquisa_pessoas_btn_pesquisar);
    }

    private void configuraActionBar(ActionBar supportActionBar) {
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
    }

    private void configuraDadosTela() {
        mTxtNomeBarreira.setText(getIntent().getStringExtra(Constantes.EXTRA_NOME_BARREIRA));
    }

    private void configuraRecyclerView() {
        if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutManager);
            mPessoasRecyclerAdapter = new PessoasRecyclerAdapter(this);
            mRecyclerView.setAdapter(mPessoasRecyclerAdapter);
        }
    }

    private void configuraClickListeners() {
        mFabCadastroPessoa.setOnClickListener(v -> clickBotaoCadastroPessoa());

        mPessoasRecyclerAdapter.setOnItemClickListener(new PessoasRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(Pessoa pessoa) {
                onClickItemLista(pessoa);
            }

            @Override
            public void onLongClick(Pessoa pessoa) {
                onLongClickClickItemLista(pessoa);
            }
        });

        mBtnPesquisar.setOnClickListener(view -> pesquisaPessoas());
    }

    private void pesquisaPessoas() {
        if (mPessoasRecyclerAdapter != null) {
            String termoPesquisa = "";

            try {
                termoPesquisa = mEdtPesquisa.getText().toString();
                PessoaDAO pessoaDAO = (PessoaDAO) DAOFactory.getInstance().getDataSource(TabelasDataBase.PESSOA);

                if (termoPesquisa.isEmpty()) {
                    mPessoasRecyclerAdapter.atualiza(new ArrayList<>());
                    mTxtListaVazia.setVisibility(View.VISIBLE);
                } else {
                    long documnentoPesquisa = Long.parseLong(termoPesquisa);

                    List<Pessoa> pessoas = pessoaDAO.pesquisarPorDocumento(documnentoPesquisa);
                    mPessoasRecyclerAdapter.atualiza(pessoas);

                    if (pessoas.size() > 0) {
                        mTxtListaVazia.setVisibility(View.GONE);
                    }
                }
            } catch (Exception e) {
                String mensagem = String.format(getResources().getString(R.string.msg_erro_termo_pesquisa_), termoPesquisa);
                UtilDialog.showDialogOK(this, mensagem);
                e.printStackTrace();
            }
        }
    }

    private void clickBotaoCadastroPessoa() {
        Intent intent = new Intent(this, DetalhesPessoaActivity.class);
        intent.putExtra(Constantes.EXTRA_MODO_CADASTRO, true);
        intent.putExtra(Constantes.EXTRA_NUMERO_DOCUMENTO, mEdtPesquisa.getText().toString());
        startActivityForResult(intent, RESULT_TELA_DETALHES);
    }

    private void onClickItemLista(Pessoa pessoa) {
        Intent intent = new Intent(this, QuestionarioActivity.class);
        intent.putExtra(Constantes.EXTRA_ID_BARREIRA, getIntent().getLongExtra(Constantes.EXTRA_ID_BARREIRA, 0));
        intent.putExtra(Constantes.EXTRA_NOME_BARREIRA, getIntent().getStringExtra(Constantes.EXTRA_NOME_BARREIRA));
        intent.putExtra(Constantes.EXTRA_ID_PESSOA, pessoa.getId());
        intent.putExtra(Constantes.EXTRA_NOME_PESSOA, pessoa.getNome());
        startActivity(intent);
    }

    private void onLongClickClickItemLista(Pessoa pessoa) {
        String messagem = String.format(getResources().getString(R.string.msg_deseja_editar_informacoes_), pessoa.getNome());
        UtilDialog.showDialogSimNao(this, messagem, (dialogInterface, i) -> {

            Intent intent = new Intent(PesquisaPessoasActivity.this, DetalhesPessoaActivity.class);
            intent.putExtra(Constantes.EXTRA_ID_PESSOA, pessoa.getId());
            intent.putExtra(Constantes.EXTRA_MODO_CADASTRO, false);
            startActivityForResult(intent, RESULT_TELA_DETALHES);

        });
    }

}
