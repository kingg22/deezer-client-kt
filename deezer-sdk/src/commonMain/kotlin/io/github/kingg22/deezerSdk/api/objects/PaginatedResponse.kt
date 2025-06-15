package io.github.kingg22.deezerSdk.api.objects

import io.github.kingg22.deezerSdk.api.GlobalDeezerApiClient
import io.github.kingg22.deezerSdk.exceptions.DeezerApiException
import io.github.kingg22.deezerSdk.utils.AfterInitialize
import io.ktor.http.takeFrom
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.coroutines.cancellation.CancellationException
import kotlin.jvm.JvmStatic

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
data class PaginatedResponse<out T : @Serializable Any>(
    val data: List<T> = emptyList(),
    val checksum: String? = null,
    val total: Int? = null,
    val prev: String? = null,
    val next: String? = null,
) {
    companion object {
        /** Instance of [io.github.kingg22.deezerSdk.api.DeezerApiClient] to make requests easy. */
        @AfterInitialize
        @Transient
        @JvmStatic
        val client by lazy { GlobalDeezerApiClient.requireInstance() }
    }

    /**
     * Fetch the next page of the search
     *
     * @param R Type of the response. Required to parse the response.
     * @param expand true to expand [data] with a new result
     * @return Null if [next] is null else [PaginatedResponse]
     * @throws IllegalArgumentException if [data] is empty and try to expand it
     * @throws IllegalArgumentException if [expand] is true and types [R] != [T]
     */
    @AfterInitialize
    @Throws(IllegalArgumentException::class, DeezerApiException::class, CancellationException::class)
    suspend inline fun <reified R : @Serializable Any> fetchNext(expand: Boolean = false): PaginatedResponse<R>? {
        if (next.isNullOrBlank()) return null
        val result = client.rawExecuteAsync<PaginatedResponse<R>> { url.takeFrom(next) }
        return if (expand) {
            require(data.isNotEmpty()) { "Requires data not empty to expand it" }
            require(data.first()::class == R::class) {
                "Requires type equals to expand in fetchNext. R ${R::class} and T ${data.first()::class} mismatch"
            }
            @Suppress("UNCHECKED_CAST")
            val castedData = data as List<R>
            return result.copy(data = castedData.plus(result.data))
        } else {
            result
        }
    }

    /**
     * Fetch the previous page of the search
     *
     * @param R Type of the response. Required to parse the response
     * @return Null if [prev] is null else [PaginatedResponse]
     */
    @AfterInitialize
    @Throws(DeezerApiException::class, CancellationException::class)
    suspend inline fun <reified R : @Serializable Any> fetchPrevious(): PaginatedResponse<R>? =
        if (prev.isNullOrBlank()) {
            null
        } else {
            client.rawExecuteAsync { url.takeFrom(prev) }
        }
}
