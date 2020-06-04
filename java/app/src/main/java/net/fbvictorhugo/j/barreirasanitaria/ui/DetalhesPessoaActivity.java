package net.fbvictorhugo.j.barreirasanitaria.ui;

import android.os.Bundle;
import android.view.MenuItem;

import net.fbvictorhugo.j.barreirasanitaria.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class DetalhesPessoaActivity extends AppCompatActivity {

    private boolean isModoCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_pessoa);
        configuraActionBar(getSupportActionBar());
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

    void configuraActionBar(ActionBar supportActionBar) {
        if (supportActionBar != null) {
            if (isModoCadastro) {
                supportActionBar.setTitle(R.string.title_activity_cadastro_pessoa);
            }
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
    }
}
