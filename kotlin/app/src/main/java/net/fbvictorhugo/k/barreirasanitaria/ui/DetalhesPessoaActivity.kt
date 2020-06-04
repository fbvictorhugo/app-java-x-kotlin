package net.fbvictorhugo.k.barreirasanitaria.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import net.fbvictorhugo.k.barreirasanitaria.R

class DetalhesPessoaActivity : AppCompatActivity() {

    private var isModoCadastro = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_pessoa)
        configuraActionBar(supportActionBar)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun configuraActionBar(supportActionBar: ActionBar?) {
        if (isModoCadastro) {
            supportActionBar?.title =
                resources.getString(R.string.title_activity_cadastro_pessoa)
        }
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

}