package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Album
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse
import io.github.kingg22.deezer.client.api.objects.Track
import io.github.kingg22.deezer.client.api.objects.User
import io.github.kingg22.ktorgen.http.GET
import io.github.kingg22.ktorgen.http.Path
import io.github.kingg22.ktorgen.http.Query

/**
 * Defines all endpoints related to [io.github.kingg22.deezer.client.api.objects.Album]
 * @author Kingg22
 */
interface AlbumRoutes {
    /** Retrieve an [Album] by ID */
    @GET("album/{id}")
    suspend fun getById(@Path id: Long): Album

    /** Retrieve an [Album] by UPC (Universal Product Code) */
    @GET("album/upc:{upc}")
    suspend fun getByUpc(@Path upc: String): Album

    /** Retrieve the fans of an [Album] */
    @GET("album/{id}/fans")
    suspend fun getFans(@Path id: Long, @Query index: Int? = null, @Query limit: Int? = null): PaginatedResponse<User>

    /** Retrieve a [PaginatedResponse] with all [io.github.kingg22.deezer.client.api.objects.Track] from an [Album] */
    @GET("album/{id}/tracks")
    suspend fun getTracks(
        @Path id: Long,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Track>
}
