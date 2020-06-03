package net.fbvictorhugo.k.barreirasanitaria.data.dao.dummy

import net.fbvictorhugo.k.barreirasanitaria.data.dao.IUsuarioDAO
import net.fbvictorhugo.k.barreirasanitaria.data.model.Usuario
import java.util.*

object UsuarioDummyDataBase : IUsuarioDAO {

    override fun procurarUsuario(login: String, senha: String): Usuario? {
        return if (login.isEmpty() || senha.isEmpty()) {
            Usuario(1, "Victor Hugo", login, senha, Date())
        } else
            null
    }

}
