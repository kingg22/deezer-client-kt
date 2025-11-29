package io.github.kingg22.deezer.client.api.objects;

import io.github.kingg22.deezer.client.api.GlobalDeezerApiClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.github.kingg22.deezer.client.KtorEngineMocked;
import io.github.kingg22.deezer.client.api.DeezerApiClient;

class PaginatedResponsesTest {
    @BeforeEach
    void setup() {
        new DeezerApiClient(KtorEngineMocked.createHttpClientMock());
        Assertions.assertNotNull(GlobalDeezerApiClient.instance);
    }

    @Test
    void fetchNextWithoutDataSuccess() {
        final PaginatedResponse<Track> paging2 = new PaginatedResponse<>(Collections.emptyList(), null, null, null, PaginatedResponseTest.TRACK_LINK);

        Assertions.assertDoesNotThrow(() -> {
            final List<PaginatedResponse<Track>> results = Arrays.asList(
                PaginatedResponses.fetchNext(paging2, Track.class, true),
                Assertions.assertTimeout(Duration.ofMinutes(1), () -> PaginatedResponses.fetchNextFuture(paging2, Track.class, true).get())
            );

            for (final PaginatedResponse<Track> newPaging : results) {
                Assertions.assertNotNull(newPaging);
                Assertions.assertFalse(newPaging.getData().isEmpty());
                Assertions.assertNull(newPaging.getPrev());
                Assertions.assertNotNull(newPaging.getNext());
            }
        });
    }

    @Test
    void fetchNextWithDataSuccess() {
        final PaginatedResponse<Track> paging2 = new PaginatedResponse<>(Collections.singletonList(PaginatedResponseTest.emptyTrack), null, null, null, PaginatedResponseTest.TRACK_LINK);

        Assertions.assertDoesNotThrow(() -> {
            final List<PaginatedResponse<Track>> results = Arrays.asList(
                PaginatedResponses.fetchNext(paging2, Track.class, true),
                Assertions.assertTimeout(Duration.ofMinutes(1), () -> PaginatedResponses.fetchNextFuture(paging2, Track.class, true).get())
            );

            for (final PaginatedResponse<Track> newPaging : results) {
                Assertions.assertNotNull(newPaging);
                Assertions.assertFalse(newPaging.getData().isEmpty());
                Assertions.assertTrue(newPaging.getData().contains(PaginatedResponseTest.emptyTrack));
                Assertions.assertNull(newPaging.getPrev());
                Assertions.assertNotNull(newPaging.getNext());
            }
        });
    }

    @Test
    void fetchPreviousWithoutDataSuccess() {
        final PaginatedResponse<Track> paging2 = new PaginatedResponse<>(Collections.emptyList(), null, null, PaginatedResponseTest.TRACK_LINK);

        Assertions.assertDoesNotThrow(() -> {
            final List<PaginatedResponse<Track>> results = Arrays.asList(
                PaginatedResponses.fetchPrevious(paging2, Track.class, true),
                Assertions.assertTimeout(Duration.ofMinutes(1), () -> PaginatedResponses.fetchPreviousFuture(paging2, Track.class, true).get())
            );

            for (final PaginatedResponse<Track> newPaging : results) {
                Assertions.assertNotNull(newPaging);
                Assertions.assertFalse(newPaging.getData().isEmpty());
                Assertions.assertNotNull(newPaging.getNext());
                Assertions.assertNull(newPaging.getPrev());
            }
        });
    }

    @Test
    void fetchPreviousWithDataSuccess() {
        final PaginatedResponse<Track> paging2 = new PaginatedResponse<>(Collections.singletonList(PaginatedResponseTest.emptyTrack), null, null, PaginatedResponseTest.TRACK_LINK);

        Assertions.assertDoesNotThrow(() -> {
            final List<PaginatedResponse<Track>> results = Arrays.asList(
                PaginatedResponses.fetchPrevious(paging2, Track.class, true),
                Assertions.assertTimeout(Duration.ofMinutes(1), () -> PaginatedResponses.fetchPreviousFuture(paging2, Track.class, true).get())
            );

            for (final PaginatedResponse<Track> newPaging : results) {
                Assertions.assertNotNull(newPaging);
                Assertions.assertFalse(newPaging.getData().isEmpty());
                Assertions.assertTrue(newPaging.getData().contains(PaginatedResponseTest.emptyTrack));
            }
        });
    }
}
