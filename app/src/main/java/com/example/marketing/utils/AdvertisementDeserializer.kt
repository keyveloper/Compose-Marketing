package com.example.marketing.utils

import com.example.marketing.domain.Advertisement
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type

class AdvertisementDeserializer: JsonDeserializer<Advertisement> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Advertisement {
        val nonNullJson = json ?: throw JsonParseException("JsonElement is null")
        val nonNullContext = context ?: throw JsonParseException("JsonDeserializationContext is null")

        val jsonObject = nonNullJson.asJsonObject
        val reviewType = jsonObject.get("reviewType")?.asString
            ?: throw JsonParseException("reviewType field is missing or null")

        return if (reviewType == "VISITED") {
            nonNullContext.deserialize(nonNullJson,
                Advertisement.AdvertisementWithLocation::class.java)
        } else {
            nonNullContext.deserialize(nonNullJson, Advertisement.AdvertisementGeneral::class.java)
        }
    }
}