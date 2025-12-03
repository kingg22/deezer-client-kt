package io.github.kingg22.deezer.client.api.objects;

import io.github.kingg22.deezer.client.KtorEngineMocked;
import io.github.kingg22.deezer.client.api.DeezerApiClient;
import io.github.kingg22.deezer.client.api.GlobalDeezerApiClient;
import kotlinx.datetime.LocalDateTime;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTimeout;

class ResourceJavaTest {
    private @NotNull DeezerApiClient client;

    @BeforeEach
    void setup() {
        client = new DeezerApiClient(KtorEngineMocked.createHttpClientMock());
        assertNotNull(GlobalDeezerApiClient.instance);
    }

    @Test
    void testReload() {
        final Episode tested = new Episode(
            526673645,
            "",
            0,
            LocalDateTime.Companion.parse("2019-09-09T00:00:00", LocalDateTime.Formats.INSTANCE.getISO())
        );
        final Episode episode = (Episode) tested.reload(client);
        final Episode episodeFuture = (Episode) assertTimeout(Duration.ofMinutes(1), () -> tested.reloadFuture(client).get());
        assertNotEquals(tested, episode);
        assertNotEquals(tested, episodeFuture);
    }
}
