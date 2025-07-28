package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.KtorEngineMocked
import io.github.kingg22.deezer.client.KtorEngineMocked.getJsonFromPath
import io.github.kingg22.deezer.client.KtorEngineMocked.jsonSerializer
import io.github.kingg22.deezer.client.api.DeezerApiClient
import io.github.kingg22.deezer.client.api.objects.SearchOrder
import io.github.kingg22.deezer.client.api.routes.SearchRoutes.Companion.buildAdvanceQuery
import io.github.kingg22.deezer.client.api.routes.SearchRoutes.Companion.setStrict
import io.kotest.assertions.json.shouldEqualJson
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.time.Duration.Companion.seconds

class SearchRoutesTest {
    lateinit var client: DeezerApiClient

    @BeforeTest
    fun setup() {
        client = DeezerApiClient.initialize(KtorEngineMocked.createHttpBuilderMock())
    }

    /* Static methods */
    @Test
    fun search_query_without_params_throws_an_exception() {
        assertFailsWith(IllegalArgumentException::class) { buildAdvanceQuery() }.let {
            it.message shouldBe "Requires at least 1 parameter to search"
        }
    }

    @Test
    fun search_query_with_params_but_query_is_blank_then_throws_an_exception() {
        assertFailsWith(IllegalStateException::class) { buildAdvanceQuery(q = "    ") }.let {
            it.message shouldBe "Query cannot be blank"
        }
    }

    @Test
    fun search_query_with_multiple_params() {
        val search = buildAdvanceQuery(artist = "aloe black", album = "i need a dollar")
        assertEquals(search, "artist:\"aloe black\" album:\"i need a dollar\"")
    }

    @Test
    fun search_query_with_numbers_without_quotes() {
        val search = buildAdvanceQuery {
            durationMin = 10.seconds
        }
        assertEquals(search, "dur_min:10")
    }

    @Test
    fun strict_shortcut() {
        assertEquals("on", setStrict(true))
        assertNull(setStrict(false))
    }

    /* Routes */
    @Test
    fun fetch_basic_search() = runTest {
        val result = client.searches.search("eminem")
        val json = getJsonFromPath("/search?q=eminem")
        assertEquals(193, result.total)
        assertEquals(25, result.data.size)
        assertNotNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun fetch_search_with_strict_on() = runTest {
        val result = client.searches.search("Not Afraid", strict = setStrict(true))
        val json = getJsonFromPath("/search?q=Not+Afraid&strict=on")
        assertEquals(293, result.total)
        assertEquals(25, result.data.size)
        assertNotNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun fetch_search_with_order() = runTest {
        val result = client.searches.search("Not Afraid", order = SearchOrder.TRACK_ASC)
        val json = getJsonFromPath("/search?q=Not+Afraid&order=TRACK_ASC")
        assertEquals(293, result.total)
        assertEquals(25, result.data.size)
        assertNotNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun fetch_search_with_multiple_params() = runTest {
        val result = client.searches.search(buildAdvanceQuery("Not Afraid", artist = "eminem"), limit = 15, index = 10)
        val json = getJsonFromPath("/search?q=%22Not+Afraid%22+artist%3A%22eminem%22&index=10&limit=15")
        assertEquals(15, result.data.size)
        assertEquals(225, result.total)
        assertNotNull(result.next)
        assertNotNull(result.prev)
        assertNull(result.checksum)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun fetch_search_album_with_multiple_params() = runTest {
        val result =
            client.searches.searchAlbum(buildAdvanceQuery("King", artist = "eminem"), order = SearchOrder.RATING_DESC)
        val json = getJsonFromPath("/search/album?q=%22King%22+artist%3A%22eminem%22&order=RATING_DESC")
        assertEquals(25, result.data.size)
        assertEquals(266, result.total)
        assertNotNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun fetch_search_artist_with_multiple_params() = runTest {
        val result = client.searches.searchArtist("cat", order = SearchOrder.ARTIST_DESC)
        val json = getJsonFromPath("/search/artist?q=cat&order=ARTIST_DESC")
        assertEquals(25, result.data.size)
        assertEquals(119, result.total)
        assertNotNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun fetch_search_playlist_with_multiple_params() = runTest {
        val result = client.searches.searchPlaylist("eminem", order = SearchOrder.RANKING)
        val json = getJsonFromPath("/search/playlist?q=eminem&order=RANKING")
        assertEquals(25, result.data.size)
        assertEquals(300, result.total)
        assertNotNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        json shouldEqualJson jsonSerializer.encodeToString(result)
            .replace("\"creator\":", "\"user\":")
            .replace("2024-05-28 16:49", "2024-05-28 16:49:00")
    }

    @Test
    fun fetch_search_podcast_with_multiple_params() = runTest {
        val result = client.searches.searchPodcast("eminem", order = SearchOrder.RATING_ASC)
        val json = getJsonFromPath("/search/podcast?q=eminem&order=RATING_ASC")
        assertEquals(10, result.data.size)
        assertEquals(10, result.total)
        assertNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun fetch_search_radio() = runTest {
        val result = client.searches.searchRadio("Electro", order = SearchOrder.DURATION_ASC)
        val json = getJsonFromPath("/search/radio?q=Electro&order=DURATION_ASC")
        assertEquals(3, result.data.size)
        assertEquals(3, result.total)
        assertNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun fetch_search_track_with_multiple_params() = runTest {
        val result = client.searches.searchTrack("eminem", order = SearchOrder.DURATION_DESC)
        val json = getJsonFromPath("/search/track?q=eminem&order=DURATION_DESC")
        assertEquals(25, result.data.size)
        assertEquals(208, result.total)
        assertNotNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun fetch_search_user_with_multiple_params() = runTest {
        val result = client.searches.searchUser("eminem", order = SearchOrder.RANKING)
        val json = getJsonFromPath("/search/user?q=eminem&order=RANKING")
        assertEquals(25, result.data.size)
        assertEquals(54, result.total)
        assertNotNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }
}
