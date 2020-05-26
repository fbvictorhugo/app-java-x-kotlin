package net.fbvictorhugo.k.barreirasanitaria.modelo

import java.util.*

data class Pessoa(
    var id: Long, var nome: String, var numeroDocumento: Long, var tipoDocumento: Int,
    var dataNascimento: Date, var telefone: Int, var cidade: String, var estado: String,
    var bairro: String
)