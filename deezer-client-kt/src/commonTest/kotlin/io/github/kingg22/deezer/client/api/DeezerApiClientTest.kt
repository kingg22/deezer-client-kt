package io.github.kingg22.deezer.client.api

import io.github.kingg22.deezer.client.KtorEngineMocked
import io.github.kingg22.deezer.client.KtorEngineMocked.getJsonFromPath
import io.github.kingg22.deezer.client.KtorEngineMocked.jsonSerializer
import io.github.kingg22.deezer.client.api.objects.Episode
import io.github.kingg22.deezer.client.api.objects.Infos
import io.github.kingg22.deezer.client.exceptions.DeezerApiException
import io.kotest.assertions.json.shouldEqualJson
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDateTime
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class DeezerApiClientTest {
    lateinit var client: DeezerApiClient

    @BeforeTest
    fun setup() {
        client = DeezerApiClient.initialize(KtorEngineMocked.createHttpBuilderMock())
    }

    @Test
    fun test_when_api_response_boolean_throw_exception_before_deserialize() = runTest {
        assertFailsWith<DeezerApiException> { client.playlists.getRadio(908622995) }
    }

    @Test
    fun test_when_api_response_a_error_body_in_2xx_status_code_throw_exception() = runTest {
        assertFailsWith<DeezerApiException> { client.users.getById(0) }
    }

    /* -- Episode -- */
    @Test
    fun fetch_episode_by_id() = runTest {
        val result = client.episodes.getById(526673645)
        val json = getJsonFromPath("/episode/526673645")
        assertEquals(526673645, result.id)
        assertNotNull(result.podcast)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun reload_episode() = runTest {
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
    fun fetch_deezer_infos() = runTest {
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
    fun fetch_deezer_options() = runTest {
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
