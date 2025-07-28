package io.github.kingg22.deezer.client.api

import io.github.kingg22.deezer.client.utils.HttpClientBuilder
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.logging.LogLevel
import kotlinx.coroutines.test.runTest
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

@Ignore("Prevent abuse of the API when testing")
class DeezerApiClientIntegrationTest {
    @Test
    fun `Fetch Album by UPC`() = runTest {
        val result = DeezerApiClient(
            HttpClientBuilder()
                .maxRetryCount(1)
                .httpLogLevel(LogLevel.ALL)
                .httpEngine(CIO.create()),
        ).albums.getByUpc("196589221285")
        assertEquals(327983357, result.id)
        assertEquals("196589221285", result.upc)
        assertEquals("329d27f2437976090d4a150cb4da4348", result.md5Image)
    }
}
