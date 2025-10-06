package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.KtorEngineMocked
import io.github.kingg22.deezer.client.KtorEngineMocked.getJsonFromPath
import io.github.kingg22.deezer.client.KtorEngineMocked.jsonSerializer
import io.github.kingg22.deezer.client.api.DeezerApiClient
import io.github.kingg22.deezer.client.api.objects.Artist
import io.github.kingg22.deezer.client.api.objects.User
import io.kotest.assertions.json.shouldEqualJson
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ArtistRoutesTest {
    lateinit var client: DeezerApiClient

    @BeforeTest
    fun setup() {
        client = DeezerApiClient.initialize(KtorEngineMocked.createHttpBuilderMock())
    }

    @Test
    fun fetch_artist_by_id() = runTest {
        val result = client.artists.getById(27)
        val json = getJsonFromPath("/artist/27")
        assertEquals(27, result.id)
        assertEquals("Daft Punk", result.name)
        assertEquals("https://www.deezer.com/artist/27", result.link)
        assertEquals(36, result.albumCount)
        assertEquals(4802079, result.fansCount)
        assertEquals(true, result.isRadio)
        assertEquals("https://api.deezer.com/artist/27/top?limit=50", result.tracklist)
        assertEquals("artist", result.type)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun reload_artist() = runTest {
        val artist = Artist(27, "Daft Punk")
        val newArtist = artist.reload()
        val json = getJsonFromPath("/artist/27")
        assertNotEquals(artist, newArtist)
        json shouldEqualJson jsonSerializer.encodeToString(newArtist)
    }

    @Test
    fun fetch_artist_fans() = runTest {
        val result = client.artists.getFans(27)
        val json = getJsonFromPath("/artist/27/fans")
        assertEquals(50, result.data.size)
        assertEquals(250, result.total)
        assertEquals("https://api.deezer.com/artist/27/fans?index=50", result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        assertEquals(
            User(
                5,
                "Daniel Marhely",
                link = "https://www.deezer.com/profile/5",
                picture = "https://api.deezer.com/user/5/image",
                type = "user",
            ),
            result.data.first(),
        )
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun fetch_artist_top() = runTest {
        val result = client.artists.getTopTracks(27)
        val json = getJsonFromPath("/artist/27/top")
        assertEquals(5, result.data.size)
        assertEquals(51, result.total)
        assertEquals("https://api.deezer.com/artist/27/top?index=5", result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        assertEquals(1, result.data.first().contributors?.size)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun fetch_artist_albums() = runTest {
        val result = client.artists.getAlbums(27)
        val json = getJsonFromPath("/artist/27/albums")
        assertEquals(25, result.data.size)
        assertEquals(36, result.total)
        assertEquals("https://api.deezer.com/artist/27/albums?index=25", result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun fetch_artist_radio() = runTest {
        val result = client.artists.getRadio(27)
        val json = getJsonFromPath("/artist/27/radio")
        assertEquals(25, result.data.size)
        assertNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        assertNull(result.total)
        assertNotNull(result.data.first().artist)
        assertNotNull(result.data.first().album)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun fetch_artist_playlists() = runTest {
        val result = client.artists.getPlaylists(27)
        val json = getJsonFromPath("/artist/27/playlists")
        assertEquals(10, result.data.size)
        assertEquals(100, result.total)
        assertEquals("https://api.deezer.com/artist/27/playlists?index=10", result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        assertNotNull(result.data.first().creator)
        json shouldEqualJson jsonSerializer.encodeToString(result).replace("\"creator\":", "\"user\":")
    }

    @Test
    fun fetch_artist_related() = runTest {
        val result = client.artists.getRelated(27)
        val json = getJsonFromPath("/artist/27/related")
        assertEquals(20, result.data.size)
        assertEquals(20, result.total)
        assertNull(result.next)
        assertNull(result.prev)
        assertNull(result.checksum)
        assertEquals(
            Artist(
                103, "Pharrell Williams", "https://www.deezer.com/artist/103",
                picture = "https://api.deezer.com/artist/103/image",
                pictureSmall =
                "https://cdn-images.dzcdn.net/images/artist/1267b8781c5bff065a20dca4a3c9fda7/56x56-000000-80-0-0.jpg",
                pictureMedium =
                "https://cdn-images.dzcdn.net/images/artist/1267b8781c5bff065a20dca4a3c9fda7/250x250-000000-80-0-0.jpg",
                pictureBig =
                "https://cdn-images.dzcdn.net/images/artist/1267b8781c5bff065a20dca4a3c9fda7/500x500-000000-80-0-0.jpg",
                pictureXl =
                "https://cdn-images.dzcdn.net/images/artist/1267b8781c5bff065a20dca4a3c9fda7/1000x1000-000000-80-0-0.jpg",
                albumCount = 38, fansCount = 3121845, isRadio = true,
                tracklist = "https://api.deezer.com/artist/103/top?limit=50", type = "artist",
            ),
            result.data.first(),
        )
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }
}
