@file:Suppress("kotlin:S6517", "DEPRECATION")

package io.github.kingg22.deezer.client.utils

/**
 * Interface for a client that can be initialized.
 *
 * Define an `initialize` and [isInitialized] method.
 * @author Kingg22
 */
@Deprecated("Since GlobalDeezerClient are deprecated, this is not needed anymore.", level = DeprecationLevel.ERROR)
interface LateInitClient {
    /**
     * Indicate if the client is already initialized.
     * @return `true` if the client is initialized, `false` otherwise.
     */
    fun isInitialized(): Boolean
}
