package com.codewithfk.quickernotes.utils

import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

actual object DateUtils {
    @OptIn(ExperimentalTime::class)
    actual fun formatDate(instant: Instant, pattern: String): String {
        val dateFormatter = SimpleDateFormat(pattern, Locale.getDefault())
        val date = java.util.Date(instant.toEpochMilliseconds())
        return dateFormatter.format(date)
    }
}