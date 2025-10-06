package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Album
import io.github.kingg22.deezer.client.api.objects.Artist
import io.github.kingg22.deezer.client.api.objects.Chart
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse
import io.github.kingg22.deezer.client.api.objects.Playlist
import io.github.kingg22.deezer.client.api.objects.Podcast
import io.github.kingg22.deezer.client.api.objects.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.runBlocking
import org.jetbrains.annotations.Blocking
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Defines all endpoints related to [Chart]
 * @author Kingg22
 */
@PublishedApi
/*
This is internal, for kotlin consumers can't access this, but Java consumers can access avoid internal stuff.
Is PublishedApi to maintain binary compatibility.
 */
internal class ChartJavaRoutes private constructor(
    private val delegate: ChartRoutes,
) {
    /** Retrieve [Chart] blocking the thread */
    @Blocking
    @JvmOverloads
    fun getAll(index: Int? = null, limit: Int? = null) = runBlocking { delegate.getAll(index, limit) }

    /** **Unofficial** Retrieve [Chart] by ID _maybe genre id?_ blocking the thread */
    @Blocking
    @JvmOverloads
    fun getById(id: Long, index: Int? = null, limit: Int? = null) = runBlocking { delegate.getById(id, index, limit) }

    /** Retrieve the Top [Track] blocking the thread */
    @Blocking
    @JvmOverloads
    fun getTracks(id: Long = 0, index: Int? = null, limit: Int? = null) =
        runBlocking { delegate.getTracks(id, index, limit) }

    /** Retrieve the Top [Album] blocking the thread */
    @Blocking
    @JvmOverloads
    fun getAlbums(id: Long = 0, index: Int? = null, limit: Int? = null) =
        runBlocking { delegate.getAlbums(id, index, limit) }

    /** Retrieve the Top [Artist] blocking the thread */
    @Blocking
    @JvmOverloads
    fun getArtists(id: Long = 0, index: Int? = null, limit: Int? = null) =
        runBlocking { delegate.getArtists(id, index, limit) }

    /** Retrieve the Top [Playlist] blocking the thread */
    @Blocking
    @JvmOverloads
    fun getPlaylists(id: Long = 0, index: Int? = null, limit: Int? = null) =
        runBlocking { delegate.getPlaylists(id, index, limit) }

    /** Retrieve the Top [Podcast] blocking the thread */
    @Blocking
    @JvmOverloads
    fun getPodcasts(id: Long = 0, index: Int? = null, limit: Int? = null) =
        runBlocking { delegate.getPodcasts(id, index, limit) }

    // -- Completable Future --

    /** Retrieve [Chart] with [CompletableFuture] */
    @JvmOverloads
    fun getAllFuture(
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<Chart> = CoroutineScope(coroutineContext).future { delegate.getAll(index, limit) }

    /** **Unofficial** Retrieve [Chart] by ID _maybe genre id?_ with [CompletableFuture] */
    @JvmOverloads
    fun getByIdFuture(
        id: Long,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<Chart> = CoroutineScope(coroutineContext).future { delegate.getById(id, index, limit) }

    /** Retrieve the Top [Track] with [CompletableFuture] */
    @JvmOverloads
    fun getTracksFuture(
        id: Long = 0,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Track>> =
        CoroutineScope(coroutineContext).future { delegate.getTracks(id, index, limit) }

    /** Retrieve the Top [Album] with [CompletableFuture] */
    @JvmOverloads
    fun getAlbumsFuture(
        id: Long = 0,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Album>> =
        CoroutineScope(coroutineContext).future { delegate.getAlbums(id, index, limit) }

    /** Retrieve the Top [Artist] with [CompletableFuture] */
    @JvmOverloads
    fun getArtistsFuture(
        id: Long = 0,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Artist>> =
        CoroutineScope(coroutineContext).future { delegate.getArtists(id, index, limit) }

    /** Retrieve the Top [Playlist] with [CompletableFuture] */
    @JvmOverloads
    fun getPlaylistsFuture(
        id: Long = 0,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Playlist>> =
        CoroutineScope(coroutineContext).future { delegate.getPlaylists(id, index, limit) }

    /** Retrieve the Top [Podcast] with [CompletableFuture] */
    @JvmOverloads
    fun getPodcastsFuture(
        id: Long = 0,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Podcast>> =
        CoroutineScope(coroutineContext).future { delegate.getPodcasts(id, index, limit) }

    companion object {
        /** Create a [ChartJavaRoutes] with [Chart Kotlin Routes][ChartRoutes] as delegate. */
        @PublishedApi
        @JvmStatic
        @JvmName("create")
        internal fun ChartRoutes.asJava() = ChartJavaRoutes(this)
    }
}
