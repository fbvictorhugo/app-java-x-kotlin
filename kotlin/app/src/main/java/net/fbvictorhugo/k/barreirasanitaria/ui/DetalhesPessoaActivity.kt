package net.fbvictorhugo.k.barreirasanitaria.ui

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detalhes_pessoa.*
import net.fbvictorhugo.k.barreirasanitaria.R
import net.fbvictorhugo.k.barreirasanitaria.data.dao.DAOFactory
import net.fbvictorhugo.k.barreirasanitaria.data.dao.PessoaDAO
import net.fbvictorhugo.k.barreirasanitaria.data.dao.TabelasDataBase
import net.fbvictorhugo.k.barreirasanitaria.data.model.Pessoa
import net.fbvictorhugo.k.barreirasanitaria.utils.Constantes
import net.fbvictorhugo.k.barreirasanitaria.utils.Constantes.FORMATO_DATA_NASCIMENTO
import net.fbvictorhugo.k.barreirasanitaria.utils.UtilDialog
import java.text.SimpleDateFormat
import java.util.*

class DetalhesPessoaActivity : AppCompatActivity() {

    private var _modoCadastro = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_pessoa)

        _modoCadastro = intent.getBooleanExtra(Constantes.EXTRA_MODO_CADASTRO, true)

        configuraActionBar(supportActionBar)
        configuraDadosTela()
        configuraClickListeners()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun configuraActionBar(supportActionBar: ActionBar?) {
        if (_modoCadastro) {
            supportActionBar?.title =
                resources.getString(R.string.title_activity_cadastro_pessoa)
        }
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun configuraDadosTela() {
        if (_modoCadastro) {
            pessoa_edt_documento.setText(intent.getStringExtra(Constantes.EXTRA_NUMERO_DOCUMENTO))
        }
    }

    private fun configuraClickListeners() {
        pessoa_btn_salvar.setOnClickListener { onClickBtnSalvar() }
    }

    private fun onClickBtnSalvar() {
        val pessoaDAO = DAOFactory.getDataSource(TabelasDataBase.PESSOA) as PessoaDAO

        if (verificaFormularioValido()) {
            val pessoa = populaModelo()

            try {
                val mensagem = if (_modoCadastro) {
                    pessoaDAO.inserir(pessoa)
                    resources.getString(R.string.msg_cadastrado_com_sucesso)
                } else {
                    pessoaDAO.atualizar(pessoa)
                    resources.getString(R.string.msg_alterado_com_sucesso)
                }

                UtilDialog.showDialogOK(this, mensagem,
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        val intent = Intent()
                            .apply {
                                putExtra(
                                    Constantes.EXTRA_NUMERO_DOCUMENTO,
                                    pessoa.numeroDocumento
                                )
                            }
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    }
                )

            } catch (exception: Exception) {
                UtilDialog.showDialogOK(
                    this, resources.getString(R.string.msg_erro_generico)
                )
            }
        }
    }

    private fun verificaFormularioValido(): Boolean {
        pessoa_inputlayout_nome.error = ""
        pessoa_inputlayout_documento.error = ""
        pessoa_inputlayout_data_nascimento.error = ""
        pessoa_inputlayout_telefone.error = ""
        pessoa_inputlayout_cidade.error = ""
        pessoa_inputlayout_estado.error = ""

        var isFormularioValido = true

        if (pessoa_edt_nome.text.toString().isEmpty()) {
            isFormularioValido = false
            pessoa_inputlayout_nome.error = resources.getString(R.string.msg_erro_campo_obrigatorio)
        }

        if (pessoa_edt_documento.text.toString().isEmpty()) {
            isFormularioValido = false
            pessoa_inputlayout_documento.error =
                resources.getString(R.string.msg_erro_campo_obrigatorio)
        }

        if (pessoa_edt_data_nascimento.text.toString().isEmpty()) {
            isFormularioValido = false
            pessoa_inputlayout_data_nascimento.error =
                resources.getString(R.string.msg_erro_campo_obrigatorio)
        } else {
            val dataNascimento = formataDataNascimento(pessoa_edt_data_nascimento.text.toString())
            if (dataNascimento == null) {
                isFormularioValido = false
                val msgErroDataNascimento = String.format(
                    resources.getString(R.string.msg_erro_data_invalida_),
                    FORMATO_DATA_NASCIMENTO
                )
                pessoa_inputlayout_data_nascimento.error = msgErroDataNascimento
            }
        }

        if (pessoa_edt_telefone.text.toString().isEmpty()) {
            isFormularioValido = false
            pessoa_inputlayout_telefone.error =
                resources.getString(R.string.msg_erro_campo_obrigatorio)
        }

        if (pessoa_edt_cidade.text.toString().isEmpty()) {
            isFormularioValido = false
            pessoa_inputlayout_cidade.error =
                resources.getString(R.string.msg_erro_campo_obrigatorio)
        }

        if (pessoa_edt_estado.text.toString().isEmpty()) {
            isFormularioValido = false
            pessoa_inputlayout_estado.error =
                resources.getString(R.string.msg_erro_campo_obrigatorio)
        }

        return isFormularioValido
    }

    private fun populaModelo(): Pessoa {
        return Pessoa(
            pessoa_edt_nome.text.toString(),
            formataNumeroDocumento(pessoa_edt_documento.text.toString())
        ).apply {
            dataNascimento = formataDataNascimento(pessoa_edt_data_nascimento.text.toString())
            telefone = formataNumeroTelefone(pessoa_edt_telefone.text.toString())
            bairro = pessoa_edt_bairro.text.toString()
            cidade = pessoa_edt_cidade.text.toString()
            estado = pessoa_edt_estado.text.toString()
        }
    }

    private fun formataNumeroDocumento(texto: String): Long {
        return texto.toLong()
    }

    private fun formataDataNascimento(texto: String): Date? {
        val sdf =
            SimpleDateFormat(FORMATO_DATA_NASCIMENTO, Locale.getDefault())
        return try {
            sdf.parse(texto)
        } catch (exception: Exception) {
            exception.printStackTrace()
            null
        }
    }

    private fun formataNumeroTelefone(texto: String): Long {
        return texto.toLong()
    }

}
