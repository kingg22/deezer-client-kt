package io.github.kingg22.deezerSdk.api.objects

import io.github.kingg22.deezerSdk.api.DeezerApiClient
import kotlinx.serialization.Serializable

/**
 * _Internal_ Represent a Resource of [Deezer API](https://developers.deezer.com/api/).
 *
 * Inspired in [Deezer Python Resource](https://github.com/browniebroke/deezer-python/blob/06065d357be3761e895e3605fdc0619783685b7c/src/deezer/resources/resource.py)
 * @author Kingg22
 */
@Serializable
abstract class Resource {
    abstract val id: Long
    abstract val type: String

    companion object {
        /** The [DeezerApiClient] to make operations easy */
        val client: DeezerApiClient = DeezerApiClient
    }

    /** Reloads the current resource from the API, getting all of its full properties if it was initially obtained partially */
    abstract suspend fun reload(): Resource
}
