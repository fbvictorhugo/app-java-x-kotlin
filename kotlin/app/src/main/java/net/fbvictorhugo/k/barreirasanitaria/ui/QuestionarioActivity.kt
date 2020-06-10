package net.fbvictorhugo.k.barreirasanitaria.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import net.fbvictorhugo.k.barreirasanitaria.R
import net.fbvictorhugo.k.barreirasanitaria.utils.Constantes

class QuestionarioActivity : AppCompatActivity() {

    private var _txtNomeBarreira: AppCompatTextView? = null
    private var _txtNomePessoa: AppCompatTextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionario)

        findViews()
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

    private fun findViews() {
        _txtNomeBarreira = findViewById(R.id.questionario_txt_nome_barreira)
        _txtNomePessoa = findViewById(R.id.questionario_txt_nome_pessoa)
    }

    private fun configuraActionBar(supportActionBar: ActionBar?) {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun configuraDadosTela() {
        _txtNomeBarreira?.text = intent.getStringExtra(Constantes.EXTRA_NOME_BARREIRA)
        _txtNomePessoa?.text = intent.getStringExtra(Constantes.EXTRA_NOME_PESSOA)
    }

    private fun configuraClickListeners() {

    }
}
