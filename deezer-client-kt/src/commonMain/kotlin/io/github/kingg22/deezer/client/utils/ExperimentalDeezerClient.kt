package io.github.kingg22.deezer.client.utils

/** Use by your risk */
@RequiresOptIn(
    "Indicate this API is experimental, can or not tested and may be break your app",
    RequiresOptIn.Level.ERROR,
)
@Retention(AnnotationRetention.BINARY)
@MustBeDocumented
annotation class ExperimentalDeezerClient
