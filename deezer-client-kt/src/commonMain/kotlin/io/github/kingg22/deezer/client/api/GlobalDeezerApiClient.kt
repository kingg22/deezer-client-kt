@file:OptIn(ExperimentalDeezerClient::class, InternalDeezerClient::class)

package io.github.kingg22.deezer.client.api

import io.github.kingg22.deezer.client.utils.ExperimentalDeezerClient
import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import io.github.kingg22.deezer.client.utils.LateInitClient
import kotlinx.coroutines.isActive
import kotlin.jvm.JvmField
import kotlin.jvm.JvmStatic

/**
 * Fallback object to get an instance of [DeezerApiClient].
 *
 * This is used for [io.github.kingg22.deezer.client.api.objects.Resource.reload] and
 * [io.github.kingg22.deezer.client.api.objects.PaginatedResponse] to fetch and reload objects.
 *
 * By default, all new instances of [DeezerApiClient] fill this holder if needed.
 *
 * @see [io.github.kingg22.deezer.client.api.objects.Resource.reload]
 * @see [io.github.kingg22.deezer.client.api.objects.PaginatedResponse.fetchNext]
 * @see [io.github.kingg22.deezer.client.api.objects.PaginatedResponse.fetchPrevious]
 */
data object GlobalDeezerApiClient : LateInitClient {
    /**
     * The actual instance of [DeezerApiClient]
     *
     * *Java users*: Don't confuse `instance` with `INSTANCE`, the second refers to `GlobalDeezerApiClient` kotlin object.
     */
    @JvmField
    var instance: DeezerApiClient? = null

    override fun isInitialized() = instance != null

    @JvmStatic
    fun requireInstance() = requireNotNull(instance) { "DeezerApiClient not initialized" }

    @JvmStatic
    fun initIfNeeded(client: DeezerApiClient) {
        if (instance == null) {
            instance = client
            return
        }
        if (instance?.httpClient?.isActive == false) instance = client
    }

    @JvmStatic
    fun reset() {
        instance = null
    }

    @JvmStatic
    fun resetIf(condition: Boolean) {
        if (condition) reset()
    }

    @JvmStatic
    fun resetIf(condition: () -> Boolean) {
        resetIf(condition())
    }
}
