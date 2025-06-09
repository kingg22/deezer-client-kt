package io.github.kingg22.deezerSdk.api

import de.jensklingenberg.ktorfit.ktorfit
import io.github.kingg22.deezerSdk.api.objects.Error
import io.github.kingg22.deezerSdk.api.routes.createAlbumRoutes
import io.github.kingg22.deezerSdk.exceptions.DeezerApiException
import io.github.kingg22.deezerSdk.utils.ExperimentalDeezerSdk
import io.github.kingg22.deezerSdk.utils.HttpClientBuilder
import io.github.kingg22.deezerSdk.utils.HttpClientProvider
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.coroutines.coroutineContext
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

@Ignore("Prevent abuse of the API when testing")
class DeezerApiClientIntegrationTest {
    private data object DeezerTempClient {
        @OptIn(ExperimentalDeezerSdk::class)
        private val ktorfit = ktorfit {
            baseUrl(HttpClientProvider.DeezerApiSupported.API_DEEZER.baseUrl)
            httpClient(
                HttpClientBuilder.httpClient {
                    addCustomConfig {
                        HttpResponseValidator {
                            validateResponse {
                                if (it.status.isSuccess()) {
                                    runCatching {
                                        val content = Json.decodeFromString<Error>(it.bodyAsText()).error
                                        throw DeezerApiException(
                                            errorCode = content.code,
                                            errorMessage = content.message,
                                        )
                                    }.onFailure { coroutineContext.ensureActive() }.getOrNull()
                                }
                            }
                        }
                    }
                },
            )
        }
        val albums = ktorfit.createAlbumRoutes()
    }

    private val client = DeezerTempClient

    @Test
    fun `Fetch Album by UPC`() = runTest {
        val result = client.albums.getByUpc("196589221285")
        assertEquals(327983357, result.id)
        assertEquals("196589221285", result.upc)
        assertEquals("329d27f2437976090d4a150cb4da4348", result.md5Image)
    }
}
