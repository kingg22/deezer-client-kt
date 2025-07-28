package io.github.kingg22.deezer.client.api.routes

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query
import io.github.kingg22.deezer.client.api.objects.Album
import io.github.kingg22.deezer.client.api.objects.Artist
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse
import io.github.kingg22.deezer.client.api.objects.Playlist
import io.github.kingg22.deezer.client.api.objects.Podcast
import io.github.kingg22.deezer.client.api.objects.Radio
import io.github.kingg22.deezer.client.api.objects.SearchOrder
import io.github.kingg22.deezer.client.api.objects.SearchUserHistory
import io.github.kingg22.deezer.client.api.objects.Track
import io.github.kingg22.deezer.client.api.objects.User
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * Defines all endpoints related to Search in [Deezer API](https://developers.deezer.com/api/).
 *
 * Strict only can be 'on' or null.
 * Disable the fuzzy mode (strict=on)
 *
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/search#infos">Deezer Search</a>
 * @see <a href="https://developers.deezer.com/api/search#connections">Deezer Search Methods</a>
 * @see SearchRoutes.buildAdvanceQuery
 * @see SearchRoutes.setStrict
 * @see SearchRoutes.AdvancedQueryBuilder
 */
interface SearchRoutes {
    /** Search utilities */
    companion object {
        /**
         * Advanced Search
         *
         * You can mix your search to be more specific.
         * @throws IllegalArgumentException if not provided any arguments
         * @throws IllegalStateException if the query is blank after built
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
        @JvmOverloads
        @JvmStatic
        @Throws(IllegalArgumentException::class, IllegalStateException::class)
        @Suppress("kotlin:S107")
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
                q.takeIf { !it.isNullOrBlank() }?.let { append("\"$it\"") }
                artist.takeIf { !it.isNullOrBlank() }?.let { append(" artist:\"$it\"") }
                album.takeIf { !it.isNullOrBlank() }?.let { append(" album:\"$it\"") }
                track.takeIf { !it.isNullOrBlank() }?.let { append(" track:\"$it\"") }
                label.takeIf { !it.isNullOrBlank() }?.let { append(" label:\"$it\"") }
                durationMin?.let { append(" dur_min:${it.inWholeSeconds}") }
                durationMax?.let { append(" dur_max:${it.inWholeSeconds}") }
                bpmMin?.let { append(" bpm_min:$it") }
                bpmMax?.let { append(" bpm_max:$it") }
            }.trim().replace("\"\\s*\"".toRegex(), "").replace("\\s{2,}".toRegex(), " ")
            check(query.isNotBlank()) { "Query cannot be blank" }
            return query
        }

        /**
         * DSL to build advanced search
         * @throws IllegalArgumentException if not provided any arguments
         * @throws IllegalStateException if the query is blank after built
         */
        @JvmSynthetic
        @Throws(IllegalArgumentException::class, IllegalStateException::class)
        fun buildAdvanceQuery(block: AdvancedQueryBuilder.() -> Unit) = AdvancedQueryBuilder().apply(block).build()

        /**
         * Build advanced search using builder
         * @throws IllegalArgumentException if not provided any arguments
         * @throws IllegalStateException if the query is blank after built
         */
        @JvmStatic
        @Throws(IllegalArgumentException::class, IllegalStateException::class)
        fun buildAdvanceQuery(builder: AdvancedQueryBuilder) = builder.build()

        /**
         * Shortcut for strict
         * @param strict True is 'on' else null
         */
        @JvmStatic
        fun setStrict(strict: Boolean) = when (strict) {
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

    /**
     * Builder for advanced queries
     *
     * @property q A simple query
     * @property artist The artist name (example: "aloe blacc")
     * @property album The album's title (example: "good things")
     * @property track The track's title (example: "I need a dollar")
     * @property label The label name (example: "because music")
     * @property durationMin The track's minimum duration in seconds (example: 300)
     * @property durationMax The track's maximum duration in seconds (example: 500)
     * @property bpmMin The track's minimum bpm (example: 120)
     * @property bpmMax The track's maximum bpm (example: 200)
     */
    class AdvancedQueryBuilder {
        var q: String? = null
        var artist: String? = null
        var album: String? = null
        var track: String? = null
        var label: String? = null

        @get:JvmSynthetic
        @set:JvmSynthetic
        var durationMin: Duration? = null

        @get:JvmSynthetic
        @set:JvmSynthetic
        private var durationMax: Duration? = null
        private var bpmMin: Int? = null
        private var bpmMax: Int? = null

        fun q(q: String?) = apply { this.q = q }
        fun artist(artist: String?) = apply { this.artist = artist }
        fun album(album: String?) = apply { this.album = album }
        fun track(track: String?) = apply { this.track = track }
        fun label(label: String?) = apply { this.label = label }

        @JvmSynthetic
        fun durationMin(durationMin: Duration?) = apply { this.durationMin = durationMin }

        @JvmSynthetic
        fun durationMax(durationMax: Duration?) = apply { this.durationMax = durationMax }
        fun durationMin(durationMinSeconds: Long?) = apply { this.durationMin = durationMinSeconds?.seconds }
        fun durationMax(durationMaxSeconds: Long?) = apply { this.durationMax = durationMaxSeconds?.seconds }
        fun bpmMin(bpmMin: Int?) = apply { this.bpmMin = bpmMin }
        fun bpmMax(bpmMax: Int?) = apply { this.bpmMax = bpmMax }

        /**
         * Build the advanced query
         * @throws IllegalArgumentException if not provided any arguments
         * @throws IllegalStateException if the query is blank after built
         */
        @Throws(IllegalArgumentException::class, IllegalStateException::class)
        fun build() = buildAdvanceQuery(
            q = q,
            artist = artist,
            album = album,
            track = track,
            label = label,
            durationMin = durationMin,
            durationMax = durationMax,
            bpmMin = bpmMin,
            bpmMax = bpmMax,
        )
    }
}
