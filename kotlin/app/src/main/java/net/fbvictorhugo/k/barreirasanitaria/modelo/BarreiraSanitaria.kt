package net.fbvictorhugo.k.barreirasanitaria.modelo

data class BarreiraSanitaria(
    var id: Long, var nome: String, var descricao: String,
    var cidade: String, var estado: String, var bairro: String, var endereco: String
)