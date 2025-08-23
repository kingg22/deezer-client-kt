package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Genre
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse
import io.github.kingg22.deezer.client.api.objects.Radio
import io.github.kingg22.deezer.client.api.objects.Track
import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import io.github.kingg22.ktorgen.core.KtorGen
import io.github.kingg22.ktorgen.http.GET
import io.github.kingg22.ktorgen.http.Path
import io.github.kingg22.ktorgen.http.Query
import kotlin.jvm.JvmSynthetic

/**
 * Defines all endpoints related to [io.github.kingg22.deezer.client.api.objects.Radio]
 * @author Kingg22
 */
@KtorGen(
    visibilityModifier = "internal",
    functionAnnotations = [JvmSynthetic::class, InternalDeezerClient::class],
    annotations = [InternalDeezerClient::class],
)
interface RadioRoutes {
    /** Retrieve all [Radio] */
    @GET("radio")
    @JvmSynthetic
    suspend fun getAll(@Query index: Int? = null, @Query limit: Int? = null): PaginatedResponse<Radio>

    /** Retrieve a [Radio] by ID */
    @GET("radio/{id}")
    @JvmSynthetic
    suspend fun getById(@Path id: Long): Radio

    /** Retrieve a [PaginatedResponse] with [Genre.radios] split by [Genre] */
    @GET("radio/genres")
    @JvmSynthetic
    suspend fun getAllSplitInGenres(@Query index: Int? = null, @Query limit: Int? = null): PaginatedResponse<Genre>

    /** Retrieve a [PaginatedResponse] with the top [Radio] (default to 25 radios) */
    @GET("radio/top")
    @JvmSynthetic
    suspend fun getTop(@Query index: Int? = null, @Query limit: Int? = null): PaginatedResponse<Radio>

    /** Retrieve a [PaginatedResponse] with first 40 [Track] in the radio */
    @GET("radio/{id}/tracks")
    @JvmSynthetic
    suspend fun getTracks(
        @Path id: Long,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Track>

    /** Retrieve a [PaginatedResponse] with personal [Radio] split by genre (MIX in website) */
    @GET("radio/lists")
    @JvmSynthetic
    suspend fun getLists(@Query index: Int? = null, @Query limit: Int? = null): PaginatedResponse<Radio>
}
