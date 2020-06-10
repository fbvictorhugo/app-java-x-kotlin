package net.fbvictorhugo.k.barreirasanitaria.data.dao.dummy

import net.fbvictorhugo.k.barreirasanitaria.data.dao.UsuarioDAO
import net.fbvictorhugo.k.barreirasanitaria.data.model.Usuario

object DummyUsuarioDAO : UsuarioDAO {

    private val _dummyList: ArrayList<Usuario> = ArrayList()

    init {
        val usuario = Usuario("Victor Hugo", "asd", "123", 1)
        _dummyList.add(usuario)
    }

    override fun procurarUsuario(login: String, senha: String): Usuario? {
        if (login.trim().isNotEmpty() && senha.trim().isNotEmpty()) {
            for (usuario in _dummyList) {
                if (usuario.login == login && usuario.senha == senha) {
                    return usuario
                }
            }
        }
        return null
    }

}
