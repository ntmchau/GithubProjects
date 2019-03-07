package com.git.example.ntmchau.gitsample.api.gsonconverter

import com.git.example.ntmchau.gitsample.util.FORMAT_DATE_ISO
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class GsonUTCDateAdapter : JsonDeserializer<Date> {

    private val dateSSSFormat: SimpleDateFormat = SimpleDateFormat(FORMAT_DATE_ISO, Locale.US)

    init {
        dateSSSFormat.timeZone = TimeZone.getTimeZone("UTC")
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Date {
        return try {
            dateSSSFormat.parse(json.asString)
        } catch (e: ParseException) {
            throw JsonParseException(e)
        }

    }
}