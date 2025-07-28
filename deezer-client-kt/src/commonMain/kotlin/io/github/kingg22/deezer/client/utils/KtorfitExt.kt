package io.github.kingg22.deezer.client.utils

import de.jensklingenberg.ktorfit.ktorfit
import io.ktor.client.HttpClient
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlin.jvm.JvmSynthetic

@JvmSynthetic
internal fun createKtorfit(baseUrl: String, httpClient: HttpClient) = ktorfit {
    baseUrl(baseUrl, false)
    httpClient(httpClient)
}

@OptIn(ExperimentalSerializationApi::class)
@JvmSynthetic
internal fun getJson() = Json {
    encodeDefaults = true
    isLenient = true
    allowSpecialFloatingPointValues = true
    allowStructuredMapKeys = true

    prettyPrint = true
    ignoreUnknownKeys = true
    explicitNulls = false
    decodeEnumsCaseInsensitive = true
    useArrayPolymorphism = true
}

@JvmSynthetic
internal inline fun <reified T : @Serializable Any> decodeOrNull(json: String): T? =
    runCatching { Json.decodeFromString<T>(json) }.getOrNull()
