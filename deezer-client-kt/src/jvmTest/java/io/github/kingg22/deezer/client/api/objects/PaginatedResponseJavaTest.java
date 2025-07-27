package io.github.kingg22.deezer.client.api.objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import io.github.kingg22.deezer.client.KtorEngineMocked;
import io.github.kingg22.deezer.client.api.DeezerApiClient;
import kotlinx.serialization.KSerializer;

class PaginatedResponseJavaTest {
    private final KSerializer<Track> ser = Track.Companion.serializer();

    @BeforeEach
    void setup() {
        DeezerApiClient.initialize(KtorEngineMocked.createHttpBuilderMock());
    }

    @Test
    void fetchNextWithDataAndDifferentTypesThrowException() {
        final var paging = new PaginatedResponse<>(List.of(new User(0, "")), null, null, null, PaginatedResponseTest.TRACK_LINK);
        Assertions.assertNotNull(paging.getData());
        Assertions.assertEquals(1, paging.getData().size());
        Assertions.assertNull(paging.getPrev());
        Assertions.assertNotNull(paging.getNext());
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> paging.fetchNext(ser, true));
    }

    @Test
    void fetchNextWithoutDataAndDifferentTypesSuccess() {
        final var paging2 = new PaginatedResponse<User>(Collections.emptyList(), null, null, null, PaginatedResponseTest.TRACK_LINK);
        Assertions.assertDoesNotThrow(() -> {
            final var newPaging = paging2.fetchNext(ser, true);
            Assertions.assertNotNull(newPaging);
            Assertions.assertFalse(newPaging.getData().isEmpty());
            Assertions.assertNull(newPaging.getPrev());
            Assertions.assertNotNull(newPaging.getNext());
        });
    }

    @Test
    void fetchNextWithDataSuccess() {
        final var paging2 = new PaginatedResponse<>(List.of(PaginatedResponseTest.emptyTrack), null, null, null, PaginatedResponseTest.TRACK_LINK);
        Assertions.assertDoesNotThrow(() -> {
            final var newPaging = paging2.fetchNext(ser, true);
            Assertions.assertNotNull(newPaging);
            Assertions.assertFalse(newPaging.getData().isEmpty());
            Assertions.assertTrue(newPaging.getData().contains(PaginatedResponseTest.emptyTrack));
            Assertions.assertNull(newPaging.getPrev());
            Assertions.assertNotNull(newPaging.getNext());
        });
    }

    @Test
    void fetchPreviousWithDataAndDifferentTypesThrowException() {
        final var paging = new PaginatedResponse<>(List.of(new User(0, "")), null, null, PaginatedResponseTest.TRACK_LINK);
        Assertions.assertNotNull(paging.getData());
        Assertions.assertEquals(1, paging.getData().size());
        Assertions.assertNull(paging.getNext());
        Assertions.assertNotNull(paging.getPrev());
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> paging.fetchPrevious(ser, true));
    }

    @Test
    void fetchPreviousWithoutDataAndDifferentTypesSuccess() {
        final var paging2 = new PaginatedResponse<User>(Collections.emptyList(), null, null, PaginatedResponseTest.TRACK_LINK);
        Assertions.assertDoesNotThrow(() -> {
            final var newPaging = paging2.fetchPrevious(ser, true);
            Assertions.assertNotNull(newPaging);
            Assertions.assertFalse(newPaging.getData().isEmpty());
            Assertions.assertNotNull(newPaging.getNext());
            Assertions.assertNull(newPaging.getPrev());
        });
    }

    @Test
    void fetchPreviousWithDataSuccess() {
        final var paging2 = new PaginatedResponse<>(List.of(PaginatedResponseTest.emptyTrack), null, null, PaginatedResponseTest.TRACK_LINK);

        Assertions.assertDoesNotThrow(() -> {
            final var newPaging = paging2.fetchPrevious(ser, true);
            Assertions.assertNotNull(newPaging);
            Assertions.assertFalse(newPaging.getData().isEmpty());
            Assertions.assertTrue(newPaging.getData().contains(PaginatedResponseTest.emptyTrack));
        });
    }
}
