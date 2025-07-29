package io.github.kingg22.deezer.client.api.routes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.github.kingg22.deezer.client.KtorEngineMocked;
import io.github.kingg22.deezer.client.api.DeezerApiJavaClient;
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse;
import io.github.kingg22.deezer.client.api.objects.Playlist;
import io.github.kingg22.deezer.client.api.objects.Track;
import io.github.kingg22.deezer.client.api.objects.User;
import io.github.kingg22.deezer.client.exceptions.DeezerApiException;

class PlaylistJavaRoutesTest {
    private DeezerApiJavaClient client;

    @BeforeEach
    void setup() {
        client = DeezerApiJavaClient.initialize(KtorEngineMocked.createHttpBuilderMock());
    }

    @Test
    void testFetchPlaylistById() {
        final List<Playlist> results = new ArrayList<>(2);
        results.add(client.playlists.getById(908622995));
        results.add(assertTimeout(Duration.ofMinutes(1), () -> client.playlists.getByIdFuture(908622995).get()));
        for (final Playlist result : results) {
            assertEquals(908622995, result.getId());
            assertNotNull(result.getCreator());
            assertNotNull(result.getTracks());
            assertNotNull(result.getTracks().getData());
        }
    }

    @Test
    void testFetchPlaylistFans() {
        final List<PaginatedResponse<User>> results = new ArrayList<>(2);
        results.add(client.playlists.getFans(4341978));
        results.add(assertTimeout(Duration.ofMinutes(1), () -> client.playlists.getFansFuture(4341978).get()));
        for (final PaginatedResponse<User> result : results) {
            assertEquals(1, result.getTotal());
            assertNotNull(result.getData());
            assertEquals(1, result.getData().size());
        }
    }

    @Test
    void testFetchPlaylistTracks() {
        final List<PaginatedResponse<Track>> results = new ArrayList<>(2);
        results.add(client.playlists.getTracks(908622995));
        results.add(assertTimeout(Duration.ofMinutes(1), () -> client.playlists.getTracksFuture(908622995).get()));
        for (final PaginatedResponse<Track> result : results) {
            assertEquals(25, result.getData().size());
            assertEquals(50, result.getTotal());
            assertNotNull(result.getChecksum());
        }
    }

    @Test
    void testFetchPlaylistRadioShouldThrow() {
        ExecutionException ex = assertThrows(ExecutionException.class, () -> client.playlists.getRadioFuture(908622995).get());
        assertNotNull(ex.getCause());
        assertInstanceOf(DeezerApiException.class, ex.getCause());
    }
}
