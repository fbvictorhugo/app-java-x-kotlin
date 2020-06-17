package net.fbvictorhugo.k.barreirasanitaria.data.model

import java.util.*

data class Questionario(
    val id: Long = 0,
    val barreiraId: Long = 0,
    val pessoaId: Long = 0
) {
    var viagemExterior = false
    var sintomaFebre = false
    var sintomaCoriza = false
    var sintomaTosse = false
    var sintomaCancaco = false
    var sintomaPerdaPaladar = false
    var sintomaFaltaAr = false
    var sintomaContatoComEnfermos = false
    var observacoes: String = ""
    var dataResposta: Date = Date()
}
