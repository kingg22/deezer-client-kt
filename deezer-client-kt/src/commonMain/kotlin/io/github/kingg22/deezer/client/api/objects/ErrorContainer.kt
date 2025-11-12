package io.github.kingg22.deezer.client.api.objects

import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import kotlinx.serialization.Serializable

/**
 * Represent the error response of [Deezer API](https://developers.deezer.com/api/).
 *
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/errors">Deezer Errors</a>
 */
@Serializable
@InternalDeezerClient
internal class ErrorContainer(val error: ErrorDetail) {
    @Serializable
    @InternalDeezerClient
    internal class ErrorDetail(val type: String, val message: String? = null, val code: Int)
}
