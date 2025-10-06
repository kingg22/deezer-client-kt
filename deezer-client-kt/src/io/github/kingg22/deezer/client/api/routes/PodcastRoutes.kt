package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Episode
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse
import io.github.kingg22.deezer.client.api.objects.Podcast
import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import io.github.kingg22.ktorgen.core.KtorGen
import io.github.kingg22.ktorgen.http.GET
import io.github.kingg22.ktorgen.http.Path
import io.github.kingg22.ktorgen.http.Query

/**
 * Defines all endpoints related to [io.github.kingg22.deezer.client.api.objects.Podcast]
 * @author Kingg22
 */
@KtorGen(
    visibilityModifier = "internal",
    classVisibilityModifier = "private",
    functionAnnotations = [InternalDeezerClient::class],
    annotations = [InternalDeezerClient::class],
)
interface PodcastRoutes {
    /**
     * Retrieve all [Podcast].
     *
     * _Dev Note_: Always return an empty list?
     */
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
