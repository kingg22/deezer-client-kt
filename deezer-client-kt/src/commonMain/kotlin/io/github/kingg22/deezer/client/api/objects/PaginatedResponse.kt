@file:OptIn(ExperimentalDeezerClient::class, InternalDeezerClient::class)

package io.github.kingg22.deezer.client.api.objects

import io.github.kingg22.deezer.client.api.GlobalDeezerApiClient
import io.github.kingg22.deezer.client.exceptions.DeezerApiException
import io.github.kingg22.deezer.client.utils.AfterInitialize
import io.github.kingg22.deezer.client.utils.ExperimentalDeezerClient
import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.Url
import kotlinx.serialization.Serializable
import kotlin.collections.plus
import kotlin.coroutines.cancellation.CancellationException

/**
 * **Unofficial** Represent a response of [Deezer API](https://developers.deezer.com/api/).
 *
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/explorer">Deezer API Explorer</a>
 *
 * @param T The type of the items contained in the result list.
 * @property data A List containing the result of the search
 * @property checksum **_Optional_** hash generated to verify data integrity. Can be useful to validate that data has not been modified.
 * @property total The total number of items for the search
 * @property prev Link to the previous page of the search
 * @property next Link to the next page of the search
 */
@Serializable
class PaginatedResponse<T : @Serializable Any> @JvmOverloads constructor(
    val data: List<T> = emptyList(),
    val checksum: String? = null,
    val total: Int? = null,
    val prev: String? = null,
    val next: String? = null,
) {
    @JvmOverloads
    fun copy(
        data: List<T> = this.data,
        checksum: String? = this.checksum,
        total: Int? = this.total,
        prev: String? = this.prev,
        next: String? = this.next,
    ) = PaginatedResponse(data, checksum, total, prev, next)

    /**
     * Fetch the next page of the search
     *
     * @param N Type of the response. Required to parse the response, and expand the [PaginatedResponse.data] with a new result.
     * @param expand true to expand [PaginatedResponse.data] with a new result
     * @return Null if [PaginatedResponse.next] is null else a `PaginatedResponse`
     * @throws IllegalArgumentException if [PaginatedResponse.data] is not empty and types [N] != `T` (original type)
     */
    @AfterInitialize
    @JvmSynthetic
    @Throws(IllegalArgumentException::class, DeezerApiException::class, CancellationException::class)
    suspend inline fun <reified N : @Serializable Any> fetchNext(expand: Boolean = false): PaginatedResponse<N>? {
        if (next.isNullOrBlank()) return null
        if (data.isNotEmpty()) {
            require(N::class == data.first()::class) {
                "Requires type equals to fetchNext. ${N::class} != ${data.first()::class}"
            }
        }
        val result = client().httpClient.get(Url(next)).body<PaginatedResponse<N>>()
        return if (expand && data.isNotEmpty()) {
            @Suppress("UNCHECKED_CAST")
            return result.copy(data = data as List<N> + result.data)
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
     * @throws IllegalArgumentException if [PaginatedResponse.data] is not empty and types [P] != `T` (original type)
     */
    @AfterInitialize
    @JvmSynthetic
    @Throws(DeezerApiException::class, CancellationException::class)
    suspend inline fun <reified P : @Serializable Any> fetchPrevious(expand: Boolean = false): PaginatedResponse<P>? {
        if (prev.isNullOrBlank()) return null
        if (data.isNotEmpty()) {
            require(P::class == data.first()::class) {
                "Requires type equals to expand in fetchPrevious. ${P::class} != ${data.first()::class}"
            }
        }
        val result = client().httpClient.get(Url(prev)).body<PaginatedResponse<P>>()
        return if (expand && data.isNotEmpty()) {
            @Suppress("UNCHECKED_CAST")
            result.copy(data = result.data + data as List<P>)
        } else {
            result
        }
    }

    companion object {
        /** The [io.github.kingg22.deezer.client.api.DeezerApiClient] to make operations easy */
        @AfterInitialize
        @PublishedApi
        @JvmStatic
        @JvmSynthetic
        internal fun client() = GlobalDeezerApiClient.requireInstance()
    }
}
