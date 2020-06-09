package net.fbvictorhugo.k.barreirasanitaria.data.model

import java.util.*

data class Pessoa(var nome: String) {
    var id: Long = 0
    var numeroDocumento: Long = 0
    var dataNascimento: Date? = null
    var telefone: Long? = null
    lateinit var cidade: String
    lateinit var estado: String
    lateinit var bairro: String
}
