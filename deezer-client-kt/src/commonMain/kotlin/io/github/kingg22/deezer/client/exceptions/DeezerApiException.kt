package io.github.kingg22.deezer.client.exceptions

import io.github.kingg22.deezer.client.utils.DeezerApiPoko
import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * Represents an exception specific to the [Deezer API](https://developers.deezer.com/api/).
 *
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/errors">Deezer API errors</a>
 * @see DeezerApiException.DeezerErrorCode
 *
 * @property error The [Deezer Error Code][DeezerApiException.DeezerErrorCode] if is set
 */
@DeezerApiPoko
class DeezerApiException private constructor(
    val error: DeezerErrorCode?,
    override val message: String,
    override val cause: Throwable?,
) : DeezerClientException(cause = cause) {

    /**
     * Create new instance of [DeezerApiException], this is an internal constructor.
     * @param errorCode The code of the exception. See [DeezerErrorCode]
     * @param errorMessage The messages to logger. Default description of the error code
     * @param cause The cause of the exception
     */
    @InternalDeezerClient
    @JvmOverloads
    constructor(
        errorCode: Int? = null,
        errorMessage: String? = null,
        cause: Throwable? = null,
    ) : this(
        error = errorCode?.let { DeezerErrorCode.fromCode(it) },
        message = buildString {
            append("[Deezer API Exception]")
            errorCode?.let { intCode ->
                val deezerError = DeezerErrorCode.fromCode(intCode)
                if (deezerError != null) {
                    appendLine(
                        " [Error: ${deezerError.description} (Code: ${deezerError.code}, type: ${deezerError.type})]",
                    )
                } else {
                    appendLine(" [Error: Unknown (Code: $intCode)]")
                }
                appendLine("For more detail, see: https://developers.deezer.com/api/errors")
            }
            if (!errorMessage.isNullOrBlank()) appendLine("Detail: $errorMessage")
            append(generateLinks())
        },
        cause = cause,
    )

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
