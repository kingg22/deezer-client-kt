package io.github.kingg22.deezer.client.api.routes

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import io.github.kingg22.deezer.client.api.objects.Album
import io.github.kingg22.deezer.client.api.objects.Chart
import io.github.kingg22.deezer.client.api.objects.Editorial
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse
import kotlinx.datetime.LocalDate

/**
 * Defines all endpoints related to [io.github.kingg22.deezerSdk.api.objects.Editorial]
 *
 * Editorial with id 0 (zero) is "All"
 * @author Kingg22
 */
interface EditorialRoutes {
    /** Retrieve all [Editorial] */
    @GET("editorial")
    suspend fun getAll(@Query index: Int? = null, @Query limit: Int? = null): PaginatedResponse<Editorial>

    /** Retrieve an [Editorial] by ID */
    @GET("editorial/{id}")
    suspend fun getById(@Path id: Long, @Query index: Int? = null, @Query limit: Int? = null): Editorial

    /**
     * Retrieve [PaginatedResponse] with [Album] selected every week by the Deezer Team.
     *
     * @param date Date of the selection
     *
     * @return Album with: [Album.id], [Album.title], [Album.cover], [Album.coverSmall],
     * [Album.coverMedium], [Album.coverBig], [Album.coverXl], [Album.recordType], [Album.explicitLyrics] and [Album.artist]
     * (with id, name and link, **unofficial** tracklist and type).
     * **Unofficial**: [Album.md5Image], [Album.genreId], [Album.tracklist] and [Album.type]
     */
    @GET("editorial/{id}/selection")
    suspend fun getDeezerSelection(
        @Path id: Long = 0,
        @Query date: LocalDate? = null,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Album>

    /** Retrieve [Chart] */
    @GET("editorial/{id}/charts")
    suspend fun getCharts(@Path id: Long = 0, @Query index: Int? = null, @Query limit: Int? = null): Chart

    /** Retrieve [PaginatedResponse] with new [Album] releases per genre for the current country */
    @GET("editorial/{id}/releases")
    suspend fun getReleases(
        @Path id: Long = 0,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Album>
}
