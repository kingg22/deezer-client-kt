@file:Suppress("kotlin:S6517")

package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Options
import io.github.kingg22.ktorgen.http.GET

/**
 * Defines all endpoints related to [io.github.kingg22.deezer.client.api.objects.Options]
 * @author Kingg22
 */
interface OptionsRoute {
    /** Retrieve [io.github.kingg22.deezer.client.api.objects.Options] */
    @GET("options")
    suspend fun getOptions(): Options
}
