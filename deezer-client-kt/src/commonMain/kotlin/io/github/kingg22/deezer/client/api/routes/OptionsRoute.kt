@file:Suppress("kotlin:S6517")

package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.DeezerApiClient.Companion.API_DEEZER_URL
import io.github.kingg22.deezer.client.api.objects.Options
import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import io.github.kingg22.ktorgen.core.KtorGen
import io.github.kingg22.ktorgen.http.GET
import kotlin.jvm.JvmSynthetic

/**
 * Defines all endpoints related to [io.github.kingg22.deezer.client.api.objects.Options]
 * @author Kingg22
 */
@KtorGen(
    basePath = "$API_DEEZER_URL/options",
    visibilityModifier = "internal",
    classVisibilityModifier = "private",
    functionAnnotations = [JvmSynthetic::class, InternalDeezerClient::class],
    annotations = [InternalDeezerClient::class],
)
interface OptionsRoute {
    /** Retrieve [io.github.kingg22.deezer.client.api.objects.Options] */
    @GET
    @JvmSynthetic
    suspend fun getOptions(): Options
}
