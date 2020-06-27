package net.fbvictorhugo.k.barreirasanitaria.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_questionario.*
import kotlinx.android.synthetic.main.layout_perguntas_questionario.*
import net.fbvictorhugo.k.barreirasanitaria.R
import net.fbvictorhugo.k.barreirasanitaria.data.dao.BarreiraSanitariaDAO
import net.fbvictorhugo.k.barreirasanitaria.data.dao.DAOFactory
import net.fbvictorhugo.k.barreirasanitaria.data.dao.QuestionarioDAO
import net.fbvictorhugo.k.barreirasanitaria.data.dao.TabelasDataBase
import net.fbvictorhugo.k.barreirasanitaria.data.model.Questionario
import net.fbvictorhugo.k.barreirasanitaria.extension.toString
import net.fbvictorhugo.k.barreirasanitaria.utils.Constantes
import net.fbvictorhugo.k.barreirasanitaria.utils.Constantes.FORMATO_DATA_HISTORICO
import net.fbvictorhugo.k.barreirasanitaria.utils.UtilDialog
import java.util.*

class QuestionarioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionario)

//        intent.getLongExtra(Constantes.EXTRA_ID_BARREIRA,0)
//        intent.getStringExtra(Constantes.EXTRA_NOME_BARREIRA)
//        intent.getLongExtra(Constantes.EXTRA_ID_PESSOA,0)
//        intent.getStringExtra(Constantes.EXTRA_NOME_PESSOA)

        configuraActionBar(supportActionBar)
        configuraDadosTela()
        configuraClickListeners()

        verificaOutrosQuestionarios(intent.getLongExtra(Constantes.EXTRA_ID_PESSOA, 0))
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
        questionario_btn_salvar.setOnClickListener { onClickBtnSalvar() }
    }

    private fun onClickBtnSalvar() {
        try {
            val questionarioDAO =
                DAOFactory.getDataSource(TabelasDataBase.QUESTIONARIO) as QuestionarioDAO
            val questionario = populaModelo()

            questionarioDAO.inserir(questionario)

            UtilDialog.showDialogOK(this, resources.getString(R.string.questionarioa_salvo),
                DialogInterface.OnClickListener { dialogInterface, i -> finish() })

        } catch (e: Exception) {
            e.printStackTrace()
            UtilDialog.showDialogOK(this, resources.getString(R.string.msg_erro_generico))
        }
    }

    private fun populaModelo(): Questionario {

        return Questionario().apply {
            barreiraId = intent.getLongExtra(Constantes.EXTRA_ID_BARREIRA, 0)
            pessoaId = intent.getLongExtra(Constantes.EXTRA_ID_PESSOA, 0)
            viagemExterior = questionario_check_contato_com_enfermos.isChecked
            sintomaFebre = questionario_check_pergunta_febre.isChecked
            sintomaCoriza = questionario_check_pergunta_coriza.isChecked
            sintomaTosse = questionario_check_pergunta_tosse.isChecked
            sintomaCancaco = questionario_check_pergunta_cancaco.isChecked
            sintomaDorGarganta = questionario_check_dor_garganta.isChecked
            sintomaFaltaAr = questionario_check_pergunta_falta_ar.isChecked
            sintomaContatoComEnfermos = questionario_check_contato_com_enfermos.isChecked
            observacoes = questionario_edt_observacoes.text.toString()
            dataResposta = Date()
        }
    }

    fun verificaOutrosQuestionarios(idPessoa: Long) {

        val questionarioDAO: QuestionarioDAO =
            DAOFactory.getDataSource(TabelasDataBase.QUESTIONARIO) as QuestionarioDAO
        val questionarioAnterior =
            questionarioDAO.procurarUltimoQuestionarioRespondidoPorPessoa(idPessoa)

        if (questionarioAnterior != null) {

            val mensagem = String.format(
                resources.getString(
                    R.string.msg_historico_passagem_,
                    questionarioAnterior.dataResposta.toString(FORMATO_DATA_HISTORICO)
                )
            )

            Snackbar.make(
                findViewById(R.id.questionario_cordinator_layout),
                mensagem,
                Snackbar.LENGTH_INDEFINITE
            ).setAction(
                R.string.txt_btn_ver,
                exibirDialogQuestionarioListener(questionarioAnterior)
            ).show()

        }
    }

    private fun exibirDialogQuestionarioListener(questionarioAnterior: Questionario): View.OnClickListener {

        return View.OnClickListener {

            val barreirDAO: BarreiraSanitariaDAO =
                DAOFactory.getDataSource(TabelasDataBase.BARREIRA_SANITARIA) as BarreiraSanitariaDAO
            val nomeBarreiraAnterior =
                barreirDAO.procurarNomeBarreira(questionarioAnterior.barreiraId)

            DialogQuestionarioPreview(
                questionarioAnterior,
                questionario_txt_nome_pessoa.text.toString(),
                nomeBarreiraAnterior
            ).show(supportFragmentManager, "DIALOG_QUESTIONARIO_PREVIEW")
        }
    }

}
