package net.fbvictorhugo.k.barreirasanitaria.data.model

import java.util.*

data class Usuario(var nome: String) {
    var id: Long = 0
    lateinit var login: String
    lateinit var senha: String
    var ultimoLogin: Date? = null
}
