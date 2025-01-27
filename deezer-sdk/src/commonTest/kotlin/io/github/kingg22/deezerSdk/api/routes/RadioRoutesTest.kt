package io.github.kingg22.deezerSdk.api.routes

import io.github.kingg22.deezerSdk.api.DeezerApiClientTest.Companion.client
import io.github.kingg22.deezerSdk.api.KtorEngineMocked.getJsonFromPath
import io.github.kingg22.deezerSdk.api.KtorEngineMocked.jsonSerializer
import io.github.kingg22.deezerSdk.api.objects.Radio
import io.kotest.assertions.json.shouldEqualJson
import kotlinx.coroutines.test.runTest
import kotlin.test.*

class RadioRoutesTest {
    @Test
    fun `Fetch Radio`() = runTest {
        val result = client.radios.getAll()
        val json = getJsonFromPath("/radio")
        assertEquals(139, result.data.size)
        assertNull(result.prev)
        assertNull(result.next)
        assertNull(result.checksum)
        assertNull(result.total)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun `Fetch Radio by ID`() = runTest {
        val result = client.radios.getById(1236)
        val json = getJsonFromPath("/radio/1236")
        assertEquals(1236, result.id)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun `Reload Radio`() = runTest {
        val tested = Radio(1236, "")
        val radio = tested.reload()
        val json = getJsonFromPath("/radio/1236")
        assertNotEquals(tested, radio)
        json shouldEqualJson jsonSerializer.encodeToString(radio)
    }

    @Test
    fun `Fetch Radio split by Genre`() = runTest {
        val result = client.radios.getAllSplitInGenres()
        val json = getJsonFromPath("/radio/genres")
        assertEquals(20, result.data.size)
        assertNull(result.prev)
        assertNull(result.next)
        assertNull(result.checksum)
        assertNull(result.total)
        assertNotNull(result.data.first().radios)
        // Genre has different to a result type
        json shouldEqualJson jsonSerializer.encodeToString(result)
            .replace("\"name\":", "\"title\":")
            .replace("\"type\": \"genre\",", "")
    }

    @Test
    fun `Fetch Radio Top`() = runTest {
        val result = client.radios.getTop()
        val json = getJsonFromPath("/radio/top")
        assertEquals(9, result.data.size)
        assertEquals(9, result.total)
        assertNull(result.prev)
        assertNull(result.checksum)
        assertNull(result.next)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun `Fetch Radio Tracks`() = runTest {
        val result = client.radios.getTracks(31061)
        val json = getJsonFromPath("/radio/31061/tracks")
        assertEquals(25, result.data.size)
        assertNull(result.prev)
        assertNull(result.next)
        assertNull(result.checksum)
        assertNull(result.total)
        assertNotNull(result.data.first().artist)
        assertNotNull(result.data.first().album)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun `Fetch Radio Lists`() = runTest {
        val result = client.radios.getLists()
        val json = getJsonFromPath("/radio/lists")
        assertEquals(25, result.data.size)
        assertEquals(76, result.total)
        assertNotNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }
}
