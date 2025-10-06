package io.github.kingg22.deezer.client.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import io.github.kingg22.deezer.client.KtorEngineMocked;
import io.github.kingg22.deezer.client.api.objects.Episode;
import io.github.kingg22.deezer.client.api.objects.Infos;
import io.github.kingg22.deezer.client.api.objects.Options;

class DeezerApiClientJavaTest {
    private DeezerApiJavaClient client;

    @BeforeEach
    void setup() {
        client = DeezerApiJavaClient.initialize(KtorEngineMocked.createHttpBuilderMock());
        assertNotNull(GlobalDeezerApiClient.instance);
    }

    @Test
    void testFetchEpisodeById() {
        final List<Episode> results = new ArrayList<>(2);
        results.add(client.episodes.getById(526673645));
        results.add(assertTimeout(Duration.ofMinutes(1), () -> client.episodes.getByIdFuture(526673645).get()));
        for (final Episode result : results) {
            assertEquals(526673645, result.getId());
            assertNotNull(result.getPodcast());
        }
    }

    @Test
    void testFetchDeezerInfos() {
        final List<Infos> results = new ArrayList<>(2);
        results.add(client.infos.getInfos());
        results.add(assertTimeout(Duration.ofMinutes(1), () -> client.infos.getInfosFuture().get()));
        for (final Infos result : results) {
            assertEquals("PA", result.getCountryIso());
            assertEquals("Panamá", result.getCountry());
            assertTrue(result.isOpen());
            assertEquals("fr", result.getPop());
            assertEquals("de98277b24d792625bacfc16bae605d1", result.getUploadToken());
            assertEquals(14400, result.getUploadTokenLifetime());
            assertNull(result.getUserToken());

            Infos.HostDetails expectedHosts = new Infos.HostDetails(
                "http://e-cdn-proxy-{0}.deezer.com/mobile/1/",
                "http://cdn-images.dzcdn.net/images"
            );
            assertEquals(expectedHosts, result.getHosts());

            assertNotNull(result.getAds());
            assertNotNull(result.getHasPodcast());
            assertTrue(result.getHasPodcast());
            assertNotNull(result.getOffers());
        }
    }

    @Test
    void testFetchDeezerOptions() {
        final List<Options> results = new ArrayList<>(2);
        results.add(client.options.getOptions());
        results.add(assertTimeout(Duration.ofMinutes(1), () -> client.options.getOptionsFuture().get()));
        for (final Options result : results) {
            assertFalse(result.isStreaming());
            assertEquals(0, result.getStreamingDuration());
            assertFalse(result.isOffline());
            assertFalse(result.isHq());
            assertTrue(result.isAdsDisplay());
            assertTrue(result.isAdsAudio());
            assertFalse(result.isTooManyDevices());
            assertFalse(result.isCanSubscribe());
            assertEquals(6, result.getRadioSkips());
            assertFalse(result.isLossless());
            assertTrue(result.isPreview());
            assertTrue(result.isRadio());
            assertEquals("options", result.getType());
        }
    }
}
