package io.github.kingg22.deezer.client.api.objects

import io.github.kingg22.deezer.client.api.GlobalDeezerApiClient
import io.github.kingg22.deezer.client.exceptions.DeezerApiException
import io.github.kingg22.deezer.client.utils.AfterInitialize
import kotlin.coroutines.cancellation.CancellationException
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

/**
 * _Internal_ Represents a Resource of [Deezer API](https://developers.deezer.com/api/).
 *
 * Inspired in [Deezer Python Resource](https://github.com/browniebroke/deezer-python/blob/06065d357be3761e895e3605fdc0619783685b7c/src/deezer/resources/resource.py)
 * @author Kingg22
 */
abstract class Resource {
    abstract val id: Long
    abstract val type: String

    // Needed to define `companion object` on each inheritance to Java access to serializer of this and not supper
    companion object {
        /** The [io.github.kingg22.deezer.client.api.DeezerApiClient] to make operations easy */
        @AfterInitialize
        @JvmStatic
        protected fun client() = GlobalDeezerApiClient.requireInstance()
    }

    /** Reloads the current resource from the API, getting all of its full properties if it was initially obtained partially */
    @AfterInitialize
    @JvmSynthetic
    @Throws(DeezerApiException::class, CancellationException::class)
    abstract suspend fun reload(): Resource
}
