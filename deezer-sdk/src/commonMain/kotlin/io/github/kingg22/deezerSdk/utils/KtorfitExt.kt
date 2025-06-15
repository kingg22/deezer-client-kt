package io.github.kingg22.deezerSdk.utils

import de.jensklingenberg.ktorfit.ktorfit
import io.ktor.client.HttpClient

internal fun createKtorfit(baseUrl: String, httpClient: HttpClient) = ktorfit {
    baseUrl(baseUrl, false)
    httpClient(httpClient)
}
