package net.fbvictorhugo.j.barreirasanitaria.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import net.fbvictorhugo.j.barreirasanitaria.R;

public class DetalhesBarreiraSanitariaActivty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_barreira_sanitaria);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.title_activity_nova_barreira_sanitaria);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
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

}
