package io.github.kingg22.deezer.client.api.objects

import io.github.kingg22.deezer.client.exceptions.DeezerApiException
import io.github.kingg22.deezer.client.utils.AfterInitialize
import kotlin.coroutines.cancellation.CancellationException
import kotlin.jvm.JvmSynthetic

/**
 * _Internal_ Represents a Resource of [Deezer API](https://developers.deezer.com/api/).
 *
 * Inspired in [Deezer Python Resource](https://github.com/browniebroke/deezer-python/blob/06065d357be3761e895e3605fdc0619783685b7c/src/deezer/resources/resource.py)
 * @author Kingg22
 */
interface Resource {
    val id: Long
    val type: String

    /** Reloads the current resource from the API, getting all of its full properties if it was initially obtained partially, or it's outdated */
    @AfterInitialize
    @JvmSynthetic
    @Throws(DeezerApiException::class, CancellationException::class)
    suspend fun reload(): Resource
}
