package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.Infos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.runBlocking
import org.jetbrains.annotations.Blocking
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Defines all endpoints related to [Infos]
 * @author Kingg22
 */
@PublishedApi
/*
This is internal, for kotlin consumers can't access this, but Java consumers can access avoid internal stuff.
Is PublishedApi to maintain binary compatibility.
 */
internal class InfosJavaRoute private constructor(
    private val delegate: InfosRoute,
) {
    /** Retrieve [Infos] in the current country blocking the thread. */
    @Blocking
    fun getInfos() = runBlocking { delegate.getInfos() }

    // -- Completable Future --

    /** Retrieve [Infos] in the current country with [CompletableFuture] */
    @JvmOverloads
    fun getInfosFuture(coroutineContext: CoroutineContext = EmptyCoroutineContext): CompletableFuture<Infos> =
        CoroutineScope(coroutineContext).future { delegate.getInfos() }

    companion object {
        /** Create an [InfosJavaRoute] with [Infos Kotlin Route][InfosRoute] as delegate. */
        @PublishedApi
        @JvmStatic
        @JvmName("create")
        internal fun InfosRoute.asJava() = InfosJavaRoute(this)
    }
}
