package io.github.kingg22.deezer.client.api.routes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import io.github.kingg22.deezer.client.KtorEngineMocked;
import io.github.kingg22.deezer.client.api.DeezerApiJavaClient;
import io.github.kingg22.deezer.client.api.objects.Album;
import io.github.kingg22.deezer.client.api.objects.Chart;
import io.github.kingg22.deezer.client.api.objects.Editorial;
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse;

class EditorialJavaRoutesTest {
    private DeezerApiJavaClient client;

    @BeforeEach
    void setup() {
        client = new DeezerApiJavaClient(KtorEngineMocked.createHttpClientMock());
    }

    @Test
    void testFetchEditorials() throws Exception {
        final List<PaginatedResponse<Editorial>> results = new ArrayList<>(2);
        results.add(client.editorials.getAll());
        results.add(client.editorials.getAllFuture().get());

        for (final PaginatedResponse<Editorial> result : results) {
            assertEquals(25, result.getData().size());
            assertEquals(26, result.getTotal());
            assertNotNull(result.getNext());
            assertNull(result.getPrev());
            assertNull(result.getChecksum());
        }
    }

    @Test
    void testFetchEditorialById() throws Exception {
        final List<Editorial> results = new ArrayList<>(2);
        results.add(client.editorials.getById(3));
        results.add(client.editorials.getByIdFuture(3).get());

        for (final Editorial result : results) {
            assertEquals(3, result.getId());
        }
    }

    @Test
    void testFetchEditorialSelection() throws Exception {
        final List<PaginatedResponse<Album>> results = new ArrayList<>(2);
        results.add(client.editorials.getDeezerSelection());
        results.add(client.editorials.getDeezerSelectionFuture().get());

        for (final PaginatedResponse<Album> result : results) {
            assertEquals(10, result.getData().size());
            assertNull(result.getNext());
            assertNull(result.getPrev());
            assertNull(result.getChecksum());
            assertNull(result.getTotal());
        }
    }

    @Test
    void testFetchEditorialCharts() throws Exception {
        final List<Chart> results = new ArrayList<>(2);
        results.add(client.editorials.getCharts());
        results.add(client.editorials.getChartsFuture().get());

        for (final Chart result : results) {
            assertEquals(10, result.getTracks().getData().size());
            assertEquals(10, result.getTracks().getTotal());
            assertNull(result.getTracks().getNext());
            assertNull(result.getTracks().getPrev());
            assertNull(result.getTracks().getChecksum());

            assertEquals(9, result.getAlbums().getData().size());
            assertEquals(9, result.getAlbums().getTotal());
            assertNull(result.getAlbums().getNext());
            assertNull(result.getAlbums().getPrev());
            assertNull(result.getAlbums().getChecksum());

            assertEquals(10, result.getArtists().getData().size());
            assertEquals(10, result.getArtists().getTotal());
            assertNull(result.getArtists().getNext());
            assertNull(result.getArtists().getPrev());
            assertNull(result.getArtists().getChecksum());

            assertEquals(10, result.getPlaylists().getData().size());
            assertEquals(10, result.getPlaylists().getTotal());
            assertNull(result.getPlaylists().getNext());
            assertNull(result.getPlaylists().getPrev());
            assertNull(result.getPlaylists().getChecksum());

            assertEquals(10, result.getPodcasts().getData().size());
            assertEquals(10, result.getPodcasts().getTotal());
            assertNull(result.getPodcasts().getNext());
            assertNull(result.getPodcasts().getPrev());
            assertNull(result.getPodcasts().getChecksum());
        }
    }

    @Test
    void testFetchEditorialReleases() throws Exception {
        final List<PaginatedResponse<Album>> results = new ArrayList<>(2);
        results.add(client.editorials.getReleases());
        results.add(client.editorials.getReleasesFuture().get());

        for (final PaginatedResponse<Album> result : results) {
            assertEquals(20, result.getData().size());
            assertEquals(58, result.getTotal());
            assertNotNull(result.getNext());
            assertNull(result.getPrev());
            assertNull(result.getChecksum());
        }
    }
}
