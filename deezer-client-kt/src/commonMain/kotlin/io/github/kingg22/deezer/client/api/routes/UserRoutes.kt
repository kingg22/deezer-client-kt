package io.github.kingg22.deezer.client.api.routes

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import io.github.kingg22.deezer.client.api.objects.User

/**
 * Defines all endpoints related to [io.github.kingg22.deezer.client.api.objects.User]
 * @author Kingg22
 */
interface UserRoutes {
    /** Retrieve an [io.github.kingg22.deezer.client.api.objects.User] by ID */
    @GET("user/{id}")
    suspend fun getById(@Path id: Long): User
    // TODO: add rest of personal endpoints which require authorization
}
