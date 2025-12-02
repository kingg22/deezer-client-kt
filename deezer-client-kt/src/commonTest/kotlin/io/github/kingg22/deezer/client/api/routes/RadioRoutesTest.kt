package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.KtorEngineMocked
import io.github.kingg22.deezer.client.KtorEngineMocked.getJsonFromPath
import io.github.kingg22.deezer.client.KtorEngineMocked.jsonSerializer
import io.github.kingg22.deezer.client.api.DeezerApiClient
import io.github.kingg22.deezer.client.api.objects.Radio
import io.kotest.assertions.json.shouldEqualJson
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class RadioRoutesTest {
    private lateinit var client: DeezerApiClient

    @BeforeTest
    fun setup() {
        client = DeezerApiClient(KtorEngineMocked.createHttpClientMock())
    }

    @Test
    fun fetch_radio() = runTest {
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
    fun fetch_radio_by_id() = runTest {
        val result = client.radios.getById(1236)
        val json = getJsonFromPath("/radio/1236")
        assertEquals(1236, result.id)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun reload_radio() = runTest {
        val tested = Radio(1236, "")
        val radio = tested.reload(client)
        val json = getJsonFromPath("/radio/1236")
        assertNotEquals(tested, radio)
        json shouldEqualJson jsonSerializer.encodeToString(radio)
    }

    @Test
    fun fetch_radio_split_by_genre() = runTest {
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
    fun fetch_radio_top() = runTest {
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
    fun fetch_radio_tracks() = runTest {
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
    fun fetch_radio_lists() = runTest {
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
