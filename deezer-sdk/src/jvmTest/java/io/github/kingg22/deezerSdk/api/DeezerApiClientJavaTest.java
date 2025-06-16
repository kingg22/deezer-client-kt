package io.github.kingg22.deezerSdk.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.kingg22.deezerSdk.KtorEngineMocked;

class DeezerApiClientJavaTest {
    @Test
    void globalHolder() {
        Assertions.assertDoesNotThrow(() -> DeezerApiClient.initialize(KtorEngineMocked.createHttpBuilderMock()));
        Assertions.assertNotNull(GlobalDeezerApiClient.instance);
    }
}
