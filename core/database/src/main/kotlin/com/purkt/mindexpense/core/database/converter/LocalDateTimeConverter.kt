package com.purkt.mindexpense.core.database.converter

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class LocalDateTimeConverter {
    @TypeConverter
    fun fromTimestampUtc(value: Long): LocalDateTime {
        return Instant.ofEpochMilli(value)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    }

    @TypeConverter
    fun toTimestampUtc(localDateTime: LocalDateTime): Long {
        return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }
}