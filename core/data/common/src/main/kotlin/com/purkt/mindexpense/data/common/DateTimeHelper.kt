package com.purkt.mindexpense.data.common

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss"

fun LocalDateTime.toIsoDateTimeStringOrNull(pattern: String = DATE_TIME_PATTERN): String? {
    try {
        return toIsoDateTimeStringOrThrowError(pattern = pattern)
    } catch (e: Throwable) {
        e.printStackTrace()
        return null
    }
}

fun LocalDateTime.toIsoDateTimeStringOrThrowError(pattern: String = DATE_TIME_PATTERN): String {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return formatter.format(this)
}

fun String.toLocalDateTimeOrNull(pattern: String = DATE_TIME_PATTERN): LocalDateTime? {
    try {
        return toLocalDateTimeOrThrowError(pattern = pattern)
    } catch (e: Throwable) {
        e.printStackTrace()
        return null
    }
}

fun String.toLocalDateTimeOrThrowError(pattern: String = DATE_TIME_PATTERN): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return LocalDateTime.parse(this, formatter)
}