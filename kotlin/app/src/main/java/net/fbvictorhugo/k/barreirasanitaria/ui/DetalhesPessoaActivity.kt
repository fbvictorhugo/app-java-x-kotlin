package net.fbvictorhugo.k.barreirasanitaria.ui

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
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

    private var _edtNome: TextInputEditText? = null
    private var _edtDocumento: TextInputEditText? = null
    private var _edtDataNascimento: TextInputEditText? = null
    private var _edtTelefone: TextInputEditText? = null
    private var _edtBairro: TextInputEditText? = null
    private var _edtCidade: TextInputEditText? = null
    private var _edtEstado: TextInputEditText? = null
    private var _inputlayoutNome: TextInputLayout? = null
    private var _inputlayoutDocumento: TextInputLayout? = null
    private var _inputlayoutDataNascimento: TextInputLayout? = null
    private var _inputlayoutTelefone: TextInputLayout? = null
    private var _inputlayoutCidade: TextInputLayout? = null
    private var _inputlayoutEstado: TextInputLayout? = null
    private var _btnSalvar: AppCompatButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_pessoa)

        _modoCadastro = intent.getBooleanExtra(Constantes.EXTRA_MODO_CADASTRO, true)

        findViews()
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

    private fun findViews() {
        _edtNome = findViewById(R.id.pessoa_edt_nome)
        _edtDocumento = findViewById(R.id.pessoa_edt_documento)
        _edtDataNascimento = findViewById(R.id.pessoa_edt_data_nascimento)
        _edtTelefone = findViewById(R.id.pessoa_edt_telefone)
        _edtBairro = findViewById(R.id.pessoa_edt_bairro)
        _edtCidade = findViewById(R.id.pessoa_edt_cidade)
        _edtEstado = findViewById(R.id.pessoa_edt_estado)
        _inputlayoutNome = findViewById(R.id.pessoa_inputlayout_nome)
        _inputlayoutDocumento = findViewById(R.id.pessoa_inputlayout_documento)
        _inputlayoutDataNascimento = findViewById(R.id.pessoa_inputlayout_data_nascimento)
        _inputlayoutTelefone = findViewById(R.id.pessoa_inputlayout_telefone)
        _inputlayoutCidade = findViewById(R.id.pessoa_inputlayout_cidade)
        _inputlayoutEstado = findViewById(R.id.pessoa_inputlayout_estado)
        _btnSalvar = findViewById(R.id.pessoa_btn_salvar)
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
            _edtDocumento?.setText(intent.getStringExtra(Constantes.EXTRA_NUMERO_DOCUMENTO))
        }
    }

    private fun configuraClickListeners() {
        _btnSalvar?.setOnClickListener { onClickBtnSalvar() }
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
                UtilDialog.showDialogAlerta(
                    this,
                    resources.getString(R.string.msg_erro_generico)
                )
            }
        }
    }

    private fun verificaFormularioValido(): Boolean {
        _inputlayoutNome?.error = ""
        _inputlayoutDocumento?.error = ""
        _inputlayoutDataNascimento?.error = ""
        _inputlayoutTelefone?.error = ""
        _inputlayoutCidade?.error = ""
        _inputlayoutEstado?.error = ""

        var isFormularioValido = true

        if (_edtNome?.text.toString().isEmpty()) {
            isFormularioValido = false
            _inputlayoutNome?.error = resources.getString(R.string.msg_erro_campo_obrigatorio)
        }

        if (_edtDocumento?.text.toString().isEmpty()) {
            isFormularioValido = false
            _inputlayoutDocumento?.error = resources.getString(R.string.msg_erro_campo_obrigatorio)
        }

        if (_edtDataNascimento?.text.toString().isEmpty()) {
            isFormularioValido = false
            _inputlayoutDataNascimento?.error =
                resources.getString(R.string.msg_erro_campo_obrigatorio)
        } else {
            val dataNascimento = formataDataNascimento(_edtDataNascimento?.text.toString())
            if (dataNascimento == null) {
                isFormularioValido = false;
                val msgErroDataNascimento = String.format(
                    resources.getString(R.string.msg_erro_data_invalida_),
                    FORMATO_DATA_NASCIMENTO
                )
                _inputlayoutDataNascimento?.error = msgErroDataNascimento;
            }
        }

        if (_edtTelefone?.text.toString().isEmpty()) {
            isFormularioValido = false
            _inputlayoutTelefone?.error = resources.getString(R.string.msg_erro_campo_obrigatorio)
        }

        if (_edtCidade?.text.toString().isEmpty()) {
            isFormularioValido = false
            _inputlayoutCidade?.error = resources.getString(R.string.msg_erro_campo_obrigatorio)
        }

        if (_edtEstado?.text.toString().isEmpty()) {
            isFormularioValido = false
            _inputlayoutEstado?.error = resources.getString(R.string.msg_erro_campo_obrigatorio)
        }

        return isFormularioValido
    }

    private fun populaModelo(): Pessoa {
        return Pessoa(
            _edtNome?.text.toString(),
            formataNumeroDocumento(_edtDocumento?.text.toString())
        ).apply {
            dataNascimento = formataDataNascimento(_edtDataNascimento?.text.toString())
            telefone = formataNumeroTelefone(_edtTelefone?.text.toString())
            bairro = _edtBairro?.text.toString()
            cidade = _edtCidade?.text.toString()
            estado = _edtEstado?.text.toString()
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
