package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Artist
import io.github.kingg22.deezer.client.api.objects.Genre
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse
import io.github.kingg22.deezer.client.api.objects.Podcast
import io.github.kingg22.deezer.client.api.objects.Radio
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.runBlocking
import org.jetbrains.annotations.Blocking
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Defines all endpoints related to [Genre]
 * @author Kingg22
 */
@PublishedApi
/*
This is internal, for kotlin consumers can't access this, but Java consumers can access avoid internal stuff.
Is PublishedApi to maintain binary compatibility.
 */
internal class GenreJavaRoutes private constructor(
    private val delegate: GenreRoutes,
) {
    /** Retrieve all [Genre] blocking the thread */
    @Blocking
    @JvmOverloads
    fun getAll(index: Int? = null, limit: Int? = null) = runBlocking { delegate.getAll(index, limit) }

    /** Retrieve a [Genre] by ID blocking the thread */
    @Blocking
    fun getById(id: Long) = runBlocking { delegate.getById(id) }

    /** Retrieve [PaginatedResponse] with all [Artist] for a genre blocking the thread */
    @Blocking
    @JvmOverloads
    fun getArtists(id: Long = 0, index: Int? = null, limit: Int? = null) =
        runBlocking { delegate.getArtists(id, index, limit) }

    /** Retrieve [PaginatedResponse] with all [Podcast] for a genre blocking the thread */
    @Blocking
    @JvmOverloads
    fun getPodcasts(id: Long = 0, index: Int? = null, limit: Int? = null) =
        runBlocking { delegate.getPodcasts(id, index, limit) }

    /** Retrieve [PaginatedResponse] with all [Radio] for a genre blocking the thread */
    @Blocking
    @JvmOverloads
    fun getRadios(id: Long = 0, index: Int? = null, limit: Int? = null) =
        runBlocking { delegate.getRadios(id, index, limit) }

    // -- Completable Future --

    /** Retrieve all [Genre] with [CompletableFuture] */
    @JvmOverloads
    fun getAllFuture(
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Genre>> =
        CoroutineScope(coroutineContext).future { delegate.getAll(index, limit) }

    /** Retrieve a [Genre] by ID with [CompletableFuture] */
    @JvmOverloads
    fun getByIdFuture(id: Long, coroutineContext: CoroutineContext = EmptyCoroutineContext): CompletableFuture<Genre> =
        CoroutineScope(coroutineContext).future { delegate.getById(id) }

    /** Retrieve [PaginatedResponse] with all [Artist] for a genre with [CompletableFuture] */
    @JvmOverloads
    fun getArtistsFuture(
        id: Long = 0,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Artist>> =
        CoroutineScope(coroutineContext).future { delegate.getArtists(id, index, limit) }

    /** Retrieve [PaginatedResponse] with all [Podcast] for a genre with [CompletableFuture] */
    @JvmOverloads
    fun getPodcastsFuture(
        id: Long = 0,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Podcast>> =
        CoroutineScope(coroutineContext).future { delegate.getPodcasts(id, index, limit) }

    /** Retrieve [PaginatedResponse] with all [Radio] for a genre with [CompletableFuture] */
    @JvmOverloads
    fun getRadiosFuture(
        id: Long = 0,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Radio>> =
        CoroutineScope(coroutineContext).future { delegate.getRadios(id, index, limit) }

    companion object {
        /** Create a [GenreJavaRoutes] with [Genre Kotlin Routes][GenreRoutes] as delegate. */
        @PublishedApi
        @JvmStatic
        @JvmName("create")
        internal fun GenreRoutes.asJava() = GenreJavaRoutes(this)
    }
}
