package io.github.kingg22.deezerSdk.api.routes

import de.jensklingenberg.ktorfit.http.GET
import io.github.kingg22.deezerSdk.api.objects.Options

/**
 * Defines all endpoints related to [io.github.kingg22.deezerSdk.api.objects.Options]
 * @author Kingg22
 */
interface OptionsRoute {
    /** Retrieve [io.github.kingg22.deezerSdk.api.objects.Options] */
    @GET("options")
    suspend fun getOptions(): Options
}
