package io.github.kingg22.deezer.client.api.objects

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents a Track object of [Deezer API](https://developers.deezer.com/api/).
 *
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/track">Deezer Track Object</a>
 *
 * @property id The track's Deezer id
 * @property readable If the track is readable in the player for the current user
 * @property title The track's full title
 * @property titleShort The track's short title
 * @property titleVersion The track version
 * @property unseen The track unseen status
 * @property isrc The track ISRC [(International Standard Recording Code)](https://isrc.ifpi.org/es/)
 * @property link The url of the track on Deezer
 * @property share The share link of the track on Deezer
 * @property duration The track's duration in seconds
 * @property trackPosition The position of the track in its album
 * @property diskNumber The track's album's disk number
 * @property rank The track's Deezer rank
 * @property releaseDate The track's release date
 * @property explicitLyrics Whether the track contains explicit lyrics
 * @property explicitContentLyrics The explicit content lyrics
 * @property explicitContentCover The explicit cover value
 * @property preview The url of track's preview file. This file contains the first 30 seconds of the track
 * @property bpm Beats per minute
 * @property gain Signal strength
 * @property availableCountries List of countries where the track is available
 * @property alternative Return an alternative readable track if the current track is not readable
 * @property contributors Return a list of contributors on the track
 * @property md5Image MD5 value of the image represented as a hexadecimal string.
 * @property trackToken    The track token for media service
 * @property artist Artist object
 * @property album Album object
 * @property type **unofficial** The type of object, usually the name of the class.
 * @property position **only on charts** The position of the track in the charts
 * @property timeAdd **unofficial only on playlist**
 */
@Serializable
data class Track @JvmOverloads constructor(
    override val id: Long,
    val readable: Boolean? = null,
    val title: String,
    @SerialName("title_short") val titleShort: String,
    @SerialName("title_version") val titleVersion: String? = null,
    val unseen: Boolean? = null,
    val isrc: String? = null,
    val link: String? = null,
    val share: String? = null,
    val duration: Int,
    @SerialName("track_position") val trackPosition: Int? = null,
    @SerialName("disk_number") val diskNumber: Int? = null,
    val rank: Int,
    @SerialName("release_date") val releaseDate: LocalDate? = null,
    @SerialName("explicit_lyrics") val explicitLyrics: Boolean,
    @SerialName("explicit_content_lyrics") val explicitContentLyrics: Explicit? = null,
    @SerialName("explicit_content_cover") val explicitContentCover: Explicit? = null,
    val preview: String,
    val bpm: Float? = null,
    val gain: Float? = null,
    @SerialName("available_countries") val availableCountries: List<String>? = null,
    val alternative: Track? = null,
    val contributors: List<Artist>? = null,
    @SerialName("md5_image") val md5Image: String? = null,
    @SerialName("track_token") val trackToken: String? = null,

    /**
     * Artist object containing:
     *
     * @property Artist.id
     * @property Artist.name
     * @property Artist.link
     * @property Artist.share
     * @property Artist.picture
     * @property Artist.pictureSmall
     * @property Artist.pictureMedium
     * @property Artist.pictureBig
     * @property Artist.pictureXl
     * @property Artist.albumCount Note: Missing in samples
     * @property Artist.fansCount Note: Missing in samples
     * @property Artist.radio
     * @property Artist.tracklist
     * @property Artist.type **unofficial**
     */
    val artist: Artist,

    /**
     * Album object containing:
     * @property Album.id
     * @property Album.title
     * @property Album.link
     * @property Album.cover
     * @property Album.coverSmall
     * @property Album.coverMedium
     * @property Album.coverBig
     * @property Album.coverXl
     * @property Album.releaseDate
     * @property Album.type **unofficial**
     */
    val album: Album? = null,
    override val type: String = "track",
    val position: Int? = null,
    @SerialName("time_add") val timeAdd: Long? = null,
) : Resource() {
    @JvmSynthetic
    override suspend fun reload() = client().tracks.getById(this.id)

    companion object
}
