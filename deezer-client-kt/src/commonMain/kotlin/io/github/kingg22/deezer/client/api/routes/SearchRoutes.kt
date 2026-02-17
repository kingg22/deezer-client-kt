package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.DeezerApiClient.Companion.API_DEEZER_URL
import io.github.kingg22.deezer.client.api.objects.*
import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import io.github.kingg22.ktorgen.core.KtorGen
import io.github.kingg22.ktorgen.http.GET
import io.github.kingg22.ktorgen.http.Query
import kotlin.jvm.JvmName
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
 * @see SearchRoutes.buildAdvancedQuery
 * @see SearchRoutes.setStrict
 * @see SearchRoutes.AdvancedQueryBuilder
 */
@KtorGen(
    basePath = "$API_DEEZER_URL/search",
    generateTopLevelFunction = false,
    classVisibilityModifier = "internal",
    annotations = [InternalDeezerClient::class],
)
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
         * @param album The album's title (example: "good things")
         * @param track The track's title (example: "I need a dollar")
         * @param label The label name (example: "because music")
         * @param durationMin The track's minimum duration in seconds (example: 300)
         * @param durationMax The track's maximum duration in seconds (example: 500)
         * @param bpmMin The track's minimum bpm (example: 120)
         * @param bpmMax The track's maximum bpm (example: 200)
         */
        @JvmSynthetic
        @Throws(IllegalArgumentException::class, IllegalStateException::class)
        @Suppress("kotlin:S107")
        fun buildAdvancedQuery(
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
        fun buildAdvancedQuery(block: AdvancedQueryBuilder.() -> Unit) = AdvancedQueryBuilder().apply(block).build()

        /**
         * Build advanced search using builder
         * @param q Simple query to start
         * @see AdvancedQueryBuilder
         */
        @JvmName("buildAdvancedQuery")
        @JvmStatic
        @JvmOverloads
        fun builder(q: String? = null) = AdvancedQueryBuilder().q(q)

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
     * @property Track.isReadable
     * @property Track.title
     * @property Track.titleShort
     * @property Track.titleVersion
     * @property Track.link
     * @property Track.duration
     * @property Track.rank
     * @property Track.isExplicitLyrics
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
    @GET
    @JvmSynthetic
    suspend fun search(
        @Query q: String,
        @Query strict: String? = null,
        @Query order: SearchOrder? = null,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Track>

    /** Search [Album] */
    @GET("/album")
    @JvmSynthetic
    suspend fun searchAlbum(
        @Query q: String,
        @Query strict: String? = null,
        @Query order: SearchOrder? = null,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Album>

    /** Search [Artist] */
    @GET("/artist")
    @JvmSynthetic
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
    @GET("/history")
    @JvmSynthetic
    suspend fun searchHistory(
        @Query q: String,
        @Query strict: String? = null,
        @Query order: SearchOrder? = null,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<SearchUserHistory>

    /** Search [Playlist] */
    @GET("/playlist")
    @JvmSynthetic
    suspend fun searchPlaylist(
        @Query q: String,
        @Query strict: String? = null,
        @Query order: SearchOrder? = null,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Playlist>

    /** Search [Podcast] */
    @GET("/podcast")
    @JvmSynthetic
    suspend fun searchPodcast(
        @Query q: String,
        @Query strict: String? = null,
        @Query order: SearchOrder? = null,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Podcast>

    /** Search [Radio] */
    @GET("/radio")
    @JvmSynthetic
    suspend fun searchRadio(
        @Query q: String,
        @Query strict: String? = null,
        @Query order: SearchOrder? = null,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Radio>

    /** Search [Track] */
    @GET("/track")
    @JvmSynthetic
    suspend fun searchTrack(
        @Query q: String,
        @Query strict: String? = null,
        @Query order: SearchOrder? = null,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Track>

    /** Search [User] */
    @GET("/user")
    @JvmSynthetic
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
        var durationMax: Duration? = null
        var bpmMin: Int? = null
        var bpmMax: Int? = null

        /**
         * Set a simple query
         * @param q query
         */
        fun q(q: String?) = apply { this.q = q }

        /**
         * Set an artist name (example: "aloe blacc")
         * @param artist name
         */
        fun artist(artist: String?) = apply { this.artist = artist }

        /**
         * Set an album's title (example: "good things")
         * @param album title
         */
        fun album(album: String?) = apply { this.album = album }

        /**
         * Set a track's title (example: "I need a dollar")
         * @param track title
         */
        fun track(track: String?) = apply { this.track = track }

        /**
         * Set the label name (example: "because music")
         * @param label name
         */
        fun label(label: String?) = apply { this.label = label }

        /**
         * Set the track's minimum duration in seconds (example: 300)
         * @param durationMin min duration in seconds
         */
        @JvmSynthetic
        fun durationMin(durationMin: Duration?) = apply { this.durationMin = durationMin }

        /**
         * Set the track's minimum duration in seconds (example: 300)
         * @param durationMax max duration in seconds
         */
        @JvmSynthetic
        fun durationMax(durationMax: Duration?) = apply { this.durationMax = durationMax }

        /**
         * Set the track's minimum duration in seconds (example: 300)
         * @param durationMin min duration in seconds
         */
        fun durationMin(durationMinSeconds: Long?) = apply { this.durationMin = durationMinSeconds?.seconds }

        /**
         * Set the track's minimum duration in seconds (example: 300)
         * @param durationMax max duration in seconds
         */
        fun durationMax(durationMaxSeconds: Long?) = apply { this.durationMax = durationMaxSeconds?.seconds }

        /**
         * Set the track's minimum bpm (example: 120)
         * @param bpmMin min bpm
         */
        fun bpmMin(bpmMin: Int?) = apply { this.bpmMin = bpmMin }

        /**
         * Set the track's maximum bpm (example: 200)
         * @param bpmMin max bpm
         */
        fun bpmMax(bpmMax: Int?) = apply { this.bpmMax = bpmMax }

        /**
         * Build the advanced query
         * @throws IllegalArgumentException if not provided any arguments
         * @throws IllegalStateException if the query is blank after built
         */
        @Throws(IllegalArgumentException::class, IllegalStateException::class)
        fun build() = buildAdvancedQuery(
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
