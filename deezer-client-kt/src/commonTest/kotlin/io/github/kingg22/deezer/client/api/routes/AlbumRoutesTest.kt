package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.KtorEngineMocked.getJsonFromPath
import io.github.kingg22.deezer.client.KtorEngineMocked.jsonSerializer
import io.github.kingg22.deezer.client.api.DeezerApiClientTest.Companion.client
import io.github.kingg22.deezer.client.api.objects.Album
import io.github.kingg22.deezer.client.api.objects.User
import io.kotest.assertions.json.shouldEqualJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class AlbumRoutesTest {
    @Test
    fun `Fetch Album by ID`() = runTest {
        val result = client.albums.getById(302127)
        val json = getJsonFromPath("/album/302127")
        assertEquals(302127, result.id)
        assertEquals("Discovery", result.title)
        assertEquals("724384960650", result.upc)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun `Reload Album`() = runTest {
        val album = Album(
            id = 302127,
            title = "Discovery",
        )
        val newAlbum = album.reload()
        val json = getJsonFromPath("/album/302127")
        assertNotEquals(album, newAlbum)
        json shouldEqualJson jsonSerializer.encodeToString(newAlbum)
    }

    @Test
    fun `Fetch Album Fans`() = runTest {
        val result = client.albums.getFans(302127)
        val json = getJsonFromPath("/album/302127/fans")
        assertEquals(50, result.data.size)
        assertEquals(100, result.total)
        assertEquals("https://api.deezer.com/album/302127/fans?index=50", result.next)
        assertEquals(
            User(
                54,
                "NancyLovesPara",
                link = "https://www.deezer.com/profile/544",
                picture = "https://api.deezer.com/user/544/image",
                type = "user",
            ),
            result.data.first(),
        )
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun `Fetch Album Tracks`() = runTest {
        val result = client.albums.getTracks(302127)
        val json = getJsonFromPath("/album/302127/tracks")
        assertEquals(14, result.data.size)
        assertEquals(14, result.total)
        assertNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        assertNotNull(result.data.first().artist)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun `Fetch Album by UPC`() = runTest {
        val result = client.albums.getByUpc("196589221285")
        val json = getJsonFromPath("/album/upc:196589221285")
        assertEquals(327983357, result.id)
        assertEquals("196589221285", result.upc)
        assertEquals("329d27f2437976090d4a150cb4da4348", result.md5Image)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }
}
