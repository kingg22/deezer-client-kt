@file:JvmName("-RoutesAccessor")
@file:JvmSynthetic

package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.ktorgen.core.KtorGenExperimental
import io.github.kingg22.ktorgen.core.KtorGenFunctionKmp
import io.ktor.client.HttpClient
import kotlin.jvm.JvmName
import kotlin.jvm.JvmSynthetic

@JvmSynthetic
@OptIn(KtorGenExperimental::class)
@KtorGenFunctionKmp
internal expect fun AlbumRoutes(httpClient: HttpClient): AlbumRoutes

@JvmSynthetic
@OptIn(KtorGenExperimental::class)
@KtorGenFunctionKmp
internal expect fun ArtistRoutes(httpClient: HttpClient): ArtistRoutes

@JvmSynthetic
@OptIn(KtorGenExperimental::class)
@KtorGenFunctionKmp
internal expect fun ChartRoutes(httpClient: HttpClient): ChartRoutes

@JvmSynthetic
@OptIn(KtorGenExperimental::class)
@KtorGenFunctionKmp
internal expect fun EditorialRoutes(httpClient: HttpClient): EditorialRoutes

@JvmSynthetic
@OptIn(KtorGenExperimental::class)
@KtorGenFunctionKmp
internal expect fun EpisodeRoutes(httpClient: HttpClient): EpisodeRoutes

@OptIn(KtorGenExperimental::class)
@KtorGenFunctionKmp
@JvmSynthetic
internal expect fun GenreRoutes(httpClient: HttpClient): GenreRoutes

@OptIn(KtorGenExperimental::class)
@KtorGenFunctionKmp
@JvmSynthetic
internal expect fun InfosRoute(httpClient: HttpClient): InfosRoute

@OptIn(KtorGenExperimental::class)
@KtorGenFunctionKmp
@JvmSynthetic
internal expect fun OptionsRoute(httpClient: HttpClient): OptionsRoute

@OptIn(KtorGenExperimental::class)
@KtorGenFunctionKmp
@JvmSynthetic
internal expect fun PlaylistRoutes(httpClient: HttpClient): PlaylistRoutes

@OptIn(KtorGenExperimental::class)
@KtorGenFunctionKmp
@JvmSynthetic
internal expect fun PodcastRoutes(httpClient: HttpClient): PodcastRoutes

@OptIn(KtorGenExperimental::class)
@KtorGenFunctionKmp
@JvmSynthetic
internal expect fun RadioRoutes(httpClient: HttpClient): RadioRoutes

@OptIn(KtorGenExperimental::class)
@KtorGenFunctionKmp
@JvmSynthetic
internal expect fun TrackRoutes(httpClient: HttpClient): TrackRoutes

@OptIn(KtorGenExperimental::class)
@KtorGenFunctionKmp
@JvmSynthetic
internal expect fun SearchRoutes(httpClient: HttpClient): SearchRoutes

@OptIn(KtorGenExperimental::class)
@KtorGenFunctionKmp
@JvmSynthetic
internal expect fun UserRoutes(httpClient: HttpClient): UserRoutes
