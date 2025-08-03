@file:Suppress("kotlin:S6517")

package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.User
import io.github.kingg22.ktorgen.http.GET
import io.github.kingg22.ktorgen.http.Path

/**
 * Defines all endpoints related to [io.github.kingg22.deezer.client.api.objects.User]
 *
 * _Dev Notes:_ TODO add rest of personal endpoints which require authorization
 * @author Kingg22
 */
interface UserRoutes {
    /** Retrieve an [io.github.kingg22.deezer.client.api.objects.User] by ID */
    @GET("user/{id}")
    suspend fun getById(@Path id: Long): User
}
