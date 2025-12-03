package io.github.kingg22.deezer.client.api.objects

import io.github.kingg22.deezer.client.api.DeezerApiClient
import io.github.kingg22.deezer.client.api.DeezerApiJavaClient
import io.github.kingg22.deezer.client.utils.DeezerApiPoko
import io.github.kingg22.deezer.client.utils.getDefaultJson
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.serializer
import org.jetbrains.annotations.Blocking
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Suppress("unused")
@DeezerApiPoko
@Serializable
actual class PaginatedResponse<out T : @Serializable Any> @JvmOverloads actual constructor(
    actual val data: List<T>,
    actual val checksum: String?,
    actual val total: Int?,
    actual val prev: String?,
    actual val next: String?,
) {
    actual fun copy(
        data: List<@UnsafeVariance T>,
        checksum: String?,
        total: Int?,
        prev: String?,
        next: String?,
    ): PaginatedResponse<T> = PaginatedResponse(data, checksum, total, prev, next)

    /* -- Implementation Zone -- */
    private suspend inline fun fetchNext(
        httpClient: HttpClient,
        expand: Boolean,
        serializer: KSerializer<T>,
    ): PaginatedResponse<T>? {
        if (next.isNullOrBlank()) return null
        val resultString = httpClient.get(Url(next)).bodyAsText()
        val result = getDefaultJson().decodeFromString(serializer(serializer), resultString)
        return if (expand && data.isNotEmpty()) {
            result.copy(data = data + result.data)
        } else {
            result
        }
    }

    private suspend inline fun fetchPrevious(
        httpClient: HttpClient,
        expand: Boolean,
        serializer: KSerializer<T>,
    ): PaginatedResponse<T>? {
        if (prev.isNullOrBlank()) return null
        val resultString = httpClient.get(Url(prev)).bodyAsText()
        val result = getDefaultJson().decodeFromString(serializer(serializer), resultString)
        return if (expand && data.isNotEmpty()) {
            result.copy(data = result.data + data)
        } else {
            result
        }
    }

    /*
    -- Public Zone --
    -- Fetch Next ZONE --
     */

    /**
     * Fetch the next page of the search blocking the thread.
     *
     * Usage:
     * ```java
     * DeezerApiJavaClient client; // Initialized somewhere
     * PaginatedResponse<User> page; // Can be retrieved from other methods
     * PaginatedResponse<User> nextPage = PaginatedResponses.fetchNext(client, page, User.class));
     * ```
     *
     * @param client The [DeezerApiJavaClient] to use to fetch the next page
     * @param clazz The KSerializer of the type [T]. Required to parse the response.
     * @param expand true to expand [PaginatedResponse.data] with a new result. Default `false`.
     * @param coroutineContext the [CoroutineContext] to pass in [CoroutineScope], can be a [kotlinx.coroutines.Dispatchers].
     * Default [EmptyCoroutineContext].
     * @return Null if [PaginatedResponse.next] is null else a `PaginatedResponse`
     */
    @InternalSerializationApi
    @PublishedApi
    @Blocking
    @JvmOverloads
    internal fun fetchNext(
        client: DeezerApiJavaClient,
        clazz: Class<@UnsafeVariance T>,
        expand: Boolean = false,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): PaginatedResponse<T>? = fetchNext(client.delegate, clazz, expand, coroutineContext)

    /**
     * Fetch the next page of the search blocking the thread.
     *
     * Usage:
     * ```java
     * DeezerApiClient client; // Initialized somewhere
     * PaginatedResponse<User> page; // Can be retrieved from other methods
     * PaginatedResponse<User> nextPage = PaginatedResponses.fetchNext(client, page, User.class));
     * ```
     *
     * @param client The [DeezerApiClient] to use to fetch the next page
     * @param clazz The KSerializer of the type [T]. Required to parse the response.
     * @param expand true to expand [PaginatedResponse.data] with a new result
     * @param coroutineContext the [CoroutineContext] to pass in [CoroutineScope], can be a [kotlinx.coroutines.Dispatchers].
     * @return Null if [PaginatedResponse.next] is null else a `PaginatedResponse`
     */
    @InternalSerializationApi
    @PublishedApi
    @Blocking
    @JvmOverloads
    internal fun fetchNext(
        client: DeezerApiClient,
        clazz: Class<@UnsafeVariance T>,
        expand: Boolean = false,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): PaginatedResponse<T>? = runBlocking(coroutineContext) {
        fetchNext(client.httpClient, expand, clazz.kotlin.serializer())
    }

    /* -- Fetch next FUTURE zone -- */

    /**
     * Fetch the next page of the search using [CompletableFuture].
     *
     * Usage:
     * ```java
     * DeezerApiJavaClient client; // Initialized somewhere
     * PaginatedResponse<User> page; // Can be retrieved from other methods
     * CompletableFuture<PaginatedResponse<User>> nextPage = PaginatedResponses.fetchNextFuture(client, page, User.class));
     * ```
     *
     * @param client The [DeezerApiJavaClient] to use to fetch the next page
     * @param clazz The KSerializer of the type [T]. Required to parse the response.
     * @param expand true to expand [PaginatedResponse.data] with a new result. Default `false`.
     * @param coroutineContext the [CoroutineContext] to pass in [CoroutineScope], can be a [kotlinx.coroutines.Dispatchers].
     * Default [EmptyCoroutineContext].
     * @return Null if [PaginatedResponse.next] is null else a `PaginatedResponse`
     */
    @InternalSerializationApi
    @PublishedApi
    @JvmOverloads
    internal fun fetchNextFuture(
        client: DeezerApiJavaClient,
        clazz: Class<@UnsafeVariance T>,
        expand: Boolean = false,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<@UnsafeVariance T>?> =
        fetchNextFuture(client.delegate, clazz, expand, coroutineContext)

    /**
     * Fetch the next page of the search using [CompletableFuture].
     *
     * Usage:
     * ```java
     * DeezerApiJavaClient client; // Initialized somewhere
     * PaginatedResponse<User> page; // Can be retrieved from other methods
     * CompletableFuture<PaginatedResponse<User>> nextPage = PaginatedResponses.fetchNextFuture(client, page, User.class));
     * ```
     *
     * @param client The [DeezerApiClient] to use to fetch the next page
     * @param clazz The KSerializer of the type [T]. Required to parse the response.
     * @param expand true to expand [PaginatedResponse.data] with a new result. Default `false`.
     * @param coroutineContext the [CoroutineContext] to pass in [CoroutineScope], can be a [kotlinx.coroutines.Dispatchers].
     * Default [EmptyCoroutineContext].
     * @return Null if [PaginatedResponse.next] is null else a `PaginatedResponse`
     */
    @InternalSerializationApi
    @PublishedApi
    @JvmOverloads
    internal fun fetchNextFuture(
        client: DeezerApiClient,
        clazz: Class<@UnsafeVariance T>,
        expand: Boolean = false,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<@UnsafeVariance T>?> = CoroutineScope(coroutineContext).future {
        fetchNext(client.httpClient, expand, clazz.kotlin.serializer())
    }

    /* -- Fetch previous ZONE -- */

    /**
     * Fetch the previous page of the search blocking the thread.
     *
     * Usage:
     * ```java
     * DeezerApiClient client; // Initialized somewhere
     * PaginatedResponse<User> page; // Can be retrieved from other methods
     * PaginatedResponse<User> previousPage = PaginatedResponses.fetchPrevious(client, page, User.class));
     * ```
     *
     * @param client The [DeezerApiClient] to use to fetch the next page
     * @param clazz The KSerializer of the type [T]. Required to parse the response.
     * @param expand true to expand [PaginatedResponse.data] with a new result. Default `false`.
     * @param coroutineContext the [CoroutineContext] to pass in [CoroutineScope], can be a [kotlinx.coroutines.Dispatchers].
     * Default [EmptyCoroutineContext].
     * @return Null if [PaginatedResponse.next] is null else [PaginatedResponse]
     */
    @InternalSerializationApi
    @PublishedApi
    @Blocking
    @JvmOverloads
    internal fun fetchPrevious(
        client: DeezerApiJavaClient,
        clazz: Class<@UnsafeVariance T>,
        expand: Boolean = false,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): PaginatedResponse<T>? = fetchPrevious(client.delegate, clazz, expand, coroutineContext)

    /**
     * Fetch the previous page of the search blocking the thread.
     *
     * Usage:
     * ```java
     * DeezerApiClient client; // Initialized somewhere
     * PaginatedResponse<User> page; // Can be retrieved from other methods
     * PaginatedResponse<User> previousPage = PaginatedResponses.fetchPrevious(client, page, User.class));
     * ```
     *
     * @param client The [DeezerApiJavaClient] to use to fetch the next page
     * @param clazz The KSerializer of the type [T]. Required to parse the response.
     * @param expand true to expand [PaginatedResponse.data] with a new result. Default `false`.
     * @param coroutineContext the [CoroutineContext] to pass in [CoroutineScope], can be a [kotlinx.coroutines.Dispatchers].
     * Default [EmptyCoroutineContext].
     * @return Null if [PaginatedResponse.next] is null else [PaginatedResponse]
     */
    @InternalSerializationApi
    @PublishedApi
    @Blocking
    @JvmOverloads
    internal fun fetchPrevious(
        client: DeezerApiClient,
        clazz: Class<@UnsafeVariance T>,
        expand: Boolean = false,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): PaginatedResponse<T>? = runBlocking(coroutineContext) {
        fetchPrevious(client.httpClient, expand, clazz.kotlin.serializer())
    }

    /* -- Fetch previous FUTURE zone -- */

    /**
     * Fetch the previous page of the search with [CompletableFuture].
     *
     * Usage:
     * ```java
     * DeezerApiClient client; // Initialized somewhere
     * PaginatedResponse<User> page; // Can be retrieved from other methods
     * CompletableFuture<PaginatedResponse<User>> previousPage = PaginatedResponses.fetchPreviousFuture(client, page, User.class));
     * ```
     *
     * @param client The [DeezerApiJavaClient] to use to fetch the next page
     * @param clazz The KSerializer of the type [T]. Required to parse the response.
     * @param expand true to expand [PaginatedResponse.data] with a new result. Default `false`.
     * @param coroutineContext the [CoroutineContext] to pass in [CoroutineScope], can be a [kotlinx.coroutines.Dispatchers].
     * Default [EmptyCoroutineContext].
     * @return Null if [PaginatedResponse.next] is null else [PaginatedResponse]
     */
    @InternalSerializationApi
    @PublishedApi
    @JvmOverloads
    internal fun fetchPreviousFuture(
        client: DeezerApiJavaClient,
        clazz: Class<@UnsafeVariance T>,
        expand: Boolean = false,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<@UnsafeVariance T>?> =
        fetchPreviousFuture(client.delegate, clazz, expand, coroutineContext)

    /**
     * Fetch the previous page of the search with [CompletableFuture].
     *
     * Usage:
     * ```java
     * DeezerApiClient client; // Initialized somewhere
     * PaginatedResponse<User> page; // Can be retrieved from other methods
     * CompletableFuture<PaginatedResponse<User>> previousPage = PaginatedResponses.fetchPreviousFuture(client, page, User.class));
     * ```
     *
     * @param client The [DeezerApiClient] to use to fetch the next page
     * @param clazz The KSerializer of the type [T]. Required to parse the response.
     * @param expand true to expand [PaginatedResponse.data] with a new result. Default `false`.
     * @param coroutineContext the [CoroutineContext] to pass in [CoroutineScope], can be a [kotlinx.coroutines.Dispatchers].
     * Default [EmptyCoroutineContext].
     * @return Null if [PaginatedResponse.next] is null else [PaginatedResponse]
     */
    @InternalSerializationApi
    @PublishedApi
    @JvmOverloads
    internal fun fetchPreviousFuture(
        client: DeezerApiClient,
        clazz: Class<@UnsafeVariance T>,
        expand: Boolean = false,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<@UnsafeVariance T>?> = CoroutineScope(coroutineContext).future {
        fetchPrevious(client.httpClient, expand, clazz.kotlin.serializer())
    }
}
