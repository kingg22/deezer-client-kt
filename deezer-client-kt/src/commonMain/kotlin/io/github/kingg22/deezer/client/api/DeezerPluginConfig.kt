package io.github.kingg22.deezer.client.api

import io.github.kingg22.deezer.client.utils.DeezerApiPoko
import io.ktor.http.Url
import io.ktor.utils.io.KtorDsl

/** Configurations for [DeezerClientPlugin] */
@KtorDsl
@DeezerApiPoko
class DeezerPluginConfig(
    /**
     * A list of [hosts][Url.host] to match and apply this plugin. Default [DeezerApiClient.API_DEEZER_URL].
     *
     * Important: Match **HOST** only, don't include schema/protocol, path, segments, query, etc.
     * The plugin use simple `request.host in allowedHosts`.
     *
     * @see Url
     */
    val allowedHosts: MutableSet<String> = mutableSetOf(Url(DeezerApiClient.API_DEEZER_URL).host),

    /**
     * Whether to include the default headers to be compatible with the official API.
     * See source code of [default Deezer Headers][io.github.kingg22.deezer.client.utils.getDefaultDeezerHeaders].
     * Default `false`
     */
    var includeDefaultHeaders: Boolean = false,
)
