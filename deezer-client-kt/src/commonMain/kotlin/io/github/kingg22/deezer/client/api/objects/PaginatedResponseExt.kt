@file:JvmName("-PaginatedResponseExt")

package io.github.kingg22.deezer.client.api.objects

import io.github.kingg22.deezer.client.api.DeezerApiClient
import io.github.kingg22.deezer.client.exceptions.DeezerApiException
import io.github.kingg22.deezer.client.utils.AfterInitialize
import io.github.kingg22.deezer.client.utils.ExperimentalDeezerClient
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.Serializable
import kotlin.coroutines.cancellation.CancellationException

/**
 * _Experimental_ Fetch the next page of the search
 *
 * @param N Type of the response. Required to parse the response, and expand the [PaginatedResponse.data] with a new result.
 * @param httpClient The [HttpClient] to use to fetch the next page,
 * is recommended to have installed [DeezerClientPlugin][io.github.kingg22.deezer.client.api.DeezerClientPlugin]
 * @param expand true to expand [PaginatedResponse.data] with a new result
 * @return Null if [PaginatedResponse.next] is null else a `PaginatedResponse`
 */
@JvmSynthetic
@ExperimentalDeezerClient
@Throws(DeezerApiException::class, CancellationException::class)
suspend inline fun <reified N : @Serializable Any> PaginatedResponse<N>.fetchNext(
    httpClient: HttpClient,
    expand: Boolean = false,
): PaginatedResponse<N>? {
    if (next.isNullOrBlank()) return null
    val result = httpClient.get(Url(next!!)).body<PaginatedResponse<N>>()
    return if (expand && data.isNotEmpty()) {
        return result.copy(data = data + result.data)
    } else {
        result
    }
}

/**
 * _Experimental_ Fetch the previous page of the search
 *
 * @param P Type of the response. Required to parse the response, and expand the [PaginatedResponse.data] with a new result.
 * @param httpClient The [HttpClient] to use to fetch the next page,
 * is recommended to have installed [DeezerClientPlugin][io.github.kingg22.deezer.client.api.DeezerClientPlugin]
 * @param expand true to expand [PaginatedResponse.data] with a new result
 * @return Null if [PaginatedResponse.next] is null else [PaginatedResponse]
 */
@JvmSynthetic
@ExperimentalDeezerClient
@Throws(DeezerApiException::class, CancellationException::class)
suspend inline fun <reified P : @Serializable Any> PaginatedResponse<P>.fetchPrevious(
    httpClient: HttpClient,
    expand: Boolean = false,
): PaginatedResponse<P>? {
    if (prev.isNullOrBlank()) return null
    val result = httpClient.get(Url(prev!!)).body<PaginatedResponse<P>>()
    return if (expand && data.isNotEmpty()) {
        result.copy(data = result.data + data)
    } else {
        result
    }
}

/**
 * Fetch the next page of the search
 *
 * @param N Type of the response. Required to parse the response, and expand the [PaginatedResponse.data] with a new result.
 * @param client The [DeezerApiClient] to use to fetch the next page
 * @param expand true to expand [PaginatedResponse.data] with a new result
 * @return Null if [PaginatedResponse.next] is null else a `PaginatedResponse`
 */
@JvmSynthetic
@Throws(DeezerApiException::class, CancellationException::class)
suspend inline fun <reified N : @Serializable Any> PaginatedResponse<N>.fetchNext(
    client: DeezerApiClient,
    expand: Boolean = false,
): PaginatedResponse<N>? = this.fetchNext(client.httpClient, expand)

/**
 * Fetch the previous page of the search
 *
 * @param P Type of the response. Required to parse the response, and expand the [PaginatedResponse.data] with a new result.
 * @param client The [DeezerApiClient] to use to fetch the previous page
 * @param expand true to expand [PaginatedResponse.data] with a new result
 * @return Null if [PaginatedResponse.next] is null else [PaginatedResponse]
 */
@JvmSynthetic
@Throws(DeezerApiException::class, CancellationException::class)
suspend inline fun <reified P : @Serializable Any> PaginatedResponse<P>.fetchPrevious(
    client: DeezerApiClient,
    expand: Boolean = false,
): PaginatedResponse<P>? = this.fetchPrevious(client.httpClient, expand)

/**
 * Fetch the next page of the search
 *
 * @param N Type of the response. Required to parse the response, and expand the [PaginatedResponse.data] with a new result.
 * @param expand true to expand [PaginatedResponse.data] with a new result
 * @return Null if [PaginatedResponse.next] is null else a `PaginatedResponse`
 */
@Suppress("DEPRECATION", "DEPRECATION_ERROR")
@Deprecated(
    "Use fetchNext(client: DeezerApiClient, expand: Boolean) instead, pass a client explicitly",
    ReplaceWith("fetchNext(client, expand)", "io.github.kingg22.deezer.client.api.objects.fetchNext"),
    level = DeprecationLevel.ERROR,
)
@AfterInitialize
@JvmSynthetic
@Throws(DeezerApiException::class, CancellationException::class)
suspend inline fun <reified N : @Serializable Any> PaginatedResponse<N>.fetchNext(
    expand: Boolean = false,
): PaginatedResponse<N>? =
    this.fetchNext(io.github.kingg22.deezer.client.api.GlobalDeezerApiClient.requireInstance(), expand)

/**
 * Fetch the previous page of the search
 *
 * @param P Type of the response. Required to parse the response, and expand the [PaginatedResponse.data] with a new result.
 * @param expand true to expand [PaginatedResponse.data] with a new result
 * @return Null if [PaginatedResponse.next] is null else [PaginatedResponse]
 */
@Suppress("DEPRECATION", "DEPRECATION_ERROR")
@Deprecated(
    "Use fetchPrevious(client: DeezerApiClient, expand: Boolean) instead, pass a client explicitly",
    ReplaceWith("fetchPrevious(client, expand)", "io.github.kingg22.deezer.client.api.objects.fetchPrevious"),
    level = DeprecationLevel.ERROR,
)
@AfterInitialize
@JvmSynthetic
@Throws(DeezerApiException::class, CancellationException::class)
suspend inline fun <reified P : @Serializable Any> PaginatedResponse<P>.fetchPrevious(
    expand: Boolean = false,
): PaginatedResponse<P>? =
    this.fetchPrevious(io.github.kingg22.deezer.client.api.GlobalDeezerApiClient.requireInstance(), expand)
