package net.fbvictorhugo.j.barreirasanitaria.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.fbvictorhugo.j.barreirasanitaria.R;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.DAOFactory;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.IPessoaDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.TabelasDataBase;
import net.fbvictorhugo.j.barreirasanitaria.data.model.Pessoa;
import net.fbvictorhugo.j.barreirasanitaria.ui.adapter.PessoasRecyclerAdapter;

import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PesquisaPessoasActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FloatingActionButton mFabCadastroPessoa;
    private PessoasRecyclerAdapter mPessoasRecyclerAdapter;
    private AppCompatTextView mTxtNomeBarreira;

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

    void configuraActionBar(ActionBar supportActionBar) {
        if (supportActionBar != null) {
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

    @Override
    protected void onResume() {
        super.onResume();
        pesquisaPessoas();
    }

    private void findViews() {
        mRecyclerView = findViewById(R.id.pesquisa_pessoas_recyclerview);
        mFabCadastroPessoa = findViewById(R.id.pesquisa_pessoas_fab);
        mTxtNomeBarreira = findViewById(R.id.pesquisa_pessoas_txt_nome_barreira);
    }

    private void configuraDadosTela() {
        String nomeBarreira = getIntent().getStringExtra(Constantes.EXTRA_NOME_BARREIRA);
        mTxtNomeBarreira.setText(nomeBarreira);
    }

    private void configuraRecyclerView() {
        if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutManager);
            mPessoasRecyclerAdapter = new PessoasRecyclerAdapter();
            mRecyclerView.setAdapter(mPessoasRecyclerAdapter);
        }
    }

    private void configuraClickListeners() {
        mFabCadastroPessoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBotaoCadastroPessoa();
            }
        });

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
    }

    private void pesquisaPessoas() {
        if (mPessoasRecyclerAdapter != null) {
            IPessoaDAO pessoaDAO = (IPessoaDAO) DAOFactory.getInstance().getDataSource(TabelasDataBase.PESSOA);
            List<Pessoa> pessoas = pessoaDAO.listar();
            mPessoasRecyclerAdapter.atualiza(pessoas);
        }
    }

    private void clickBotaoCadastroPessoa() {
        startActivity(new Intent(getApplicationContext(), DetalhesPessoaActivity.class));
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
        UtilDialog.showDialogSimNao(this, messagem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO não implementado
                UtilDialog.showToast(getBaseContext(), "Não implementado.");
            }
        });
    }

}
