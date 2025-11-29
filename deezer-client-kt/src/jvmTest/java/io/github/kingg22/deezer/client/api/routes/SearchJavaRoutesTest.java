package io.github.kingg22.deezer.client.api.routes;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static io.github.kingg22.deezer.client.api.routes.SearchRoutes.buildAdvancedQuery;
import static io.github.kingg22.deezer.client.api.routes.SearchRoutes.setStrict;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import io.github.kingg22.deezer.client.KtorEngineMocked;
import io.github.kingg22.deezer.client.api.DeezerApiJavaClient;
import io.github.kingg22.deezer.client.api.objects.Album;
import io.github.kingg22.deezer.client.api.objects.Artist;
import io.github.kingg22.deezer.client.api.objects.PaginatedResponse;
import io.github.kingg22.deezer.client.api.objects.Playlist;
import io.github.kingg22.deezer.client.api.objects.Podcast;
import io.github.kingg22.deezer.client.api.objects.Radio;
import io.github.kingg22.deezer.client.api.objects.SearchOrder;
import io.github.kingg22.deezer.client.api.objects.Track;
import io.github.kingg22.deezer.client.api.objects.User;

class SearchJavaRoutesTest {
    private DeezerApiJavaClient client;

    @BeforeEach
    void setup() {
        client = new DeezerApiJavaClient(KtorEngineMocked.createHttpClientMock());
    }

    @Test
    void testSearchBuilder() {
        final String query = SearchRoutes.buildAdvancedQuery()
            .durationMin(10L).build();
        assertEquals("dur_min:10", query);
    }

    @Test
    void testFetchBasicSearch() {
        final List<PaginatedResponse<Track>> results = new ArrayList<>(2);
        results.add(client.searches.search("eminem"));
        results.add(assertTimeout(Duration.ofMinutes(1), () -> client.searches.searchFuture("eminem").get()));
        for (PaginatedResponse<Track> result : results) {
            assertEquals(193, result.getTotal());
            assertEquals(25, result.getData().size());
            assertNotNull(result.getNext());
            assertNull(result.getPrev());
            assertNull(result.getChecksum());
        }
    }

    @Test
    void testFetchSearchWithStrictOn() {
        final List<PaginatedResponse<Track>> results = new ArrayList<>(2);
        results.add(client.searches.search("Not Afraid", setStrict(true)));
        results.add(assertTimeout(Duration.ofMinutes(1), () -> client.searches.searchFuture("Not Afraid", setStrict(true)).get()));
        for (PaginatedResponse<Track> result : results) {
            assertEquals(293, result.getTotal());
            assertEquals(25, result.getData().size());
            assertNotNull(result.getNext());
            assertNull(result.getPrev());
            assertNull(result.getChecksum());
        }
    }

    @Test
    void testFetchSearchWithOrder() {
        final List<PaginatedResponse<Track>> results = new ArrayList<>(2);
        results.add(client.searches.search("Not Afraid", null, SearchOrder.TRACK_ASC));
        results.add(assertTimeout(Duration.ofMinutes(1), () -> client.searches.searchFuture("Not Afraid", null, SearchOrder.TRACK_ASC).get()));
        for (PaginatedResponse<Track> result : results) {
            assertEquals(293, result.getTotal());
            assertEquals(25, result.getData().size());
            assertNotNull(result.getNext());
            assertNull(result.getPrev());
            assertNull(result.getChecksum());
        }
    }

    @Test
    void testFetchSearchAlbumWithMultipleParams() {
        final List<PaginatedResponse<Album>> results = new ArrayList<>(2);
        results.add(client.searches.searchAlbum(buildAdvancedQuery("King").artist("eminem").build(), null, SearchOrder.RATING_DESC));
        results.add(assertTimeout(Duration.ofMinutes(1), () -> client.searches.searchAlbumFuture(buildAdvancedQuery("King").artist("eminem").build(), null, SearchOrder.RATING_DESC).get()));
        for (PaginatedResponse<Album> result : results) {
            assertEquals(266, result.getTotal());
            assertEquals(25, result.getData().size());
            assertNotNull(result.getNext());
            assertNull(result.getPrev());
            assertNull(result.getChecksum());
        }
    }

    @Test
    void testFetchSearchArtistWithOrder() {
        final List<PaginatedResponse<Artist>> results = new ArrayList<>(2);
        results.add(client.searches.searchArtist("cat", null, SearchOrder.ARTIST_DESC));
        results.add(assertTimeout(Duration.ofMinutes(1), () -> client.searches.searchArtistFuture("cat", null, SearchOrder.ARTIST_DESC).get()));
        for (PaginatedResponse<Artist> result : results) {
            assertEquals(119, result.getTotal());
            assertEquals(25, result.getData().size());
            assertNotNull(result.getNext());
            assertNull(result.getPrev());
            assertNull(result.getChecksum());
        }
    }

    @Test
    void testFetchSearchPlaylistWithOrder() {
        final List<PaginatedResponse<Playlist>> results = new ArrayList<>(2);
        results.add(client.searches.searchPlaylist("eminem", null, SearchOrder.RANKING));
        results.add(assertTimeout(Duration.ofMinutes(1), () -> client.searches.searchPlaylistFuture("eminem", null, SearchOrder.RANKING).get()));
        for (PaginatedResponse<Playlist> result : results) {
            assertEquals(300, result.getTotal());
            assertEquals(25, result.getData().size());
            assertNotNull(result.getNext());
            assertNull(result.getPrev());
            assertNull(result.getChecksum());
        }
    }

    @Test
    void testFetchSearchPodcastWithOrder() {
        final List<PaginatedResponse<Podcast>> results = new ArrayList<>(2);
        results.add(client.searches.searchPodcast("eminem", null, SearchOrder.RATING_ASC));
        results.add(assertTimeout(Duration.ofMinutes(1), () -> client.searches.searchPodcastFuture("eminem", null, SearchOrder.RATING_ASC).get()));
        for (PaginatedResponse<Podcast> result : results) {
            assertEquals(10, result.getTotal());
            assertEquals(10, result.getData().size());
            assertNull(result.getNext());
            assertNull(result.getPrev());
            assertNull(result.getChecksum());
        }
    }

    @Test
    void testFetchSearchRadioWithOrder() {
        final List<PaginatedResponse<Radio>> results = new ArrayList<>(2);
        results.add(client.searches.searchRadio("Electro", null, SearchOrder.DURATION_ASC));
        results.add(assertTimeout(Duration.ofMinutes(1), () -> client.searches.searchRadioFuture("Electro", null, SearchOrder.DURATION_ASC).get()));
        for (PaginatedResponse<Radio> result : results) {
            assertEquals(3, result.getTotal());
            assertEquals(3, result.getData().size());
            assertNull(result.getNext());
            assertNull(result.getPrev());
            assertNull(result.getChecksum());
        }
    }

    @Test
    void testFetchSearchTrackWithOrder() {
        final List<PaginatedResponse<Track>> results = new ArrayList<>(2);
        results.add(client.searches.searchTrack("eminem", null, SearchOrder.DURATION_DESC));
        results.add(assertTimeout(Duration.ofMinutes(1), () -> client.searches.searchTrackFuture("eminem", null, SearchOrder.DURATION_DESC).get()));
        for (PaginatedResponse<Track> result : results) {
            assertEquals(208, result.getTotal());
            assertEquals(25, result.getData().size());
            assertNotNull(result.getNext());
            assertNull(result.getPrev());
            assertNull(result.getChecksum());
        }
    }

    @Test
    void testFetchSearchUserWithOrder() {
        final List<PaginatedResponse<User>> results = new ArrayList<>(2);
        results.add(client.searches.searchUser("eminem", null, SearchOrder.RANKING));
        results.add(assertTimeout(Duration.ofMinutes(1), () -> client.searches.searchUserFuture("eminem", null, SearchOrder.RANKING).get()));
        for (PaginatedResponse<User> result : results) {
            assertEquals(54, result.getTotal());
            assertEquals(25, result.getData().size());
            assertNotNull(result.getNext());
            assertNull(result.getPrev());
            assertNull(result.getChecksum());
        }
    }
}
