package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Album
import io.github.kingg22.deezer.client.api.objects.Artist
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
 * Defines all endpoints related to [io.github.kingg22.deezer.client.api.objects.Artist]
 * @author Kingg22
 */
@KtorGen(
    basePath = "artist/",
    visibilityModifier = "internal",
    classVisibilityModifier = "private",
    functionAnnotations = [InternalDeezerClient::class],
    annotations = [InternalDeezerClient::class],
)
sealed interface ArtistRoutes {
    /** Retrieve an [Artist] by ID */
    @GET("{id}")
        suspend fun getById(@Path id: Long, @Query index: Int? = null, @Query limit: Int? = null): Artist

    /** Retrieve a [PaginatedResponse] with all [User] fans of an [Artist] */
    @GET("{id}/fans")
        suspend fun getFans(@Path id: Long, @Query index: Int? = null, @Query limit: Int? = null): PaginatedResponse<User>

    /** Retrieve a [PaginatedResponse] containing the top [Track]s of an [Artist] */
    @GET("{id}/top")
        suspend fun getTopTracks(
        @Path id: Long,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Track>

    /** Retrieve a [PaginatedResponse] with all [Album]s of an [Artist] */
    @GET("{id}/albums")
        suspend fun getAlbums(
        @Path id: Long,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Album>

    /** Retrieve a [PaginatedResponse] with the radio [io.github.kingg22.deezer.client.api.objects.Track]s of an [Artist] */
    @GET("{id}/radio")
        suspend fun getRadio(@Path id: Long, @Query index: Int? = null, @Query limit: Int? = null): PaginatedResponse<Track>

    /** Retrieve a [PaginatedResponse] with all [Playlist]s featuring an [Artist] */
    @GET("{id}/playlists")
        suspend fun getPlaylists(
        @Path id: Long,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Playlist>

    /** Retrieve a [PaginatedResponse] with [Artist]s related to a specific [Artist] */
    @GET("{id}/related")
        suspend fun getRelated(
        @Path id: Long,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Artist>
}
