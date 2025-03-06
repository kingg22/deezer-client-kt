package io.github.kingg22.deezerSdk.api.routes

import io.github.kingg22.deezerSdk.KtorEngineMocked.getJsonFromPath
import io.github.kingg22.deezerSdk.KtorEngineMocked.jsonSerializer
import io.github.kingg22.deezerSdk.api.DeezerApiClientTest.Companion.client
import io.github.kingg22.deezerSdk.api.objects.Playlist
import io.github.kingg22.deezerSdk.api.objects.User
import io.kotest.assertions.json.shouldEqualJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class PlaylistRoutesTest {
    @Test
    fun `Fetch Playlist by ID`() = runTest {
        val result = client.playlists.getById(908622995)
        val json = getJsonFromPath("/playlist/908622995")
        assertEquals(908622995, result.id)
        assertNotNull(result.creator)
        assertNotNull(result.tracks)
        assertEquals(50, result.tracks.data.size)
        assertNotNull(result.tracks.checksum)
        assertNull(result.tracks.next)
        assertNull(result.tracks.prev)
        assertNull(result.tracks.total)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun `Reload Playlist`() = runTest {
        val tested = Playlist(
            908622995,
            "",
            public = false,
            link = "",
            creator = User(0, ""),
        )
        val playlist = tested.reload()
        val json = getJsonFromPath("/playlist/908622995")
        assertNotEquals(tested, playlist)
        json shouldEqualJson jsonSerializer.encodeToString(playlist)
    }

    @Test
    fun `Fetch Playlist Fans`() = runTest {
        val result = client.playlists.getFans(4341978)
        val json = getJsonFromPath("/playlist/4341978/fans")
        assertEquals(1, result.total)
        assertEquals(1, result.data.size)
        assertNull(result.prev)
        assertNull(result.next)
        assertNull(result.checksum)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun `Fetch Playlist Tracks`() = runTest {
        val result = client.playlists.getTracks(908622995)
        val json = getJsonFromPath("/playlist/908622995/tracks")
        assertEquals(25, result.data.size)
        assertEquals(50, result.total)
        assertNotNull(result.checksum)
        assertNotNull(result.next)
        assertNull(result.prev)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    // TODO find result with data
    @Ignore // exception tested in Deezer Api client
    @Test
    fun `Fetch Playlist Radio`() = runTest {
        val result = client.playlists.getRadio(908622995)
        val json = getJsonFromPath("/playlist/908622995/radio")
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }
}
