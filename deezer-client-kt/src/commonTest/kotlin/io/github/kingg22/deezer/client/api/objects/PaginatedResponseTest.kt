package io.github.kingg22.deezer.client.api.objects

import io.github.kingg22.deezer.client.KtorEngineMocked
import io.github.kingg22.deezer.client.api.DeezerApiClient
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldContainIgnoringCase
import kotlinx.coroutines.test.runTest
import kotlin.jvm.JvmField
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PaginatedResponseTest {
    companion object {
        const val TRACK_LINK = "https://api.deezer.com/search?q=eminem"

        @JvmField
        val emptyTrack = Track(
            0,
            title = "",
            explicitLyrics = false,
            titleShort = "",
            duration = 0,
            rank = 0,
            preview = "",
            artist = Artist(0, ""),
        )
    }
    lateinit var client: DeezerApiClient

    @BeforeTest
    fun setup() {
        client = DeezerApiClient.initialize(KtorEngineMocked.createHttpBuilderMock())
    }

    @Test
    fun fetch_next() = runTest {
        var tested = PaginatedResponse<Track>(emptyList(), next = TRACK_LINK)
        tested = assertNotNull(tested.fetchNext())
        assertTrue { tested.data.isNotEmpty() }
    }

    @Test
    fun fetch_next_without_link_return_null() = runTest {
        val result = PaginatedResponse<User>(emptyList())
        assertNull(result.next)
        assertNull(result.fetchNext<User>())
    }

    @Test
    fun fetch_next_expand_with_data_empty_dont_throw_exception() = runTest {
        val tested = PaginatedResponse<Track>(emptyList(), next = TRACK_LINK)
        tested.fetchNext<Track>(true).shouldNotBeNull().data.shouldNotBeEmpty()
    }

    @Test
    fun fetch_next_expand_with_data_and_different_type_throw_exception() = runTest {
        val tested = PaginatedResponse(listOf(User(1, "User")), next = TRACK_LINK)
        assertFailsWith(IllegalArgumentException::class) {
            tested.fetchNext<Track>(true)
        }.message.shouldContainIgnoringCase("Requires type equals")
    }

    @Test
    fun fetch_next_expanded() = runTest {
        var tested = PaginatedResponse(listOf(emptyTrack), next = TRACK_LINK)
        tested = assertNotNull(tested.fetchNext(true))
        tested.data shouldContain emptyTrack
        assertNotNull(tested.next)
        assertNotEquals(TRACK_LINK, tested.next)
    }

    @Test
    fun fetch_next_expand_with_different_types_throw_exception() = runTest {
        val tested = PaginatedResponse(listOf(User(0, "")), next = TRACK_LINK)
        assertFailsWith(IllegalArgumentException::class) { tested.fetchNext<Track>(true) }.let {
            it.message shouldContainIgnoringCase "Requires type equals"
        }
    }

    @Test
    fun fetch_previous_without_link_return_null() = runTest {
        val result = PaginatedResponse<User>(emptyList())
        assertNull(result.prev)
        assertNull(result.fetchPrevious<User>())
    }

    @Test
    fun fetch_previous() = runTest {
        var tested = PaginatedResponse<Track>(emptyList(), prev = TRACK_LINK)
        tested = assertNotNull(tested.fetchPrevious())
        assertTrue { tested.data.isNotEmpty() }
    }

    @Test
    fun fetch_previous_expand_with_data_empty_dont_throw_exception() = runTest {
        val tested = PaginatedResponse<Track>(emptyList(), prev = TRACK_LINK)
        tested.fetchPrevious<Track>(true).shouldNotBeNull().data.shouldNotBeEmpty()
    }

    @Test
    fun fetch_previous_expand_without_data_and_different_types_dont_throw_exception() = runTest {
        val tested = PaginatedResponse<User>(emptyList(), prev = TRACK_LINK)
        tested.fetchPrevious<Track>(true).shouldNotBeNull().data.shouldNotBeEmpty()
    }

    @Test
    fun fetch_previous_expanded() = runTest {
        var tested = PaginatedResponse(listOf(emptyTrack), prev = TRACK_LINK)
        tested = tested.fetchPrevious<Track>(true).shouldNotBeNull()
        tested.data shouldContain emptyTrack
        tested.prev.shouldBeNull()
    }

    @Test
    fun fetch_previous_expand_with_different_types_throw_exception() = runTest {
        val tested = PaginatedResponse(listOf(User(0, "")), prev = TRACK_LINK)
        assertFailsWith(IllegalArgumentException::class) {
            tested.fetchPrevious<Track>(true)
        }.message.shouldContainIgnoringCase("Requires type equals to expand in fetchPrevious.")
    }
}
