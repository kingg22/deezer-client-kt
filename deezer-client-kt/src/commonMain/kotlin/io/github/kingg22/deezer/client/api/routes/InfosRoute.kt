@file:Suppress("kotlin:S6517")

package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Infos
import io.github.kingg22.ktorgen.http.GET

/**
 * Defines all endpoints related to [io.github.kingg22.deezer.client.api.objects.Infos]
 * @author Kingg22
 */
interface InfosRoute {
    /** Retrieve [io.github.kingg22.deezer.client.api.objects.Infos] in the current country */
    @GET("infos")
    suspend fun getInfos(): Infos
}
