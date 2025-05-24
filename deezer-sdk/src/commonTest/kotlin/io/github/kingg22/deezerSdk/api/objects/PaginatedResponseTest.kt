package io.github.kingg22.deezerSdk.api.objects

import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.string.shouldBeEqualIgnoringCase
import io.kotest.matchers.string.shouldContainIgnoringCase
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PaginatedResponseTest {
    private val nextLink = "https://api.deezer.com/search?q=eminem"

    @Test
    fun `Fetch Next`() = runTest {
        var tested: PaginatedResponse<Track> = PaginatedResponse(emptyList(), next = nextLink)
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
    fun `Fetch Next Expand with data empty throw exception`() = runTest {
        val tested = PaginatedResponse<Track>(emptyList(), next = nextLink)
        assertFailsWith(IllegalArgumentException::class) { tested.fetchNext<Track>(true) }.let {
            it.message shouldBeEqualIgnoringCase "Requires data not empty to expand it"
        }
    }

    @Test
    fun `Fetch Next Expanded`() = runTest {
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

        var tested = PaginatedResponse(listOf(emptyTrack), next = nextLink)
        tested = assertNotNull(tested.fetchNext(true))
        tested.data shouldContain emptyTrack
        assertNotNull(tested.next)
        assertNotEquals(nextLink, tested.next)
    }

    @Test
    fun `Fetch Next Expand with different type throw exception`() = runTest {
        val tested = PaginatedResponse(listOf(User(0, "")), next = nextLink)
        assertFailsWith(IllegalArgumentException::class) { tested.fetchNext<Track>(true) }.let {
            it.message shouldContainIgnoringCase "Requires type equals to expand in fetchNext."
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
        var tested: PaginatedResponse<Track> = PaginatedResponse(emptyList(), prev = nextLink)
        tested = assertNotNull(tested.fetchPrevious())
        assertTrue { tested.data.isNotEmpty() }
    }
}
