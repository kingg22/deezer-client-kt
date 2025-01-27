package io.github.kingg22.deezerSdk.api.routes

import io.github.kingg22.deezerSdk.api.DeezerApiClientTest.Companion.client
import io.github.kingg22.deezerSdk.api.KtorEngineMocked.getJsonFromPath
import io.github.kingg22.deezerSdk.api.KtorEngineMocked.jsonSerializer
import io.kotest.assertions.json.shouldEqualJson
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class UserRoutesTest {
    @Test
    fun `Fetch User by ID`() = runTest {
        val result = client.users.getById(2616835602)
        val json = getJsonFromPath("/user/2616835602")
        assertEquals(2616835602, result.id)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }
}
