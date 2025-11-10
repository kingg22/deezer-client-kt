package io.github.kingg22.deezer.client.exceptions

import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * General exception of the _Deezer Client_
 *
 * This is a design open to expanding in the future.
 */
open class DeezerClientException
/**
 * Initialize with a message and cause if exist
 * @param messageString a message to help the developer find a solution
 * @param cause when a cause is external
 */
@JvmOverloads
constructor(
    /** Message to help the developer find a solution */
    val messageString: String? = null,
    /**
     * The throwable that caused this throwable to get thrown, or null if this
     * throwable was not caused by another throwable, or if the causative
     * throwable is unknown.
     */
    override val cause: Throwable? = null,
) : RuntimeException(messageString, cause) {
    /** Specific details about the exception */
    override val message = buildString {
        append("[Deezer Client Exception]")
        messageString?.let { append(": $it") }
        generateLinks()
    }

    /** Utility functions to create this exception */
    companion object {
        /** Append links to repository and documentation */
        @JvmStatic
        protected fun generateLinks() = buildString {
            appendLine("For more detail, see documentation: https://kingg22.github.io/deezer-client-kt/")
            appendLine("Or the repository: https://github.com/kingg22/deezer-client-kt")
        }
    }
}
