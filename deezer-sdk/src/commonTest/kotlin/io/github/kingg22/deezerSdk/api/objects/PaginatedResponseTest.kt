package io.github.kingg22.deezerSdk.api.objects

import io.github.kingg22.deezerSdk.api.DeezerApiClientTest.Companion.client
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.string.shouldContainIgnoringCase
import kotlinx.coroutines.test.runTest
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

    @BeforeTest
    fun config() {
        // Initialize DeezerApiClient before each test
        client
    }

    @Test
    fun `Fetch Next`() = runTest {
        var tested = PaginatedResponse<Track>(emptyList(), next = TRACK_LINK)
        tested = assertNotNull(tested.fetchNext())
        assertTrue { tested.data.isNotEmpty() }
    }

    @Test
    fun `Fetch Next without link return null`() = runTest {
        val result = PaginatedResponse<User>(emptyList())
        assertNull(result.next)
        assertNull(result.fetchNext<User>())
    }

    @Test
    fun `Fetch Next Expand with data empty don't throw exception`() = runTest {
        val tested = PaginatedResponse<Track>(emptyList(), next = TRACK_LINK)
        tested.fetchNext<Track>(true).shouldNotBeNull().data.shouldNotBeEmpty()
    }

    @Test
    fun `Fetch Next Expand with data and different type throw exception`() = runTest {
        val tested = PaginatedResponse(listOf(User(1, "User")), next = TRACK_LINK)
        assertFailsWith(IllegalArgumentException::class) {
            tested.fetchNext<Track>(true)
        }.message.shouldContainIgnoringCase("Requires type equals")
    }

    @Test
    fun `Fetch Next Expanded`() = runTest {
        var tested = PaginatedResponse(listOf(emptyTrack), next = TRACK_LINK)
        tested = assertNotNull(tested.fetchNext(true))
        tested.data shouldContain emptyTrack
        assertNotNull(tested.next)
        assertNotEquals(TRACK_LINK, tested.next)
    }

    @Test
    fun `Fetch Next Expand with different type throw exception`() = runTest {
        val tested = PaginatedResponse(listOf(User(0, "")), next = TRACK_LINK)
        assertFailsWith(IllegalArgumentException::class) { tested.fetchNext<Track>(true) }.let {
            it.message shouldContainIgnoringCase "Requires type equals"
        }
    }

    @Test
    fun `Fetch Previous without link return null`() = runTest {
        val result = PaginatedResponse<User>(emptyList())
        assertNull(result.prev)
        assertNull(result.fetchPrevious<User>())
    }

    @Test
    fun `Fetch Previous`() = runTest {
        var tested = PaginatedResponse<Track>(emptyList(), prev = TRACK_LINK)
        tested = assertNotNull(tested.fetchPrevious())
        assertTrue { tested.data.isNotEmpty() }
    }

    @Test
    fun `Fetch Previous Expand with data empty don't throw exception`() = runTest {
        val tested = PaginatedResponse<Track>(emptyList(), prev = TRACK_LINK)
        tested.fetchPrevious<Track>(true).shouldNotBeNull().data.shouldNotBeEmpty()
    }

    @Test
    fun `Fetch Previous Expand without data and different type don't throw exception`() = runTest {
        val tested = PaginatedResponse<User>(emptyList(), prev = TRACK_LINK)
        tested.fetchPrevious<Track>(true).shouldNotBeNull().data.shouldNotBeEmpty()
    }

    @Test
    fun `Fetch Previous Expanded`() = runTest {
        var tested = PaginatedResponse(listOf(emptyTrack), prev = TRACK_LINK)
        tested = tested.fetchPrevious<Track>(true).shouldNotBeNull()
        tested.data shouldContain emptyTrack
        tested.prev.shouldBeNull()
    }

    @Test
    fun `Fetch Previous Expand with different type throw exception`() = runTest {
        val tested = PaginatedResponse(listOf(User(0, "")), prev = TRACK_LINK)
        assertFailsWith(IllegalArgumentException::class) {
            tested.fetchPrevious<Track>(true)
        }.message.shouldContainIgnoringCase("Requires type equals to expand in fetchPrevious.")
    }
}
