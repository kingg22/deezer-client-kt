package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.api.objects.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.future.future
import kotlinx.coroutines.runBlocking
import org.jetbrains.annotations.Blocking
import java.util.concurrent.CompletableFuture
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * Defines all endpoints related to [User]
 * @author Kingg22
 */
@PublishedApi
/*
This is internal, for kotlin consumers can't access this, but Java consumers can access avoid internal stuff.
Is PublishedApi to maintain binary compatibility.
 */
internal class UserJavaRoutes private constructor(
    private val delegate: UserRoutes,
) {
    /** Retrieve a [User] by ID blocking the thread. */
    @Blocking
    fun getById(id: Long) = runBlocking { delegate.getById(id) }

    // -- Completable Future --

    /** Retrieve a [User] by ID with [CompletableFuture] */
    @JvmOverloads
    fun getByIdFuture(id: Long, coroutineContext: CoroutineContext = EmptyCoroutineContext): CompletableFuture<User> =
        CoroutineScope(coroutineContext).future { delegate.getById(id) }

    companion object {
        /** Create a [UserJavaRoutes] with [User Kotlin Routes][UserRoutes] as delegate. */
        @PublishedApi
        @JvmStatic
        @JvmName("create")
        internal fun UserRoutes.asJava() = UserJavaRoutes(this)
    }
}
