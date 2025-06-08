package io.github.kingg22.deezerSdk.utils

/**
 * Indicate the function **need** to be called **after** the `initialize` method of the client.
 *
 * @author Kingg22
 * @see io.github.kingg22.deezerSdk.api.DeezerApiClient.initialize
 * @see io.github.kingg22.deezerSdk.gw.DeezerGwClient.initialize
 */
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class AfterInitialize
