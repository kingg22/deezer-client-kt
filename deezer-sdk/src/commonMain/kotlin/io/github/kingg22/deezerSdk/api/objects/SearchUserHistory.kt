package io.github.kingg22.deezerSdk.api.objects

import kotlinx.serialization.Serializable

/**
 * Represent the response of user search history on [Deezer API](https://developers.deezer.com/api/).
 *
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/search#connections">Deezer Search Methods – History</a>
 */
@Serializable
data class SearchUserHistory(
    val tracks: List<Track>,
    val albums: List<Album>,
    val artists: List<Artist>,
    val playlists: List<Playlist>,
    val podcasts: List<Podcast>,
    val radio: List<Radio>,
)
