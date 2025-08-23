package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Album
import io.github.kingg22.deezer.client.api.objects.Chart
import io.github.kingg22.deezer.client.api.objects.Editorial
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse
import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import io.github.kingg22.ktorgen.core.KtorGen
import io.github.kingg22.ktorgen.http.GET
import io.github.kingg22.ktorgen.http.Path
import io.github.kingg22.ktorgen.http.Query
import kotlinx.datetime.LocalDate
import kotlin.jvm.JvmSynthetic

/**
 * Defines all endpoints related to [io.github.kingg22.deezer.client.api.objects.Editorial]
 *
 * Editorial with id 0 (zero) is "All"
 * @author Kingg22
 */
@KtorGen(
    visibilityModifier = "internal",
    classVisibilityModifier = "private",
    functionAnnotations = [JvmSynthetic::class, InternalDeezerClient::class],
    annotations = [InternalDeezerClient::class],
)
interface EditorialRoutes {
    /** Retrieve all [Editorial] */
    @GET("editorial")
    @JvmSynthetic
    suspend fun getAll(@Query index: Int? = null, @Query limit: Int? = null): PaginatedResponse<Editorial>

    /** Retrieve an [Editorial] by ID */
    @GET("editorial/{id}")
    @JvmSynthetic
    suspend fun getById(@Path id: Long, @Query index: Int? = null, @Query limit: Int? = null): Editorial

    /**
     * Retrieve [PaginatedResponse] with [Album] selected every week by the Deezer Team.
     *
     * @param date Date of the selection
     *
     * @return Album with: [Album.id], [Album.title], [Album.cover], [Album.coverSmall],
     * [Album.coverMedium], [Album.coverBig], [Album.coverXl], [Album.recordType], [Album.isExplicitLyrics] and [Album.artist]
     * (with id, name and link, **unofficial** tracklist and type).
     * **Unofficial**: [Album.md5Image], [Album.genreId], [Album.tracklist] and [Album.type]
     */
    @GET("editorial/{id}/selection")
    @JvmSynthetic
    suspend fun getDeezerSelection(
        @Path id: Long = 0,
        @Query date: LocalDate? = null,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Album>

    /** Retrieve [Chart] */
    @GET("editorial/{id}/charts")
    @JvmSynthetic
    suspend fun getCharts(@Path id: Long = 0, @Query index: Int? = null, @Query limit: Int? = null): Chart

    /** Retrieve [PaginatedResponse] with new [Album] releases per genre for the current country */
    @GET("editorial/{id}/releases")
    @JvmSynthetic
    suspend fun getReleases(
        @Path id: Long = 0,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Album>
}
