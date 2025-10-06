@file:Suppress("kotlin:S6517")

package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Options
import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import io.github.kingg22.ktorgen.core.KtorGen
import io.github.kingg22.ktorgen.http.GET

/**
 * Defines all endpoints related to [io.github.kingg22.deezer.client.api.objects.Options]
 * @author Kingg22
 */
@KtorGen(
    visibilityModifier = "internal",
    classVisibilityModifier = "private",
    functionAnnotations = [InternalDeezerClient::class],
    annotations = [InternalDeezerClient::class],
)
interface OptionsRoute {
    /** Retrieve [io.github.kingg22.deezer.client.api.objects.Options] */
    @GET("options")
        suspend fun getOptions(): Options
}
