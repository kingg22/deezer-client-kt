@file:Suppress("kotlin:S6517")

package io.github.kingg22.deezer.client.api.routes

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import io.github.kingg22.deezer.client.api.objects.Episode

/**
 * Defines all endpoints related to [io.github.kingg22.deezer.client.api.objects.Episode]
 * @author Kingg22
 */
interface EpisodeRoutes {
    /** Retrieve an [io.github.kingg22.deezer.client.api.objects.Episode] by ID */
    @GET("episode/{id}")
    suspend fun getById(@Path id: Long): Episode
}
