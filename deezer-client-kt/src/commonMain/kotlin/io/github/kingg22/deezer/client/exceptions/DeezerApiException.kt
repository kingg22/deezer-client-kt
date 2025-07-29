package io.github.kingg22.deezer.client.exceptions

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

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
 * @property error The [Deezer Error Code][DeezerApiException.DeezerErrorCode] if is set
 */
data class DeezerApiException @JvmOverloads constructor(
    private val errorCode: Int? = null,
    private val errorMessage: String? = null,
    override val cause: Throwable? = null,
) : DeezerClientException(errorMessage, cause) {
    val error = errorCode?.let { DeezerErrorCode.fromCode(it) }

    override val message = buildString {
        append("[Deezer API Exception]")
        // Don't call supper, generate a more detail message
        error?.let {
            append(" [Error: ${it.description} (Code: ${it.code})]")
            appendLine()
            append("For more detail, see: https://developers.deezer.com/api/errors")
            appendLine()
        }
        if (!errorMessage.isNullOrBlank()) append("Detail: $errorMessage")
        append("-------------")
        append(generateLinks())
    }

    /**
     * [Deezer API](https://developers.deezer.com/api/) returns some error codes if the request failed.
     * Here is the list of all codes and their description you can encounter.
     *
     * @property code Number code of the exception
     * @property description Human-readable name
     * @property type Type of Deezer Errors
     *
     * @author Kingg22
     * @see <a href="https://developers.deezer.com/api/errors">Deezer API errors</a>
     */
    @Serializable
    enum class DeezerErrorCode(val code: Int, val description: String, val type: String? = null) {
        /** Quota exceeded */
        QUOTA(4, "Quota exceeded", "Exception"),

        /** Items limit exceeded */
        ITEMS_LIMIT_EXCEEDED(100, "Items limit exceeded", "Exception"),

        /** Permission denied */
        PERMISSION(200, "Permission denied", "OAuthException"),

        /** Invalid token */
        TOKEN_INVALID(300, "Invalid token", "OAuthException"),

        /** Invalid parameter */
        PARAMETER(500, "Invalid parameter", "ParameterException"),

        /** Missing parameter */
        PARAMETER_MISSING(501, "Missing parameter", "MissingParameterException"),

        /** Invalid query */
        QUERY_INVALID(600, "Invalid query", "InvalidQueryException"),

        /** Service busy */
        SERVICE_BUSY(700, "Service busy", "Exception"),

        /** Data not found */
        DATA_NOT_FOUND(800, "Data not found", "DataException"),

        /** Individual account isn't allowed */
        INDIVIDUAL_ACCOUNT_NOT_ALLOWED(
            901,
            "Individual account not allowed",
            "InvalidAccountChangedNotAllowedException",
        ),
        ;

        /** Utility for Deezer Error Code */
        companion object {
            /**
             * Searches for a code in the Deezer Error Code enum.
             * @return [DeezerErrorCode] or null if no element with the provided code is found
             */
            @JvmStatic
            fun fromCode(code: Int) = entries.firstOrNull { it.code == code }
        }
    }
}
