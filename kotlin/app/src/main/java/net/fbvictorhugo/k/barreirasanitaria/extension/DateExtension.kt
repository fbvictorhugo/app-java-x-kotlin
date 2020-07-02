package net.fbvictorhugo.k.barreirasanitaria.extension

import java.text.SimpleDateFormat
import java.util.*


fun Date.toString(formato: String): String {
    return SimpleDateFormat(formato, Locale.getDefault()).format(this)
}

fun Date.fromString(date: String, formato: String): Date {
    return SimpleDateFormat(formato, Locale.getDefault()).parse(date)
}