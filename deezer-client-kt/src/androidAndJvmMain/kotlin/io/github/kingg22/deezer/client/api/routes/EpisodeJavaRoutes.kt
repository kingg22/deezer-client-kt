package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Episode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.runBlocking
import org.jetbrains.annotations.Blocking
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Defines all endpoints related to [Episode]
 * @author Kingg22
 */
@PublishedApi
/*
This is internal, for kotlin consumers can't access this, but Java consumers can access avoid internal stuff.
Is PublishedApi to maintain binary compatibility.
 */
internal class EpisodeJavaRoutes private constructor(
    private val delegate: EpisodeRoutes,
) {
    /** Retrieve an [Episode] by ID blocking the thread. */
    @Blocking
    fun getById(id: Long) = runBlocking { delegate.getById(id) }

    // -- Completable Future --

    /** Retrieve an [Episode] by ID with [CompletableFuture] */
    @JvmOverloads
    fun getByIdFuture(
        id: Long,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<Episode> = CoroutineScope(coroutineContext).future { delegate.getById(id) }

    companion object {
        /** Create an [EpisodeJavaRoutes] with [Episode Kotlin Routes][EpisodeRoutes] as delegate. */
        @PublishedApi
        @JvmStatic
        @JvmName("create")
        internal fun EpisodeRoutes.asJava() = EpisodeJavaRoutes(this)
    }
}
