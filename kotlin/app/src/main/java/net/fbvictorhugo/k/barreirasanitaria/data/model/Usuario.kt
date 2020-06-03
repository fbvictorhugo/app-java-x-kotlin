package net.fbvictorhugo.k.barreirasanitaria.data.model

import java.util.*

data class Usuario(
    var id: Long,
    var nome: String,
    var login: String,
    var senha: String,
    var ultimoLogin: Date? = null
)
