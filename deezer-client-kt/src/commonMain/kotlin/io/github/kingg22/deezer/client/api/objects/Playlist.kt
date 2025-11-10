package io.github.kingg22.deezer.client.api.objects

import io.github.kingg22.deezer.client.utils.DeezerApiPoko
import io.github.kingg22.deezer.client.utils.LocalDateTimeSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmSynthetic

/**
 * Represents a Playlist object of [Deezer API](https://developers.deezer.com/api/).
 *
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/playlist">Deezer Playlist Object</a>
 *
 * @property id The playlist's Deezer id
 * @property title The playlist's title
 * @property description The playlist description
 * @property duration The playlist's duration (seconds)
 * @property isPublic If the playlist is public or not
 * @property isLovedTrack If the playlist is the love tracks playlist
 * @property isCollaborative If the playlist is collaborative or not
 * @property rating The playlist's rate
 * @property trackCount Number of tracks in the playlist
 * @property unseenTrackCount Number of unseen tracks
 * @property fanCount The number of playlist's fans
 * @property link The url of the playlist on Deezer
 * @property share The share link of the playlist on Deezer
 * @property picture The url of the playlist's cover
 * @property pictureSmall The url of the playlist's cover in size small.
 * @property pictureMedium The url of the playlist's cover in size medium.
 * @property pictureBig The url of the playlist's cover in size big.
 * @property pictureXl The url of the playlist's cover in size xl.
 * @property checksum The checksum for the track list
 * @property creator User object containing: id, name
 * @property tracks List of track
 * @property creationDate **unofficial** Playlist's creation date time (for example, "2014-06-27 04:09:31")
 * @property md5Image **unofficial** MD5 value of the image represented as a hexadecimal string.
 * @property type **unofficial** The type of object, usually the name of the class.
 * @property position **unofficial only on charts** The position of the artist in the charts
 * @property tracklist **unofficial only on resumes** The url of the playlist's tracks
 */
@DeezerApiPoko
@Serializable
@OptIn(ExperimentalSerializationApi::class)
class Playlist @JvmOverloads constructor(
    override val id: Long,
    val title: String,
    @SerialName("public") val isPublic: Boolean,
    val description: String? = null,
    val duration: Int? = null,
    val link: String,
    /**
     * User object containing:
     * @property User.id
     * @property User.name
     * @property User.tracklist **unofficial**
     * @property User.type **unofficial**
     */
    @JsonNames("user") val creator: User,
    @SerialName("is_loved_track") val isLovedTrack: Boolean? = null,
    @SerialName("collaborative") val isCollaborative: Boolean? = null,
    val rating: Int? = null,
    @SerialName("nb_tracks") val trackCount: Int? = null,
    @SerialName("unseen_track_count") val unseenTrackCount: Int? = null,
    @SerialName("fans") val fanCount: Int? = null,
    val share: String? = null,
    val picture: String? = null,
    @SerialName("picture_small") val pictureSmall: String? = null,
    @SerialName("picture_medium") val pictureMedium: String? = null,
    @SerialName("picture_big") val pictureBig: String? = null,
    @SerialName("picture_xl") val pictureXl: String? = null,
    val checksum: String? = null,
    val tracks: PaginatedResponse<Track>? = null,
    @Serializable(LocalDateTimeSerializer::class) @SerialName("creation_date") val creationDate: LocalDateTime? = null,
    @SerialName("md5_image") val md5Image: String? = null,
    @SerialName("picture_type") val pictureType: String? = null,
    override val type: String = "playlist",
    val position: Int? = null,
    val tracklist: String? = null,
) : Resource {
    @JvmSynthetic
    override suspend fun reload() = client().playlists.getById(this.id)

    companion object
}
