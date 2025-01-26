package io.github.kingg22.deezerSdk.api.routes

import io.github.kingg22.deezerSdk.api.DeezerApiClientTest.Companion.client
import io.github.kingg22.deezerSdk.api.KtorEngineMocked.getJsonFromPath
import io.github.kingg22.deezerSdk.api.KtorEngineMocked.jsonSerializer
import io.kotest.assertions.json.shouldEqualJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class GenreRoutesTest {
    @Test
    fun `Fetch Genres`() = runTest {
        val result = client.genres.getAll()
        val json = getJsonFromPath("/genre")
        assertNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        assertNull(result.total)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun `Fetch Genre by ID`() = runTest {
        val result = client.genres.getById(12)
        val json = getJsonFromPath("/genre/12")
        assertEquals(12, result.id)
        assertEquals("Música árabe", result.name)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun `Fetch Genre Artists`() = runTest {
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
    fun `Fetch Genre Podcasts Empty`() = runTest {
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
    fun `Fetch Genre Radios`() = runTest {
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
