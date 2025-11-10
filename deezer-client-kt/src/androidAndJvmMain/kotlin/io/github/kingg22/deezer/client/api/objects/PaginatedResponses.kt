package io.github.kingg22.deezer.client.api.objects

import io.github.kingg22.deezer.client.exceptions.DeezerApiException
import io.github.kingg22.deezer.client.utils.AfterInitialize
import io.github.kingg22.deezer.client.utils.ExperimentalDeezerClient
import io.github.kingg22.deezer.client.utils.getDefaultJson
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Url
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
 * **Is experimental** because use internal api of serialization and aren't tested with external classes, inheritance, etc.
 *
 * For Kotlin users: **You don't need this, use the member function instead, this is only for Java**
 */
@ExperimentalDeezerClient
@InternalSerializationApi
/*
This is internal, for kotlin consumers can't access this, but Java consumers can access avoid internal stuff.
Is PublishedApi to maintain binary compatibility.
 */
@PublishedApi
internal object PaginatedResponses {
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
    @AfterInitialize
    @Blocking
    @JvmOverloads
    @JvmStatic
    @Throws(IllegalArgumentException::class, DeezerApiException::class, CancellationException::class)
    fun <N : @Serializable Any> PaginatedResponse<N>.fetchNext(clazz: Class<N>, expand: Boolean = false) =
        runBlocking { fetchNextJava(expand, clazz.kotlin.serializer()) }

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
    @AfterInitialize
    @JvmOverloads
    @JvmStatic
    @Throws(IllegalArgumentException::class, DeezerApiException::class, CancellationException::class)
    fun <N : @Serializable Any> PaginatedResponse<N>.fetchNextFuture(
        clazz: Class<N>,
        expand: Boolean = false,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<N>?> = CoroutineScope(coroutineContext).future {
        fetchNextJava(expand, clazz.kotlin.serializer())
    }

    private suspend inline fun <N : @Serializable Any> PaginatedResponse<N>.fetchNextJava(
        expand: Boolean = false,
        serializer: KSerializer<N>,
    ): PaginatedResponse<N>? {
        if (next.isNullOrBlank()) return null
        val resultString = client().httpClient.get(Url(next)).bodyAsText()
        val result = getDefaultJson().decodeFromString(PaginatedResponse.serializer(serializer), resultString)

        return if (expand && data.isNotEmpty()) {
            result.copy(data = data + result.data)
        } else {
            result
        }
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
    @AfterInitialize
    @Blocking
    @JvmOverloads
    @JvmStatic
    @Throws(IllegalArgumentException::class, DeezerApiException::class, CancellationException::class)
    fun <P : @Serializable Any> PaginatedResponse<P>.fetchPrevious(clazz: Class<P>, expand: Boolean = false) =
        runBlocking { fetchPreviousJava(expand, clazz.kotlin.serializer()) }

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
    @AfterInitialize
    @JvmOverloads
    @JvmStatic
    @Throws(IllegalArgumentException::class, DeezerApiException::class, CancellationException::class)
    fun <P : @Serializable Any> PaginatedResponse<P>.fetchPreviousFuture(
        clazz: Class<P>,
        expand: Boolean = false,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<P>?> = CoroutineScope(coroutineContext).future {
        fetchPreviousJava(expand, clazz.kotlin.serializer())
    }

    private suspend inline fun <N : @Serializable Any> PaginatedResponse<N>.fetchPreviousJava(
        expand: Boolean = false,
        serializer: KSerializer<N>,
    ): PaginatedResponse<N>? {
        if (prev.isNullOrBlank()) return null
        val resultString = client().httpClient.get(Url(prev)).bodyAsText()
        val result = getDefaultJson().decodeFromString(PaginatedResponse.serializer(serializer), resultString)

        return if (expand && data.isNotEmpty()) {
            result.copy(data = result.data + data)
        } else {
            result
        }
    }
}
