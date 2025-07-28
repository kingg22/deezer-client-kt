@file:Suppress("kotlin:S6517")

package io.github.kingg22.deezer.client.api.routes

import de.jensklingenberg.ktorfit.http.GET
import io.github.kingg22.deezer.client.api.objects.Infos

/**
 * Defines all endpoints related to [io.github.kingg22.deezer.client.api.objects.Infos]
 * @author Kingg22
 */
interface InfosRoute {
    /** Retrieve [io.github.kingg22.deezer.client.api.objects.Infos] in the current country */
    @GET("infos")
    suspend fun getInfos(): Infos
}
