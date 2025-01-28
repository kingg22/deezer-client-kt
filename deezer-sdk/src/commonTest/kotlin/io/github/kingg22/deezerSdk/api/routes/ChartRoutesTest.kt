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

// NOTE: Chart use playlist.user and not creator
class ChartRoutesTest {
    @Test
    fun `Fetch Charts`() = runTest {
        val result = client.charts.getAll()
        val json = getJsonFromPath("/chart")
        assertEquals(10, result.tracks.data.size)
        assertEquals(10, result.tracks.total)

        assertEquals(9, result.albums.data.size)
        assertEquals(9, result.albums.total)

        assertEquals(10, result.artists.data.size)
        assertEquals(10, result.artists.total)

        assertEquals(10, result.playlists.data.size)
        assertEquals(10, result.playlists.total)

        assertEquals(10, result.podcasts.data.size)
        assertEquals(10, result.podcasts.total)

        json shouldEqualJson jsonSerializer.encodeToString(result).replace("\"creator\":", "\"user\":")
    }

    @Test
    fun `Fetch Chart by ID`() = runTest {
        val result = client.charts.getById(2)
        val json = getJsonFromPath("/chart/2")
        assertEquals(10, result.tracks.data.size)
        assertEquals(10, result.tracks.total)

        assertTrue(result.albums.data.isEmpty())
        assertEquals(0, result.albums.total)

        assertEquals(10, result.artists.data.size)
        assertEquals(10, result.artists.total)

        assertEquals(8, result.playlists.data.size)
        assertEquals(8, result.playlists.total)

        assertTrue(result.podcasts.data.isEmpty())
        assertEquals(0, result.podcasts.total)

        json shouldEqualJson jsonSerializer.encodeToString(result).replace("\"creator\":", "\"user\":")
    }

    @Test
    fun `Fetch Chart Tracks`() = runTest {
        val result = client.charts.getTracks()
        val json = getJsonFromPath("/chart/0/tracks")
        json shouldEqualJson jsonSerializer.encodeToString(result).replace("\"creator\":", "\"user\":")
    }

    @Test
    fun `Fetch Chart Albums`() = runTest {
        val result = client.charts.getAlbums()
        val json = getJsonFromPath("/chart/0/albums")
        assertEquals(9, result.total)
        assertEquals(9, result.data.size)
        assertNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun `Fetch Chart Artists`() = runTest {
        val result = client.charts.getArtists()
        val json = getJsonFromPath("/chart/0/artists")
        assertEquals(10, result.total)
        assertEquals(10, result.data.size)
        assertNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun `Fetch Chart Playlists`() = runTest {
        val result = client.charts.getPlaylists()
        val json = getJsonFromPath("/chart/0/playlists")
        assertEquals(10, result.total)
        assertEquals(10, result.data.size)
        assertNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        json shouldEqualJson jsonSerializer.encodeToString(result).replace("\"creator\":", "\"user\":")
    }

    @Test
    fun `Fetch Chart Podcast`() = runTest {
        val result = client.charts.getPodcasts()
        val json = getJsonFromPath("/chart/0/podcasts")
        assertEquals(10, result.total)
        assertEquals(10, result.data.size)
        assertNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }
}
