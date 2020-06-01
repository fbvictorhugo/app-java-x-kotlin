package net.fbvictorhugo.k.barreirasanitaria.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import net.fbvictorhugo.k.barreirasanitaria.R
import net.fbvictorhugo.k.barreirasanitaria.data.dao.DAOFactory
import net.fbvictorhugo.k.barreirasanitaria.data.dao.IUsuarioDAO
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
        var login: String? = null
        var senha: String? = null

        mInputLayoutUsuario?.error = ""
        login = mEdtUsuario?.text.toString()

        mInputLayoutSenha?.error = ""
        senha = mEdtSenha?.text.toString()

        if (login.trim { it <= ' ' }.isEmpty()) {
            mInputLayoutUsuario!!.error = resources.getString(R.string.msg_erro_usuario_obrigatorio)
        } else if (senha.trim { it <= ' ' }.isEmpty()) {
            mInputLayoutSenha!!.error = resources.getString(R.string.msg_erro_senha_obrigatoria)
        } else {

            val usuarioDAO: IUsuarioDAO =
                DAOFactory.getDataSource(IUsuarioDAO::class) as IUsuarioDAO
            val usuario: Usuario? = usuarioDAO.procurarUsuario(login, senha)
            if (usuarioValido(usuario)) {
                val menssagem = java.lang.String.format(
                    resources.getString(R.string.msg_bem_vindo_), usuario?.nome
                )
                UtilDialog.showToast(applicationContext, menssagem)
                startActivity(Intent(applicationContext, ListaBarreirasActivity::class.java))
            }
        }


    }

    private fun usuarioValido(usuario: Usuario?): Boolean {
        return usuario != null && usuario.id > 0
    }
}
