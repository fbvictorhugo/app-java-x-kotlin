package net.fbvictorhugo.k.barreirasanitaria.data.dao

import net.fbvictorhugo.k.barreirasanitaria.data.model.Usuario

interface IUsuarioDAO {
    fun procurarUsuario(login: String, senha: String): Usuario?
}