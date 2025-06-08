package io.github.kingg22.deezerSdk.utils

/**
 * Interface for a client that can be initialized.
 *
 * Define an `initialize` and [isInitialized] method.
 * @author Kingg22
 */
interface LateInitClient {
    /**
     * Indicate if the client is already initialized.
     * @return `true` if the client is initialized, `false` otherwise.
     */
    fun isInitialized(): Boolean

    /**
     * Indicate if the client is partially initialized.
     * @return `true` if the client is partially initialized, `false` otherwise.
     */
    fun isPartialInitialized() = isInitialized()
}
