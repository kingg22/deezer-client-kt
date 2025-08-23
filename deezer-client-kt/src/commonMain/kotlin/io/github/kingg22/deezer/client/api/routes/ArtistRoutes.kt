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
import kotlin.jvm.JvmSynthetic

/**
 * Defines all endpoints related to [io.github.kingg22.deezer.client.api.objects.Artist]
 * @author Kingg22
 */
@KtorGen(
    basePath = "artist/",
    visibilityModifier = "internal",
    functionAnnotations = [JvmSynthetic::class, InternalDeezerClient::class],
    annotations = [InternalDeezerClient::class],
)
interface ArtistRoutes {
    /** Retrieve an [Artist] by ID */
    @GET("{id}")
    @JvmSynthetic
    suspend fun getById(@Path id: Long, @Query index: Int? = null, @Query limit: Int? = null): Artist

    /** Retrieve a [PaginatedResponse] with all [User] fans of an [Artist] */
    @GET("{id}/fans")
    @JvmSynthetic
    suspend fun getFans(@Path id: Long, @Query index: Int? = null, @Query limit: Int? = null): PaginatedResponse<User>

    /** Retrieve a [PaginatedResponse] containing the top [Track]s of an [Artist] */
    @GET("{id}/top")
    @JvmSynthetic
    suspend fun getTopTracks(
        @Path id: Long,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Track>

    /** Retrieve a [PaginatedResponse] with all [Album]s of an [Artist] */
    @GET("{id}/albums")
    @JvmSynthetic
    suspend fun getAlbums(
        @Path id: Long,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Album>

    /** Retrieve a [PaginatedResponse] with the radio [io.github.kingg22.deezer.client.api.objects.Track]s of an [Artist] */
    @GET("{id}/radio")
    @JvmSynthetic
    suspend fun getRadio(@Path id: Long, @Query index: Int? = null, @Query limit: Int? = null): PaginatedResponse<Track>

    /** Retrieve a [PaginatedResponse] with all [Playlist]s featuring an [Artist] */
    @GET("{id}/playlists")
    @JvmSynthetic
    suspend fun getPlaylists(
        @Path id: Long,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Playlist>

    /** Retrieve a [PaginatedResponse] with [Artist]s related to a specific [Artist] */
    @GET("{id}/related")
    @JvmSynthetic
    suspend fun getRelated(
        @Path id: Long,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Artist>
}
