@file:Suppress("kotlin:S6517")

package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Episode
import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import io.github.kingg22.ktorgen.core.KtorGen
import io.github.kingg22.ktorgen.http.GET
import io.github.kingg22.ktorgen.http.Path
import kotlin.jvm.JvmSynthetic

/**
 * Defines all endpoints related to [io.github.kingg22.deezer.client.api.objects.Episode]
 * @author Kingg22
 */
@KtorGen(
    visibilityModifier = "internal",
    classVisibilityModifier = "private",
    functionAnnotations = [JvmSynthetic::class, InternalDeezerClient::class],
    annotations = [InternalDeezerClient::class],
)
interface EpisodeRoutes {
    /** Retrieve an [io.github.kingg22.deezer.client.api.objects.Episode] by ID */
    @GET("episode/{id}")
    @JvmSynthetic
    suspend fun getById(@Path id: Long): Episode
}
