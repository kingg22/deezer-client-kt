package io.github.kingg22.deezer.client.api.objects

import io.github.kingg22.deezer.client.KtorEngineMocked
import io.github.kingg22.deezer.client.api.DeezerApiClient
import io.kotest.matchers.collections.shouldContain
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
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
            isExplicitLyrics = false,
            titleShort = "",
            duration = 0,
            rank = 0,
            preview = "",
            artist = Artist(0, ""),
        )
    }

    private lateinit var client: DeezerApiClient

    @BeforeTest
    fun setup() {
        client = DeezerApiClient(KtorEngineMocked.createHttpClientMock())
    }

    @Test
    fun fetch_next() = runTest {
        var tested = PaginatedResponse<Track>(emptyList(), next = TRACK_LINK)
        tested = assertNotNull(tested.fetchNext(client))
        assertTrue { tested.data.isNotEmpty() }
    }

    @Test
    fun fetch_next_without_link_return_null() = runTest {
        val result = PaginatedResponse<User>(emptyList())
        assertNull(result.next)
        assertNull(result.fetchNext(client))
    }

    @Test
    fun fetch_next_expand_with_data_empty_dont_throw_exception() = runTest {
        val tested = PaginatedResponse<Track>(emptyList(), next = TRACK_LINK)
        tested.fetchNext(client, true).shouldNotBeNull().data.shouldNotBeEmpty()
    }

    @Test
    fun fetch_next_expanded() = runTest {
        var tested = PaginatedResponse(listOf(emptyTrack), next = TRACK_LINK)
        tested = assertNotNull(tested.fetchNext(client, true))
        tested.data shouldContain emptyTrack
        assertNotNull(tested.next)
        assertNotEquals(TRACK_LINK, tested.next)
    }

    @Test
    fun fetch_previous_without_link_return_null() = runTest {
        val result = PaginatedResponse<User>(emptyList())
        assertNull(result.prev)
        assertNull(result.fetchPrevious(client))
    }

    @Test
    fun fetch_previous() = runTest {
        var tested = PaginatedResponse<Track>(emptyList(), prev = TRACK_LINK)
        tested = assertNotNull(tested.fetchPrevious(client))
        assertTrue { tested.data.isNotEmpty() }
    }

    @Test
    fun fetch_previous_expand_with_data_empty_dont_throw_exception() = runTest {
        val tested = PaginatedResponse<Track>(emptyList(), prev = TRACK_LINK)
        tested.fetchPrevious(client, true).shouldNotBeNull().data.shouldNotBeEmpty()
    }

    @Test
    fun fetch_previous_expanded() = runTest {
        var tested = PaginatedResponse(listOf(emptyTrack), prev = TRACK_LINK)
        tested = tested.fetchPrevious(client, true).shouldNotBeNull()
        tested.data shouldContain emptyTrack
        tested.prev.shouldBeNull()
    }
}
