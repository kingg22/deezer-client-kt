package io.github.kingg22.deezer.client.api.objects

import io.github.kingg22.deezer.client.api.GlobalDeezerApiClient
import io.github.kingg22.deezer.client.exceptions.DeezerApiException
import io.github.kingg22.deezer.client.utils.AfterInitialize
import io.github.kingg22.deezer.client.utils.ForJavaDeezerSdk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Transient
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.cancellation.CancellationException

/**
 * _Internal_ Represents a Resource of [Deezer API](https://developers.deezer.com/api/).
 *
 * Inspired in [Deezer Python Resource](https://github.com/browniebroke/deezer-python/blob/06065d357be3761e895e3605fdc0619783685b7c/src/deezer/resources/resource.py)
 * @author Kingg22
 */
abstract class Resource {
    abstract val id: Long
    abstract val type: String

    companion object {
        /** The [io.github.kingg22.deezer.client.api.DeezerApiClient] to make operations easy */
        @AfterInitialize
        @Transient
        @JvmStatic
        val client by lazy { GlobalDeezerApiClient.requireInstance() }
    }

    /** Reloads the current resource from the API, getting all of its full properties if it was initially obtained partially */
    @AfterInitialize
    @JvmSynthetic
    @Throws(DeezerApiException::class, CancellationException::class)
    abstract suspend fun reload(): Resource

    /**
     * Reloads the current resource from the API, getting all of its full properties if it was initially obtained partially.
     *
     * _For Java:_ You need to cast the result to the expected type example:
     * ```Java
     * Type resource = ...;
     * var obj = (Type) resource.reload();
     * ```
     */
    @AfterInitialize
    @ForJavaDeezerSdk
    @JvmName("reload")
    @Throws(DeezerApiException::class, CancellationException::class)
    fun reloadBlocking() = runBlocking { reload() }

    /**
     * Reloads the current resource from the API, getting all of its full properties if it was initially obtained partially.
     *
     * _For Java:_ You need to cast the result to the expected type example:
     * ```Java
     * Type resource = ...;
     * var obj = (Type) resource.reload();
     * ```
     */
    @AfterInitialize
    @ForJavaDeezerSdk
    @JvmName("reloadFuture")
    @Throws(DeezerApiException::class, CancellationException::class)
    fun reloadJava(coroutineContext: CoroutineContext = EmptyCoroutineContext) =
        CoroutineScope(coroutineContext).future { reload() }
}
