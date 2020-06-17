package net.fbvictorhugo.k.barreirasanitaria.data.model

data class BarreiraSanitaria(
    var nome: String,
    var cidade: String,
    var estado: String,
    var id: Long = 0,
    var descricao: String = "",
    var bairro: String = "",
    var endereco: String = ""
)