package io.github.kingg22.deezerSdk.api.routes

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query
import io.github.kingg22.deezerSdk.api.objects.Album
import io.github.kingg22.deezerSdk.api.objects.Artist
import io.github.kingg22.deezerSdk.api.objects.PaginatedResponse
import io.github.kingg22.deezerSdk.api.objects.Playlist
import io.github.kingg22.deezerSdk.api.objects.Podcast
import io.github.kingg22.deezerSdk.api.objects.Radio
import io.github.kingg22.deezerSdk.api.objects.SearchOrder
import io.github.kingg22.deezerSdk.api.objects.SearchUserHistory
import io.github.kingg22.deezerSdk.api.objects.Track
import io.github.kingg22.deezerSdk.api.objects.User
import io.github.kingg22.deezerSdk.api.routes.SearchRoutes.Companion.buildAdvanceQuery
import io.github.kingg22.deezerSdk.api.routes.SearchRoutes.Companion.setStrict
import kotlin.time.Duration

/**
 * Defines all endpoints related to Search in [Deezer API](https://developers.deezer.com/api/).
 *
 * Strict only can be 'on' or null.
 * Disable the fuzzy mode (strict=on)
 *
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/search#infos">Deezer Search</a>
 * @see <a href="https://developers.deezer.com/api/search#connections">Deezer Search Methods</a>
 * @see buildAdvanceQuery
 * @see setStrict
 */
interface SearchRoutes {
    companion object {
        /**
         * Advanced Search
         *
         * You can mix your search to be more specific.
         * @throws IllegalArgumentException if not provided any arguments
         * @throws IllegalArgumentException if the query is blank after built
         *
         * @param q A simple query
         * @param artist The artist name (example: "aloe blacc")
         * @param album    The album's title (example: "good things")
         * @param track    The track's title (example: "I need a dollar")
         * @param label    The label name (example: "because music")
         * @param durationMin The track's minimum duration in seconds (example: 300)
         * @param durationMax The track's maximum duration in seconds (example: 500)
         * @param bpmMin The track's minimum bpm (example: 120)
         * @param bpmMax The track's maximum bpm (example: 200)
         */
        fun buildAdvanceQuery(
            q: String? = null,
            artist: String? = null,
            album: String? = null,
            track: String? = null,
            label: String? = null,
            durationMin: Duration? = null,
            durationMax: Duration? = null,
            bpmMin: Int? = null,
            bpmMax: Int? = null,
        ): String {
            require(
                !(
                    q == null &&
                        artist == null &&
                        album == null &&
                        track == null &&
                        label == null &&
                        durationMin == null &&
                        durationMax == null &&
                        bpmMin == null &&
                        bpmMax == null
                    ),
            ) {
                "Requires at least 1 parameter to search"
            }
            val query = buildString {
                q?.let { append("\"$it\"") }
                artist?.let { append(" artist:\"$it\"") }
                album?.let { append(" album:\"$it\"") }
                track?.let { append(" track:\"$it\"") }
                label?.let { append(" label:\"$it\"") }
                durationMin?.let { append(" dur_min:${it.inWholeSeconds}") }
                durationMax?.let { append(" dur_max:${it.inWholeSeconds}") }
                bpmMin?.let { append(" bpm_min:$it") }
                bpmMax?.let { append(" bpm_max:$it") }
            }.trim().replace(Regex("\"\\s*\""), "").replace(Regex("\\s{2,}"), " ")
            require(query.isNotEmpty() && query.isNotBlank()) { "Query cannot be blank" }
            return query
        }

        /** Shortcut for strict */
        fun setStrict(strict: Boolean): String? = when (strict) {
            true -> "on"
            false -> null
        }
    }

    /**
     * Basic Search
     *
     * @return [PaginatedResponse] with minimal fields of [Track] described in properties
     * @see <a href="https://developers.deezer.com/api/search">Deezer Search Fields</a>
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
     * @property Track.explicitContentLyrics
     * @property Track.explicitContentCover
     * @property Track.md5Image
     * @property Track.preview
     * @property Track.artist Artist object containing: [Artist.id], [Artist.name], [Artist.link]
     * [Artist.picture], [Artist.pictureSmall], [Artist.pictureMedium], [Artist.pictureBig], [Artist.pictureXl] and
     * [Artist.type]
     * @property Track.album Album object containing: [Album.id], [Album.title], [Album.cover], [Album.coverSmall]
     * [Album.coverMedium], [Album.coverBig], [Album.coverXl] and [Album.type]
     */
    @GET("search")
    suspend fun search(
        @Query q: String,
        @Query strict: String? = null,
        @Query order: SearchOrder? = null,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Track>

    /** Search [Album] */
    @GET("search/album")
    suspend fun searchAlbum(
        @Query q: String,
        @Query strict: String? = null,
        @Query order: SearchOrder? = null,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Album>

    /** Search [Artist] */
    @GET("search/artist")
    suspend fun searchArtist(
        @Query q: String,
        @Query strict: String? = null,
        @Query order: SearchOrder? = null,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Artist>

    /**
     * Retrieve the current user search history
     *
     * **Required** OAuth. **unsupported**
     */
    @GET("search/history")
    suspend fun searchHistory(
        @Query q: String,
        @Query strict: String? = null,
        @Query order: SearchOrder? = null,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<SearchUserHistory>

    /** Search [Playlist] */
    @GET("search/playlist")
    suspend fun searchPlaylist(
        @Query q: String,
        @Query strict: String? = null,
        @Query order: SearchOrder? = null,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Playlist>

    /** Search [Podcast] */
    @GET("search/podcast")
    suspend fun searchPodcast(
        @Query q: String,
        @Query strict: String? = null,
        @Query order: SearchOrder? = null,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Podcast>

    /** Search [Radio] */
    @GET("search/radio")
    suspend fun searchRadio(
        @Query q: String,
        @Query strict: String? = null,
        @Query order: SearchOrder? = null,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Radio>

    /** Search [Track] */
    @GET("search/track")
    suspend fun searchTrack(
        @Query q: String,
        @Query strict: String? = null,
        @Query order: SearchOrder? = null,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Track>

    /** Search [User] */
    @GET("search/user")
    suspend fun searchUser(
        @Query q: String,
        @Query strict: String? = null,
        @Query order: SearchOrder? = null,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<User>
}
