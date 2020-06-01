package net.fbvictorhugo.k.barreirasanitaria.data.dao.dummy

import net.fbvictorhugo.k.barreirasanitaria.data.dao.IUsuarioDAO
import net.fbvictorhugo.k.barreirasanitaria.data.model.Usuario

class UsuarioDummyDAO : IUsuarioDAO {

    override fun procurarUsuario(login: String, senha: String): Usuario? {

        return if (login.isEmpty() || senha.isEmpty()) {
            val usuario = Usuario(1,"Victor Hugo")
            usuario.id = 1
            //   usuario.login = login
            //   usuario.senha = senha
            usuario.nome = "Victor Hugo"
            //  usuario.ultimoLogin = Date()
            usuario

        } else null

    }
}
