package io.github.kingg22.deezer.client.api.routes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SearchJavaRoutesTest {
    @Test
    void testSearchBuilder() {
        final var query = new SearchRoutes.AdvancedQueryBuilder()
            .durationMin(10L).build();
        assertEquals("dur_min:10", query);
    }
}
