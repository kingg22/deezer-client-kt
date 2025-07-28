package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Episode
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse
import io.github.kingg22.deezer.client.api.objects.Podcast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.runBlocking
import org.jetbrains.annotations.Blocking
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Defines all endpoints related to [Podcast]
 * @author Kingg22
 */
@PublishedApi
/*
This is internal, for kotlin consumers can't access this, but Java consumers can access avoid internal stuff.
Is PublishedApi to maintain binary compatibility.
 */
internal class PodcastJavaRoutes private constructor(
    private val delegate: PodcastRoutes,
) {
    /**
     * Retrieve all [Podcast] blocking the thread.
     *
     * _Dev Note_: Always return an empty list?
     */
    @Blocking
    @JvmOverloads
    fun getAll(index: Int? = null, limit: Int? = null) = runBlocking { delegate.getAll(index, limit) }

    /** Retrieve a [Podcast] by ID blocking the thread */
    @Blocking
    @JvmOverloads
    fun getById(id: Long, index: Int? = null, limit: Int? = null) = runBlocking { delegate.getById(id, index, limit) }

    /** Retrieve a [PaginatedResponse] with all [Episode] of the podcast blocking the thread */
    @Blocking
    @JvmOverloads
    fun getEpisodes(id: Long, index: Int? = null, limit: Int? = null) =
        runBlocking { delegate.getEpisodes(id, index, limit) }

    // -- Completable Future --

    /**
     * Retrieve all [Podcast] with [CompletableFuture].
     *
     * _Dev Note_: Always return an empty list?
     */
    @JvmOverloads
    fun getAllFuture(
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Podcast>> =
        CoroutineScope(coroutineContext).future { delegate.getAll(index, limit) }

    /** Retrieve a [Podcast] by ID with [CompletableFuture] */
    @JvmOverloads
    fun getByIdFuture(
        id: Long,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<Podcast> = CoroutineScope(coroutineContext).future { delegate.getById(id, index, limit) }

    /** Retrieve a [PaginatedResponse] with all [Episode] of the podcast with [CompletableFuture] */
    @JvmOverloads
    fun getEpisodesFuture(
        id: Long,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Episode>> =
        CoroutineScope(coroutineContext).future { delegate.getEpisodes(id, index, limit) }

    companion object {
        /** Create a [PodcastJavaRoutes] with [Podcast Kotlin Routes][PodcastRoutes] as delegate. */
        @PublishedApi
        @JvmStatic
        @JvmName("create")
        internal fun PodcastRoutes.asJava() = PodcastJavaRoutes(this)
    }
}
