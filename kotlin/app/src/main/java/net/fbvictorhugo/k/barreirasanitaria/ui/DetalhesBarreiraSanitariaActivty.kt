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
import net.fbvictorhugo.k.barreirasanitaria.data.dao.BarreiraSanitariaDAO
import net.fbvictorhugo.k.barreirasanitaria.data.dao.DAOFactory
import net.fbvictorhugo.k.barreirasanitaria.data.dao.TabelasDataBase
import net.fbvictorhugo.k.barreirasanitaria.data.model.BarreiraSanitaria
import net.fbvictorhugo.k.barreirasanitaria.utils.Constantes
import net.fbvictorhugo.k.barreirasanitaria.utils.UtilDialog

class DetalhesBarreiraSanitariaActivty : AppCompatActivity() {

    private var _modoCadastro = false
    private var _edtNome: TextInputEditText? = null
    private var _edtDescricao: TextInputEditText? = null
    private var _edtEndereco: TextInputEditText? = null
    private var _edtBairro: TextInputEditText? = null
    private var _edtCidade: TextInputEditText? = null
    private var _edtEstado: TextInputEditText? = null
    private var _btnSalvar: AppCompatButton? = null

    private var _inputlayoutNome: TextInputLayout? = null
    private var _inputlayoutCidade: TextInputLayout? = null
    private var _inputlayoutEstado: TextInputLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_barreira_sanitaria)
        _modoCadastro = intent.getBooleanExtra(Constantes.EXTRA_MODO_CADASTRO, true)

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
        _edtNome = findViewById(R.id.barreira_edt_nome)
        _edtDescricao = findViewById(R.id.barreira_edt_descricao)
        _edtEndereco = findViewById(R.id.barreira_edt_endereco)
        _edtBairro = findViewById(R.id.barreira_edt_bairro)
        _edtCidade = findViewById(R.id.barreira_edt_cidade)
        _edtEstado = findViewById(R.id.barreira_edt_estado)
        _btnSalvar = findViewById(R.id.barreira_btn_salvar)

        _inputlayoutNome = findViewById(R.id.barreira_inputlayout_nome)
        _inputlayoutCidade = findViewById(R.id.barreira_inputlayout_cidade)
        _inputlayoutEstado = findViewById(R.id.barreira_inputlayout_estado)
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
        _btnSalvar?.setOnClickListener { onClickBtnSalvar() }
    }

    private fun onClickBtnSalvar() {
        val valido = verificaFormularioValido()
        if (valido) {
            val barreiraSanitaria = populaModelo()
            try {
                val barreiraSanitariaDAO =
                    DAOFactory.getDataSource(TabelasDataBase.BARREIRA_SANITARIA) as BarreiraSanitariaDAO

                var mensagem = ""

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
                UtilDialog.showDialogAlerta(
                    this, resources.getString(R.string.msg_erro_generico)
                )
            }
        }
    }

    private fun verificaFormularioValido(): Boolean {
        _inputlayoutNome?.error = ""
        _inputlayoutCidade?.error = ""
        _inputlayoutEstado?.error = ""
        var isFormularioValido = true

        if (_edtNome?.text.toString().isEmpty()) {
            isFormularioValido = false
            _inputlayoutNome?.error = resources.getString(R.string.msg_erro_campo_obrigatorio)
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

    private fun populaModelo(): BarreiraSanitaria {
        val barreiraSanitaria = BarreiraSanitaria(
            _edtNome?.text.toString(),
            _edtCidade?.text.toString(),
            _edtEstado?.text.toString()
        ).apply {
            descricao = _edtDescricao?.text.toString()
            endereco = _edtEndereco?.text.toString()
            bairro = _edtBairro?.text.toString()
        }
        return barreiraSanitaria
    }

}
