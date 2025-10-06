package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Album
import io.github.kingg22.deezer.client.api.objects.Artist
import io.github.kingg22.deezer.client.api.objects.Chart
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse
import io.github.kingg22.deezer.client.api.objects.Playlist
import io.github.kingg22.deezer.client.api.objects.Podcast
import io.github.kingg22.deezer.client.api.objects.Track
import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import io.github.kingg22.ktorgen.core.KtorGen
import io.github.kingg22.ktorgen.http.GET
import io.github.kingg22.ktorgen.http.Path
import io.github.kingg22.ktorgen.http.Query

/**
 * Defines all endpoints related to [io.github.kingg22.deezer.client.api.objects.Chart]
 *
 * Chart with id = 0 (zero) is "All" _maybe_
 * @author Kingg22
 */
@KtorGen(
    visibilityModifier = "internal",
    classVisibilityModifier = "private",
    functionAnnotations = [InternalDeezerClient::class],
    annotations = [InternalDeezerClient::class],
)
sealed interface ChartRoutes {
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
