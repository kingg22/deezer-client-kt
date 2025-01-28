package io.github.kingg22.deezerSdk.api.routes

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import io.github.kingg22.deezerSdk.api.objects.Genre
import io.github.kingg22.deezerSdk.api.objects.PaginatedResponse
import io.github.kingg22.deezerSdk.api.objects.Radio
import io.github.kingg22.deezerSdk.api.objects.Track

/**
 * Defines all endpoints related to [io.github.kingg22.deezerSdk.api.objects.Radio]
 * @author Kingg22
 */
interface RadioRoutes {
    /** Retrieve all [Radio] */
    @GET("radio")
    suspend fun getAll(@Query index: Int? = null, @Query limit: Int? = null): PaginatedResponse<Radio>

    /** Retrieve a [Radio] by ID */
    @GET("radio/{id}")
    suspend fun getById(@Path id: Long): Radio

    /** Retrieve a [PaginatedResponse] with [Genre.radios] split by [Genre] */
    @GET("radio/genres")
    suspend fun getAllSplitInGenres(@Query index: Int? = null, @Query limit: Int? = null): PaginatedResponse<Genre>

    /** Retrieve a [PaginatedResponse] with the top [Radio] (default to 25 radios) */
    @GET("radio/top")
    suspend fun getTop(@Query index: Int? = null, @Query limit: Int? = null): PaginatedResponse<Radio>

    /** Retrieve a [PaginatedResponse] with first 40 [Track] in the radio */
    @GET("radio/{id}/tracks")
    suspend fun getTracks(
        @Path id: Long,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Track>

    /** Retrieve a [PaginatedResponse] with personal [Radio] split by genre (MIX in website) */
    @GET("radio/lists")
    suspend fun getLists(@Query index: Int? = null, @Query limit: Int? = null): PaginatedResponse<Radio>
}
