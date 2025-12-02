package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.KtorEngineMocked
import io.github.kingg22.deezer.client.KtorEngineMocked.getJsonFromPath
import io.github.kingg22.deezer.client.KtorEngineMocked.jsonSerializer
import io.github.kingg22.deezer.client.api.DeezerApiClient
import io.github.kingg22.deezer.client.api.objects.Artist
import io.github.kingg22.deezer.client.api.objects.Track
import io.kotest.assertions.json.shouldEqualJson
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

class TrackRoutesTest {
    private lateinit var client: DeezerApiClient

    @BeforeTest
    fun setup() {
        client = DeezerApiClient(KtorEngineMocked.createHttpClientMock())
    }

    @Test
    fun fetch_track_by_id() = runTest {
        val result = client.tracks.getById(3135556)
        val json = getJsonFromPath("/track/3135556")
        assertEquals(3135556, result.id)
        assertNotNull(result.contributors)
        assertNotNull(result.availableCountries)
        assertEquals(209, result.availableCountries.size)
        assertNotNull(result.album)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun fetch_track_by_ISRC() = runTest {
        val result = client.tracks.getByIsrc("GBDUW0000061")
        val json = getJsonFromPath("/track/isrc:GBDUW0000061")
        assertEquals("GBDUW0000061", result.isrc)
        assertEquals(3135558, result.id)
        assertNotNull(result.contributors)
        assertNotNull(result.availableCountries)
        assertEquals(209, result.availableCountries.size)
        assertNotNull(result.album)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun reload_track() = runTest {
        val tested = Track(
            3135556,
            title = "",
            titleShort = "",
            duration = 0,
            rank = 0,
            isExplicitLyrics = false,
            preview = "",
            artist = Artist(0, ""),
        )
        val track = tested.reload(client)
        val json = getJsonFromPath("/track/3135556")
        assertNotEquals(tested, track)
        json shouldEqualJson jsonSerializer.encodeToString(track)
    }
}
