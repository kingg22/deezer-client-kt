package io.github.kingg22.deezer.client.api.objects

import io.github.kingg22.deezer.client.api.GlobalDeezerApiClient
import io.github.kingg22.deezer.client.utils.AfterInitialize
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmSynthetic

/**
 * **Unofficial** Represent a response of [Deezer API](https://developers.deezer.com/api/).
 *
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/explorer">Deezer API Explorer</a>
 *
 * @param T The type of the items contained in the result list.
 * @property data A List containing the result of the search
 * @property checksum **_Optional_** hash generated to verify data integrity. Can be useful to validate that data has not been modified.
 * @property total The total number of items for the search
 * @property prev Link to the previous page of the search
 * @property next Link to the next page of the search
 */
@Serializable
data class PaginatedResponse<out T : @Serializable Any> @JvmOverloads constructor(
    val data: List<T> = emptyList(),
    val checksum: String? = null,
    val total: Int? = null,
    val prev: String? = null,
    val next: String? = null,
) {
    /** The [io.github.kingg22.deezer.client.api.DeezerApiClient] to make operations easy */
    @AfterInitialize
    @PublishedApi
    @JvmSynthetic
    internal fun client() = GlobalDeezerApiClient.requireInstance()
}
