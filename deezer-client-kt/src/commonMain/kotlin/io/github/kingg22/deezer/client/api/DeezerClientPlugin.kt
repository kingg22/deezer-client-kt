package io.github.kingg22.deezer.client.api

import io.github.kingg22.deezer.client.api.objects.ErrorContainer
import io.github.kingg22.deezer.client.exceptions.DeezerApiException
import io.github.kingg22.deezer.client.utils.getDefaultDeezerHeaders
import io.ktor.client.call.*
import io.ktor.client.plugins.api.*
import io.ktor.client.statement.*
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive

@Suppress("kotlin:S6312")
private suspend inline fun <reified T : Any> HttpResponse.bodyOrNull(): T? = try {
    body()
} catch (_: Throwable) {
    currentCoroutineContext().ensureActive()
    null
}

// Expose as a kotlin object to easy access from java
@Suppress("ktlint:standard:backing-property-naming")
private val _DeezerClientPlugin = createClientPlugin("DeezerValidationPlugin", ::DeezerPluginConfig) {
    /* Add default Deezer headers if enabled */
    on(SetupRequest) { request ->
        if (request.url.host in pluginConfig.allowedHosts && pluginConfig.includeDefaultHeaders) {
            request.headers.appendAll(getDefaultDeezerHeaders())
        }
    }

    /* Validate a boolean (unique case) response and errors */
    on(Send) { request ->
        val call = proceed(request)

        /* Skip another host is not Deezer */
        if (request.url.host !in this@createClientPlugin.pluginConfig.allowedHosts) {
            return@on call
        }

        val response = call.response

        val error = response.bodyOrNull<ErrorContainer>()?.error
        val asBoolean = response.bodyOrNull<Boolean>()

        when {
            asBoolean != null -> throw DeezerApiException(
                errorMessage = "API responded with boolean: $asBoolean",
            )

            error != null -> throw DeezerApiException(
                errorCode = error.code,
                errorMessage = error.message,
            )
        }

        return@on call
    }
}

/**
 * Ktor Client Plugin with all [configurations][DeezerPluginConfig] and workaround for [Deezer API](https://developers.deezer.com/api/).
 * @see DeezerApiClient
 * @see DeezerPluginConfig
 */
data object DeezerClientPlugin : ClientPlugin<DeezerPluginConfig> by _DeezerClientPlugin
