package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Album
import io.github.kingg22.deezer.client.api.objects.Artist
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
 * Defines all endpoints related to [Artist]
 * @author Kingg22
 */
@PublishedApi
/*
This is internal, for kotlin consumers can't access this, but Java consumers can access avoid internal stuff.
Is PublishedApi to maintain binary compatibility.
 */
internal class ArtistJavaRoutes private constructor(
    private val delegate: ArtistRoutes,
) {
    /** Retrieve an [Artist] by ID blocking the thread. */
    @Blocking
    @JvmOverloads
    fun getById(id: Long, index: Int? = null, limit: Int? = null) = runBlocking { delegate.getById(id, index, limit) }

    /** Retrieve a [PaginatedResponse] with all [User] fans of an [Artist] blocking the thread */
    @Blocking
    @JvmOverloads
    fun getFans(id: Long, index: Int? = null, limit: Int? = null) = runBlocking { delegate.getFans(id, index, limit) }

    /** Retrieve a [PaginatedResponse] containing the top [Track]s of an [Artist] blocking the thread */
    @Blocking
    @JvmOverloads
    fun getTopTracks(id: Long, index: Int? = null, limit: Int? = null) =
        runBlocking { delegate.getTopTracks(id, index, limit) }

    /** Retrieve a [PaginatedResponse] with all [Album]s of an [Artist] blocking the thread */
    @Blocking
    @JvmOverloads
    fun getAlbums(id: Long, index: Int? = null, limit: Int? = null) =
        runBlocking { delegate.getAlbums(id, index, limit) }

    /** Retrieve a [PaginatedResponse] with the radio [Track]s of an [Artist] blocking the thread */
    @Blocking
    @JvmOverloads
    fun getRadio(id: Long, index: Int? = null, limit: Int? = null) = runBlocking { delegate.getRadio(id, index, limit) }

    /** Retrieve a [PaginatedResponse] with all [Playlist]s featuring an [Artist] blocking the thread */
    @Blocking
    @JvmOverloads
    fun getPlaylists(id: Long, index: Int? = null, limit: Int? = null) =
        runBlocking { delegate.getPlaylists(id, index, limit) }

    /** Retrieve a [PaginatedResponse] with [Artist]s related to a specific [Artist] blocking the thread */
    @Blocking
    @JvmOverloads
    fun getRelated(id: Long, index: Int? = null, limit: Int? = null) =
        runBlocking { delegate.getRelated(id, index, limit) }

    // -- Completable Future --

    /** Retrieve an [Artist] by ID with [CompletableFuture] */
    @JvmOverloads
    fun getByIdFuture(
        id: Long,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<Artist> = CoroutineScope(coroutineContext).future { delegate.getById(id, index, limit) }

    /** Retrieve a [PaginatedResponse] with all [User] fans of an [Artist] with [CompletableFuture] */
    @JvmOverloads
    fun getFansFuture(
        id: Long,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<User>> =
        CoroutineScope(coroutineContext).future { delegate.getFans(id, index, limit) }

    /** Retrieve a [PaginatedResponse] containing the top [Track]s of an [Artist] with [CompletableFuture] */
    @JvmOverloads
    fun getTopTracksFuture(
        id: Long,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Track>> =
        CoroutineScope(coroutineContext).future { delegate.getTopTracks(id, index, limit) }

    /** Retrieve a [PaginatedResponse] with all [Album]s of an [Artist] with [CompletableFuture] */
    @JvmOverloads
    fun getAlbumsFuture(
        id: Long,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Album>> =
        CoroutineScope(coroutineContext).future { delegate.getAlbums(id, index, limit) }

    /** Retrieve a [PaginatedResponse] with the radio [Track]s of an [Artist] with [CompletableFuture] */
    @JvmOverloads
    fun getRadioFuture(
        id: Long,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Track>> =
        CoroutineScope(coroutineContext).future { delegate.getRadio(id, index, limit) }

    /** Retrieve a [PaginatedResponse] with all [Playlist]s featuring an [Artist] with [CompletableFuture] */
    @JvmOverloads
    fun getPlaylistsFuture(
        id: Long,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Playlist>> =
        CoroutineScope(coroutineContext).future { delegate.getPlaylists(id, index, limit) }

    /** Retrieve a [PaginatedResponse] with [Artist]s related to a specific [Artist] with [CompletableFuture] */
    @JvmOverloads
    fun getRelatedFuture(
        id: Long,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Artist>> =
        CoroutineScope(coroutineContext).future { delegate.getRelated(id, index, limit) }

    companion object {
        /** Create an [ArtistJavaRoutes] with [Artist Kotlin Routes][ArtistRoutes] as delegate. */
        @PublishedApi
        @JvmStatic
        @JvmName("create")
        internal fun ArtistRoutes.asJava() = ArtistJavaRoutes(this)
    }
}
