package net.fbvictorhugo.j.barreirasanitaria.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.fbvictorhugo.j.barreirasanitaria.R;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.DAOFactory;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.IBarreiraSanitariaDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.TabelasDataBase;
import net.fbvictorhugo.j.barreirasanitaria.data.model.BarreiraSanitaria;
import net.fbvictorhugo.j.barreirasanitaria.ui.adapter.BarreirasRecyclerAdapter;

import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListaBarreirasActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private BarreirasRecyclerAdapter mBarreirasRecyclerAdapter;
    private FloatingActionButton mFabNovaBarreira;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barreiras);

        findViews();
        configuraActionBar(getSupportActionBar());
        configuraRecyclerView();
        configuraClickListeners();
    }

    private void configuraActionBar(ActionBar supportActionBar) {
        if (supportActionBar != null) {
            String nomeUsuario = getIntent().getStringExtra(Constantes.EXTRA_NOME_USURARIO);
            supportActionBar.setSubtitle(String.format(getResources().getString(R.string.msg_bem_vindo_), nomeUsuario));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        pesquisaBarreirasSanitarias();
    }

    private void findViews() {
        mRecyclerView = findViewById(R.id.barreiras_recyclerview);
        mFabNovaBarreira = findViewById(R.id.barreiras_fab);
    }

    private void configuraRecyclerView() {
        if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(layoutManager);
            mBarreirasRecyclerAdapter = new BarreirasRecyclerAdapter();
            mRecyclerView.setAdapter(mBarreirasRecyclerAdapter);
        }
    }

    private void configuraClickListeners() {
        mFabNovaBarreira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickBotaoNovaBarreira();
            }
        });

        mBarreirasRecyclerAdapter.setOnItemClickListener(new BarreirasRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(BarreiraSanitaria barreiraSanitaria) {
                onClickItemLista(barreiraSanitaria);
            }

            @Override
            public void onLongClick(BarreiraSanitaria barreiraSanitaria) {
                onLongClickClickItemLista(barreiraSanitaria);
            }
        });
    }

    private void pesquisaBarreirasSanitarias() {
        if (mBarreirasRecyclerAdapter != null) {
            IBarreiraSanitariaDAO barreiraSanitariaDAO = (IBarreiraSanitariaDAO) DAOFactory.getInstance().getDataSource(TabelasDataBase.BARREIRA_SANITARIA);
            List<BarreiraSanitaria> barreiraSanitarias = barreiraSanitariaDAO.listar();
            mBarreirasRecyclerAdapter.atualiza(barreiraSanitarias);
        }
    }

    private void clickBotaoNovaBarreira() {
        startActivity(new Intent(getApplicationContext(), DetalhesBarreiraSanitariaActivty.class));
    }

    private void onClickItemLista(BarreiraSanitaria barreiraSanitaria) {
        Intent intent = new Intent(this, PesquisaPessoasActivity.class);
        intent.putExtra(Constantes.EXTRA_ID_BARREIRA, barreiraSanitaria.getId());
        intent.putExtra(Constantes.EXTRA_NOME_BARREIRA, barreiraSanitaria.getNome());
        startActivity(intent);
    }

    private void onLongClickClickItemLista(BarreiraSanitaria barreiraSanitaria) {
        String messagem = String.format(getResources().getString(R.string.msg_deseja_editar_informacoes_), barreiraSanitaria.getNome());
        UtilDialog.showDialogSimNao(this, messagem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //TODO não implementado
                UtilDialog.showToast(getBaseContext(), "Não implementado.");
            }
        });
    }

}
