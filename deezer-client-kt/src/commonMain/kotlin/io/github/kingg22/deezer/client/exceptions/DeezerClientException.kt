package io.github.kingg22.deezer.client.exceptions

import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * General exception of the _Deezer Client_
 *
 * This is a design open to expanding in the future.
 *
 * @constructor Initialize with a message and cause if exist
 */
open class DeezerClientException @JvmOverloads constructor(
    /** Message to help the developer find a solution */
    messageString: String? = null,
    /**
     * The throwable that caused this throwable to get thrown, or null if this
     * throwable was not caused by another throwable, or if the causative
     * throwable is unknown.
     */
    cause: Throwable? = null,
) : RuntimeException(
    buildString {
        append("[Deezer Client Exception]")
        messageString?.let { append(": $it") }
        append(generateLinks())
    },
    cause,
) {
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
