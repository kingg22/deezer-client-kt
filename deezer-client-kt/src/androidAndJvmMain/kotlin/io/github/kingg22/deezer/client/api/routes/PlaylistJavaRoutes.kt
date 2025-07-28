package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.PaginatedResponse
import io.github.kingg22.deezer.client.api.objects.Playlist
import io.github.kingg22.deezer.client.api.objects.Track
import io.github.kingg22.deezer.client.api.objects.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.runBlocking
import org.jetbrains.annotations.Blocking
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Defines all endpoints related to [Playlist]
 * @author Kingg22
 */
@PublishedApi
/*
This is internal, for kotlin consumers can't access this, but Java consumers can access avoid internal stuff.
Is PublishedApi to maintain binary compatibility.
 */
internal class PlaylistJavaRoutes private constructor(
    private val delegate: PlaylistRoutes,
) {
    /** Retrieve a [Playlist] by ID blocking the thread. */
    @Blocking
    fun getById(id: Long) = runBlocking { delegate.getById(id) }

    /** Retrieve a [PaginatedResponse] with [User] fans of playlist blocking the thread */
    @Blocking
    @JvmOverloads
    fun getFans(id: Long, index: Int? = null, limit: Int? = null) = runBlocking { delegate.getFans(id, index, limit) }

    /** Retrieve a [PaginatedResponse] with all [Track] from a [Playlist] blocking the thread */
    @Blocking
    @JvmOverloads
    fun getTracks(id: Long, index: Int? = null, limit: Int? = null) =
        runBlocking { delegate.getTracks(id, index, limit) }

    /** Retrieve a [PaginatedResponse] with the radio [Track]s of a [Playlist] blocking the thread */
    @Blocking
    @JvmOverloads
    fun getRadio(id: Long, index: Int? = null, limit: Int? = null) = runBlocking { delegate.getRadio(id, index, limit) }

    // -- Completable Future --

    /** Retrieve a [Playlist] by ID with [CompletableFuture] */
    @JvmOverloads
    fun getByIdFuture(
        id: Long,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<Playlist> = CoroutineScope(coroutineContext).future { delegate.getById(id) }

    /** Retrieve a [PaginatedResponse] with [User] fans of playlist with [CompletableFuture] */
    @JvmOverloads
    fun getFansFuture(
        id: Long,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<User>> =
        CoroutineScope(coroutineContext).future { delegate.getFans(id, index, limit) }

    /** Retrieve a [PaginatedResponse] with all [Track] from a [Playlist] with [CompletableFuture] */
    @JvmOverloads
    fun getTracksFuture(
        id: Long,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Track>> =
        CoroutineScope(coroutineContext).future { delegate.getTracks(id, index, limit) }

    /** Retrieve a [PaginatedResponse] with the radio [Track]s of a [Playlist] with [CompletableFuture] */
    @JvmOverloads
    fun getRadioFuture(
        id: Long,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Track>> =
        CoroutineScope(coroutineContext).future { delegate.getRadio(id, index, limit) }

    companion object {
        /** Create a [PlaylistJavaRoutes] with [Playlist Kotlin Routes][PlaylistRoutes] as delegate. */
        @PublishedApi
        @JvmStatic
        @JvmName("create")
        internal fun PlaylistRoutes.asJava() = PlaylistJavaRoutes(this)
    }
}
