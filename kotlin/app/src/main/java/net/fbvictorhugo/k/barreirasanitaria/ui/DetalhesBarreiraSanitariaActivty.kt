package net.fbvictorhugo.k.barreirasanitaria.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detalhes_barreira_sanitaria.*
import net.fbvictorhugo.k.barreirasanitaria.R
import net.fbvictorhugo.k.barreirasanitaria.data.dao.BarreiraSanitariaDAO
import net.fbvictorhugo.k.barreirasanitaria.data.dao.DAOFactory
import net.fbvictorhugo.k.barreirasanitaria.data.dao.TabelasDataBase
import net.fbvictorhugo.k.barreirasanitaria.data.model.BarreiraSanitaria
import net.fbvictorhugo.k.barreirasanitaria.utils.Constantes
import net.fbvictorhugo.k.barreirasanitaria.utils.UtilDialog

class DetalhesBarreiraSanitariaActivty : AppCompatActivity() {

    private var _modoCadastro = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_barreira_sanitaria)
        _modoCadastro = intent.getBooleanExtra(Constantes.EXTRA_MODO_CADASTRO, true)

        configuraActionBar(supportActionBar)
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
        if (_modoCadastro) {
            supportActionBar?.title =
                resources.getString(R.string.title_activity_cadastro_barreira_sanitaria)
        }
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun configuraClickListeners() {
        barreira_btn_salvar.setOnClickListener { onClickBtnSalvar() }
    }

    private fun onClickBtnSalvar() {
        val valido = verificaFormularioValido()
        if (valido) {
            val barreiraSanitaria = populaModelo()
            try {
                val barreiraSanitariaDAO =
                    DAOFactory.getDataSource(TabelasDataBase.BARREIRA_SANITARIA) as BarreiraSanitariaDAO

                val mensagem: String

                if (_modoCadastro) {
                    barreiraSanitariaDAO.inserir(barreiraSanitaria)
                    mensagem = resources.getString(R.string.msg_cadastrado_com_sucesso)
                } else {
                    barreiraSanitariaDAO.atualizar(barreiraSanitaria)
                    mensagem = resources.getString(R.string.msg_alterado_com_sucesso)
                }

                UtilDialog.showDialogOK(this, mensagem,
                    DialogInterface.OnClickListener { dialogInterface, i -> finish() })

            } catch (exception: Exception) {
                UtilDialog.showDialogOK(
                    this, resources.getString(R.string.msg_erro_generico)
                )
            }
        }
    }

    private fun verificaFormularioValido(): Boolean {
        barreira_inputlayout_nome.error = ""
        barreira_inputlayout_cidade.error = ""
        barreira_inputlayout_estado.error = ""
        var isFormularioValido = true

        if (barreira_edt_nome.text.toString().isEmpty()) {
            isFormularioValido = false
            barreira_inputlayout_nome.error =
                resources.getString(R.string.msg_erro_campo_obrigatorio)
        }

        if (barreira_edt_cidade.text.toString().isEmpty()) {
            isFormularioValido = false
            barreira_inputlayout_cidade.error =
                resources.getString(R.string.msg_erro_campo_obrigatorio)
        }

        if (barreira_edt_estado.text.toString().isEmpty()) {
            isFormularioValido = false
            barreira_inputlayout_estado.error =
                resources.getString(R.string.msg_erro_campo_obrigatorio)
        }

        return isFormularioValido
    }

    private fun populaModelo(): BarreiraSanitaria {
        return BarreiraSanitaria(
            barreira_edt_nome.text.toString(),
            barreira_edt_cidade.text.toString(),
            barreira_edt_estado.text.toString()
        ).apply {
            descricao = barreira_edt_descricao.text.toString()
            endereco = barreira_edt_endereco.text.toString()
            bairro = barreira_edt_bairro.text.toString()
        }
    }

}
