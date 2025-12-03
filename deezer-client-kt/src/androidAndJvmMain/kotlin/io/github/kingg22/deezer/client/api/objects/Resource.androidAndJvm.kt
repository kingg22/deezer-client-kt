package io.github.kingg22.deezer.client.api.objects

import io.github.kingg22.deezer.client.api.DeezerApiClient
import io.github.kingg22.deezer.client.api.DeezerApiJavaClient
import io.github.kingg22.deezer.client.exceptions.DeezerApiException
import io.github.kingg22.deezer.client.utils.AfterInitialize
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.runBlocking
import org.jetbrains.annotations.Blocking
import java.util.concurrent.CancellationException
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

@Suppress("unused")
actual abstract class Resource {
    actual abstract val id: Long
    actual abstract val type: String

    @JvmSynthetic
    @Throws(DeezerApiException::class, CancellationException::class)
    actual abstract suspend fun reload(client: DeezerApiClient): Resource

    /** Reloads the resource from the API, getting all of its full properties if it was initially obtained partially, or it's outdated */
    @PublishedApi
    @Blocking
    @JvmOverloads
    internal fun reload(
        client: DeezerApiJavaClient,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): Resource = runBlocking(coroutineContext) { reload(client.delegate) }

    /**
     * Reloads the resource from the API with [CompletableFuture],
     * getting all of its full properties if it was initially obtained partially, or it's outdated.
     */
    @PublishedApi
    @JvmOverloads
    internal fun reloadFuture(
        client: DeezerApiJavaClient,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<Resource> = CoroutineScope(coroutineContext).future { reload(client.delegate) }

    /** Reloads the resource from the API, getting all of its full properties if it was initially obtained partially, or it's outdated */
    @Blocking
    @JvmOverloads
    @PublishedApi
    internal fun reload(
        client: DeezerApiClient,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): Resource = runBlocking(coroutineContext) { reload(client) }

    /**
     * Reloads the resource from the API with [CompletableFuture],
     * getting all of its full properties if it was initially obtained partially, or it's outdated.
     */
    @JvmOverloads
    @PublishedApi
    internal fun reloadFuture(
        client: DeezerApiClient,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<Resource> = CoroutineScope(coroutineContext).future { reload(client) }

    /* -- DEPRECATED ZONE--  */

    @Suppress("DEPRECATION", "DEPRECATION_ERROR")
    @Deprecated(
        message = "Use reload(client: DeezerApiClient) instead, pass a client explicitly",
        replaceWith = ReplaceWith(expression = "reload(client)"),
        level = DeprecationLevel.ERROR,
    )
    @AfterInitialize
    @JvmSynthetic
    @Throws(DeezerApiException::class, CancellationException::class)
    actual open suspend fun reload(): Resource =
        reload(io.github.kingg22.deezer.client.api.GlobalDeezerApiClient.requireInstance())

    /** Reloads the resource from the API, getting all of its full properties if it was initially obtained partially, or it's outdated */
    @JvmName("reload")
    @Deprecated(
        "Use reload(client, resource, coroutineContext) instead, pass a client explicitly and optionally a coroutine context to use",
        ReplaceWith("reload(client, resource)"),
        DeprecationLevel.ERROR,
    )
    @Suppress("DEPRECATION", "DEPRECATION_ERROR")
    @Blocking
    fun reloadBlocking(): Resource = runBlocking { reload() }

    /**
     * Reloads the resource from the API with [CompletableFuture],
     * getting all of its full properties if it was initially obtained partially, or it's outdated.
     */
    @Deprecated(
        "Use reloadFuture(client, resource, coroutineContext) instead, pass a client explicitly",
        ReplaceWith("reloadFuture(client, resource)"),
        DeprecationLevel.ERROR,
    )
    @Suppress("DEPRECATION", "DEPRECATION_ERROR")
    @JvmOverloads
    fun reloadFuture(coroutineContext: CoroutineContext = EmptyCoroutineContext): CompletableFuture<out Resource> =
        CoroutineScope(coroutineContext).future { reload() }
}
