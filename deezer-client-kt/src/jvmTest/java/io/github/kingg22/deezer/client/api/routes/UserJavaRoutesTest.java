package io.github.kingg22.deezer.client.api.routes;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import io.github.kingg22.deezer.client.KtorEngineMocked;
import io.github.kingg22.deezer.client.api.DeezerApiJavaClient;
import io.github.kingg22.deezer.client.api.objects.User;

class UserJavaRoutesTest {
    private DeezerApiJavaClient client;

    @BeforeEach
    void setup() {
        client = new DeezerApiJavaClient(KtorEngineMocked.createHttpBuilderMock());
    }

    @Test
    void testFetchUserById() throws Exception {
        final List<User> results = Arrays.asList(
            client.users.getById(2616835602L),
            client.users.getByIdFuture(2616835602L).get()
        );

        for (final User result : results) {
            assertEquals(2616835602L, result.getId());
            assertEquals("https://www.deezer.com/profile/2616835602", result.getLink());
        }
    }
}
