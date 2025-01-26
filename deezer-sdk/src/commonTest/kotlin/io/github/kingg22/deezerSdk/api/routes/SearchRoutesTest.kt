package io.github.kingg22.deezerSdk.api.routes

import io.kotest.matchers.shouldBe
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.time.Duration.Companion.seconds

class SearchRoutesTest {
    @Test
    fun `Search query without params throws an exception`() {
        assertFailsWith(IllegalArgumentException::class) { SearchRoutes.buildAdvanceQuery() }.let {
            it.message shouldBe "Requires at least 1 parameter to search"
        }
    }

    @Test
    fun `Search query with params but query is blank then throws an exception`() {
        assertFailsWith(IllegalArgumentException::class) { SearchRoutes.buildAdvanceQuery(q = "") }.let {
            it.message shouldBe "Query cannot be blank"
        }
    }

    @Test
    fun `Search query with multiple params`() {
        val search = SearchRoutes.buildAdvanceQuery(artist = "aloe black", album = "i need a dollar")
        assertEquals(search, "artist:\"aloe black\" album:\"i need a dollar\"")
    }

    @Test
    fun `Search query with numbers without quotes`() {
        val search = SearchRoutes.buildAdvanceQuery(durationMin = 10.seconds)
        assertEquals(search, "dur_min:10")
    }
}
