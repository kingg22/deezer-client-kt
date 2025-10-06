package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.PaginatedResponse
import io.github.kingg22.deezer.client.api.objects.Playlist
import io.github.kingg22.deezer.client.api.objects.Track
import io.github.kingg22.deezer.client.api.objects.User
import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import io.github.kingg22.ktorgen.core.KtorGen
import io.github.kingg22.ktorgen.http.GET
import io.github.kingg22.ktorgen.http.Path
import io.github.kingg22.ktorgen.http.Query

/**
 * Defines all endpoints related to [io.github.kingg22.deezer.client.api.objects.Playlist]
 * @author Kingg22
 */
@KtorGen(
    basePath = "playlist/",
    visibilityModifier = "internal",
    classVisibilityModifier = "private",
    functionAnnotations = [InternalDeezerClient::class],
    annotations = [InternalDeezerClient::class],
)
interface PlaylistRoutes {
    /** Retrieve a [io.github.kingg22.deezer.client.api.objects.Playlist] by ID */
    @GET("{id}")
        suspend fun getById(@Path id: Long): Playlist

    /** Retrieve a [io.github.kingg22.deezer.client.api.objects.PaginatedResponse] with [io.github.kingg22.deezer.client.api.objects.User] fans of playlist */
    @GET("{id}/fans")
        suspend fun getFans(@Path id: Long, @Query index: Int? = null, @Query limit: Int? = null): PaginatedResponse<User>

    /** Retrieve a [PaginatedResponse] with all [io.github.kingg22.deezer.client.api.objects.Track] from an [Playlist] */
    @GET("{id}/tracks")
        suspend fun getTracks(
        @Path id: Long,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Track>

    /** Retrieve a [PaginatedResponse] with the radio [Track]s of a [Playlist] */
    @GET("{id}/radio")
        suspend fun getRadio(@Path id: Long, @Query index: Int? = null, @Query limit: Int? = null): PaginatedResponse<Track>
}
