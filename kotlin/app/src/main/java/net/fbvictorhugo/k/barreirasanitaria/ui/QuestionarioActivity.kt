package net.fbvictorhugo.k.barreirasanitaria.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_questionario.*
import net.fbvictorhugo.k.barreirasanitaria.R
import net.fbvictorhugo.k.barreirasanitaria.utils.Constantes

class QuestionarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionario)

        configuraActionBar(supportActionBar)
        configuraDadosTela()
        configuraClickListeners()
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

    private fun configuraActionBar(supportActionBar: ActionBar?) {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun configuraDadosTela() {
        questionario_txt_nome_barreira.text = intent.getStringExtra(Constantes.EXTRA_NOME_BARREIRA)
        questionario_txt_nome_pessoa.text = intent.getStringExtra(Constantes.EXTRA_NOME_PESSOA)
    }

    private fun configuraClickListeners() {

    }
}
