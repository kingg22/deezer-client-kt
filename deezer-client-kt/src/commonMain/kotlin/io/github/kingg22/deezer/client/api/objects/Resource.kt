package io.github.kingg22.deezer.client.api.objects

import io.github.kingg22.deezer.client.api.DeezerApiClient
import io.github.kingg22.deezer.client.exceptions.DeezerApiException
import io.github.kingg22.deezer.client.utils.AfterInitialize
import kotlin.coroutines.cancellation.CancellationException

/**
 * _Internal_ Represents a Resource of [Deezer API](https://developers.deezer.com/api/).
 *
 * Inspired in [Deezer Python Resource](https://github.com/browniebroke/deezer-python/blob/06065d357be3761e895e3605fdc0619783685b7c/src/deezer/resources/resource.py)
 * @author Kingg22
 */
@JvmDefaultWithoutCompatibility
interface Resource {
    val id: Long
    val type: String

    /**
     * Reloads the current resource from the API,
     * getting all of its full properties if it was initially obtained partially, or it's outdated
     * @param client The Deezer API client to use
     */
    @JvmSynthetic
    @Throws(DeezerApiException::class, CancellationException::class)
    suspend fun reload(client: DeezerApiClient): Resource

    /**
     * Reloads the current resource from the API,
     * getting all of its full properties if it was initially obtained partially, or it's outdated
     */
    @Suppress("DEPRECATION")
    @Deprecated(
        "Use reload(client: DeezerApiClient) instead, pass a client explicitly",
        ReplaceWith("reload(client)"),
        level = DeprecationLevel.WARNING,
    )
    @AfterInitialize
    @JvmSynthetic
    @Throws(DeezerApiException::class, CancellationException::class)
    suspend fun reload(): Resource = reload(io.github.kingg22.deezer.client.api.GlobalDeezerApiClient.requireInstance())
}
