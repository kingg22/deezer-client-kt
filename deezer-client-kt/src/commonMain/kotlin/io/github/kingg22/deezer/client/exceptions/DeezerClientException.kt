package io.github.kingg22.deezer.client.exceptions

import kotlin.jvm.JvmOverloads

/**
 * General exception of the _Deezer Client_
 *
 * This is a design open to expanding in the future.
 */
open class DeezerClientException
/**
 * Initialize with a message and cause if exist
 * @param message a message to help the developer find a solution
 * @param cause when a cause is external
 */
@JvmOverloads
constructor(
    val messageString: String? = null,
    override val cause: Throwable? = null,
) : RuntimeException(messageString, cause) {
    override val message = buildString {
        append("[Deezer Client Exception]")
        messageString?.let { append(": $it") }
        generateLinks()
    }

    /** Append links to repository and documentation */
    protected fun generateLinks() = buildString {
        appendLine()
        append("For more detail, see documentation: https://kingg22.github.io/deezer-client-kt/")
        appendLine()
        append("Or the repository: https://github.com/kingg22/deezer-client-kt")
    }
}
