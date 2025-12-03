package io.github.kingg22.deezer.client.api.objects

import kotlinx.serialization.Serializable

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
expect class PaginatedResponse<out T : @Serializable Any> { // actual classes need to have Poko!!
    @JvmOverloads constructor(
        data: List<T> = emptyList(),
        checksum: String? = null,
        total: Int? = null,
        prev: String? = null,
        next: String? = null,
    )
    val data: List<T>
    val checksum: String?
    val total: Int?
    val prev: String?
    val next: String?

    fun copy(
        data: List<@UnsafeVariance T> = this.data,
        checksum: String? = this.checksum,
        total: Int? = this.total,
        prev: String? = this.prev,
        next: String? = this.next,
    ): PaginatedResponse<T>
}
