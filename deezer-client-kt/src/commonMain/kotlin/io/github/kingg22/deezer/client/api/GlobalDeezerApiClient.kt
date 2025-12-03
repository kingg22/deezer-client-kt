package io.github.kingg22.deezer.client.api

import kotlinx.coroutines.isActive

/**
 * Object to get an instance of [DeezerApiClient].
 *
 * This is used for [reload][io.github.kingg22.deezer.client.api.objects.Resource.reload] and
 * [fetch][io.github.kingg22.deezer.client.api.objects.PaginatedResponse] resources.
 *
 * By default, all new instances of [DeezerApiClient] fill this holder if needed.
 *
 * @see [io.github.kingg22.deezer.client.api.objects.Resource.reload]
 * @see [io.github.kingg22.deezer.client.api.objects.fetchNext]
 * @see [io.github.kingg22.deezer.client.api.objects.fetchPrevious]
 */
@Suppress("DEPRECATION", "DEPRECATION_ERROR")
@Deprecated("Create and store a DeezerApiClient by yourself", level = DeprecationLevel.ERROR)
object GlobalDeezerApiClient : io.github.kingg22.deezer.client.utils.LateInitClient {
    /**
     * The actual instance of [DeezerApiClient]
     *
     * *Java users*: Don't confuse `instance` with `INSTANCE`, the second refers to `GlobalDeezerApiClient` kotlin object.
     */
    @JvmField
    var instance: DeezerApiClient? = null

    override fun isInitialized() = instance != null

    /**
     * Retrieve the current instance of the api client.
     * @throws IllegalArgumentException If not there exists current instance
     */
    @JvmStatic
    @Throws(IllegalArgumentException::class)
    fun requireInstance() = requireNotNull(instance) { "DeezerApiClient not initialized" }

    /**
     * Evaluate the current instance and set only if needed
     * @param client a new instance of the api client
     */
    @JvmStatic
    fun initIfNeeded(client: DeezerApiClient) {
        if (instance == null) {
            instance = client
            return
        }
        if (instance?.httpClient?.isActive == false) instance = client
    }

    /** Make `null` the current saved instance */
    @JvmStatic
    fun reset() {
        instance = null
    }

    /**
     * Reset only if the condition is `true`
     * @see reset
     */
    @JvmStatic
    fun resetIf(condition: Boolean) {
        if (condition) reset()
    }

    /**
     * Reset only if the condition is `true`
     * @see reset
     */
    @JvmStatic
    fun resetIf(condition: () -> Boolean) {
        resetIf(condition())
    }
}
