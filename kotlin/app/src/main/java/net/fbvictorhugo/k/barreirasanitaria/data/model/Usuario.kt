package net.fbvictorhugo.k.barreirasanitaria.data.model

import java.util.*

data class Usuario(var nome: String) {
    var id: Long = 0
    var login: String = ""
    var senha: String = ""
    var ultimoLogin: Date? = null
}
