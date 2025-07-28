package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Genre
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse
import io.github.kingg22.deezer.client.api.objects.Radio
import io.github.kingg22.deezer.client.api.objects.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.runBlocking
import org.jetbrains.annotations.Blocking
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Defines all endpoints related to [Radio]
 * @author Kingg22
 */
@PublishedApi
/*
This is internal, for kotlin consumers can't access this, but Java consumers can access avoid internal stuff.
Is PublishedApi to maintain binary compatibility.
 */
internal class RadioJavaRoutes private constructor(
    private val delegate: RadioRoutes,
) {
    /** Retrieve all [Radio] blocking the thread */
    @Blocking
    @JvmOverloads
    fun getAll(index: Int? = null, limit: Int? = null) = runBlocking { delegate.getAll(index, limit) }

    /** Retrieve a [Radio] by ID blocking the thread */
    @Blocking
    fun getById(id: Long) = runBlocking { delegate.getById(id) }

    /** Retrieve a [PaginatedResponse] with [Genre.radios] split by [Genre] blocking the thread */
    @Blocking
    @JvmOverloads
    fun getAllSplitInGenres(index: Int? = null, limit: Int? = null) =
        runBlocking { delegate.getAllSplitInGenres(index, limit) }

    /** Retrieve a [PaginatedResponse] with the top [Radio] (default to 25 radios) blocking the thread */
    @Blocking
    @JvmOverloads
    fun getTop(index: Int? = null, limit: Int? = null) = runBlocking { delegate.getTop(index, limit) }

    /** Retrieve a [PaginatedResponse] with first 40 [Track] in the radio blocking the thread */
    @Blocking
    @JvmOverloads
    fun getTracks(id: Long, index: Int? = null, limit: Int? = null) =
        runBlocking { delegate.getTracks(id, index, limit) }

    /** Retrieve a [PaginatedResponse] with personal [Radio] split by genre (MIX in website) blocking the thread */
    @Blocking
    @JvmOverloads
    fun getLists(index: Int? = null, limit: Int? = null) = runBlocking { delegate.getLists(index, limit) }

    // -- Completable Future --

    /** Retrieve all [Radio] with [CompletableFuture] */
    @JvmOverloads
    fun getAllFuture(
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Radio>> =
        CoroutineScope(coroutineContext).future { delegate.getAll(index, limit) }

    /** Retrieve a [Radio] by ID with [CompletableFuture] */
    @JvmOverloads
    fun getByIdFuture(id: Long, coroutineContext: CoroutineContext = EmptyCoroutineContext): CompletableFuture<Radio> =
        CoroutineScope(coroutineContext).future { delegate.getById(id) }

    /** Retrieve a [PaginatedResponse] with [Genre.radios] split by [Genre] with [CompletableFuture] */
    @JvmOverloads
    fun getAllSplitInGenresFuture(
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Genre>> =
        CoroutineScope(coroutineContext).future { delegate.getAllSplitInGenres(index, limit) }

    /** Retrieve a [PaginatedResponse] with the top [Radio] (default to 25 radios) with [CompletableFuture] */
    @JvmOverloads
    fun getTopFuture(
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Radio>> =
        CoroutineScope(coroutineContext).future { delegate.getTop(index, limit) }

    /** Retrieve a [PaginatedResponse] with first 40 [Track] in the radio with [CompletableFuture] */
    @JvmOverloads
    fun getTracksFuture(
        id: Long,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Track>> =
        CoroutineScope(coroutineContext).future { delegate.getTracks(id, index, limit) }

    /** Retrieve a [PaginatedResponse] with personal [Radio] split by genre (MIX in website) with [CompletableFuture] */
    @JvmOverloads
    fun getListsFuture(
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Radio>> =
        CoroutineScope(coroutineContext).future { delegate.getLists(index, limit) }

    companion object {
        /** Create a [RadioJavaRoutes] with [Radio Kotlin Routes][RadioRoutes] as delegate. */
        @PublishedApi
        @JvmStatic
        @JvmName("create")
        internal fun RadioRoutes.asJava() = RadioJavaRoutes(this)
    }
}
