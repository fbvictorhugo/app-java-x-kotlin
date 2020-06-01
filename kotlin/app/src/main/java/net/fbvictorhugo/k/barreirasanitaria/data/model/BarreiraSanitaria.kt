package net.fbvictorhugo.k.barreirasanitaria.data.model

data class BarreiraSanitaria(
    var id: Long, var nome: String, var descricao: String,
    var cidade: String, var estado: String, var bairro: String, var endereco: String
)