package net.fbvictorhugo.k.barreirasanitaria.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import net.fbvictorhugo.k.barreirasanitaria.R
import net.fbvictorhugo.k.barreirasanitaria.data.dao.DAOFactory
import net.fbvictorhugo.k.barreirasanitaria.data.dao.IBarreiraSanitariaDAO
import net.fbvictorhugo.k.barreirasanitaria.data.dao.TabelasDataBase
import net.fbvictorhugo.k.barreirasanitaria.data.model.BarreiraSanitaria

class DetalhesBarreiraSanitariaActivty : AppCompatActivity() {

    private var isModoCadastro = false
    private var mEdtNome: TextInputEditText? = null
    private var mEdtDescricao: TextInputEditText? = null
    private var mEdtEndereco: TextInputEditText? = null
    private var mEdtBairro: TextInputEditText? = null
    private var mEdtCidade: TextInputEditText? = null
    private var mEdtEstado: TextInputEditText? = null
    private var mBtnSalvar: AppCompatButton? = null
    private var mBarreiraSanitariaDAO: IBarreiraSanitariaDAO? = null
    private var mInputlayoutNome: TextInputLayout? = null
    private var mInputlayoutCidade: TextInputLayout? = null
    private var mInputlayoutEstado: TextInputLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_barreira_sanitaria)
        isModoCadastro = intent.getBooleanExtra(Constantes.EXTRA_MODO_CADASTRO, true)

        mBarreiraSanitariaDAO =
            DAOFactory.getDataSource(TabelasDataBase.BARREIRA_SANITARIA) as IBarreiraSanitariaDAO?

        findViews()
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

    private fun findViews() {
        mEdtNome = findViewById(R.id.barreira_edt_nome)
        mEdtDescricao = findViewById(R.id.barreira_edt_descricao)
        mEdtEndereco = findViewById(R.id.barreira_edt_endereco)
        mEdtBairro = findViewById(R.id.barreira_edt_bairro)
        mEdtCidade = findViewById(R.id.barreira_edt_cidade)
        mEdtEstado = findViewById(R.id.barreira_edt_estado)
        mBtnSalvar = findViewById(R.id.barreira_btn_salvar)

        mInputlayoutNome = findViewById(R.id.barreira_inputlayout_nome)
        mInputlayoutCidade = findViewById(R.id.barreira_inputlayout_cidade)
        mInputlayoutEstado = findViewById(R.id.barreira_inputlayout_estado)
    }

    fun configuraActionBar(supportActionBar: ActionBar?) {
        if (isModoCadastro) {
            supportActionBar?.title =
                resources.getString(R.string.title_activity_cadastro_barreira_sanitaria)
        }
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }

    fun configuraClickListeners() {
        mBtnSalvar?.setOnClickListener { onClickBtnSalvar() }
    }

    fun onClickBtnSalvar() {
        val valido = verificaFormularioValido()
        if (valido) {
            val barreiraSanitaria = populaModelo()
            try {
                var mensagem = ""

                if (isModoCadastro) {
                    mBarreiraSanitariaDAO!!.inserir(barreiraSanitaria)
                    mensagem = resources.getString(R.string.msg_cadastrado_com_sucesso)
                } else {
                    mBarreiraSanitariaDAO!!.atualizar(barreiraSanitaria)
                    mensagem = resources.getString(R.string.msg_alterado_com_sucesso)
                }

                UtilDialog.showDialogOK(this, mensagem,
                    DialogInterface.OnClickListener { dialogInterface, i -> finish() })

            } catch (exception: Exception) {
                UtilDialog.showDialogAlerta(
                    this, resources.getString(R.string.msg_erro_generico)
                )
            }
        }
    }

    fun verificaFormularioValido(): Boolean {
        mInputlayoutNome?.error = ""
        mInputlayoutCidade?.error = ""
        mInputlayoutEstado?.error = ""
        var isFormularioValido = true

        if (mEdtNome?.text.toString().isEmpty()) {
            isFormularioValido = false
            mInputlayoutNome?.error = resources.getString(R.string.msg_erro_campo_obrigatorio)
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

    fun populaModelo(): BarreiraSanitaria {
        return BarreiraSanitaria(mEdtNome?.text.toString())
            .apply {
                descricao = mEdtDescricao?.text.toString()
                endereco = mEdtEndereco?.text.toString()
                bairro = mEdtBairro?.text.toString()
                cidade = mEdtCidade?.text.toString()
                estado = mEdtEstado?.text.toString()
            }
    }

}
