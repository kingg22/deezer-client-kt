package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.KtorEngineMocked
import io.github.kingg22.deezer.client.KtorEngineMocked.getJsonFromPath
import io.github.kingg22.deezer.client.KtorEngineMocked.jsonSerializer
import io.github.kingg22.deezer.client.api.DeezerApiClient
import io.github.kingg22.deezer.client.api.objects.Genre
import io.kotest.assertions.json.shouldEqualJson
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GenreRoutesTest {
    private lateinit var client: DeezerApiClient

    @BeforeTest
    fun setup() {
        client = DeezerApiClient(KtorEngineMocked.createHttpClientMock())
    }

    @Test
    fun fetch_genres() = runTest {
        val result = client.genres.getAll()
        val json = getJsonFromPath("/genre")
        assertNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        assertNull(result.total)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun fetch_genre_by_id() = runTest {
        val result = client.genres.getById(12)
        val json = getJsonFromPath("/genre/12")
        assertEquals(12, result.id)
        assertEquals("Música árabe", result.name)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun reload_genre() = runTest {
        val tested = Genre(12, "")
        val genre = tested.reload(client)
        val json = getJsonFromPath("/genre/12")
        assertNotEquals(tested, genre)
        json shouldEqualJson jsonSerializer.encodeToString(genre)
    }

    @Test
    fun fetch_genre_artists() = runTest {
        val result = client.genres.getArtists()
        val json = getJsonFromPath("/genre/0/artists")
        assertEquals(49, result.data.size)
        assertNull(result.next)
        assertNull(result.prev)
        assertNull(result.total)
        assertNull(result.checksum)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun fetch_genre_podcasts_empty() = runTest {
        val result = client.genres.getPodcasts()
        val json = getJsonFromPath("/genre/0/podcasts")
        assertTrue(result.data.isEmpty())
        assertEquals(0, result.total)
        assertNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun fetch_genre_radios() = runTest {
        val result = client.genres.getRadios()
        val json = getJsonFromPath("/genre/0/radios")
        assertEquals(2, result.data.size)
        assertNull(result.next)
        assertNull(result.prev)
        assertNull(result.total)
        assertNull(result.checksum)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }
}
