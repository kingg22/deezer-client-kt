package io.github.kingg22.deezerSdk.api.objects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Fallback object if the type in the response of [Deezer API](https://developers.deezer.com/api/)
 * isn't have an own class.
 *
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/search">Deezer Search Fields</a>
 * @see Resource
 *
 * @property id The track's Deezer id
 * @property readable If the track is readable in the player for the current user
 * @property title The track's full title
 * @property titleShort The track's short title
 * @property titleVersion The track version
 * @property link The url of the track on Deezer
 * @property duration The track's duration in seconds
 * @property rank The track's Deezer rank
 * @property explicitLyrics Whether the track contains explicit lyrics
 * @property preview The url of track's preview file. This file contains the first 30 seconds of the track
 * @property artist Artist object
 * @property album Album object
 */
@Serializable
data class Search(
    val id: Long? = null,
    val readable: Boolean? = null,
    val title: String? = null,
    @SerialName("title_short") val titleShort: String? = null,
    @SerialName("title_version") val titleVersion: String? = null,
    val link: String? = null,
    val duration: Int? = null,
    val rank: Int? = null,
    @SerialName("explicit_lyrics") val explicitLyrics: Boolean? = null,
    val preview: String? = null,
    /**
     * Artist object containing:
     *
     * @property Artist.id
     * @property Artist.name
     * @property Artist.link
     * @property Artist.picture
     * @property Artist.pictureSmall
     * @property Artist.pictureMedium
     * @property Artist.pictureBig
     * @property Artist.pictureXl
     * @property Artist.type
     */
    val artist: Artist? = null,

    /**
     * Album object containing:
     * @property Album.id
     * @property Album.title
     * @property Album.cover
     * @property Album.coverSmall
     * @property Album.coverMedium
     * @property Album.coverBig
     * @property Album.coverXl
     */
    val album: Album? = null,
)

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
