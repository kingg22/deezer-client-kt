package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.runBlocking
import org.jetbrains.annotations.Blocking
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Defines all endpoints related to Search in Deezer API
 * @author Kingg22
 * @see SearchRoutes.buildAdvancedQuery
 * @see SearchRoutes.setStrict
 * @see SearchRoutes.AdvancedQueryBuilder
 */
@PublishedApi
/*
This is internal, for kotlin consumers can't access this, but Java consumers can access avoid internal stuff.
Is PublishedApi to maintain binary compatibility.
 */
internal class SearchJavaRoutes private constructor(
    private val delegate: SearchRoutes,
) {
    /** Basic Search blocking the thread */
    @Blocking
    @JvmOverloads
    fun search(q: String, strict: String? = null, order: SearchOrder? = null, index: Int? = null, limit: Int? = null) =
        runBlocking { delegate.search(q, strict, order, index, limit) }

    /** Search [Album] blocking the thread */
    @Blocking
    @JvmOverloads
    fun searchAlbum(
        q: String,
        strict: String? = null,
        order: SearchOrder? = null,
        index: Int? = null,
        limit: Int? = null,
    ) = runBlocking { delegate.searchAlbum(q, strict, order, index, limit) }

    /** Search [Artist] blocking the thread */
    @Blocking
    @JvmOverloads
    fun searchArtist(
        q: String,
        strict: String? = null,
        order: SearchOrder? = null,
        index: Int? = null,
        limit: Int? = null,
    ) = runBlocking { delegate.searchArtist(q, strict, order, index, limit) }

    /**
     * Retrieve the current user search history blocking the thread
     *
     * **Required** OAuth. **unsupported**
     */
    @Blocking
    @JvmOverloads
    fun searchHistory(
        q: String,
        strict: String? = null,
        order: SearchOrder? = null,
        index: Int? = null,
        limit: Int? = null,
    ) = runBlocking { delegate.searchHistory(q, strict, order, index, limit) }

    /** Search [Playlist] blocking the thread */
    @Blocking
    @JvmOverloads
    fun searchPlaylist(
        q: String,
        strict: String? = null,
        order: SearchOrder? = null,
        index: Int? = null,
        limit: Int? = null,
    ) = runBlocking { delegate.searchPlaylist(q, strict, order, index, limit) }

    /** Search [Podcast] blocking the thread */
    @Blocking
    @JvmOverloads
    fun searchPodcast(
        q: String,
        strict: String? = null,
        order: SearchOrder? = null,
        index: Int? = null,
        limit: Int? = null,
    ) = runBlocking { delegate.searchPodcast(q, strict, order, index, limit) }

    /** Search [Radio] blocking the thread */
    @Blocking
    @JvmOverloads
    fun searchRadio(
        q: String,
        strict: String? = null,
        order: SearchOrder? = null,
        index: Int? = null,
        limit: Int? = null,
    ) = runBlocking { delegate.searchRadio(q, strict, order, index, limit) }

    /** Search [Track] blocking the thread */
    @Blocking
    @JvmOverloads
    fun searchTrack(
        q: String,
        strict: String? = null,
        order: SearchOrder? = null,
        index: Int? = null,
        limit: Int? = null,
    ) = runBlocking { delegate.searchTrack(q, strict, order, index, limit) }

    /** Search [User] blocking the thread */
    @Blocking
    @JvmOverloads
    fun searchUser(
        q: String,
        strict: String? = null,
        order: SearchOrder? = null,
        index: Int? = null,
        limit: Int? = null,
    ) = runBlocking { delegate.searchUser(q, strict, order, index, limit) }

    // -- Completable Future --

    /** Basic Search with [CompletableFuture] */
    @JvmOverloads
    fun searchFuture(
        q: String,
        strict: String? = null,
        order: SearchOrder? = null,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Track>> =
        CoroutineScope(coroutineContext).future { delegate.search(q, strict, order, index, limit) }

    /** Search [Album] with [CompletableFuture] */
    @JvmOverloads
    fun searchAlbumFuture(
        q: String,
        strict: String? = null,
        order: SearchOrder? = null,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Album>> =
        CoroutineScope(coroutineContext).future { delegate.searchAlbum(q, strict, order, index, limit) }

    /** Search [Artist] with [CompletableFuture] */
    @JvmOverloads
    fun searchArtistFuture(
        q: String,
        strict: String? = null,
        order: SearchOrder? = null,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Artist>> =
        CoroutineScope(coroutineContext).future { delegate.searchArtist(q, strict, order, index, limit) }

    /**
     * Retrieve the current user search history with [CompletableFuture]
     *
     * **Required** OAuth. **unsupported**
     */
    @JvmOverloads
    fun searchHistoryFuture(
        q: String,
        strict: String? = null,
        order: SearchOrder? = null,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<SearchUserHistory>> =
        CoroutineScope(coroutineContext).future { delegate.searchHistory(q, strict, order, index, limit) }

    /** Search [Playlist] with [CompletableFuture] */
    @JvmOverloads
    fun searchPlaylistFuture(
        q: String,
        strict: String? = null,
        order: SearchOrder? = null,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Playlist>> =
        CoroutineScope(coroutineContext).future { delegate.searchPlaylist(q, strict, order, index, limit) }

    /** Search [Podcast] with [CompletableFuture] */
    @JvmOverloads
    fun searchPodcastFuture(
        q: String,
        strict: String? = null,
        order: SearchOrder? = null,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Podcast>> =
        CoroutineScope(coroutineContext).future { delegate.searchPodcast(q, strict, order, index, limit) }

    /** Search [Radio] with [CompletableFuture] */
    @JvmOverloads
    fun searchRadioFuture(
        q: String,
        strict: String? = null,
        order: SearchOrder? = null,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Radio>> =
        CoroutineScope(coroutineContext).future { delegate.searchRadio(q, strict, order, index, limit) }

    /** Search [Track] with [CompletableFuture] */
    @JvmOverloads
    fun searchTrackFuture(
        q: String,
        strict: String? = null,
        order: SearchOrder? = null,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Track>> =
        CoroutineScope(coroutineContext).future { delegate.searchTrack(q, strict, order, index, limit) }

    /** Search [User] with [CompletableFuture] */
    @JvmOverloads
    fun searchUserFuture(
        q: String,
        strict: String? = null,
        order: SearchOrder? = null,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<User>> =
        CoroutineScope(coroutineContext).future { delegate.searchUser(q, strict, order, index, limit) }

    companion object {
        /** Create a [SearchJavaRoutes] with [Search Kotlin Routes][SearchRoutes] as delegate. */
        @PublishedApi
        @JvmStatic
        @JvmName("create")
        internal fun SearchRoutes.asJava() = SearchJavaRoutes(this)
    }
}
