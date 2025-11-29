package io.github.kingg22.deezer.client.api.routes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import io.github.kingg22.deezer.client.KtorEngineMocked;
import io.github.kingg22.deezer.client.api.DeezerApiJavaClient;
import io.github.kingg22.deezer.client.api.objects.Episode;
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse;
import io.github.kingg22.deezer.client.api.objects.Podcast;

class PodcastJavaRoutesTest {

    private DeezerApiJavaClient client;

    @BeforeEach
    void setup() {
        client = new DeezerApiJavaClient(KtorEngineMocked.createHttpClientMock());
    }

    @Test
    void testFetchPodcastsEmpty() throws Exception {
        final List<PaginatedResponse<Podcast>> results = Arrays.asList(
            client.podcasts.getAll(),
            client.podcasts.getAllFuture().get()
        );

        for (final PaginatedResponse<Podcast> result : results) {
            assertTrue(result.getData().isEmpty());
            assertNull(result.getNext());
            assertNull(result.getPrev());
            assertNull(result.getChecksum());
            assertNull(result.getTotal());
        }
    }

    @Test
    void testFetchPodcastById() throws Exception {
        final List<Podcast> results = Arrays.asList(
            client.podcasts.getById(20269),
            client.podcasts.getByIdFuture(20269).get()
        );

        for (final Podcast result : results) {
            assertEquals(20269, result.getId());
        }
    }

    @Test
    void testFetchPodcastEpisodesEmpty() throws Exception {
        final List<PaginatedResponse<Episode>> results = Arrays.asList(
            client.podcasts.getEpisodes(20269),
            client.podcasts.getEpisodesFuture(20269).get()
        );

        for (final PaginatedResponse<Episode> result : results) {
            assertTrue(result.getData().isEmpty());
            assertEquals(44, result.getTotal());
            assertNotNull(result.getNext());
            assertNull(result.getPrev());
            assertNull(result.getChecksum());
        }
    }

    @Test
    void testFetchPodcastEpisodes() throws Exception {
        final List<PaginatedResponse<Episode>> results = Arrays.asList(
            client.podcasts.getEpisodes(20289),
            client.podcasts.getEpisodesFuture(20289).get()
        );

        for (final PaginatedResponse<Episode> result : results) {
            assertEquals(25, result.getData().size());
            assertEquals(59, result.getTotal());
            assertNotNull(result.getNext());
            assertNull(result.getPrev());
            assertNull(result.getChecksum());
        }
    }
}
