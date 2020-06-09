package net.fbvictorhugo.k.barreirasanitaria.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import net.fbvictorhugo.k.barreirasanitaria.R
import net.fbvictorhugo.k.barreirasanitaria.data.dao.DAOFactory
import net.fbvictorhugo.k.barreirasanitaria.data.dao.UsuarioDAO
import net.fbvictorhugo.k.barreirasanitaria.data.dao.TabelasDataBase
import net.fbvictorhugo.k.barreirasanitaria.data.model.Usuario

class LoginActivity : AppCompatActivity() {

    var mEdtUsuario: TextInputEditText? = null
    var mInputLayoutUsuario: TextInputLayout? = null
    var mEdtSenha: TextInputEditText? = null
    var mInputLayoutSenha: TextInputLayout? = null
    var mBtnEntrar: AppCompatButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        findViews()
        configuraClickListeners()
    }

    private fun findViews() {
        mEdtUsuario = findViewById(R.id.login_edt_usuario)
        mInputLayoutUsuario = findViewById(R.id.login_inputlayout_usuario)
        mEdtSenha = findViewById(R.id.login_edt_senha)
        mInputLayoutSenha = findViewById(R.id.login_inputlayout_senha)
        mBtnEntrar = findViewById(R.id.login_btn_entrar)
    }

    private fun configuraClickListeners() {
        mBtnEntrar?.setOnClickListener { clickBotaoEntrar() }
    }

    private fun clickBotaoEntrar() {
        mInputLayoutUsuario?.error = ""
        mInputLayoutSenha?.error = ""

        val login = mEdtUsuario?.text.toString()
        val senha = mEdtSenha?.text.toString()

        if (login.trim().isEmpty()) {
            mInputLayoutUsuario?.error = resources.getString(R.string.msg_erro_campo_obrigatorio)
        } else if (senha.trim().isEmpty()) {
            mInputLayoutSenha?.error = resources.getString(R.string.msg_erro_campo_obrigatorio)
        } else {

            val usuarioDAO: UsuarioDAO =DAOFactory.getDataSource(TabelasDataBase.USUARIO) as UsuarioDAO
            val usuario: Usuario? = usuarioDAO.procurarUsuario(login, senha)

            if (usuarioValido(usuario)) {
                val intent = Intent(this, ListaBarreirasActivity::class.java)
                intent.putExtra(Constantes.EXTRA_ID_USURARIO, usuario?.id)
                intent.putExtra(Constantes.EXTRA_NOME_USURARIO, usuario?.nome)
                startActivity(intent)
                finish()
            } else {
                UtilDialog.showDialogAlerta(
                    this,
                    resources.getString(R.string.msg_usuario_nao_encontrado)
                )
            }
        }
    }

    private fun usuarioValido(usuario: Usuario?): Boolean {
        return usuario != null && usuario.id > 0
    }
}
