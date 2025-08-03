@file:Suppress("kotlin:S6517")

package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Episode
import io.github.kingg22.ktorgen.http.GET
import io.github.kingg22.ktorgen.http.Path

/**
 * Defines all endpoints related to [io.github.kingg22.deezer.client.api.objects.Episode]
 * @author Kingg22
 */
interface EpisodeRoutes {
    /** Retrieve an [io.github.kingg22.deezer.client.api.objects.Episode] by ID */
    @GET("episode/{id}")
    suspend fun getById(@Path id: Long): Episode
}
