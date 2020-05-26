package net.fbvictorhugo.k.barreirasanitaria.modelo

import java.util.*

data class Usuario(
    var id: Long, var nome: String, var login: String, var senha: String, var ultimoLogin: Date
)