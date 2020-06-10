package net.fbvictorhugo.k.barreirasanitaria.data.model

import java.util.*

data class Questionario(
    val id: Long = 0,
    val barreiraId: Long = 0,
    val pessoaId: Long = 0
) {
    val viagemExterior = false
    val sintomaFebre = false
    val sintomaCoriza = false
    val sintomaCancaco = false
    val sintomaPerdaPaladar = false
    val sintomaFaltaAr = false
    val sintomaContatoComEnfermos = false
    val observacoes: String = ""
    val dataResposta: Date = Date()
}
