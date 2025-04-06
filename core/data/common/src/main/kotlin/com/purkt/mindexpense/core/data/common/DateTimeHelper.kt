package com.purkt.mindexpense.core.data.common

import com.purkt.mindexpense.core.logging.AppLogger
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"
const val DATE_FULL_PATTERN = "EEEE, d MMMM yyyy"
const val TIME_12_HOUR_FORMAT_PATTERN = "hh:mm a"
const val DATE_TIME_12_HOUR_FORMAT_PATTERN = "EEE, d MMM yyyy\n$TIME_12_HOUR_FORMAT_PATTERN"

fun LocalDateTime.toDateTimeStringOrNull(pattern: String = DATE_TIME_PATTERN): String? {
    try {
        return toDateTimeStringOrThrowError(pattern = pattern)
    } catch (e: Throwable) {
        AppLogger.e(e)
        return null
    }
}

fun LocalDateTime.toDateTimeStringOrThrowError(pattern: String = DATE_TIME_PATTERN): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return formatter.format(this)
}

fun String.toLocalDateTimeOrNull(pattern: String = DATE_TIME_PATTERN): LocalDateTime? {
    try {
        return toLocalDateTimeOrThrowError(pattern = pattern)
    } catch (e: Throwable) {
        AppLogger.e(e)
        return null
    }
}

fun String.toLocalDateTimeOrThrowError(pattern: String = DATE_TIME_PATTERN): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return LocalDateTime.parse(this, formatter)
}