package io.github.kingg22.deezer.client.api.routes

import io.github.kingg22.deezer.client.KtorEngineMocked
import io.github.kingg22.deezer.client.KtorEngineMocked.getJsonFromPath
import io.github.kingg22.deezer.client.KtorEngineMocked.jsonSerializer
import io.github.kingg22.deezer.client.api.DeezerApiClient
import io.github.kingg22.deezer.client.api.objects.User
import io.kotest.assertions.json.shouldEqualJson
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class UserRoutesTest {
    lateinit var client: DeezerApiClient

    @BeforeTest
    fun setup() {
        client = DeezerApiClient.initialize(KtorEngineMocked.createHttpBuilderMock())
    }

    @Test
    fun fetch_user_by_id() = runTest {
        val result = client.users.getById(2616835602)
        val json = getJsonFromPath("/user/2616835602")
        assertEquals(2616835602, result.id)
        json shouldEqualJson jsonSerializer.encodeToString(result)
    }

    @Test
    fun reload_user() = runTest {
        val tested = User(2616835602, "")
        val user = tested.reload()
        val json = getJsonFromPath("/user/2616835602")
        assertNotEquals(tested, user)
        json shouldEqualJson jsonSerializer.encodeToString(user)
    }
}
