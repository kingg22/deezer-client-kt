package io.github.kingg22.deezer.client.api.objects

import io.github.kingg22.deezer.client.utils.DeezerApiPoko
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads

@DeezerApiPoko
@Serializable
actual class PaginatedResponse<out T : @Serializable Any> @JvmOverloads actual constructor(
    actual val data: List<T>,
    actual val checksum: String?,
    actual val total: Int?,
    actual val prev: String?,
    actual val next: String?,
) {
    actual fun copy(
        data: List<@UnsafeVariance T>,
        checksum: String?,
        total: Int?,
        prev: String?,
        next: String?,
    ): PaginatedResponse<T> = PaginatedResponse(data, checksum, total, prev, next)
}
