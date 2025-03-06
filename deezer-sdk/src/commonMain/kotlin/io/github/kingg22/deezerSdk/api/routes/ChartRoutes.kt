package io.github.kingg22.deezerSdk.api.routes

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import io.github.kingg22.deezerSdk.api.objects.Album
import io.github.kingg22.deezerSdk.api.objects.Artist
import io.github.kingg22.deezerSdk.api.objects.Chart
import io.github.kingg22.deezerSdk.api.objects.PaginatedResponse
import io.github.kingg22.deezerSdk.api.objects.Playlist
import io.github.kingg22.deezerSdk.api.objects.Podcast
import io.github.kingg22.deezerSdk.api.objects.Track

/**
 * Defines all endpoints related to [io.github.kingg22.deezerSdk.api.objects.Chart]
 *
 * Chart with id = 0 (zero) is "All" _maybe_
 * @author Kingg22
 */
interface ChartRoutes {
    /** Retrieve [Chart] */
    @GET("chart")
    suspend fun getAll(@Query index: Int? = null, @Query limit: Int? = null): Chart

    /** **Unofficial** Retrieve [Chart] by ID _maybe genre id?_ */
    @GET("chart/{id}")
    suspend fun getById(@Path id: Long, @Query index: Int? = null, @Query limit: Int? = null): Chart

    /** Retrieve the Top [Track] */
    @GET("chart/{id}/tracks")
    suspend fun getTracks(
        @Path id: Long = 0,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Track>

    /** Retrieve the Top [Album] */
    @GET("chart/{id}/albums")
    suspend fun getAlbums(
        @Path id: Long = 0,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Album>

    /** Retrieve the Top [Artist] */
    @GET("chart/{id}/artists")
    suspend fun getArtists(
        @Path id: Long = 0,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Artist>

    /** Retrieve the Top [Playlist] */
    @GET("chart/{id}/playlists")
    suspend fun getPlaylists(
        @Path id: Long = 0,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Playlist>

    /** Retrieve the Top [Podcast] */
    @GET("chart/{id}/podcasts")
    suspend fun getPodcasts(
        @Path id: Long = 0,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Podcast>
}
