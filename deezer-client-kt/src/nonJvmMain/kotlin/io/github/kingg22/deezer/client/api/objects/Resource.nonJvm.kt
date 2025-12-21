package io.github.kingg22.deezer.client.api.objects

import io.github.kingg22.deezer.client.api.DeezerApiClient
import io.github.kingg22.deezer.client.exceptions.DeezerApiException
import kotlin.coroutines.cancellation.CancellationException
import kotlin.jvm.JvmSynthetic

actual abstract class Resource {
    actual abstract val id: Long
    actual abstract val type: String

    @JvmSynthetic
    @Throws(DeezerApiException::class, CancellationException::class)
    actual abstract suspend fun reload(client: DeezerApiClient): Resource

    @Suppress("DEPRECATION", "DEPRECATION_ERROR")
    @Deprecated(
        message = "Use reload(client: DeezerApiClient) instead, pass a client explicitly",
        replaceWith = ReplaceWith(expression = "reload(client)"),
        level = DeprecationLevel.HIDDEN,
    )
    @io.github.kingg22.deezer.client.utils.AfterInitialize
    @JvmSynthetic
    @Throws(DeezerApiException::class, CancellationException::class)
    actual open suspend fun reload(): Resource =
        reload(io.github.kingg22.deezer.client.api.GlobalDeezerApiClient.requireInstance())
}
