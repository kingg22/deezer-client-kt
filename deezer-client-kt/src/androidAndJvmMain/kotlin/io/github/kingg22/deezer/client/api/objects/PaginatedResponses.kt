package io.github.kingg22.deezer.client.api.objects

import io.github.kingg22.deezer.client.api.DeezerApiClient
import io.github.kingg22.deezer.client.api.DeezerApiJavaClient
import io.github.kingg22.deezer.client.exceptions.DeezerApiException
import io.github.kingg22.deezer.client.utils.AfterInitialize
import io.github.kingg22.deezer.client.utils.ExperimentalDeezerClient
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
import kotlin.coroutines.cancellation.CancellationException

/**
 * Helper that provides utilities for handling paginated responses **in Java code**.
 *
 * Includes methods to fetch the next and previous pages of a paginated resource
 * using synchronous blocking or asynchronous operations.
 *
 * **Is experimental** because use internal api of serialization and aren't tested with external classes,
 * inheritance, java classes, etc.
 *
 * For Kotlin users: **You don't need this, use the extension function instead, this is only for Java**
 */
@Deprecated("Use the member function instead")
@Suppress("unused")
@ExperimentalDeezerClient
@InternalSerializationApi
/*
This is internal, for kotlin consumers can't access this, but Java consumers can access avoid internal stuff.
Is PublishedApi to maintain binary compatibility.
 */
@PublishedApi
internal object PaginatedResponses {
    /* -- Implementation Zone -- */
    private suspend inline fun <N : @Serializable Any> PaginatedResponse<N>.fetchNext(
        httpClient: HttpClient,
        expand: Boolean,
        serializer: KSerializer<N>,
    ): PaginatedResponse<N>? {
        if (next.isNullOrBlank()) return null
        val resultString = httpClient.get(Url(next)).bodyAsText()
        val result = getDefaultJson().decodeFromString(PaginatedResponse.serializer(serializer), resultString)
        return if (expand && data.isNotEmpty()) {
            result.copy(data = data + result.data)
        } else {
            result
        }
    }

    private suspend inline fun <N : @Serializable Any> PaginatedResponse<N>.fetchPrevious(
        httpClient: HttpClient,
        expand: Boolean,
        serializer: KSerializer<N>,
    ): PaginatedResponse<N>? {
        if (prev.isNullOrBlank()) return null
        val resultString = httpClient.get(Url(prev)).bodyAsText()
        val result = getDefaultJson().decodeFromString(PaginatedResponse.serializer(serializer), resultString)
        return if (expand && data.isNotEmpty()) {
            result.copy(data = result.data + data)
        } else {
            result
        }
    }

    private suspend inline fun <N : @Serializable Any> PaginatedResponse<N>.fetchNext(
        client: DeezerApiClient,
        expand: Boolean,
        serializer: KSerializer<N>,
    ): PaginatedResponse<N>? = this.fetchNext(client.httpClient, expand, serializer)

    private suspend inline fun <N : @Serializable Any> PaginatedResponse<N>.fetchPrevious(
        client: DeezerApiClient,
        expand: Boolean,
        serializer: KSerializer<N>,
    ): PaginatedResponse<N>? = this.fetchPrevious(client.httpClient, expand, serializer)

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
     * @param N Type of the response. Required to parse the response, and expand the [PaginatedResponse.data] with a new result.
     * @param client The [DeezerApiJavaClient] to use to fetch the next page
     * @param paginatedResponse The current [PaginatedResponse] to fetch the next page from
     * @param clazz The KSerializer of the type [N]. Required to parse the response.
     * @param expand true to expand [PaginatedResponse.data] with a new result. Default `false`.
     * @param coroutineContext the [CoroutineContext] to pass in [CoroutineScope], can be a [kotlinx.coroutines.Dispatchers].
     * Default [EmptyCoroutineContext].
     * @return Null if [PaginatedResponse.next] is null else a `PaginatedResponse`
     */
    @Blocking
    @JvmOverloads
    @JvmStatic
    fun <N : @Serializable Any> fetchNext(
        client: DeezerApiJavaClient,
        paginatedResponse: PaginatedResponse<N>,
        clazz: Class<N>,
        expand: Boolean = false,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): PaginatedResponse<N>? = runBlocking(coroutineContext) {
        paginatedResponse.fetchNext(client.delegate, expand, clazz.kotlin.serializer())
    }

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
     * @param N Type of the response. Required to parse the response, and expand the [PaginatedResponse.data] with a new result.
     * @param client The [DeezerApiClient] to use to fetch the next page
     * @param paginatedResponse The current [PaginatedResponse] to fetch the next page from
     * @param clazz The KSerializer of the type [N]. Required to parse the response.
     * @param expand true to expand [PaginatedResponse.data] with a new result
     * @param coroutineContext the [CoroutineContext] to pass in [CoroutineScope], can be a [kotlinx.coroutines.Dispatchers].
     * @return Null if [PaginatedResponse.next] is null else a `PaginatedResponse`
     */
    @Blocking
    @JvmOverloads
    @JvmStatic
    fun <N : @Serializable Any> fetchNext(
        client: DeezerApiClient,
        paginatedResponse: PaginatedResponse<N>,
        clazz: Class<N>,
        expand: Boolean = false,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): PaginatedResponse<N>? = runBlocking(coroutineContext) {
        paginatedResponse.fetchNext(client, expand, clazz.kotlin.serializer())
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
     * @param N Type of the response. Required to parse the response, and expand the [PaginatedResponse.data] with a new result.
     * @param client The [DeezerApiJavaClient] to use to fetch the next page
     * @param paginatedResponse The current [PaginatedResponse] to fetch the next page from
     * @param clazz The KSerializer of the type [N]. Required to parse the response.
     * @param expand true to expand [PaginatedResponse.data] with a new result. Default `false`.
     * @param coroutineContext the [CoroutineContext] to pass in [CoroutineScope], can be a [kotlinx.coroutines.Dispatchers].
     * Default [EmptyCoroutineContext].
     * @return Null if [PaginatedResponse.next] is null else a `PaginatedResponse`
     */
    @JvmOverloads
    @JvmStatic
    fun <N : @Serializable Any> fetchNextFuture(
        client: DeezerApiJavaClient,
        paginatedResponse: PaginatedResponse<N>,
        clazz: Class<N>,
        expand: Boolean = false,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<N>?> = CoroutineScope(coroutineContext).future {
        paginatedResponse.fetchNext(client.delegate, expand, clazz.kotlin.serializer())
    }

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
     * @param N Type of the response. Required to parse the response, and expand the [PaginatedResponse.data] with a new result.
     * @param client The [DeezerApiClient] to use to fetch the next page
     * @param paginatedResponse The current [PaginatedResponse] to fetch the next page from
     * @param clazz The KSerializer of the type [N]. Required to parse the response.
     * @param expand true to expand [PaginatedResponse.data] with a new result. Default `false`.
     * @param coroutineContext the [CoroutineContext] to pass in [CoroutineScope], can be a [kotlinx.coroutines.Dispatchers].
     * Default [EmptyCoroutineContext].
     * @return Null if [PaginatedResponse.next] is null else a `PaginatedResponse`
     */
    @JvmOverloads
    @JvmStatic
    fun <N : @Serializable Any> fetchNextFuture(
        client: DeezerApiClient,
        paginatedResponse: PaginatedResponse<N>,
        clazz: Class<N>,
        expand: Boolean = false,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<N>?> = CoroutineScope(coroutineContext).future {
        paginatedResponse.fetchNext(client, expand, clazz.kotlin.serializer())
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
     * @param P Type of the response. Required to parse the response, and expand the [PaginatedResponse.data] with a new result.
     * @param client The [DeezerApiClient] to use to fetch the next page
     * @param paginatedResponse The current [PaginatedResponse] to fetch the next page from
     * @param clazz The KSerializer of the type [P]. Required to parse the response.
     * @param expand true to expand [PaginatedResponse.data] with a new result. Default `false`.
     * @param coroutineContext the [CoroutineContext] to pass in [CoroutineScope], can be a [kotlinx.coroutines.Dispatchers].
     * Default [EmptyCoroutineContext].
     * @return Null if [PaginatedResponse.next] is null else [PaginatedResponse]
     */
    @Blocking
    @JvmOverloads
    @JvmStatic
    fun <P : @Serializable Any> fetchPrevious(
        client: DeezerApiJavaClient,
        paginatedResponse: PaginatedResponse<P>,
        clazz: Class<P>,
        expand: Boolean = false,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): PaginatedResponse<P>? = runBlocking(coroutineContext) {
        paginatedResponse.fetchPrevious(client.delegate, expand, clazz.kotlin.serializer())
    }

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
     * @param P Type of the response. Required to parse the response, and expand the [PaginatedResponse.data] with a new result.
     * @param client The [DeezerApiJavaClient] to use to fetch the next page
     * @param paginatedResponse The current [PaginatedResponse] to fetch the next page from
     * @param clazz The KSerializer of the type [P]. Required to parse the response.
     * @param expand true to expand [PaginatedResponse.data] with a new result. Default `false`.
     * @param coroutineContext the [CoroutineContext] to pass in [CoroutineScope], can be a [kotlinx.coroutines.Dispatchers].
     * Default [EmptyCoroutineContext].
     * @return Null if [PaginatedResponse.next] is null else [PaginatedResponse]
     */
    @Blocking
    @JvmOverloads
    @JvmStatic
    fun <P : @Serializable Any> fetchPrevious(
        client: DeezerApiClient,
        paginatedResponse: PaginatedResponse<P>,
        clazz: Class<P>,
        expand: Boolean = false,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): PaginatedResponse<P>? = runBlocking(coroutineContext) {
        paginatedResponse.fetchPrevious(client, expand, clazz.kotlin.serializer())
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
     * @param P Type of the response. Required to parse the response, and expand the [PaginatedResponse.data] with a new result.
     * @param client The [DeezerApiJavaClient] to use to fetch the next page
     * @param paginatedResponse The current [PaginatedResponse] to fetch the next page from
     * @param clazz The KSerializer of the type [P]. Required to parse the response.
     * @param expand true to expand [PaginatedResponse.data] with a new result. Default `false`.
     * @param coroutineContext the [CoroutineContext] to pass in [CoroutineScope], can be a [kotlinx.coroutines.Dispatchers].
     * Default [EmptyCoroutineContext].
     * @return Null if [PaginatedResponse.next] is null else [PaginatedResponse]
     */
    @JvmOverloads
    @JvmStatic
    fun <P : @Serializable Any> fetchPreviousFuture(
        client: DeezerApiJavaClient,
        paginatedResponse: PaginatedResponse<P>,
        clazz: Class<P>,
        expand: Boolean = false,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<P>?> = CoroutineScope(coroutineContext).future {
        paginatedResponse.fetchPrevious(client.delegate, expand, clazz.kotlin.serializer())
    }

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
     * @param P Type of the response. Required to parse the response, and expand the [PaginatedResponse.data] with a new result.
     * @param client The [DeezerApiClient] to use to fetch the next page
     * @param paginatedResponse The current [PaginatedResponse] to fetch the next page from
     * @param clazz The KSerializer of the type [P]. Required to parse the response.
     * @param expand true to expand [PaginatedResponse.data] with a new result. Default `false`.
     * @param coroutineContext the [CoroutineContext] to pass in [CoroutineScope], can be a [kotlinx.coroutines.Dispatchers].
     * Default [EmptyCoroutineContext].
     * @return Null if [PaginatedResponse.next] is null else [PaginatedResponse]
     */
    @JvmOverloads
    @JvmStatic
    fun <P : @Serializable Any> fetchPreviousFuture(
        client: DeezerApiClient,
        paginatedResponse: PaginatedResponse<P>,
        clazz: Class<P>,
        expand: Boolean = false,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<P>?> = CoroutineScope(coroutineContext).future {
        paginatedResponse.fetchPrevious(client, expand, clazz.kotlin.serializer())
    }

    /* -- DEPRECATED ZONE -- */

    /**
     * Fetch the next page of the search blocking the thread.
     *
     * How to get the KSerializer?
     * ```java
     * final KSerializer<Track> serializer = Track.Companion.serializer();
     * ```
     * Full example:
     * ```java
     * PaginatedResponse<User> page = new PaginatedResponse<>();
     * KSerializer<User> ser = User.Companion.serializer();
     * PaginatedResponse<User> nextPage = PaginatedResponses.fetchNext(page, ser));
     * ```
     *
     * @param N Type of the response. Required to parse the response, and expand the [PaginatedResponse.data] with a new result.
     * @param serializer The KSerializer of the type.
     * @param expand true to expand [PaginatedResponse.data] with a new result
     * @return Null if [PaginatedResponse.next] is null else a `PaginatedResponse`
     * @throws IllegalArgumentException if [PaginatedResponse.data] is not empty and types [N] != `T` (original type)
     */
    @Deprecated(
        "Use fetchNext(DeezerApiClient, PaginatedResponse, Class, Boolean, CoroutineContext) instead. Pass a client explicitly and optionally a coroutineContext.",
        ReplaceWith("fetchNext(client, paginatedResponse, clazz, expand, coroutineContext)"),
        level = DeprecationLevel.ERROR,
    )
    @Suppress("DEPRECATION", "DEPRECATION_ERROR")
    @AfterInitialize
    @Blocking
    @JvmOverloads
    @JvmStatic
    @Throws(IllegalArgumentException::class, DeezerApiException::class, CancellationException::class)
    fun <N : @Serializable Any> PaginatedResponse<N>.fetchNext(
        clazz: Class<N>,
        expand: Boolean = false,
    ): PaginatedResponse<N>? = runBlocking {
        fetchNext(
            io.github.kingg22.deezer.client.api.GlobalDeezerApiClient.requireInstance(),
            expand,
            clazz.kotlin.serializer(),
        )
    }

    /**
     * Fetch the next page of the search blocking the thread.
     *
     * How to get the KSerializer?
     * ```java
     * final KSerializer<Track> serializer = Track.Companion.serializer();
     * ```
     * Full example:
     * ```java
     * PaginatedResponse<User> page = new PaginatedResponse<>();
     * KSerializer<User> ser = User.Companion.serializer();
     * PaginatedResponse<User> nextPage = PaginatedResponses.fetchNext(page, ser));
     * ```
     *
     * @param N Type of the response. Required to parse the response, and expand the [PaginatedResponse.data] with a new result.
     * @param serializer The KSerializer of the type.
     * @param expand true to expand [PaginatedResponse.data] with a new result
     * @param coroutineContext the [CoroutineContext] to pass in [CoroutineScope], can be a [kotlinx.coroutines.Dispatchers].
     * Default [EmptyCoroutineContext].
     * @return Null if [PaginatedResponse.next] is null else a `PaginatedResponse`
     * @throws IllegalArgumentException if [PaginatedResponse.data] is not empty and types [N] != `T` (original type)
     */
    @Deprecated(
        "Use fetchNextFuture(DeezerApiClient, PaginatedResponse, Class, Boolean, CoroutineContext) instead. Pass a client explicitly and optionally a coroutineContext.",
        ReplaceWith("fetchNextFuture(client, paginatedResponse, clazz, expand, coroutineContext)"),
        level = DeprecationLevel.ERROR,
    )
    @Suppress("DEPRECATION", "DEPRECATION_ERROR")
    @AfterInitialize
    @JvmOverloads
    @JvmStatic
    @Throws(IllegalArgumentException::class, DeezerApiException::class, CancellationException::class)
    fun <N : @Serializable Any> PaginatedResponse<N>.fetchNextFuture(
        clazz: Class<N>,
        expand: Boolean = false,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<N>?> = CoroutineScope(coroutineContext).future {
        fetchNext(
            io.github.kingg22.deezer.client.api.GlobalDeezerApiClient.requireInstance(),
            expand,
            clazz.kotlin.serializer(),
        )
    }

    /**
     * Fetch the previous page of the search blocking the thread.
     *
     *  How to get the KSerializer?
     * ```java
     * final KSerializer<Track> serializer = Track.Companion.serializer();
     * ```
     * Full example:
     * ```java
     * PaginatedResponse<User> page = new PaginatedResponse<>();
     * KSerializer<User> ser = User.Companion.serializer();
     * PaginatedResponse<User> nextPage = PaginatedResponses.fetchNext(page, ser));
     * ```
     *
     * @param P Type of the response. Required to parse the response, and expand the [PaginatedResponse.data] with a new result.
     * @param serializer The KSerializer of the type.
     * @param expand true to expand [PaginatedResponse.data] with a new result
     * @return Null if [PaginatedResponse.next] is null else [PaginatedResponse]
     * @throws IllegalArgumentException if [PaginatedResponse.data] is not empty and types [P] != `T` (original type)
     */
    @Deprecated(
        "Use fetchPrevious(DeezerApiClient, PaginatedResponse, Class, Boolean, CoroutineContext) instead. Pass a client explicitly and optionally a coroutineContext.",
        ReplaceWith("fetchPrevious(client, paginatedResponse, clazz, expand, coroutineContext)"),
        level = DeprecationLevel.ERROR,
    )
    @Suppress("DEPRECATION", "DEPRECATION_ERROR")
    @AfterInitialize
    @Blocking
    @JvmOverloads
    @JvmStatic
    @Throws(IllegalArgumentException::class, DeezerApiException::class, CancellationException::class)
    fun <P : @Serializable Any> PaginatedResponse<P>.fetchPrevious(
        clazz: Class<P>,
        expand: Boolean = false,
    ): PaginatedResponse<P>? = runBlocking {
        fetchPrevious(
            io.github.kingg22.deezer.client.api.GlobalDeezerApiClient.requireInstance(),
            expand,
            clazz.kotlin.serializer(),
        )
    }

    /**
     * Fetch the previous page of the search with [CompletableFuture].
     *
     *  How to get the KSerializer?
     * ```java
     * final KSerializer<Track> serializer = Track.Companion.serializer();
     * ```
     * Full example:
     * ```java
     * PaginatedResponse<User> page = new PaginatedResponse<>();
     * KSerializer<User> ser = User.Companion.serializer();
     * PaginatedResponse<User> nextPage = PaginatedResponses.fetchNext(page, ser));
     * ```
     *
     * @param P Type of the response. Required to parse the response, and expand the [PaginatedResponse.data] with a new result.
     * @param serializer The KSerializer of the type.
     * @param expand true to expand [PaginatedResponse.data] with a new result
     * @param coroutineContext the [CoroutineContext] to pass in [CoroutineScope], can be a [kotlinx.coroutines.Dispatchers].
     * Default [EmptyCoroutineContext].
     * @return Null if [PaginatedResponse.next] is null else [PaginatedResponse]
     * @throws IllegalArgumentException if [PaginatedResponse.data] is not empty and types [P] != `T` (original type)
     */
    @Deprecated(
        "Use fetchPreviousFuture(DeezerApiClient, PaginatedResponse, Class, Boolean, CoroutineContext) instead. Pass a client explicitly and optionally a coroutineContext.",
        ReplaceWith("fetchPreviousFuture(client, paginatedResponse, clazz, expand, coroutineContext)"),
        level = DeprecationLevel.ERROR,
    )
    @Suppress("DEPRECATION", "DEPRECATION_ERROR")
    @AfterInitialize
    @JvmOverloads
    @JvmStatic
    @Throws(IllegalArgumentException::class, DeezerApiException::class, CancellationException::class)
    fun <P : @Serializable Any> PaginatedResponse<P>.fetchPreviousFuture(
        clazz: Class<P>,
        expand: Boolean = false,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<P>?> = CoroutineScope(coroutineContext).future {
        fetchPrevious(
            io.github.kingg22.deezer.client.api.GlobalDeezerApiClient.requireInstance(),
            expand,
            clazz.kotlin.serializer(),
        )
    }
}
