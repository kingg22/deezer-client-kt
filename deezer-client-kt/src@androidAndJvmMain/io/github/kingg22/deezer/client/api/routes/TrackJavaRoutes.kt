package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Track
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.runBlocking
import org.jetbrains.annotations.Blocking
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Defines all endpoints related to [Track]
 * @author Kingg22
 */
@PublishedApi
/*
This is internal, for kotlin consumers can't access this, but Java consumers can access avoid internal stuff.
Is PublishedApi to maintain binary compatibility.
 */
internal class TrackJavaRoutes private constructor(
    private val delegate: TrackRoutes,
) {
    /** Retrieve a [Track] by ID blocking the thread. */
    @Blocking
    fun getById(id: Long) = runBlocking { delegate.getById(id) }

    /** Retrieve a [Track] by ISRC (International Standard Recording Code) blocking the thread */
    @Blocking
    fun getByIsrc(isrc: String) = runBlocking { delegate.getByIsrc(isrc) }

    // -- Completable Future --

    /** Retrieve a [Track] by ID with [CompletableFuture] */
    @JvmOverloads
    fun getByIdFuture(id: Long, coroutineContext: CoroutineContext = EmptyCoroutineContext): CompletableFuture<Track> =
        CoroutineScope(coroutineContext).future { delegate.getById(id) }

    /** Retrieve a [Track] by ISRC (International Standard Recording Code) with [CompletableFuture] */
    @JvmOverloads
    fun getByIsrcFuture(
        isrc: String,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<Track> = CoroutineScope(coroutineContext).future { delegate.getByIsrc(isrc) }

    companion object {
        /** Create a [TrackJavaRoutes] with [Track Kotlin Routes][TrackRoutes] as delegate. */
        @PublishedApi
        @JvmStatic
        @JvmName("create")
        internal fun TrackRoutes.asJava() = TrackJavaRoutes(this)
    }
}
