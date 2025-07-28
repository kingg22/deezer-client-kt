package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Options
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.runBlocking
import org.jetbrains.annotations.Blocking
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Defines all endpoints related to [Options]
 * @author Kingg22
 */
@PublishedApi
/*
This is internal, for kotlin consumers can't access this, but Java consumers can access avoid internal stuff.
Is PublishedApi to maintain binary compatibility.
 */
internal class OptionsJavaRoute private constructor(
    private val delegate: OptionsRoute,
) {
    /** Retrieve [Options] blocking the thread. */
    @Blocking
    fun getOptions() = runBlocking { delegate.getOptions() }

    // -- Completable Future --

    /** Retrieve [Options] with [CompletableFuture] */
    @JvmOverloads
    fun getOptionsFuture(coroutineContext: CoroutineContext = EmptyCoroutineContext): CompletableFuture<Options> =
        CoroutineScope(coroutineContext).future { delegate.getOptions() }

    companion object {
        /** Create an [OptionsJavaRoute] with [Options Kotlin Route][OptionsRoute] as delegate. */
        @PublishedApi
        @JvmStatic
        @JvmName("create")
        internal fun OptionsRoute.asJava() = OptionsJavaRoute(this)
    }
}
