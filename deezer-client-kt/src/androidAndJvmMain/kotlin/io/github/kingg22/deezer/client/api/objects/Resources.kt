package io.github.kingg22.deezer.client.api.objects

import io.github.kingg22.deezer.client.api.DeezerApiClient
import io.github.kingg22.deezer.client.api.DeezerApiJavaClient
import io.github.kingg22.deezer.client.utils.ExperimentalDeezerClient
import org.jetbrains.annotations.Blocking
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Helper that provides utilities for handling reload resources **in Java code**.
 *
 * Includes methods to reload resource using synchronous blocking or asynchronous operations.
 *
 * For Kotlin users: **You don't need this, use the member function instead, this is only for Java**
 */
@Deprecated("Use the member function instead")
@Suppress("unused", "UNCHECKED_CAST")
@ExperimentalDeezerClient
/*
This is internal, for kotlin consumers can't access this, but Java consumers can access avoid internal stuff.
Is PublishedApi to maintain binary compatibility.
 */
@PublishedApi
internal object Resources {
    /** Reloads the resource from the API, getting all of its full properties if it was initially obtained partially, or it's outdated */
    @Blocking
    @JvmOverloads
    @JvmStatic
    fun <T : Resource> reload(
        client: DeezerApiJavaClient,
        resource: T,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): T = resource.reload(client.delegate, coroutineContext) as T

    /**
     * Reloads the resource from the API with [CompletableFuture],
     * getting all of its full properties if it was initially obtained partially, or it's outdated.
     */
    @JvmOverloads
    @JvmStatic
    fun <T : Resource> reloadFuture(
        client: DeezerApiJavaClient,
        resource: T,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<T> = resource.reloadFuture(client.delegate, coroutineContext) as CompletableFuture<T>

    /** Reloads the resource from the API, getting all of its full properties if it was initially obtained partially, or it's outdated */
    @Blocking
    @JvmOverloads
    @JvmStatic
    fun <T : Resource> reload(
        client: DeezerApiClient,
        resource: T,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): T = resource.reload(client, coroutineContext) as T

    /**
     * Reloads the resource from the API with [CompletableFuture],
     * getting all of its full properties if it was initially obtained partially, or it's outdated.
     */
    @JvmOverloads
    @JvmStatic
    fun <T : Resource> reloadFuture(
        client: DeezerApiClient,
        resource: T,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<T> = resource.reloadFuture(client, coroutineContext) as CompletableFuture<T>

    /** Reloads the resource from the API, getting all of its full properties if it was initially obtained partially, or it's outdated */
    @Deprecated(
        "Use reload(client, resource, coroutineContext) instead, pass a client explicitly and optionally a coroutine context to use",
        ReplaceWith("reload(client, resource)"),
        DeprecationLevel.ERROR,
    )
    @Suppress("DEPRECATION", "DEPRECATION_ERROR")
    @Blocking
    @JvmStatic
    fun <T : Resource> reload(resource: T): T = resource.reloadBlocking() as T

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
    @JvmStatic
    fun <T : Resource> reloadFuture(
        resource: T,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<T> = resource.reloadFuture() as CompletableFuture<T>
}
