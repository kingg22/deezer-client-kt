package io.github.kingg22.deezer.client.api.objects

import dev.drewhamilton.poko.Poko
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads

/**
 * Charts of a specified genre
 *
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/chart">Deezer API Chart</a>
 * @see Genre
 *
 * @property tracks List of track
 * @property albums List of album
 * @property artists List of artist
 * @property playlists List of playlist
 * @property podcasts List of podcast containing all properties
 */
@Poko
@Serializable
class Chart @JvmOverloads constructor(
    /**
     * List of track containing:
     *
     * @property Track.id
     * @property Track.title
     * @property Track.titleShort
     * @property Track.titleVersion
     * @property Track.link
     * @property Track.duration
     * @property Track.rank
     * @property Track.isExplicitLyrics
     * @property Track.preview
     * @property Track.artist containing: [Artist.id], [Artist.name], [Artist.link], [Artist.picture],
     * [Artist.pictureSmall], [Artist.pictureMedium], [Artist.pictureBig], [Artist.pictureXl] and [Artist.isRadio]
     * @property Track.album containing: [Album.id], [Album.title], [Album.cover], [Album.coverSmall],
     * [Album.coverMedium], [Album.coverBig] and [Album.coverXl]
     * @property Track.type **unofficial**
     */
    val tracks: PaginatedResponse<Track> = PaginatedResponse(),

    /**
     * List of Album containing:
     * @property Album.id
     * @property Album.title
     * @property Album.link
     * @property Album.cover
     * @property Album.coverSmall
     * @property Album.coverMedium
     * @property Album.coverBig
     * @property Album.coverXl
     * @property Album.recordType
     * @property Album.isExplicitLyrics
     * @property Album.position
     * @property Album.artist containing: [Artist.id], [Artist.name], [Artist.link], [Artist.picture],
     * [Artist.pictureSmall], [Artist.pictureMedium], [Artist.pictureBig], [Artist.pictureXl] and [Artist.isRadio]
     * @property Album.type **unofficial**
     */
    val albums: PaginatedResponse<Album> = PaginatedResponse(),

    /**
     * List of Artist containing:
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
     * @property Artist.isRadio
     * @property Artist.position
     * @property Artist.type **unofficial**
     */
    val artists: PaginatedResponse<Artist> = PaginatedResponse(),

    /**
     * List of Playlist containing:
     *
     * @property Playlist.id
     * @property Playlist.title
     * @property Playlist.isPublic
     * @property Playlist.link
     * @property Playlist.picture
     * @property Playlist.pictureSmall
     * @property Playlist.pictureMedium
     * @property Playlist.pictureMedium
     * @property Playlist.pictureBig
     * @property Playlist.pictureXl
     * @property Playlist.position
     * @property Playlist.creator _alias user_ containing: [User.id] and [User.name]
     * @property Playlist.type **unofficial**
     */
    val playlists: PaginatedResponse<Playlist> = PaginatedResponse(),
    val podcasts: PaginatedResponse<Podcast> = PaginatedResponse(),
)
