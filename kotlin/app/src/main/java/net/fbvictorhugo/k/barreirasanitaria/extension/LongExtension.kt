package net.fbvictorhugo.k.barreirasanitaria.extension

fun Long?.maiorQue(comparado: Long): Boolean {
    return if (this == null) {
        false
    } else {
        this > comparado
    }
}
