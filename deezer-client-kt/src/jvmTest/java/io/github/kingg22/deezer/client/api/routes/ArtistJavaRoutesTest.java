package io.github.kingg22.deezer.client.api.routes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import io.github.kingg22.deezer.client.KtorEngineMocked;
import io.github.kingg22.deezer.client.api.DeezerApiJavaClient;
import io.github.kingg22.deezer.client.api.objects.Album;
import io.github.kingg22.deezer.client.api.objects.Artist;
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse;
import io.github.kingg22.deezer.client.api.objects.Playlist;
import io.github.kingg22.deezer.client.api.objects.Track;
import io.github.kingg22.deezer.client.api.objects.User;

class ArtistJavaRoutesTest {
    private DeezerApiJavaClient client;

    @BeforeEach
    void setup() {
        client = new DeezerApiJavaClient(KtorEngineMocked.createHttpClientMock());
    }

    @Test
    void fetchArtistById() {
        final Artist result = client.artists.getById(27);

        assertEquals(27, result.getId());
        assertEquals("Daft Punk", result.getName());
        assertEquals("https://www.deezer.com/artist/27", result.getLink());
        assertEquals(36, result.getAlbumCount());
        assertEquals(4802079, result.getFansCount());
        assertTrue(result.isRadio());
        assertEquals("https://api.deezer.com/artist/27/top?limit=50", result.getTracklist());
        assertEquals("artist", result.getType());
    }

    @Test
    void fetchArtistByIdFuture() throws Exception {
        final CompletableFuture<Artist> future = client.artists.getByIdFuture(27);
        final Artist result = future.get();

        assertEquals(27, result.getId());
        assertEquals("Daft Punk", result.getName());
        assertEquals("https://www.deezer.com/artist/27", result.getLink());
        assertEquals(36, result.getAlbumCount());
        assertEquals(4802079, result.getFansCount());
        assertTrue(result.isRadio());
        assertEquals("https://api.deezer.com/artist/27/top?limit=50", result.getTracklist());
        assertEquals("artist", result.getType());
    }

    @Test
    void fetchArtistFans() {
        final PaginatedResponse<User> result = client.artists.getFans(27);

        assertEquals(50, result.getData().size());
        assertEquals(250, result.getTotal());
        assertEquals("https://api.deezer.com/artist/27/fans?index=50", result.getNext());
        assertNull(result.getPrev());
        assertNull(result.getChecksum());

        for (final User user : result.getData()) {
            assertNotNull(user.getName());
        }
    }

    @Test
    void fetchArtistTopTracks() {
        final PaginatedResponse<Track> result = client.artists.getTopTracks(27);

        assertEquals(5, result.getData().size());
        assertEquals(51, result.getTotal());
        assertEquals("https://api.deezer.com/artist/27/top?index=5", result.getNext());
        assertNull(result.getPrev());
        assertNull(result.getChecksum());

        for (final Track track : result.getData()) {
            assertNotNull(track.getContributors());
        }
    }

    @Test
    void fetchArtistAlbums() {
        final PaginatedResponse<Album> result = client.artists.getAlbums(27);

        assertEquals(25, result.getData().size());
        assertEquals(36, result.getTotal());
        assertEquals("https://api.deezer.com/artist/27/albums?index=25", result.getNext());
        assertNull(result.getPrev());
        assertNull(result.getChecksum());
    }

    @Test
    void fetchArtistRadio() {
        final PaginatedResponse<Track> result = client.artists.getRadio(27);

        assertEquals(25, result.getData().size());
        assertNull(result.getNext());
        assertNull(result.getPrev());
        assertNull(result.getChecksum());
        assertNull(result.getTotal());

        for (final Track track : result.getData()) {
            assertNotNull(track.getArtist());
            assertNotNull(track.getAlbum());
        }
    }

    @Test
    void fetchArtistPlaylists() {
        final PaginatedResponse<Playlist> result = client.artists.getPlaylists(27);

        assertEquals(10, result.getData().size());
        assertEquals(100, result.getTotal());
        assertEquals("https://api.deezer.com/artist/27/playlists?index=10", result.getNext());
        assertNull(result.getPrev());
        assertNull(result.getChecksum());

        for (final Playlist playlist : result.getData()) {
            assertNotNull(playlist.getCreator());
        }
    }

    @Test
    void fetchArtistRelated() {
        final PaginatedResponse<Artist> result = client.artists.getRelated(27);

        assertEquals(20, result.getData().size());
        assertEquals(20, result.getTotal());
        assertNull(result.getNext());
        assertNull(result.getPrev());
        assertNull(result.getChecksum());

        final Artist first = result.getData().get(0);
        assertEquals(103, first.getId());
        assertEquals("Pharrell Williams", first.getName());
    }
}
