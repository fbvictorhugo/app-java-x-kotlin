package net.fbvictorhugo.j.barreirasanitaria.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.fbvictorhugo.j.barreirasanitaria.R;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.DAOFactory;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.IBarreiraSanitariaDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.TabelasDataBase;
import net.fbvictorhugo.j.barreirasanitaria.data.model.BarreiraSanitaria;
import net.fbvictorhugo.j.barreirasanitaria.ui.adapter.BarreirasRecyclerAdapter;

import java.util.List;

public class ListaBarreirasActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private BarreirasRecyclerAdapter mBarreirasRecyclerAdapter;
    private FloatingActionButton mFabNovaBarreira;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barreiras);

        findViews();
        configuraRecyclerView();
        configuraClickListeners();
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
    }

    private void pesquisaBarreirasSanitarias() {
        if (mBarreirasRecyclerAdapter != null) {
            IBarreiraSanitariaDAO barreiraSanitariaDAO = (IBarreiraSanitariaDAO) DAOFactory.getInstance().getDataSource(TabelasDataBase.BARREIRA_SANITARIA);
            List<BarreiraSanitaria> barreiraSanitarias = barreiraSanitariaDAO.listarBarreiras();
            mBarreirasRecyclerAdapter.atualiza(barreiraSanitarias);
        }
    }

    private void clickBotaoNovaBarreira() {
        startActivity(new Intent(getApplicationContext(), DetalhesBarreiraSanitariaActivty.class));
    }

}
