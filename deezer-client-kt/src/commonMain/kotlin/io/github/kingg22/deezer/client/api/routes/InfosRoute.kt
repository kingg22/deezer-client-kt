@file:Suppress("kotlin:S6517")

package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Infos
import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import io.github.kingg22.ktorgen.core.KtorGen
import io.github.kingg22.ktorgen.http.GET
import kotlin.jvm.JvmSynthetic

/**
 * Defines all endpoints related to [io.github.kingg22.deezer.client.api.objects.Infos]
 * @author Kingg22
 */
@KtorGen(
    visibilityModifier = "internal",
    functionAnnotations = [JvmSynthetic::class, InternalDeezerClient::class],
    annotations = [InternalDeezerClient::class],
)
interface InfosRoute {
    /** Retrieve [io.github.kingg22.deezer.client.api.objects.Infos] in the current country */
    @GET("infos")
    @JvmSynthetic
    suspend fun getInfos(): Infos
}
