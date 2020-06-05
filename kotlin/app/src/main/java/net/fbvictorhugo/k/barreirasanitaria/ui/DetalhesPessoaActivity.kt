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
import net.fbvictorhugo.k.barreirasanitaria.data.dao.IPessoaDAO
import net.fbvictorhugo.k.barreirasanitaria.data.dao.TabelasDataBase
import net.fbvictorhugo.k.barreirasanitaria.data.model.Pessoa
import java.text.SimpleDateFormat
import java.util.*


class DetalhesPessoaActivity : AppCompatActivity() {

    private val FORMATO_DATA_NASCIMENTO = "dd/MM/yyyy"
    private var isModoCadastro = false

    private var mEdtNome: TextInputEditText? = null
    private var mEdtDocumento: TextInputEditText? = null
    private var mEdtDataNascimento: TextInputEditText? = null
    private var mEdtTelefone: TextInputEditText? = null
    private var mEdtBairro: TextInputEditText? = null
    private var mEdtCidade: TextInputEditText? = null
    private var mEdtEstado: TextInputEditText? = null
    private var mInputlayoutNome: TextInputLayout? = null
    private var mInputlayoutDocumento: TextInputLayout? = null
    private var mInputlayoutDataNascimento: TextInputLayout? = null
    private var mInputlayoutTelefone: TextInputLayout? = null
    private var mInputlayoutCidade: TextInputLayout? = null
    private var mInputlayoutEstado: TextInputLayout? = null
    private var mBtnSalvar: AppCompatButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_pessoa)

        isModoCadastro = intent.getBooleanExtra(Constantes.EXTRA_MODO_CADASTRO, true)

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
        mEdtNome = findViewById(R.id.pessoa_edt_nome)
        mEdtDocumento = findViewById(R.id.pessoa_edt_documento)
        mEdtDataNascimento = findViewById(R.id.pessoa_edt_data_nascimento)
        mEdtTelefone = findViewById(R.id.pessoa_edt_telefone)
        mEdtBairro = findViewById(R.id.pessoa_edt_bairro)
        mEdtCidade = findViewById(R.id.pessoa_edt_cidade)
        mEdtEstado = findViewById(R.id.pessoa_edt_estado)
        mInputlayoutNome = findViewById(R.id.pessoa_inputlayout_nome)
        mInputlayoutDocumento = findViewById(R.id.pessoa_inputlayout_documento)
        mInputlayoutDataNascimento = findViewById(R.id.pessoa_inputlayout_data_nascimento)
        mInputlayoutTelefone = findViewById(R.id.pessoa_inputlayout_telefone)
        mInputlayoutCidade = findViewById(R.id.pessoa_inputlayout_cidade)
        mInputlayoutEstado = findViewById(R.id.pessoa_inputlayout_estado)
        mBtnSalvar = findViewById(R.id.pessoa_btn_salvar)
    }

    private fun configuraActionBar(supportActionBar: ActionBar?) {
        if (isModoCadastro) {
            supportActionBar?.title =
                resources.getString(R.string.title_activity_cadastro_pessoa)
        }
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    private fun configuraDadosTela() {
        if (isModoCadastro) {
            mEdtDocumento?.setText(intent.getStringExtra(Constantes.EXTRA_NUMERO_DOCUMENTO))
        }
    }

    private fun configuraClickListeners() {
        mBtnSalvar?.setOnClickListener { onClickBtnSalvar() }
    }

    private fun onClickBtnSalvar() {
        val mPessoaDAO = DAOFactory.getDataSource(TabelasDataBase.PESSOA) as IPessoaDAO

        if (verificaFormularioValido()) {
            val pessoa = populaModelo()

            try {
                val mensagem: String
                if (isModoCadastro) {
                    mPessoaDAO.inserir(pessoa)
                    mensagem = resources.getString(R.string.msg_cadastrado_com_sucesso)
                } else {
                    mPessoaDAO.atualizar(pessoa)
                    mensagem = resources.getString(R.string.msg_alterado_com_sucesso)
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

            } catch (exceprion: Exception) {
                UtilDialog.showDialogAlerta(
                    this,
                    resources.getString(R.string.msg_erro_generico)
                )
            }
        }
    }

    private fun verificaFormularioValido(): Boolean {
        mInputlayoutNome?.error = ""
        mInputlayoutDocumento?.error = ""
        mInputlayoutDataNascimento?.error = ""
        mInputlayoutTelefone?.error = ""
        mInputlayoutCidade?.error = ""
        mInputlayoutEstado?.error = ""

        var isFormularioValido = true

        if (mEdtNome?.text.toString().isEmpty()) {
            isFormularioValido = false
            mInputlayoutNome?.error = resources.getString(R.string.msg_erro_campo_obrigatorio)
        }

        if (mEdtDocumento?.text.toString().isEmpty()) {
            isFormularioValido = false
            mInputlayoutDocumento?.error = resources.getString(R.string.msg_erro_campo_obrigatorio)
        }

        if (mEdtDataNascimento?.text.toString().isEmpty()) {
            isFormularioValido = false
            mInputlayoutDataNascimento?.error =
                resources.getString(R.string.msg_erro_campo_obrigatorio)
        } else {
            val dataNascimento = formataDataNascimento(mEdtDataNascimento?.text.toString())
            if (dataNascimento == null) {
                isFormularioValido = false;
                val msgErroDataNascimento = String.format(
                    resources.getString(R.string.msg_erro_data_invalida_),
                    FORMATO_DATA_NASCIMENTO
                )
                mInputlayoutDataNascimento?.error = msgErroDataNascimento;
            }
        }

        if (mEdtTelefone?.text.toString().isEmpty()) {
            isFormularioValido = false
            mInputlayoutTelefone?.error = resources.getString(R.string.msg_erro_campo_obrigatorio)
        }

        if (mEdtCidade?.text.toString().isEmpty()) {
            isFormularioValido = false
            mInputlayoutCidade?.error = resources.getString(R.string.msg_erro_campo_obrigatorio)
        }

        if (mEdtEstado?.text.toString().isEmpty()) {
            isFormularioValido = false
            mInputlayoutEstado?.error = resources.getString(R.string.msg_erro_campo_obrigatorio)
        }

        return isFormularioValido
    }

    private fun populaModelo(): Pessoa {
        return Pessoa(mEdtNome?.text.toString())
            .apply {
                numeroDocumento = formataNumeroDocumento(mEdtDocumento?.text.toString())
                dataNascimento = formataDataNascimento(mEdtDataNascimento?.text.toString())
                telefone = formataNumeroTelefone(mEdtTelefone?.text.toString())
                bairro = mEdtBairro?.text.toString()
                cidade = mEdtCidade?.text.toString()
                estado = mEdtEstado?.text.toString()
            }
    }

    private fun formataNumeroDocumento(texto: String): Long {
        return texto.toLong()
    }

    private fun formataDataNascimento(texto: String): Date? {
        val sdf =
            SimpleDateFormat(FORMATO_DATA_NASCIMENTO, Locale.getDefault())
        try {
            return sdf.parse(texto)
        } catch (exception: Exception) {
            exception.printStackTrace()
            return null
        }
    }

    private fun formataNumeroTelefone(texto: String): Long {
        return texto.toLong()
    }

}
