package io.github.kingg22.deezerSdk.api.routes

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import de.jensklingenberg.ktorfit.http.Query
import io.github.kingg22.deezerSdk.api.objects.PaginatedResponse
import io.github.kingg22.deezerSdk.api.objects.Playlist
import io.github.kingg22.deezerSdk.api.objects.Track
import io.github.kingg22.deezerSdk.api.objects.User

/**
 * Defines all endpoints related to [io.github.kingg22.deezerSdk.api.objects.Playlist]
 * @author Kingg22
 */
interface PlaylistRoutes {
    /** Retrieve a [Playlist] by ID */
    @GET("playlist/{id}")
    suspend fun getById(@Path id: Long): Playlist

    /** Retrieve a [PaginatedResponse] with [User] fans of playlist */
    @GET("playlist/{id}/fans")
    suspend fun getFans(@Path id: Long, @Query index: Int? = null, @Query limit: Int? = null): PaginatedResponse<User>

    /** Retrieve a [PaginatedResponse] with all [Track] from an [Playlist] */
    @GET("playlist/{id}/tracks")
    suspend fun getTracks(
        @Path id: Long,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Track>

    /** Retrieve a [PaginatedResponse] with the radio [Track]s of a [Playlist] */
    @GET("playlist/{id}/radio")
    suspend fun getRadio(@Path id: Long, @Query index: Int? = null, @Query limit: Int? = null): PaginatedResponse<Track>
}
