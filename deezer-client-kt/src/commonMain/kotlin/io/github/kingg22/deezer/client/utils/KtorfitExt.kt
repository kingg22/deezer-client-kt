package io.github.kingg22.deezer.client.utils

import de.jensklingenberg.ktorfit.ktorfit
import io.ktor.client.HttpClient

@JvmSynthetic
internal fun createKtorfit(baseUrl: String, httpClient: HttpClient) = ktorfit {
    baseUrl(baseUrl, false)
    httpClient(httpClient)
}
