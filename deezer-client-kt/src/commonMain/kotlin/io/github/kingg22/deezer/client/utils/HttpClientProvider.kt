@file:JvmName("HttpClientProvider")

package io.github.kingg22.deezer.client.utils

import io.ktor.client.utils.buildHeaders
import io.ktor.http.HttpHeaders
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlin.jvm.JvmName

/** Configure a [Json] with default configuration to be compatible with the Deezer API */
@OptIn(ExperimentalSerializationApi::class)
@InternalDeezerClient
fun getDefaultJson() = Json {
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

/**
 * _Advanced_ Configure a [io.ktor.http.Headers] with default configuration to be compatible with the Deezer API,
 * this is not needed in normal cases.
 * @see io.github.kingg22.deezer.client.api.DeezerPluginConfig.includeDefaultHeaders
 */
@InternalDeezerClient
fun getDefaultDeezerHeaders() = buildHeaders {
    append(HttpHeaders.Origin, "https://www.deezer.com")
    append(HttpHeaders.Referrer, "https://www.deezer.com")
    append("DNT", "1")
    append("Sec-Fetch-Dest", "empty")
    append("Sec-Fetch-Mode", "cors")
    append("Sec-Fetch-Site", "same-site")
}
