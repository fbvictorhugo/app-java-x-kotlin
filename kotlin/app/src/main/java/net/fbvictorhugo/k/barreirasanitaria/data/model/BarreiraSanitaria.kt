package net.fbvictorhugo.k.barreirasanitaria.data.model

data class BarreiraSanitaria(var nome: String) {

    var id: Long = 0
    lateinit var descricao: String
    lateinit var cidade: String
    lateinit var estado: String
    lateinit var bairro: String
    lateinit var endereco: String
}