package io.github.kingg22.deezer.client.api.routes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import io.github.kingg22.deezer.client.KtorEngineMocked;
import io.github.kingg22.deezer.client.api.DeezerApiJavaClient;
import io.github.kingg22.deezer.client.api.objects.Album;
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse;
import io.github.kingg22.deezer.client.api.objects.Track;
import io.github.kingg22.deezer.client.api.objects.User;

class AlbumJavaRoutesTest {
    private DeezerApiJavaClient client;

    @BeforeEach
    void setup() {
        client = new DeezerApiJavaClient(KtorEngineMocked.createHttpBuilderMock());
    }

    @Test
    void testFetchAlbumById() {
        final List<Album> results = new ArrayList<>(2);
        results.add(client.albums.getById(302127));
        results.add(Assertions.assertTimeout(Duration.ofMinutes(1), () -> client.albums.getByIdFuture(302127).get()));
        for (final Album result : results) {
            assertEquals(302127, result.getId());
            assertEquals("Discovery", result.getTitle());
            assertEquals("724384960650", result.getUpc());
        }
    }

    @Test
    void testFetchAlbumFans() {
        final List<PaginatedResponse<User>> results = new ArrayList<>(2);
        results.add(client.albums.getFans(302127));
        results.add(assertTimeout(Duration.ofMinutes(1), () -> client.albums.getFansFuture(302127).get()));
        for (final PaginatedResponse<User> result : results) {
            assertEquals(50, result.getData().size());
            assertNotNull(result.getTotal());
            assertNotNull(result.getNext());
            assertEquals(100, result.getTotal());
            assertEquals("https://api.deezer.com/album/302127/fans?index=50", result.getNext());
        }
    }

    @Test
    void testFetchAlbumTracks() {
        final List<PaginatedResponse<Track>> results = new ArrayList<>(2);
        results.add(client.albums.getTracks(302127));
        results.add(assertTimeout(Duration.ofMinutes(1), () -> client.albums.getTracksFuture(302127).get()));
        for (final PaginatedResponse<Track> result : results) {
            assertEquals(14, result.getData().size());
            assertEquals(14, result.getTotal());
            assertNull(result.getNext());
            assertNull(result.getPrev());
            assertNull(result.getChecksum());
            assertNotNull(result.getData().get(0).getArtist());
        }
    }

    @Test
    void testFetchAlbumByUpc() {
        final List<Album> results = new ArrayList<>(2);
        results.add(client.albums.getByUpc("196589221285"));
        results.add(assertTimeout(Duration.ofMinutes(1), () -> client.albums.getByUpcFuture("196589221285").get()));
        for (final Album result : results) {
            assertEquals(327983357, result.getId());
            assertEquals("196589221285", result.getUpc());
            assertEquals("329d27f2437976090d4a150cb4da4348", result.getMd5Image());
        }
    }
}
