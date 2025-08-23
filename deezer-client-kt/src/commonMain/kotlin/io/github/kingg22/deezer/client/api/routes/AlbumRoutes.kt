package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Album
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse
import io.github.kingg22.deezer.client.api.objects.Track
import io.github.kingg22.deezer.client.api.objects.User
import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import io.github.kingg22.ktorgen.core.KtorGen
import io.github.kingg22.ktorgen.http.GET
import io.github.kingg22.ktorgen.http.Path
import io.github.kingg22.ktorgen.http.Query
import kotlin.jvm.JvmSynthetic

/**
 * Defines all endpoints related to [io.github.kingg22.deezer.client.api.objects.Album]
 * @author Kingg22
 */
@KtorGen(
    basePath = "album/",
    visibilityModifier = "internal",
    functionAnnotations = [JvmSynthetic::class, InternalDeezerClient::class],
    annotations = [InternalDeezerClient::class],
)
interface AlbumRoutes {
    /** Retrieve an [Album] by ID */
    @GET("{id}")
    @JvmSynthetic
    suspend fun getById(@Path id: Long): Album

    /** Retrieve an [Album] by UPC (Universal Product Code) */
    @GET("upc:{upc}")
    @JvmSynthetic
    suspend fun getByUpc(@Path upc: String): Album

    /** Retrieve the fans of an [Album] */
    @GET("{id}/fans")
    @JvmSynthetic
    suspend fun getFans(@Path id: Long, @Query index: Int? = null, @Query limit: Int? = null): PaginatedResponse<User>

    /** Retrieve a [PaginatedResponse] with all [io.github.kingg22.deezer.client.api.objects.Track] from an [Album] */
    @GET("{id}/tracks")
    @JvmSynthetic
    suspend fun getTracks(
        @Path id: Long,
        @Query index: Int? = null,
        @Query limit: Int? = null,
    ): PaginatedResponse<Track>
}
