@file:JvmName("-PaginatedResponseExt")
@file:OptIn(InternalDeezerClient::class)

package io.github.kingg22.deezer.client.api.objects

import io.github.kingg22.deezer.client.exceptions.DeezerApiException
import io.github.kingg22.deezer.client.utils.AfterInitialize
import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.Url
import kotlinx.serialization.Serializable
import kotlin.collections.plus
import kotlin.coroutines.cancellation.CancellationException
import kotlin.jvm.JvmName
import kotlin.jvm.JvmSynthetic

/**
 * Fetch the next page of the search
 *
 * @param N Type of the response. Required to parse the response, and expand the [PaginatedResponse.data] with a new result.
 * @param expand true to expand [PaginatedResponse.data] with a new result
 * @return Null if [PaginatedResponse.next] is null else a `PaginatedResponse`
 */
@AfterInitialize
@JvmSynthetic
@Throws(DeezerApiException::class, CancellationException::class)
suspend inline fun <reified N : @Serializable Any> PaginatedResponse<N>.fetchNext(
    expand: Boolean = false,
): PaginatedResponse<N>? {
    if (next.isNullOrBlank()) return null
    val result = client().httpClient.get(Url(next)).body<PaginatedResponse<N>>()
    return if (expand && data.isNotEmpty()) {
        return result.copy(data = data + result.data)
    } else {
        result
    }
}

/**
 * Fetch the previous page of the search
 *
 * @param P Type of the response. Required to parse the response, and expand the [PaginatedResponse.data] with a new result.
 * @param expand true to expand [PaginatedResponse.data] with a new result
 * @return Null if [PaginatedResponse.next] is null else [PaginatedResponse]
 */
@AfterInitialize
@JvmSynthetic
@Throws(DeezerApiException::class, CancellationException::class)
suspend inline fun <reified P : @Serializable Any> PaginatedResponse<P>.fetchPrevious(
    expand: Boolean = false,
): PaginatedResponse<P>? {
    if (prev.isNullOrBlank()) return null
    val result = client().httpClient.get(Url(prev)).body<PaginatedResponse<P>>()
    return if (expand && data.isNotEmpty()) {
        result.copy(data = result.data + data)
    } else {
        result
    }
}
