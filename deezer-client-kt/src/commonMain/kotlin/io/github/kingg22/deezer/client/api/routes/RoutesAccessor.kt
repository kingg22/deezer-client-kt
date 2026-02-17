@file:JvmName("-RoutesAccessor")
@file:JvmSynthetic

package io.github.kingg22.deezer.client.api.routes

import io.ktor.client.HttpClient
import kotlin.jvm.JvmName
import kotlin.jvm.JvmSynthetic

@JvmSynthetic
internal expect fun AlbumRoutes(httpClient: HttpClient): AlbumRoutes

@JvmSynthetic
internal expect fun ArtistRoutes(httpClient: HttpClient): ArtistRoutes

@JvmSynthetic
internal expect fun ChartRoutes(httpClient: HttpClient): ChartRoutes

@JvmSynthetic
internal expect fun EditorialRoutes(httpClient: HttpClient): EditorialRoutes

@JvmSynthetic
internal expect fun EpisodeRoutes(httpClient: HttpClient): EpisodeRoutes

@JvmSynthetic
internal expect fun GenreRoutes(httpClient: HttpClient): GenreRoutes

@JvmSynthetic
internal expect fun InfosRoute(httpClient: HttpClient): InfosRoute

@JvmSynthetic
internal expect fun OptionsRoute(httpClient: HttpClient): OptionsRoute

@JvmSynthetic
internal expect fun PlaylistRoutes(httpClient: HttpClient): PlaylistRoutes

@JvmSynthetic
internal expect fun PodcastRoutes(httpClient: HttpClient): PodcastRoutes

@JvmSynthetic
internal expect fun RadioRoutes(httpClient: HttpClient): RadioRoutes

@JvmSynthetic
internal expect fun TrackRoutes(httpClient: HttpClient): TrackRoutes

@JvmSynthetic
internal expect fun SearchRoutes(httpClient: HttpClient): SearchRoutes

@JvmSynthetic
internal expect fun UserRoutes(httpClient: HttpClient): UserRoutes
