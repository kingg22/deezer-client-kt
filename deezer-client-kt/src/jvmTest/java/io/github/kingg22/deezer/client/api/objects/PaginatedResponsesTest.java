package io.github.kingg22.deezer.client.api.objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.github.kingg22.deezer.client.KtorEngineMocked;
import io.github.kingg22.deezer.client.api.DeezerApiClient;
import kotlinx.serialization.KSerializer;

class PaginatedResponsesTest {
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
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> PaginatedResponses.fetchNext(paging, ser, true));
        Assertions.assertInstanceOf(
                IllegalArgumentException.class,
                Assertions.assertThrowsExactly(ExecutionException.class, () -> PaginatedResponses.fetchNextFuture(paging, ser, true).get()).getCause()
        );
    }

    @Test
    void fetchNextWithoutDataAndDifferentTypesSuccess() {
        final var paging2 = new PaginatedResponse<User>(Collections.emptyList(), null, null, null, PaginatedResponseTest.TRACK_LINK);
        Assertions.assertDoesNotThrow(() -> {
            var newPaging = PaginatedResponses.fetchNext(paging2, ser, true);
            Assertions.assertNotNull(newPaging);
            Assertions.assertFalse(newPaging.getData().isEmpty());
            Assertions.assertNull(newPaging.getPrev());
            Assertions.assertNotNull(newPaging.getNext());
            newPaging = Assertions.assertTimeout(Duration.ofMinutes(1), () -> PaginatedResponses.fetchNextFuture(paging2, ser, true).get());
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
            var newPaging = PaginatedResponses.fetchNext(paging2, ser, true);
            Assertions.assertNotNull(newPaging);
            Assertions.assertFalse(newPaging.getData().isEmpty());
            Assertions.assertTrue(newPaging.getData().contains(PaginatedResponseTest.emptyTrack));
            Assertions.assertNull(newPaging.getPrev());
            Assertions.assertNotNull(newPaging.getNext());
            newPaging = Assertions.assertTimeout(Duration.ofMinutes(1), () -> PaginatedResponses.fetchNextFuture(paging2, ser, true).get());
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
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> PaginatedResponses.fetchPrevious(paging, ser, true));
        Assertions.assertInstanceOf(
                IllegalArgumentException.class,
                Assertions.assertThrowsExactly(ExecutionException.class, () -> PaginatedResponses.fetchPreviousFuture(paging, ser, true).get()).getCause()
        );
    }

    @Test
    void fetchPreviousWithoutDataAndDifferentTypesSuccess() {
        final var paging2 = new PaginatedResponse<User>(Collections.emptyList(), null, null, PaginatedResponseTest.TRACK_LINK);
        Assertions.assertDoesNotThrow(() -> {
            var newPaging = PaginatedResponses.fetchPrevious(paging2, ser, true);
            Assertions.assertNotNull(newPaging);
            Assertions.assertFalse(newPaging.getData().isEmpty());
            Assertions.assertNotNull(newPaging.getNext());
            Assertions.assertNull(newPaging.getPrev());
            newPaging = Assertions.assertTimeout(Duration.ofMinutes(1), () -> PaginatedResponses.fetchPreviousFuture(paging2, ser, true).get());
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
            var newPaging = PaginatedResponses.fetchPrevious(paging2, ser, true);
            Assertions.assertNotNull(newPaging);
            Assertions.assertFalse(newPaging.getData().isEmpty());
            Assertions.assertTrue(newPaging.getData().contains(PaginatedResponseTest.emptyTrack));
            newPaging = Assertions.assertTimeout(Duration.ofMinutes(1), () -> PaginatedResponses.fetchPreviousFuture(paging2, ser, true).get());
            Assertions.assertNotNull(newPaging);
            Assertions.assertFalse(newPaging.getData().isEmpty());
            Assertions.assertTrue(newPaging.getData().contains(PaginatedResponseTest.emptyTrack));
        });
    }
}
