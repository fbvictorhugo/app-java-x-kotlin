package net.fbvictorhugo.k.barreirasanitaria.data.dao.dummy

import net.fbvictorhugo.k.barreirasanitaria.data.dao.IUsuarioDAO
import net.fbvictorhugo.k.barreirasanitaria.data.model.Usuario

object UsuarioDummyDataBase : IUsuarioDAO {

    private val mDummyList: ArrayList<Usuario> = ArrayList()

    init {
        val usuario = Usuario("Victor Hugo")
        usuario.apply {
            id = 1
            login = "abc"
            senha = "123"
        }
        mDummyList.add(usuario)
    }

    override fun procurarUsuario(login: String, senha: String): Usuario? {
        if (login.trim().isNotEmpty() && senha.trim().isNotEmpty()) {
            for (usuario in mDummyList) {
                if (usuario.login == login && usuario.senha == senha) {
                    return usuario
                }
            }
        }
        return null
    }

}
