package io.github.kingg22.deezer.client.utils

import org.jetbrains.annotations.Blocking

/**
 * Any method or object marked is **ONLY for Java consumers, Kotlin consumers use the original method**.
 *
 * For example, a suspend method to initialize a client should be marked with this, in Java consumers the same method
 * is blocking or [java.util.concurrent.CompletableFuture] or equivalent can be called from Java without work around.
 */
@Blocking
@RequiresOptIn("This object or method is ONLY for Java consumers", RequiresOptIn.Level.ERROR)
@MustBeDocumented
annotation class ForJavaDeezerSdk
