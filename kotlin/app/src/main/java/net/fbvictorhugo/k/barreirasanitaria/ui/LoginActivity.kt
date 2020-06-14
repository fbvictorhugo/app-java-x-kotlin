package net.fbvictorhugo.k.barreirasanitaria.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import net.fbvictorhugo.k.barreirasanitaria.R
import net.fbvictorhugo.k.barreirasanitaria.data.dao.DAOFactory
import net.fbvictorhugo.k.barreirasanitaria.data.dao.TabelasDataBase
import net.fbvictorhugo.k.barreirasanitaria.data.dao.UsuarioDAO
import net.fbvictorhugo.k.barreirasanitaria.data.model.Usuario
import net.fbvictorhugo.k.barreirasanitaria.utils.Constantes
import net.fbvictorhugo.k.barreirasanitaria.utils.UtilDialog

class LoginActivity : AppCompatActivity() {

    private var _edtUsuario: TextInputEditText? = null
    private var _inputLayoutUsuario: TextInputLayout? = null
    private var _edtSenha: TextInputEditText? = null
    private var _inputLayoutSenha: TextInputLayout? = null
    private var _btnEntrar: AppCompatButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViews()
        configuraClickListeners()
    }

    private fun findViews() {
        _edtUsuario = findViewById(R.id.login_edt_usuario)
        _inputLayoutUsuario = findViewById(R.id.login_inputlayout_usuario)
        _edtSenha = findViewById(R.id.login_edt_senha)
        _inputLayoutSenha = findViewById(R.id.login_inputlayout_senha)
        _btnEntrar = findViewById(R.id.login_btn_entrar)
    }

    private fun configuraClickListeners() {
        _btnEntrar?.setOnClickListener { clickBotaoEntrar() }
    }

    private fun clickBotaoEntrar() {
        _inputLayoutUsuario?.error = ""
        _inputLayoutSenha?.error = ""

        val login = _edtUsuario?.text.toString()
        val senha = _edtSenha?.text.toString()

        if (login.trim().isEmpty()) {
            _inputLayoutUsuario?.error = resources.getString(R.string.msg_erro_campo_obrigatorio)
        } else if (senha.trim().isEmpty()) {
            _inputLayoutSenha?.error = resources.getString(R.string.msg_erro_campo_obrigatorio)
        } else {

            val usuarioDAO: UsuarioDAO =
                DAOFactory.getDataSource(TabelasDataBase.USUARIO) as UsuarioDAO
            val usuario: Usuario? = usuarioDAO.procurarUsuario(login, senha)

            if (usuarioValido(usuario)) {
                val intent = Intent(this, ListaBarreirasActivity::class.java)
                    .apply {
                        putExtra(Constantes.EXTRA_ID_USURARIO, usuario?.id)
                        putExtra(Constantes.EXTRA_NOME_USURARIO, usuario?.nome)
                    }

                startActivity(intent)
                finish()
            } else {
                UtilDialog.showDialogOK(
                    this, resources.getString(R.string.msg_usuario_nao_encontrado)
                )
            }
        }
    }

    private fun usuarioValido(usuario: Usuario?): Boolean {
        return usuario != null && usuario.id > 0
    }
}
