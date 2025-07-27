package io.github.kingg22.deezer.client.api.routes

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import io.github.kingg22.deezer.client.api.objects.Episode
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse
import io.github.kingg22.deezer.client.api.objects.Podcast

/**
 * Defines all endpoints related to [io.github.kingg22.deezerSdk.api.objects.Podcast]
 * @author Kingg22
 */
interface PodcastRoutes {
    /** Retrieve all [Podcast]. _Note_: return Empty list */
    @GET("podcast")
    suspend fun getAll(@Query index: Int? = null, @Query limit: Int? = null): PaginatedResponse<Podcast>

    /** Retrieve a [Podcast] by ID */
    @GET("podcast/{id}")
    suspend fun getById(@Path id: Long, @Query index: Int? = null, @Query limit: Int? = null): Podcast

    /** Retrieve a [PaginatedResponse] with all [Episode] of the podcast */
    @GET("podcast/{id}/episodes")
    suspend fun getEpisodes(
        @Path id: Long,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Episode>
}
