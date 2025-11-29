package io.github.kingg22.deezer.client.api.routes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import io.github.kingg22.deezer.client.KtorEngineMocked;
import io.github.kingg22.deezer.client.api.DeezerApiJavaClient;
import io.github.kingg22.deezer.client.api.objects.Genre;
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse;
import io.github.kingg22.deezer.client.api.objects.Radio;
import io.github.kingg22.deezer.client.api.objects.Track;

final class RadioJavaRoutesTest {

    private DeezerApiJavaClient client;

    @BeforeEach
    void setup() {
        client = new DeezerApiJavaClient(KtorEngineMocked.createHttpClientMock());
    }

    @Test
    void fetchRadio() throws Exception {
        List<PaginatedResponse<Radio>> results = Arrays.asList(
            client.radios.getAll(),
            client.radios.getAllFuture().get()
        );

        for (PaginatedResponse<Radio> result : results) {
            assertEquals(139, result.getData().size());
            assertNull(result.getPrev());
            assertNull(result.getNext());
            assertNull(result.getChecksum());
            assertNull(result.getTotal());
        }
    }

    @Test
    void fetchRadioById() throws Exception {
        List<Radio> results = Arrays.asList(
            client.radios.getById(1236),
            client.radios.getByIdFuture(1236).get()
        );

        for (Radio result : results) {
            assertEquals(1236, result.getId());
        }
    }

    @Test
    void fetchRadioSplitByGenre() throws Exception {
        List<PaginatedResponse<Genre>> results = Arrays.asList(
            client.radios.getAllSplitInGenres(),
            client.radios.getAllSplitInGenresFuture().get()
        );

        for (PaginatedResponse<Genre> result : results) {
            assertEquals(20, result.getData().size());
            assertNotNull(result.getData().get(0).getRadios());
            assertNull(result.getPrev());
            assertNull(result.getNext());
            assertNull(result.getChecksum());
            assertNull(result.getTotal());
        }
    }

    @Test
    void fetchRadioTop() throws Exception {
        List<PaginatedResponse<Radio>> results = Arrays.asList(
            client.radios.getTop(),
            client.radios.getTopFuture().get()
        );

        for (PaginatedResponse<Radio> result : results) {
            assertEquals(9, result.getData().size());
            assertEquals(9, result.getTotal());
            assertNull(result.getPrev());
            assertNull(result.getNext());
            assertNull(result.getChecksum());
        }
    }

    @Test
    void fetchRadioTracks() throws Exception {
        List<PaginatedResponse<Track>> results = Arrays.asList(
            client.radios.getTracks(31061),
            client.radios.getTracksFuture(31061).get()
        );

        for (PaginatedResponse<Track> result : results) {
            assertEquals(25, result.getData().size());
            assertNotNull(result.getData().get(0).getArtist());
            assertNotNull(result.getData().get(0).getAlbum());
            assertNull(result.getPrev());
            assertNull(result.getNext());
            assertNull(result.getChecksum());
            assertNull(result.getTotal());
        }
    }

    @Test
    void fetchRadioLists() throws Exception {
        List<PaginatedResponse<Radio>> results = Arrays.asList(
            client.radios.getLists(),
            client.radios.getListsFuture().get()
        );

        for (PaginatedResponse<Radio> result : results) {
            assertEquals(25, result.getData().size());
            assertEquals(76, result.getTotal());
            assertNotNull(result.getNext());
            assertNull(result.getPrev());
            assertNull(result.getChecksum());
        }
    }
}
