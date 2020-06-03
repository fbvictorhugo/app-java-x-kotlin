package net.fbvictorhugo.k.barreirasanitaria.data.model

import java.util.*

data class Pessoa(
    var id: Long, var nome: String, var numeroDocumento: Long,var dataNascimento: Date, var telefone: Int, var cidade: String, var estado: String,
    var bairro: String
)