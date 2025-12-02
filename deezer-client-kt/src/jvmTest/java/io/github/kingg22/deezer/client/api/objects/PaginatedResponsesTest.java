package io.github.kingg22.deezer.client.api.objects;

import io.github.kingg22.deezer.client.KtorEngineMocked;
import io.github.kingg22.deezer.client.api.DeezerApiClient;
import io.github.kingg22.deezer.client.api.GlobalDeezerApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PaginatedResponsesTest {
    private DeezerApiClient client;

    @BeforeEach
    void setup() {
        client = new DeezerApiClient(KtorEngineMocked.createHttpClientMock());
        assertNotNull(GlobalDeezerApiClient.instance);
    }

    @Test
    void fetchNextWithoutDataSuccess() {
        final PaginatedResponse<Track> paging2 = new PaginatedResponse<>(Collections.emptyList(), null, null, null, PaginatedResponseTest.TRACK_LINK);

        assertDoesNotThrow(() -> {
            final List<PaginatedResponse<Track>> results = Arrays.asList(
                PaginatedResponses.fetchNext(client, paging2, Track.class, true),
                assertTimeout(
                    Duration.ofMinutes(1),
                    () -> PaginatedResponses.fetchNextFuture(client, paging2, Track.class, true).get()
                )
            );

            for (final PaginatedResponse<Track> newPaging : results) {
                assertNotNull(newPaging);
                assertFalse(newPaging.getData().isEmpty());
                assertNull(newPaging.getPrev());
                assertNotNull(newPaging.getNext());
            }
        });
    }

    @Test
    void fetchNextWithDataSuccess() {
        final PaginatedResponse<Track> paging2 = new PaginatedResponse<>(Collections.singletonList(PaginatedResponseTest.emptyTrack), null, null, null, PaginatedResponseTest.TRACK_LINK);

        assertDoesNotThrow(() -> {
            final List<PaginatedResponse<Track>> results = Arrays.asList(
                PaginatedResponses.fetchNext(client, paging2, Track.class, true),
                assertTimeout(
                    Duration.ofMinutes(1),
                    () -> PaginatedResponses.fetchNextFuture(client, paging2, Track.class, true).get()
                )
            );

            for (final PaginatedResponse<Track> newPaging : results) {
                assertNotNull(newPaging);
                assertFalse(newPaging.getData().isEmpty());
                assertTrue(newPaging.getData().contains(PaginatedResponseTest.emptyTrack));
                assertNull(newPaging.getPrev());
                assertNotNull(newPaging.getNext());
            }
        });
    }

    @Test
    void fetchPreviousWithoutDataSuccess() {
        final PaginatedResponse<Track> paging2 = new PaginatedResponse<>(Collections.emptyList(), null, null, PaginatedResponseTest.TRACK_LINK);

        assertDoesNotThrow(() -> {
            final List<PaginatedResponse<Track>> results = Arrays.asList(
                PaginatedResponses.fetchPrevious(client, paging2, Track.class, true),
                assertTimeout(
                    Duration.ofMinutes(1),
                    () -> PaginatedResponses.fetchPreviousFuture(client, paging2, Track.class, true).get()
                )
            );

            for (final PaginatedResponse<Track> newPaging : results) {
                assertNotNull(newPaging);
                assertFalse(newPaging.getData().isEmpty());
                assertNotNull(newPaging.getNext());
                assertNull(newPaging.getPrev());
            }
        });
    }

    @Test
    void fetchPreviousWithDataSuccess() {
        final PaginatedResponse<Track> paging2 = new PaginatedResponse<>(Collections.singletonList(PaginatedResponseTest.emptyTrack), null, null, PaginatedResponseTest.TRACK_LINK);

        assertDoesNotThrow(() -> {
            final List<PaginatedResponse<Track>> results = Arrays.asList(
                PaginatedResponses.fetchPrevious(client, paging2, Track.class, true),
                assertTimeout(
                    Duration.ofMinutes(1),
                    () -> PaginatedResponses.fetchPreviousFuture(client, paging2, Track.class, true).get()
                )
            );

            for (final PaginatedResponse<Track> newPaging : results) {
                assertNotNull(newPaging);
                assertFalse(newPaging.getData().isEmpty());
                assertTrue(newPaging.getData().contains(PaginatedResponseTest.emptyTrack));
            }
        });
    }
}
