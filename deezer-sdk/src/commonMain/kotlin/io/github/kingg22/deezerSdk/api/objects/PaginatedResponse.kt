package io.github.kingg22.deezerSdk.api.objects

import io.github.kingg22.deezerSdk.api.GlobalDeezerApiClient
import io.github.kingg22.deezerSdk.exceptions.DeezerApiException
import io.github.kingg22.deezerSdk.utils.AfterInitialize
import io.github.kingg22.deezerSdk.utils.ForJavaDeezerSdk
import io.github.kingg22.deezerSdk.utils.HttpClientProvider
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Url
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.cancellation.CancellationException
import kotlin.jvm.JvmStatic
import kotlin.jvm.Throws
import kotlin.reflect.KClass

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
data class PaginatedResponse<out T : @Serializable Any> @JvmOverloads constructor(
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
     * @param N Type of the response. Required to parse the response, and expand the [data] with a new result.
     * @param expand true to expand [data] with a new result
     * @return Null if [next] is null else [PaginatedResponse]
     * @throws IllegalArgumentException if [data] is not empty and types [N] != [T]
     */
    @AfterInitialize
    @Throws(IllegalArgumentException::class, DeezerApiException::class, CancellationException::class)
    suspend inline fun <reified N : @Serializable Any> fetchNext(expand: Boolean = false): PaginatedResponse<N>? {
        if (next.isNullOrBlank()) return null
        if (data.isNotEmpty()) {
            require(N::class == data.first()::class) {
                "Requires type equals to fetchNext. ${N::class} != ${data.first()::class}"
            }
        }
        val result = client.httpClient.get(Url(next)).body<PaginatedResponse<N>>()
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
     * @param P Type of the response. Required to parse the response, and expand the [data] with a new result.
     * @param expand true to expand [data] with a new result
     * @return Null if [prev] is null else [PaginatedResponse]
     * @throws IllegalArgumentException if [data] is not empty and types [P] != [T]
     */
    @AfterInitialize
    @Throws(DeezerApiException::class, CancellationException::class)
    suspend inline fun <reified P : @Serializable Any> fetchPrevious(expand: Boolean = false): PaginatedResponse<P>? {
        if (prev.isNullOrBlank()) return null
        if (data.isNotEmpty()) {
            require(P::class == data.first()::class) {
                "Requires type equals to expand in fetchPrevious. ${P::class} != ${data.first()::class}"
            }
        }
        val result = client.httpClient.get(Url(prev)).body<PaginatedResponse<P>>()
        return if (expand && data.isNotEmpty()) {
            @Suppress("UNCHECKED_CAST")
            result.copy(data = result.data + data as List<P>)
        } else {
            result
        }
    }

    // -- Java --
    @AfterInitialize
    @ForJavaDeezerSdk
    @JvmName("fetchNext")
    @JvmOverloads
    @Throws(IllegalArgumentException::class, DeezerApiException::class, CancellationException::class)
    fun <N : @Serializable Any> fetchNextBlocking(serializer: KSerializer<N>, expand: Boolean = false) =
        runBlocking { fetchNextJavaInternal(expand, serializer) }

    @AfterInitialize
    @ForJavaDeezerSdk
    @JvmName("fetchNextFuture")
    @JvmOverloads
    @Throws(IllegalArgumentException::class, DeezerApiException::class, CancellationException::class)
    fun <N : @Serializable Any> fetchNextJava(
        serializer: KSerializer<N>,
        expand: Boolean = false,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ) = CoroutineScope(coroutineContext).future { fetchNextJavaInternal(expand, serializer) }

    @ForJavaDeezerSdk
    @JvmSynthetic
    private suspend fun <N : @Serializable Any> fetchNextJavaInternal(
        expand: Boolean = false,
        serializer: KSerializer<N>,
    ): PaginatedResponse<N>? {
        if (next.isNullOrBlank()) return null
        if (data.isNotEmpty()) {
            kSerializerCheck(serializer, data.first()::class, "fetchNext")
        }
        val resultString = client.httpClient.get(Url(next)).bodyAsText()
        val result = HttpClientProvider.getJson().decodeFromString(serializer(serializer), resultString)

        return if (expand && data.isNotEmpty()) {
            @Suppress("UNCHECKED_CAST")
            result.copy(data = data as List<N> + result.data)
        } else {
            result
        }
    }

    @AfterInitialize
    @ForJavaDeezerSdk
    @JvmName("fetchPrevious")
    @JvmOverloads
    @Throws(IllegalArgumentException::class, DeezerApiException::class, CancellationException::class)
    fun <N : @Serializable Any> fetchPrevBlocking(serializer: KSerializer<N>, expand: Boolean = false) =
        runBlocking { fetchPreviousJavaInternal(expand, serializer) }

    @AfterInitialize
    @ForJavaDeezerSdk
    @JvmName("fetchPreviousFuture")
    @JvmOverloads
    @Throws(IllegalArgumentException::class, DeezerApiException::class, CancellationException::class)
    fun <N : @Serializable Any> fetchPreviousJava(
        serializer: KSerializer<N>,
        expand: Boolean = false,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ) = CoroutineScope(coroutineContext).future { fetchPreviousJavaInternal(expand, serializer) }

    @ForJavaDeezerSdk
    @JvmSynthetic
    private suspend fun <N : @Serializable Any> fetchPreviousJavaInternal(
        expand: Boolean = false,
        serializer: KSerializer<N>,
    ): PaginatedResponse<N>? {
        if (prev.isNullOrBlank()) return null
        if (data.isNotEmpty()) {
            kSerializerCheck(serializer, data.first()::class, "fetchPrevious")
        }
        val resultString = client.httpClient.get(Url(prev)).bodyAsText()
        val result = HttpClientProvider.getJson().decodeFromString(serializer(serializer), resultString)

        return if (expand && data.isNotEmpty()) {
            @Suppress("UNCHECKED_CAST")
            result.copy(data = result.data + data as List<N>)
        } else {
            result
        }
    }

    private fun kSerializerCheck(serializer: KSerializer<*>, firstClass: KClass<*>, action: String) {
        require(
            serializer.descriptor.serialName.let { name ->
                Class.forName(name.replace('/', '.')).kotlin
            } == firstClass,
        ) {
            "Requires type equals to $action.  $firstClass != ${serializer.descriptor.serialName}"
        }
    }
}
