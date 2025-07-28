package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Album
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse
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
 * Defines all endpoints related to [Album]
 * @author Kingg22
 */
@PublishedApi
/*
This is internal, for kotlin consumers can't access this, but Java consumers can access avoid internal stuff.
Is PublishedApi to maintain binary compatibility.
 */
internal class AlbumJavaRoutes private constructor(
    private val delegate: AlbumRoutes,
) {
    /** Retrieve an [Album] by ID blocking the thread. */
    @Blocking
    fun getById(id: Long) = runBlocking { delegate.getById(id) }

    /** Retrieve an [Album] by UPC (Universal Product Code) blocking the thread */
    @Blocking
    fun getByUpc(upc: String) = runBlocking { delegate.getByUpc(upc) }

    /** Retrieve the fans of an [Album] blocking the thread */
    @Blocking
    @JvmOverloads
    fun getFans(id: Long, index: Int? = null, limit: Int? = null) = runBlocking { delegate.getFans(id, index, limit) }

    /** Retrieve a [PaginatedResponse] with all [Track] from an [Album] blocking the thread */
    @Blocking
    @JvmOverloads
    fun getTracks(id: Long, index: Int? = null, limit: Int? = null) =
        runBlocking { delegate.getTracks(id, index, limit) }

    // -- Completable Future --

    /** Retrieve an [Album] by ID with [CompletableFuture] */
    @JvmOverloads
    fun getByIdFuture(id: Long, coroutineContext: CoroutineContext = EmptyCoroutineContext): CompletableFuture<Album> =
        CoroutineScope(coroutineContext).future { delegate.getById(id) }

    /** Retrieve an [Album] by UPC (Universal Product Code) with [CompletableFuture] */
    @JvmOverloads
    fun getByUpcFuture(
        upc: String,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<Album> = CoroutineScope(coroutineContext).future { delegate.getByUpc(upc) }

    /** Retrieve the fans of an [Album] with [CompletableFuture] */
    @JvmOverloads
    fun getFansFuture(
        id: Long,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<User>> = CoroutineScope(coroutineContext).future {
        delegate.getFans(id, index, limit)
    }

    /** Retrieve a [PaginatedResponse] with all [Track] from an [Album] with [CompletableFuture] */
    @JvmOverloads
    fun getTracksFuture(
        id: Long,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Track>> = CoroutineScope(coroutineContext).future {
        delegate.getTracks(id, index, limit)
    }

    companion object {
        /** Create an [AlbumJavaRoutes] with [Album Kotlin Routes][AlbumRoutes] as delegate. */
        @PublishedApi
        @JvmStatic
        @JvmName("create")
        internal fun AlbumRoutes.asJava() = AlbumJavaRoutes(this)
    }
}
