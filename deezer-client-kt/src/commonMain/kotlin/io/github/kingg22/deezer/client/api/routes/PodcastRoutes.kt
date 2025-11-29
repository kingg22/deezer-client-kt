package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.DeezerApiClient.Companion.API_DEEZER_URL
import io.github.kingg22.deezer.client.api.objects.Episode
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse
import io.github.kingg22.deezer.client.api.objects.Podcast
import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import io.github.kingg22.ktorgen.core.KtorGen
import io.github.kingg22.ktorgen.http.GET
import io.github.kingg22.ktorgen.http.Path
import io.github.kingg22.ktorgen.http.Query
import kotlin.jvm.JvmSynthetic

/**
 * Defines all endpoints related to [io.github.kingg22.deezer.client.api.objects.Podcast]
 * @author Kingg22
 */
@KtorGen(
    basePath = "$API_DEEZER_URL/podcast",
    visibilityModifier = "internal",
    classVisibilityModifier = "private",
    functionAnnotations = [JvmSynthetic::class, InternalDeezerClient::class],
    annotations = [InternalDeezerClient::class],
)
interface PodcastRoutes {
    /**
     * Retrieve all [Podcast].
     *
     * _Dev Note_: Always return an empty list?
     */
    @GET
    @JvmSynthetic
    suspend fun getAll(@Query index: Int? = null, @Query limit: Int? = null): PaginatedResponse<Podcast>

    /** Retrieve a [Podcast] by ID */
    @GET("/{id}")
    @JvmSynthetic
    suspend fun getById(@Path id: Long, @Query index: Int? = null, @Query limit: Int? = null): Podcast

    /** Retrieve a [PaginatedResponse] with all [Episode] of the podcast */
    @GET("/{id}/episodes")
    @JvmSynthetic
    suspend fun getEpisodes(
        @Path id: Long,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Episode>
}
