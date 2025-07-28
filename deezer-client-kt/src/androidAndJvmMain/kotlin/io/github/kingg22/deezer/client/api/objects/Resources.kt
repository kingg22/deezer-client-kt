@file:Suppress("UNCHECKED_CAST")

package io.github.kingg22.deezer.client.api.objects

import io.github.kingg22.deezer.client.utils.ExperimentalDeezerClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.runBlocking
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
@ExperimentalDeezerClient
/*
This is internal, for kotlin consumers can't access this, but Java consumers can access avoid internal stuff.
Is PublishedApi to maintain binary compatibility.
 */
@PublishedApi
internal object Resources {
    /** Reloads the resource from the API, getting all of its full properties if it was initially obtained partially, or it's outdated */
    @Blocking
    @JvmStatic
    fun <T : Resource> reload(resource: T): T = runBlocking { resource.reload() as T }

    /**
     * Reloads the resource from the API with [CompletableFuture],
     * getting all of its full properties if it was initially obtained partially, or it's outdated.
     */
    @JvmOverloads
    @JvmStatic
    fun <T : Resource> reloadFuture(
        resource: T,
        coroutineContext: CoroutineContext = EmptyCoroutineContext,
    ): CompletableFuture<T> = CoroutineScope(coroutineContext).future { resource.reload() as T }
}
