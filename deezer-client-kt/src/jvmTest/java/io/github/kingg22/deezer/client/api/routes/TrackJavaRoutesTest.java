package io.github.kingg22.deezer.client.api.routes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import io.github.kingg22.deezer.client.KtorEngineMocked;
import io.github.kingg22.deezer.client.api.DeezerApiJavaClient;
import io.github.kingg22.deezer.client.api.objects.Track;

class TrackJavaRoutesTest {
    private DeezerApiJavaClient client;

    @BeforeEach
    void setup() {
        client = new DeezerApiJavaClient(KtorEngineMocked.createHttpBuilderMock());
    }

    @Test
    void testFetchTrackById() {
        final List<Track> results = new ArrayList<>(2);
        results.add(client.tracks.getById(3135556));
        results.add(assertTimeout(Duration.ofMinutes(1), () -> client.tracks.getByIdFuture(3135556).get()));
        for (final Track result : results) {
            assertEquals(3135556, result.getId());
            assertNotNull(result.getContributors());
            assertNotNull(result.getAvailableCountries());
            assertEquals(209, result.getAvailableCountries().size());
            assertNotNull(result.getAlbum());
        }
    }

    @Test
    void testFetchTrackByISRC() {
        final List<Track> results = new ArrayList<>(2);
        results.add(client.tracks.getByIsrc("GBDUW0000061"));
        results.add(assertTimeout(Duration.ofMinutes(1), () -> client.tracks.getByIsrcFuture("GBDUW0000061").get()));
        for (final Track result : results) {
            assertEquals("GBDUW0000061", result.getIsrc());
            assertEquals(3135558, result.getId());
            assertNotNull(result.getContributors());
            assertNotNull(result.getAvailableCountries());
            assertEquals(209, result.getAvailableCountries().size());
            assertNotNull(result.getAlbum());
        }
    }
}
