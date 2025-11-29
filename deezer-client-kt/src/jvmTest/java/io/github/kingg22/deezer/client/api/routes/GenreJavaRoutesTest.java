package io.github.kingg22.deezer.client.api.routes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import io.github.kingg22.deezer.client.KtorEngineMocked;
import io.github.kingg22.deezer.client.api.DeezerApiJavaClient;
import io.github.kingg22.deezer.client.api.objects.Artist;
import io.github.kingg22.deezer.client.api.objects.Genre;
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse;
import io.github.kingg22.deezer.client.api.objects.Podcast;
import io.github.kingg22.deezer.client.api.objects.Radio;

class GenreJavaRoutesTest {
    private DeezerApiJavaClient client;

    @BeforeEach
    void setup() {
        client = new DeezerApiJavaClient(KtorEngineMocked.createHttpClientMock());
    }

    @Test
    void testFetchGenres() throws Exception {
        final List<PaginatedResponse<Genre>> results = new ArrayList<>(2);
        results.add(client.genres.getAll());
        results.add(client.genres.getAllFuture().get());

        for (final PaginatedResponse<Genre> result : results) {
            assertNull(result.getNext());
            assertNull(result.getPrev());
            assertNull(result.getChecksum());
            assertNull(result.getTotal());
        }
    }

    @Test
    void testFetchGenreById() throws Exception {
        final List<Genre> results = new ArrayList<>(2);
        results.add(client.genres.getById(12));
        results.add(client.genres.getByIdFuture(12).get());

        for (final Genre result : results) {
            assertEquals(12, result.getId());
            assertEquals("Música árabe", result.getName());
        }
    }

    @Test
    void testFetchGenreArtists() throws Exception {
        final List<PaginatedResponse<Artist>> results = new ArrayList<>(2);
        results.add(client.genres.getArtists());
        results.add(client.genres.getArtistsFuture().get());

        for (final PaginatedResponse<Artist> result : results) {
            assertEquals(49, result.getData().size());
            assertNull(result.getNext());
            assertNull(result.getPrev());
            assertNull(result.getTotal());
            assertNull(result.getChecksum());
        }
    }

    @Test
    void testFetchGenrePodcastsEmpty() throws Exception {
        final List<PaginatedResponse<Podcast>> results = new ArrayList<>(2);
        results.add(client.genres.getPodcasts());
        results.add(client.genres.getPodcastsFuture().get());

        for (final PaginatedResponse<Podcast> result : results) {
            assertTrue(result.getData().isEmpty());
            assertEquals(0, result.getTotal());
            assertNull(result.getNext());
            assertNull(result.getPrev());
            assertNull(result.getChecksum());
        }
    }

    @Test
    void testFetchGenreRadios() throws Exception {
        final List<PaginatedResponse<Radio>> results = new ArrayList<>(2);
        results.add(client.genres.getRadios());
        results.add(client.genres.getRadiosFuture().get());

        for (final PaginatedResponse<Radio> result : results) {
            assertEquals(2, result.getData().size());
            assertNull(result.getNext());
            assertNull(result.getPrev());
            assertNull(result.getTotal());
            assertNull(result.getChecksum());
        }
    }
}

