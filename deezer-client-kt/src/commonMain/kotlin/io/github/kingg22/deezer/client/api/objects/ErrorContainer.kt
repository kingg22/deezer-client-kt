package io.github.kingg22.deezer.client.api.objects

import kotlinx.serialization.Serializable

/**
 * Represent the error response of [Deezer API](https://developers.deezer.com/api/).
 *
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/errors">Deezer Errors</a>
 */
@Serializable
data class ErrorContainer(val error: ErrorDetail) {
    @Serializable
    data class ErrorDetail(val type: String, val message: String? = null, val code: Int)
}
