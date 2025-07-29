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
import io.github.kingg22.deezer.client.api.objects.Album;
import io.github.kingg22.deezer.client.api.objects.Artist;
import io.github.kingg22.deezer.client.api.objects.Chart;
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse;
import io.github.kingg22.deezer.client.api.objects.Playlist;
import io.github.kingg22.deezer.client.api.objects.Podcast;
import io.github.kingg22.deezer.client.api.objects.Track;

class ChartJavaRoutesTest {
    private DeezerApiJavaClient client;

    @BeforeEach
    void setup() {
        client = DeezerApiJavaClient.initialize(KtorEngineMocked.createHttpBuilderMock());
    }

    @Test
    void testFetchCharts() throws Exception {
        final List<Chart> results = new ArrayList<>(2);
        results.add(client.charts.getAll());
        results.add(client.charts.getAllFuture().get());

        for (final Chart result : results) {
            assertEquals(10, result.getTracks().getData().size());
            assertEquals(10, result.getTracks().getTotal());

            assertEquals(9, result.getAlbums().getData().size());
            assertEquals(9, result.getAlbums().getTotal());

            assertEquals(10, result.getArtists().getData().size());
            assertEquals(10, result.getArtists().getTotal());

            assertEquals(10, result.getPlaylists().getData().size());
            assertEquals(10, result.getPlaylists().getTotal());

            assertEquals(10, result.getPodcasts().getData().size());
            assertEquals(10, result.getPodcasts().getTotal());
        }
    }

    @Test
    void testFetchChartById() throws Exception {
        final List<Chart> results = new ArrayList<>(2);
        results.add(client.charts.getById(2));
        results.add(client.charts.getByIdFuture(2).get());

        for (final Chart result : results) {
            assertEquals(10, result.getTracks().getData().size());
            assertEquals(10, result.getTracks().getTotal());

            assertTrue(result.getAlbums().getData().isEmpty());
            assertEquals(0, result.getAlbums().getTotal());

            assertEquals(10, result.getArtists().getData().size());
            assertEquals(10, result.getArtists().getTotal());

            assertEquals(8, result.getPlaylists().getData().size());
            assertEquals(8, result.getPlaylists().getTotal());

            assertTrue(result.getPodcasts().getData().isEmpty());
            assertEquals(0, result.getPodcasts().getTotal());
        }
    }

    @Test
    void testFetchChartTracks() throws Exception {
        final List<PaginatedResponse<Track>> results = new ArrayList<>(2);
        results.add(client.charts.getTracks());
        results.add(client.charts.getTracksFuture().get());

        for (final PaginatedResponse<Track> result : results) {
            assertEquals(10, result.getData().size());
            assertEquals(10, result.getTotal());
        }
    }

    @Test
    void testFetchChartAlbums() throws Exception {
        final List<PaginatedResponse<Album>> results = new ArrayList<>(2);
        results.add(client.charts.getAlbums());
        results.add(client.charts.getAlbumsFuture().get());

        for (final PaginatedResponse<Album> result : results) {
            assertEquals(9, result.getData().size());
            assertEquals(9, result.getTotal());
            assertNull(result.getNext());
            assertNull(result.getPrev());
            assertNull(result.getChecksum());
        }
    }

    @Test
    void testFetchChartArtists() throws Exception {
        final List<PaginatedResponse<Artist>> results = new ArrayList<>(2);
        results.add(client.charts.getArtists());
        results.add(client.charts.getArtistsFuture().get());

        for (final PaginatedResponse<Artist> result : results) {
            assertEquals(10, result.getData().size());
            assertEquals(10, result.getTotal());
            assertNull(result.getNext());
            assertNull(result.getPrev());
            assertNull(result.getChecksum());
        }
    }

    @Test
    void testFetchChartPlaylists() throws Exception {
        final List<PaginatedResponse<Playlist>> results = new ArrayList<>(2);
        results.add(client.charts.getPlaylists());
        results.add(client.charts.getPlaylistsFuture().get());

        for (final PaginatedResponse<Playlist> result : results) {
            assertEquals(10, result.getData().size());
            assertEquals(10, result.getTotal());
            assertNull(result.getNext());
            assertNull(result.getPrev());
            assertNull(result.getChecksum());
        }
    }

    @Test
    void testFetchChartPodcasts() throws Exception {
        final List<PaginatedResponse<Podcast>> results = new ArrayList<>(2);
        results.add(client.charts.getPodcasts());
        results.add(client.charts.getPodcastsFuture().get());

        for (final PaginatedResponse<Podcast> result : results) {
            assertEquals(10, result.getData().size());
            assertEquals(10, result.getTotal());
            assertNull(result.getNext());
            assertNull(result.getPrev());
            assertNull(result.getChecksum());
        }
    }
}
