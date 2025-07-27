package io.github.kingg22.deezer.client.utils

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
}
