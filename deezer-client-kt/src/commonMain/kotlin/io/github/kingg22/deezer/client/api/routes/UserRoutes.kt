@file:Suppress("kotlin:S6517")

package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.User
import io.github.kingg22.deezer.client.utils.InternalDeezerClient
import io.github.kingg22.ktorgen.core.KtorGen
import io.github.kingg22.ktorgen.http.GET
import io.github.kingg22.ktorgen.http.Path
import kotlin.jvm.JvmSynthetic

/**
 * Defines all endpoints related to [io.github.kingg22.deezer.client.api.objects.User]
 *
 * _Dev Notes:_ TODO add rest of personal endpoints which require authorization
 * @author Kingg22
 */
@KtorGen(
    visibilityModifier = "internal",
    classVisibilityModifier = "private",
    functionAnnotations = [JvmSynthetic::class, InternalDeezerClient::class],
    annotations = [InternalDeezerClient::class],
)
interface UserRoutes {
    /** Retrieve an [io.github.kingg22.deezer.client.api.objects.User] by ID */
    @GET("user/{id}")
    @JvmSynthetic
    suspend fun getById(@Path id: Long): User
}
