package net.fbvictorhugo.k.barreirasanitaria.data.model

import java.util.*

data class Usuario(
    val nome: String,
    val login: String,
    val senha: String,
    val id: Long = 0
) {
    lateinit var ultimoLogin: Date
}
