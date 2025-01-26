package io.github.kingg22.deezerSdk.api.routes

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import io.github.kingg22.deezerSdk.api.objects.Album
import io.github.kingg22.deezerSdk.api.objects.PaginatedResponse
import io.github.kingg22.deezerSdk.api.objects.Track
import io.github.kingg22.deezerSdk.api.objects.User

/**
 * Defines all endpoints related to [io.github.kingg22.deezerSdk.api.objects.Album]
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

    /** Retrieve a [PaginatedResponse] with all [Track] from an [Album] */
    @GET("album/{id}/tracks")
    suspend fun getTracks(
        @Path id: Long,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Track>
}
