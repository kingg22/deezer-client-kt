package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.DeezerApiClient.Companion.API_DEEZER_URL
import io.github.kingg22.deezer.client.api.objects.Track
import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import io.github.kingg22.ktorgen.core.KtorGen
import io.github.kingg22.ktorgen.http.GET
import io.github.kingg22.ktorgen.http.Path
import kotlin.jvm.JvmSynthetic

/**
 * Defines all endpoints related to [io.github.kingg22.deezer.client.api.objects.Track]
 * @author Kingg22
 */
@KtorGen(
    basePath = "$API_DEEZER_URL/track/",
    generateTopLevelFunction = false,
    classVisibilityModifier = "internal",
    annotations = [InternalDeezerClient::class],
)
interface TrackRoutes {
    /** Retrieve an [Track] by ID */
    @GET("{id}")
    @JvmSynthetic
    suspend fun getById(@Path id: Long): Track

    /** Retrieve an [Track] by ISRC (International Standard Recording Code) */
    @GET("isrc:{isrc}")
    @JvmSynthetic
    suspend fun getByIsrc(@Path isrc: String): Track
}
