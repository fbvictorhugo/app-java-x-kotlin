package net.fbvictorhugo.k.barreirasanitaria.data.model

data class BarreiraSanitaria(
    var nome: String,
    var cidade: String,
    var estado: String,
    var id: Long = 0
) {
    lateinit var descricao: String
    lateinit var bairro: String
    lateinit var endereco: String
}