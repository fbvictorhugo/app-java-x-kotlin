package net.fbvictorhugo.j.barreirasanitaria.ui;

import android.os.Bundle;
import android.view.MenuItem;

import net.fbvictorhugo.j.barreirasanitaria.R;

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

        findViews();
        configuraActionBar(getSupportActionBar());
        configuraDadosTela();
        configuraClickListeners();
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
}
