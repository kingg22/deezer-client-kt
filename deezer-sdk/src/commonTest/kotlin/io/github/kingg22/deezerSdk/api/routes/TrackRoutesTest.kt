package io.github.kingg22.deezerSdk.api.routes

import io.github.kingg22.deezerSdk.api.DeezerApiClientTest.Companion.client
import io.github.kingg22.deezerSdk.api.KtorEngineMocked.getJsonFromPath
import io.github.kingg22.deezerSdk.api.KtorEngineMocked.jsonSerializer
import io.github.kingg22.deezerSdk.api.objects.Artist
import io.github.kingg22.deezerSdk.api.objects.Track
import io.kotest.assertions.json.shouldEqualJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

class TrackRoutesTest {
    @Test
    fun `Fetch Track by ID`() = runTest {
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
    fun `Fetch Track by ISRC`() = runTest {
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
    fun `Reload Track`() = runTest {
        val tested = Track(
            3135556,
            title = "",
            titleShort = "",
            duration = 0,
            rank = 0,
            explicitLyrics = false,
            preview = "",
            artist = Artist(0, ""),
        )
        val track = tested.reload()
        val json = getJsonFromPath("/track/3135556")
        assertNotEquals(tested, track)
        json shouldEqualJson jsonSerializer.encodeToString(track)
    }
}
