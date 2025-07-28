@file:OptIn(ExperimentalDeezerClient::class, InternalDeezerClient::class)

package io.github.kingg22.deezer.client.api.objects

import io.github.kingg22.deezer.client.api.objects.PaginatedResponse.Companion.client
import io.github.kingg22.deezer.client.exceptions.DeezerApiException
import io.github.kingg22.deezer.client.utils.AfterInitialize
import io.github.kingg22.deezer.client.utils.ExperimentalDeezerClient
import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import io.github.kingg22.deezer.client.utils.getJson
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Url
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import org.jetbrains.annotations.Blocking
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.cancellation.CancellationException
import kotlin.reflect.KClass

/**
 * Helper that provides utilities for handling paginated responses **in Java code**.
 *
 * Includes methods to fetch the next and previous pages of a paginated resource
 * using synchronous blocking or asynchronous operations.
 *
 * For Kotlin users: **You don't need this, use the member function instead, this is only for Java**
 */
@ExperimentalDeezerClient
object PaginatedResponses {
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
    @JvmName("fetchNext")
    @JvmOverloads
    @JvmStatic
    @Throws(IllegalArgumentException::class, DeezerApiException::class, CancellationException::class)
    fun <N : @Serializable Any> PaginatedResponse<*>.fetchNextBlocking(
        serializer: KSerializer<N>,
        expand: Boolean = false,
    ) = runBlocking { fetchNextJavaInternal(expand, serializer) }

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
    @JvmName("fetchNextFuture")
    @JvmOverloads
    @JvmStatic
    @Throws(IllegalArgumentException::class, DeezerApiException::class, CancellationException::class)
    fun <N : @Serializable Any> PaginatedResponse<*>.fetchNextJava(
        serializer: KSerializer<N>,
        expand: Boolean = false,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<N>?> = CoroutineScope(coroutineContext).future {
        fetchNextJavaInternal(expand, serializer)
    }

    private suspend fun <N : @Serializable Any> PaginatedResponse<*>.fetchNextJavaInternal(
        expand: Boolean = false,
        serializer: KSerializer<N>,
    ): PaginatedResponse<N>? {
        if (next.isNullOrBlank()) return null
        if (data.isNotEmpty()) {
            kSerializerCheck(serializer, data.first()::class, "fetchNext")
        }
        val resultString = client().httpClient.get(Url(next)).bodyAsText()
        val result = getJson().decodeFromString(PaginatedResponse.serializer(serializer), resultString)

        return if (expand && data.isNotEmpty()) {
            @Suppress("UNCHECKED_CAST")
            result.copy(data = data as List<N> + result.data)
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
    @JvmName("fetchPrevious")
    @JvmOverloads
    @JvmStatic
    @Throws(IllegalArgumentException::class, DeezerApiException::class, CancellationException::class)
    fun <P : @Serializable Any> PaginatedResponse<*>.fetchPrevBlocking(
        serializer: KSerializer<P>,
        expand: Boolean = false,
    ) = runBlocking { fetchPreviousJavaInternal(expand, serializer) }

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
    @JvmName("fetchPreviousFuture")
    @JvmOverloads
    @JvmStatic
    @Throws(IllegalArgumentException::class, DeezerApiException::class, CancellationException::class)
    fun <P : @Serializable Any> PaginatedResponse<*>.fetchPreviousJava(
        serializer: KSerializer<P>,
        expand: Boolean = false,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<P>?> = CoroutineScope(coroutineContext).future {
        fetchPreviousJavaInternal(expand, serializer)
    }

    private suspend fun <N : @Serializable Any> PaginatedResponse<*>.fetchPreviousJavaInternal(
        expand: Boolean = false,
        serializer: KSerializer<N>,
    ): PaginatedResponse<N>? {
        if (prev.isNullOrBlank()) return null
        if (data.isNotEmpty()) {
            kSerializerCheck(serializer, data.first()::class, "fetchPrevious")
        }
        val resultString = client().httpClient.get(Url(prev)).bodyAsText()
        val result = getJson().decodeFromString(PaginatedResponse.serializer(serializer), resultString)

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
