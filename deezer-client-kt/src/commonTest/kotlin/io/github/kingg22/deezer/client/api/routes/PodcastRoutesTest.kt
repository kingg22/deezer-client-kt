package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.KtorEngineMocked.getJsonFromPath
import io.github.kingg22.deezer.client.KtorEngineMocked.jsonSerializer
import io.github.kingg22.deezer.client.api.DeezerApiClientTest.Companion.client
import io.github.kingg22.deezer.client.api.objects.Podcast
import io.kotest.assertions.json.shouldEqualJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class PodcastRoutesTest {
    // This endpoint always returns empty?
    @Test
    fun `Fetch Podcast`() = runTest {
        val result = client.podcasts.getAll()
        val json = getJsonFromPath("/podcast")
        assertTrue(result.data.isEmpty())
        assertNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        assertNull(result.total)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun `Fetch Podcast by ID`() = runTest {
        val result = client.podcasts.getById(20269)
        val json = getJsonFromPath("/podcast/20269")
        assertEquals(20269, result.id)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun `Reload Podcast`() = runTest {
        val tested = Podcast(20269)
        val podcast = tested.reload()
        val json = getJsonFromPath("/podcast/20269")
        assertNotEquals(tested, podcast)
        json shouldEqualJson jsonSerializer.encodeToString(podcast)
    }

    @Test
    fun `Fetch Podcast Episodes Empty`() = runTest {
        val result = client.podcasts.getEpisodes(20269)
        val json = getJsonFromPath("/podcast/20269/episodes")
        assertTrue(result.data.isEmpty())
        assertNotNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        assertEquals(44, result.total)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun `Fetch Podcast Episodes`() = runTest {
        val result = client.podcasts.getEpisodes(20289)
        val json = getJsonFromPath("/podcast/20289/episodes")
        assertEquals(25, result.data.size)
        assertNotNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        assertEquals(59, result.total)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }
}
