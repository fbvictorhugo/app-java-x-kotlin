package net.fbvictorhugo.k.barreirasanitaria.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*
import net.fbvictorhugo.k.barreirasanitaria.R
import net.fbvictorhugo.k.barreirasanitaria.data.dao.DAOFactory
import net.fbvictorhugo.k.barreirasanitaria.data.dao.TabelasDataBase
import net.fbvictorhugo.k.barreirasanitaria.data.dao.UsuarioDAO
import net.fbvictorhugo.k.barreirasanitaria.data.model.Usuario
import net.fbvictorhugo.k.barreirasanitaria.utils.Constantes
import net.fbvictorhugo.k.barreirasanitaria.utils.UtilDialog

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        configuraClickListeners()
    }

    private fun configuraClickListeners() {
        login_btn_entrar.setOnClickListener { clickBotaoEntrar() }
    }

    private fun clickBotaoEntrar() {
        login_inputlayout_usuario.error = ""
        login_inputlayout_usuario.error = ""

        val login = login_edt_usuario.text.toString()
        val senha = login_edt_senha.text.toString()

        if (login.trim().isEmpty()) {
            login_inputlayout_usuario.error =
                resources.getString(R.string.msg_erro_campo_obrigatorio)
        } else if (senha.trim().isEmpty()) {
            login_inputlayout_senha.error = resources.getString(R.string.msg_erro_campo_obrigatorio)
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
