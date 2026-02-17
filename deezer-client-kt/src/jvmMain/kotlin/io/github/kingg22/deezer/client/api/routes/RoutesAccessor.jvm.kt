@file:JvmName("-RoutesAccessor")
@file:JvmSynthetic

package io.github.kingg22.deezer.client.api.routes

import io.ktor.client.HttpClient
import kotlin.jvm.JvmName
import kotlin.jvm.JvmSynthetic

@JvmSynthetic
internal actual fun AlbumRoutes(httpClient: HttpClient): AlbumRoutes = _AlbumRoutesImpl(httpClient)

@JvmSynthetic
internal actual fun ArtistRoutes(httpClient: HttpClient): ArtistRoutes = _ArtistRoutesImpl(httpClient)

@JvmSynthetic
internal actual fun ChartRoutes(httpClient: HttpClient): ChartRoutes = _ChartRoutesImpl(httpClient)

@JvmSynthetic
internal actual fun EditorialRoutes(httpClient: HttpClient): EditorialRoutes = _EditorialRoutesImpl(httpClient)

@JvmSynthetic
internal actual fun EpisodeRoutes(httpClient: HttpClient): EpisodeRoutes = _EpisodeRoutesImpl(httpClient)

@JvmSynthetic
internal actual fun GenreRoutes(httpClient: HttpClient): GenreRoutes = _GenreRoutesImpl(httpClient)

@JvmSynthetic
internal actual fun InfosRoute(httpClient: HttpClient): InfosRoute = _InfosRouteImpl(httpClient)

@JvmSynthetic
internal actual fun OptionsRoute(httpClient: HttpClient): OptionsRoute = _OptionsRouteImpl(httpClient)

@JvmSynthetic
internal actual fun PlaylistRoutes(httpClient: HttpClient): PlaylistRoutes = _PlaylistRoutesImpl(httpClient)

@JvmSynthetic
internal actual fun PodcastRoutes(httpClient: HttpClient): PodcastRoutes = _PodcastRoutesImpl(httpClient)

@JvmSynthetic
internal actual fun RadioRoutes(httpClient: HttpClient): RadioRoutes = _RadioRoutesImpl(httpClient)

@JvmSynthetic
internal actual fun TrackRoutes(httpClient: HttpClient): TrackRoutes = _TrackRoutesImpl(httpClient)

@JvmSynthetic
internal actual fun SearchRoutes(httpClient: HttpClient): SearchRoutes = _SearchRoutesImpl(httpClient)

@JvmSynthetic
internal actual fun UserRoutes(httpClient: HttpClient): UserRoutes = _UserRoutesImpl(httpClient)
