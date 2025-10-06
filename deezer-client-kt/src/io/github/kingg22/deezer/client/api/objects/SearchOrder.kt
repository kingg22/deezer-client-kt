package io.github.kingg22.deezer.client.api.objects

import kotlinx.serialization.Serializable

/**
 * Represent the order enum for search on [Deezer API](https://developers.deezer.com/api/).
 *
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/search">Deezer Search</a>
 */
@Serializable
enum class SearchOrder {
    RANKING,
    TRACK_ASC,
    TRACK_DESC,
    ARTIST_ASC,
    ARTIST_DESC,
    ALBUM_ASC,
    ALBUM_DESC,
    RATING_ASC,
    RATING_DESC,
    DURATION_ASC,
    DURATION_DESC,
}
