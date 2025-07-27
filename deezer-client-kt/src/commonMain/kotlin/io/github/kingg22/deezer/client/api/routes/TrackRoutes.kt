package io.github.kingg22.deezer.client.api.routes

import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Path
import io.github.kingg22.deezer.client.api.objects.Track

/**
 * Defines all endpoints related to [io.github.kingg22.deezerSdk.api.objects.Track]
 * @author Kingg22
 */
interface TrackRoutes {
    /** Retrieve an [Track] by ID */
    @GET("track/{id}")
    suspend fun getById(@Path id: Long): Track

    /** Retrieve an [Track] by ISRC (International Standard Recording Code) */
    @GET("track/isrc:{isrc}")
    suspend fun getByIsrc(@Path isrc: String): Track
}
