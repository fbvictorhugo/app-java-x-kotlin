package net.fbvictorhugo.k.barreirasanitaria.extension

fun Long?.maiorQue(comparado: Long): Boolean {
    return this?.compareTo(comparado)!! > 0
}
