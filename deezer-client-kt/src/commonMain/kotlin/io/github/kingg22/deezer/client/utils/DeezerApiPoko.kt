package io.github.kingg22.deezer.client.utils

/** Internal annotation to generate [equals], [hashCode], [toString] for classes without `data class` with Poko plugin */
@InternalDeezerClient
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
internal annotation class DeezerApiPoko
