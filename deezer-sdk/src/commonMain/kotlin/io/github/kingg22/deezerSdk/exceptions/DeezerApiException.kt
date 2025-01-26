package io.github.kingg22.deezerSdk.exceptions

import kotlinx.serialization.Serializable

/**
 * Represents an exception specific to the [Deezer API](https://developers.deezer.com/api/).
 *
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/errors">Deezer API errors</a>
 * @see DeezerApiException.DeezerErrorCode
 *
 * @param errorCode The code of the exception
 * @param errorMessage The messages to logger. Default description of the error code
 * @param cause The cause of the exception
 */
class DeezerApiException(errorCode: Int? = null, private val errorMessage: String? = null, cause: Throwable? = null) :
    DeezerSdkException(errorMessage, cause) {

    private val error: DeezerErrorCode? = errorCode?.let { DeezerErrorCode.fromCode(it) }

    /**
     * Returns a more detailed error description, including the error code and message.
     *
     * @return Assembled response code and reason.
     */
    override val message: String
        get() = buildString {
            append("[Deezer API Exception]")
            // Don't call to super, more detail message
            error?.let { append(" [Error: ${it.description} (Code: ${it.code})]") }
            if (!errorMessage.isNullOrBlank()) append(": $errorMessage")
        }

    /**
     * [Deezer API](https://developers.deezer.com/api/) returns some error codes if the request failed.
     * Here is the list of all codes and their description you can encounter.
     *
     * @author Kingg22
     * @see <a href="https://developers.deezer.com/api/errors">Deezer API errors</a>
     */
    @Serializable
    enum class DeezerErrorCode(val code: Int, val description: String) {
        QUOTA(4, "Quota exceeded"),
        ITEMS_LIMIT_EXCEEDED(100, "Items limit exceeded"),
        PERMISSION(200, "Permission denied"),
        TOKEN_INVALID(300, "Invalid token"),
        PARAMETER(500, "Invalid parameter"),
        PARAMETER_MISSING(501, "Missing parameter"),
        QUERY_INVALID(600, "Invalid query"),
        SERVICE_BUSY(700, "Service busy"),
        DATA_NOT_FOUND(800, "Data not found"),
        INDIVIDUAL_ACCOUNT_NOT_ALLOWED(901, "Individual account not allowed"),
        ;

        companion object {
            /**
             * Searches for a code in the Deezer Error Code enum.
             * @return [DeezerErrorCode] or null if no element with the provided code is found
             */
            fun fromCode(code: Int): DeezerErrorCode? = entries.firstOrNull { it.code == code }
        }
    }
}
