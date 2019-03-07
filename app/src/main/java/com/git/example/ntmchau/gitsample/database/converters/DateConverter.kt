package com.git.example.ntmchau.gitsample.database.converters

import androidx.room.TypeConverter
import com.git.example.ntmchau.gitsample.util.FORMAT_DATE_ISO
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class DateConverter {

    private var dateFormat: ThreadLocal<DateFormat> = object : ThreadLocal<DateFormat>() {
        override fun initialValue(): DateFormat {
            return SimpleDateFormat(FORMAT_DATE_ISO, Locale.US)
        }
    }

    @TypeConverter
    fun toOffsetDateTime(value: String?): Date? {
        return try {
            dateFormat.get().parse(value)
        } catch (e: Exception) {
            null
        }
    }

    @TypeConverter
    fun fromOffsetDateTime(date: Date?): String? {
        return dateFormat.get().format(date)
    }
}