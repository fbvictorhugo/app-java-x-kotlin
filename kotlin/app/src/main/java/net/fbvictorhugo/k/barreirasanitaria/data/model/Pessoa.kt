package net.fbvictorhugo.k.barreirasanitaria.data.model

import java.util.*

data class Pessoa(
    var nome: String,
    var numeroDocumento: Long,
    var id: Long = 0
) {
    var dataNascimento: Date? = null
    var telefone: Long? = null
    var cidade: String = ""
    var estado: String = ""
    var bairro: String = ""
}
