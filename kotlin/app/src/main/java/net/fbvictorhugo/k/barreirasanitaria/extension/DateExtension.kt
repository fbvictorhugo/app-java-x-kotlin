package net.fbvictorhugo.k.barreirasanitaria.extension

import java.text.SimpleDateFormat
import java.util.*


fun Date.toString(formato: String): String {
    return SimpleDateFormat(formato, Locale.getDefault()).format(this)
}