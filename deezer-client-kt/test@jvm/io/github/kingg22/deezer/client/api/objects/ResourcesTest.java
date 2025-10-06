package io.github.kingg22.deezer.client.api.objects;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import kotlinx.datetime.LocalDateTime;

class ResourcesTest {
    @Test
    void testReload() {
        final Episode tested = new Episode(
            526673645,
            "",
            0,
            LocalDateTime.Companion.parse("2019-09-09T00:00:00", LocalDateTime.Formats.INSTANCE.getISO())
        );
        final Episode episode = Resources.reload(tested);
        final Episode episodeFuture = Assertions.assertTimeout(Duration.ofMinutes(1), () -> Resources.reloadFuture(tested).get());
        assertNotEquals(tested, episode);
        assertNotEquals(tested, episodeFuture);
    }
}
