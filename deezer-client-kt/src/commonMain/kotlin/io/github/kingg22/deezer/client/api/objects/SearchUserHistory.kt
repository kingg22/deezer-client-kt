package io.github.kingg22.deezer.client.api.objects

import io.github.kingg22.deezer.client.utils.DeezerApiPoko
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads

/**
 * Represent the response of the user's search history on [Deezer API](https://developers.deezer.com/api/).
 *
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/search#connections">Deezer Search Methods – History</a>
 */
@DeezerApiPoko
@Serializable
class SearchUserHistory @JvmOverloads constructor(
    val tracks: List<Track> = emptyList(),
    val albums: List<Album> = emptyList(),
    val artists: List<Artist> = emptyList(),
    val playlists: List<Playlist> = emptyList(),
    val podcasts: List<Podcast> = emptyList(),
    val radio: List<Radio> = emptyList(),
)
