package io.github.kingg22.deezerSdk.api.routes

import io.github.kingg22.deezerSdk.KtorEngineMocked.getJsonFromPath
import io.github.kingg22.deezerSdk.KtorEngineMocked.jsonSerializer
import io.github.kingg22.deezerSdk.api.DeezerApiClientTest.Companion.client
import io.github.kingg22.deezerSdk.api.objects.Editorial
import io.kotest.assertions.json.shouldEqualJson
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class EditorialRoutesTest {
    @Test
    fun `Fetch Editorial`() = runTest {
        val result = client.editorials.getAll()
        val json = getJsonFromPath("/editorial")
        assertEquals(25, result.data.size)
        assertEquals(26, result.total)
        assertNotNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun `Fetch Editorial by ID`() = runTest {
        val result = client.editorials.getById(3)
        val json = getJsonFromPath("/editorial/3")
        assertEquals(3, result.id)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun `Reload Editorial`() = runTest {
        val tested = Editorial(3, "")
        val editorial = tested.reload()
        val json = getJsonFromPath("/editorial/3")
        assertNotEquals(tested, editorial)
        json shouldEqualJson jsonSerializer.encodeToString(editorial)
    }

    @Test
    fun `Fetch Editorial Selection`() = runTest {
        val result = client.editorials.getDeezerSelection()
        val json = getJsonFromPath("/editorial/0/selection")
        assertEquals(10, result.data.size)
        assertNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        assertNull(result.total)
        json shouldEqualJson jsonSerializer.encodeToString(result)
            .replace(
                "\"cover\": \"\"",
                "\"cover\": \"\", \"cover_small\": null, \"cover_medium\": null, \"cover_big\": null, \"cover_xl\": null",
            )
    }

    @Test
    fun `Fetch Editorial Charts`() = runTest {
        val result = client.editorials.getCharts()
        val json = getJsonFromPath("/editorial/0/charts")
        assertEquals(10, result.tracks.data.size)
        assertEquals(10, result.tracks.total)
        assertNull(result.tracks.next)
        assertNull(result.tracks.prev)
        assertNull(result.tracks.checksum)

        assertEquals(9, result.albums.data.size)
        assertEquals(9, result.albums.total)
        assertNull(result.albums.next)
        assertNull(result.albums.prev)
        assertNull(result.albums.checksum)

        assertEquals(10, result.artists.data.size)
        assertEquals(10, result.artists.total)
        assertNull(result.artists.next)
        assertNull(result.artists.prev)
        assertNull(result.artists.checksum)

        assertEquals(10, result.playlists.data.size)
        assertEquals(10, result.playlists.total)
        assertNull(result.playlists.next)
        assertNull(result.playlists.prev)
        assertNull(result.playlists.checksum)

        assertEquals(10, result.podcasts.data.size)
        assertEquals(10, result.podcasts.total)
        assertNull(result.podcasts.next)
        assertNull(result.podcasts.prev)
        assertNull(result.podcasts.checksum)

        json shouldEqualJson jsonSerializer.encodeToString(result).replace("\"creator\":", "\"user\":")
    }

    @Test
    fun `Fetch Editorial Releases`() = runTest {
        val result = client.editorials.getReleases()
        val json = getJsonFromPath("/editorial/0/releases")
        assertEquals(20, result.data.size)
        assertEquals(58, result.total)
        assertNotNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }
}
