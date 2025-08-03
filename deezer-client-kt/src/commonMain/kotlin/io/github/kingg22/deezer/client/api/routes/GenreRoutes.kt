package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Artist
import io.github.kingg22.deezer.client.api.objects.Genre
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse
import io.github.kingg22.deezer.client.api.objects.Podcast
import io.github.kingg22.deezer.client.api.objects.Radio
import io.github.kingg22.ktorgen.http.GET
import io.github.kingg22.ktorgen.http.Path
import io.github.kingg22.ktorgen.http.Query

/**
 * Defines all endpoints related to [io.github.kingg22.deezer.client.api.objects.Genre]
 *
 * Genre with id 0 (zero) is "All"
 * @author Kingg22
 */
interface GenreRoutes {
    /** Retrieve all [io.github.kingg22.deezer.client.api.objects.Genre] */
    @GET("genre")
    suspend fun getAll(@Query index: Int? = null, @Query limit: Int? = null): PaginatedResponse<Genre>

    /** Retrieve a [Genre] by ID */
    @GET("genre/{id}")
    suspend fun getById(@Path id: Long): Genre

    /** Retrieve [PaginatedResponse] with all [Artist] for a genre */
    @GET("genre/{id}/artists")
    suspend fun getArtists(
        @Path id: Long = 0,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Artist>

    /** Retrieve [PaginatedResponse] with all [Podcast] for a genre */
    @GET("genre/{id}/podcasts")
    suspend fun getPodcasts(
        @Path id: Long = 0,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Podcast>

    /** Retrieve [PaginatedResponse] with all [Radio] for a genre */
    @GET("genre/{id}/radios")
    suspend fun getRadios(
        @Path id: Long = 0,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Radio>
}
