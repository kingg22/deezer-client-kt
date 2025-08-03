package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Track
import io.github.kingg22.ktorgen.http.GET
import io.github.kingg22.ktorgen.http.Path

/**
 * Defines all endpoints related to [io.github.kingg22.deezer.client.api.objects.Track]
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
