package io.github.kingg22.deezerSdk.api

import io.github.kingg22.deezerSdk.api.KtorEngineMocked.getJsonFromPath
import io.github.kingg22.deezerSdk.api.KtorEngineMocked.jsonSerializer
import io.github.kingg22.deezerSdk.api.objects.Episode
import io.github.kingg22.deezerSdk.api.objects.Infos
import io.github.kingg22.deezerSdk.exceptions.DeezerSdkException
import io.github.kingg22.deezerSdk.utils.HttpClientBuilder.Companion.httpClientBuilder
import io.kotest.assertions.json.shouldEqualJson
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import kotlin.test.*

class DeezerApiClientTest {
    companion object {
        val client = DeezerApiClient.initialize(
            httpClientBuilder {
                httpEngine(KtorEngineMocked.createMockEngine())
            },
        )
    }

    @Test
    fun `If API response boolean throw exception before deserialize`() = runTest {
        assertFailsWith(DeezerSdkException::class) { client.playlists.getRadio(908622995) }
    }

    @Test
    fun `If API response a error body in 2xx status code throw exception`() = runTest {
        assertFailsWith(DeezerSdkException::class) { client.users.getById(0) }
    }

    /* -- Episode -- */
    @Test
    fun `Fetch Episode by ID`() = runTest {
        val result = client.episodes.getById(526673645)
        val json = getJsonFromPath("/episode/526673645")
        assertEquals(526673645, result.id)
        assertNotNull(result.podcast)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun `Reload Episode`() = runTest {
        val tested = Episode(
            526673645,
            "",
            releaseDate = LocalDateTime.parse("2019-09-09T00:00:00"),
            duration = 0,
        )
        val episode = tested.reload()
        val json = getJsonFromPath("/episode/526673645")
        assertNotEquals(tested, episode)
        json shouldEqualJson jsonSerializer.encodeToString(episode)
    }

    /* -- Infos -- */
    @Test
    fun `Fetch Deezer Infos`() = runTest {
        val result = client.infos.getInfos()
        val json = getJsonFromPath("/infos")
        assertEquals("PA", result.countryIso)
        assertEquals("Panamá", result.country)
        assertTrue(result.open)
        assertEquals("fr", result.pop)
        assertEquals("de98277b24d792625bacfc16bae605d1", result.uploadToken)
        assertEquals(14400, result.uploadTokenLifetime)
        assertNull(result.userToken)
        assertEquals(
            Infos.HostDetails(
                "http://e-cdn-proxy-{0}.deezer.com/mobile/1/",
                "http://cdn-images.dzcdn.net/images",
            ),
            result.hosts,
        )
        assertNotNull(result.ads)
        assertEquals(true, result.hasPodcast)
        assertNotNull(result.offers)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    /* -- Options -- */
    @Test
    fun `Fetch Deezer Options`() = runTest {
        val result = client.options.getOptions()
        assertFalse { result.streaming }
        assertEquals(0, result.streamingDuration)
        assertFalse { result.offline }
        assertFalse { result.hq }
        assertTrue { result.adsDisplay }
        assertTrue { result.adsAudio }
        assertFalse { result.tooManyDevices }
        assertFalse { result.canSubscribe }
        assertEquals(6, result.radioSkips)
        assertFalse { result.lossless }
        assertTrue { result.preview }
        assertTrue { result.radio }
        assertEquals("options", result.type)
    }
}
