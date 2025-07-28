package io.github.kingg22.deezer.client.api.objects

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmSynthetic

/**
 * Represents an Album object of [Deezer API](https://developers.deezer.com/api/).
 *
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/album">Deezer Album Object</a>
 *
 * @property id The Deezer album id
 * @property title The album title
 * @property upc The album UPC [Universal Product Code](https://releese.io/article/what-is-an-album-upc-code-and-what-is-it-used-for/)
 * @property link The url of the album on Deezer
 * @property share The share link of the album on Deezer
 * @property cover The url of the album's cover
 * @property coverSmall The url of the album's cover in size small
 * @property coverMedium The url of the album's cover in size medium
 * @property coverBig The url of the album's cover in size big
 * @property coverXl The url of the album's cover in size xl
 * @property md5Image The MD5 hash of the album's cover image
 * @property genreId The album's first genre id (You should use the genre list instead). NB: -1 for not found
 * @property genres List of genre objects
 * @property label The album's label name
 * @property trackCount The number of tracks in the album
 * @property duration The album's duration (seconds)
 * @property fans The number of album's fans
 * @property rating The album's rate
 * @property releaseDate The album's release date
 * @property recordType The record type of the album (EP/ALBUM/etc) **unofficial**: ("single", "album", "compile", "ep", "bundle")
 * @property available Whether the album is available
 * @property alternative Return an alternative album object if the current album is not available
 * @property tracklist API Link to the tracklist of this album
 * @property explicitLyrics Whether the album contains explicit lyrics
 * @property explicitContentLyrics The explicit content lyrics values
 * @property explicitContentCover The explicit cover values
 * @property position The position of the album in the charts
 * @property contributors Return a list of contributors on the album
 * @property artist Artist object
 * @property tracks List of tracks
 * @property type **unofficial** The type of object, usually the name of the class.
 */
@Serializable
data class Album @JvmOverloads constructor(
    override val id: Long,
    val title: String,
    val upc: String? = null,
    val link: String? = null,
    val share: String? = null,
    val cover: String? = null,
    @SerialName("cover_small") val coverSmall: String? = null,
    @SerialName("cover_medium") val coverMedium: String? = null,
    @SerialName("cover_big") val coverBig: String? = null,
    @SerialName("cover_xl") val coverXl: String? = null,
    @SerialName("md5_image") val md5Image: String? = null,
    @SerialName("genre_id") val genreId: Long? = null,
    val genres: PaginatedResponse<Genre>? = null,
    val label: String? = null,
    @SerialName("nb_tracks") val trackCount: Int? = null,
    val duration: Int? = null,
    val fans: Int? = null,
    val rating: Int? = null,
    @SerialName("release_date") val releaseDate: LocalDate? = null,
    @SerialName("record_type") val recordType: String? = null,
    val available: Boolean? = null,
    val alternative: Album? = null,
    val tracklist: String? = null,
    @SerialName("explicit_lyrics") val explicitLyrics: Boolean? = null,
    @SerialName("explicit_content_lyrics") val explicitContentLyrics: Explicit? = null,
    @SerialName("explicit_content_cover") val explicitContentCover: Explicit? = null,
    val position: Int? = null,
    val contributors: List<Artist>? = null,
    /**
     * Artist object containing:
     *
     * @property Artist.id
     * @property Artist.name
     * @property Artist.picture
     * @property Artist.pictureSmall
     * @property Artist.pictureMedium
     * @property Artist.pictureBig
     * @property Artist.pictureXl
     */
    val artist: Artist? = null,
    /**
     * List of track containing:
     *
     * @property Track.id
     * @property Track.readable
     * @property Track.title
     * @property Track.titleShort
     * @property Track.titleVersion
     * @property Track.link
     * @property Track.duration
     * @property Track.rank
     * @property Track.explicitLyrics
     * @property Track.preview
     * @property Track.artist containing: [Artist.id] and [Artist.name]
     * @property Track.album containing: [Album.id], [Album.title], [Album.cover], [Album.coverSmall], [Album.coverMedium],
     * [Album.coverBig] and [Album.coverXl]
     */
    val tracks: PaginatedResponse<Track>? = null,
    override val type: String = "album",
) : Resource() {
    @JvmSynthetic
    override suspend fun reload() = client().albums.getById(this.id)

    companion object
}
