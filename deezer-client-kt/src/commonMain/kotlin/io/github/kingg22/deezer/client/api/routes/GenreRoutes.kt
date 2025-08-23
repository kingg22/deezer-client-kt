package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Artist
import io.github.kingg22.deezer.client.api.objects.Genre
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse
import io.github.kingg22.deezer.client.api.objects.Podcast
import io.github.kingg22.deezer.client.api.objects.Radio
import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import io.github.kingg22.ktorgen.core.KtorGen
import io.github.kingg22.ktorgen.http.GET
import io.github.kingg22.ktorgen.http.Path
import io.github.kingg22.ktorgen.http.Query
import kotlin.jvm.JvmSynthetic

/**
 * Defines all endpoints related to [io.github.kingg22.deezer.client.api.objects.Genre]
 *
 * Genre with id 0 (zero) is "All"
 * @author Kingg22
 */
@KtorGen(
    visibilityModifier = "internal",
    classVisibilityModifier = "private",
    functionAnnotations = [JvmSynthetic::class, InternalDeezerClient::class],
    annotations = [InternalDeezerClient::class],
)
interface GenreRoutes {
    /** Retrieve all [io.github.kingg22.deezer.client.api.objects.Genre] */
    @GET("genre")
    @JvmSynthetic
    suspend fun getAll(@Query index: Int? = null, @Query limit: Int? = null): PaginatedResponse<Genre>

    /** Retrieve a [Genre] by ID */
    @GET("genre/{id}")
    @JvmSynthetic
    suspend fun getById(@Path id: Long): Genre

    /** Retrieve [PaginatedResponse] with all [Artist] for a genre */
    @GET("genre/{id}/artists")
    @JvmSynthetic
    suspend fun getArtists(
        @Path id: Long = 0,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Artist>

    /** Retrieve [PaginatedResponse] with all [Podcast] for a genre */
    @GET("genre/{id}/podcasts")
    @JvmSynthetic
    suspend fun getPodcasts(
        @Path id: Long = 0,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Podcast>

    /** Retrieve [PaginatedResponse] with all [Radio] for a genre */
    @GET("genre/{id}/radios")
    @JvmSynthetic
    suspend fun getRadios(
        @Path id: Long = 0,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Radio>
}
