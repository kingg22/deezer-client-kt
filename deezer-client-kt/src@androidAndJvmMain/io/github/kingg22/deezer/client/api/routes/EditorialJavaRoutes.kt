package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Album
import io.github.kingg22.deezer.client.api.objects.Chart
import io.github.kingg22.deezer.client.api.objects.Editorial
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.toKotlinLocalDate
import org.jetbrains.annotations.Blocking
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import java.time.LocalDate as JavaLocalDate

/**
 * Defines all endpoints related to [Editorial]
 * @author Kingg22
 */
@PublishedApi
/*
This is internal, for kotlin consumers can't access this, but Java consumers can access avoid internal stuff.
Is PublishedApi to maintain binary compatibility.
 */
internal class EditorialJavaRoutes private constructor(
    private val delegate: EditorialRoutes,
) {
    /** Retrieve all [Editorial] blocking the thread */
    @Blocking
    @JvmOverloads
    fun getAll(index: Int? = null, limit: Int? = null) = runBlocking { delegate.getAll(index, limit) }

    /** Retrieve an [Editorial] by ID blocking the thread */
    @Blocking
    @JvmOverloads
    fun getById(id: Long, index: Int? = null, limit: Int? = null) = runBlocking { delegate.getById(id, index, limit) }

    /**
     * Retrieve [PaginatedResponse] with [Album] selected every week by the Deezer Team blocking the thread.
     *
     * @param id ID of the editorial
     * @param date Date of the selection
     * @param index Index of the first item
     * @param limit Number of items to retrieve
     */
    @Blocking
    @JvmOverloads
    fun getDeezerSelection(id: Long = 0, date: JavaLocalDate? = null, index: Int? = null, limit: Int? = null) =
        runBlocking { delegate.getDeezerSelection(id, date?.toKotlinLocalDate(), index, limit) }

    /** Retrieve [Chart] blocking the thread */
    @Blocking
    @JvmOverloads
    fun getCharts(id: Long = 0, index: Int? = null, limit: Int? = null) =
        runBlocking { delegate.getCharts(id, index, limit) }

    /** Retrieve [PaginatedResponse] with new [Album] releases per genre for the current country blocking the thread */
    @Blocking
    @JvmOverloads
    fun getReleases(id: Long = 0, index: Int? = null, limit: Int? = null) =
        runBlocking { delegate.getReleases(id, index, limit) }

    // -- Completable Future --

    /** Retrieve all [Editorial] with [CompletableFuture] */
    @JvmOverloads
    fun getAllFuture(
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Editorial>> =
        CoroutineScope(coroutineContext).future { delegate.getAll(index, limit) }

    /** Retrieve an [Editorial] by ID with [CompletableFuture] */
    @JvmOverloads
    fun getByIdFuture(
        id: Long,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<Editorial> = CoroutineScope(coroutineContext).future { delegate.getById(id, index, limit) }

    /**
     * Retrieve [PaginatedResponse] with [Album] selected every week by the Deezer Team with [CompletableFuture].
     *
     * @param id ID of the editorial
     * @param date Date of the selection
     * @param index Index of the first item
     * @param limit Number of items to retrieve
     * @param coroutineContext Context to execute the coroutine
     */
    @JvmOverloads
    fun getDeezerSelectionFuture(
        id: Long = 0,
        date: JavaLocalDate? = null,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Album>> = CoroutineScope(coroutineContext).future {
        delegate.getDeezerSelection(id, date?.toKotlinLocalDate(), index, limit)
    }

    /** Retrieve [Chart] with [CompletableFuture] */
    @JvmOverloads
    fun getChartsFuture(
        id: Long = 0,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<Chart> = CoroutineScope(coroutineContext).future { delegate.getCharts(id, index, limit) }

    /** Retrieve [PaginatedResponse] with new [Album] releases per genre for the current country with [CompletableFuture] */
    @JvmOverloads
    fun getReleasesFuture(
        id: Long = 0,
        index: Int? = null,
        limit: Int? = null,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<PaginatedResponse<Album>> =
        CoroutineScope(coroutineContext).future { delegate.getReleases(id, index, limit) }

    companion object {
        /** Create an [EditorialJavaRoutes] with [Editorial Kotlin Routes][EditorialRoutes] as delegate. */
        @PublishedApi
        @JvmStatic
        @JvmName("create")
        internal fun EditorialRoutes.asJava() = EditorialJavaRoutes(this)
    }
}
