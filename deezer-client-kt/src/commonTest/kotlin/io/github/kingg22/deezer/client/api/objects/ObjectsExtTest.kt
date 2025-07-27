package io.github.kingg22.deezer.client.api.objects

import io.github.kingg22.deezer.client.api.objects.ImageSizesAndExtensionsTest.Companion.CDN_IMAGES_DEEZER_TEST
import io.github.kingg22.deezer.client.api.objects.ImageSizesAndExtensionsTest.Companion.IMAGE_PATH_END_TEST
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class ObjectsExtTest {
    companion object {
        private val track = Track(
            id = 1L, title = "Test Track", md5Image = null,
            titleShort = "",
            duration = 0,
            rank = 0,
            explicitLyrics = false,
            preview = "",
            artist = Artist(
                id = 0,
                name = "",
            ),
        )
        private val playlist = Playlist(
            id = 1L,
            md5Image = null,
            title = "Playlist Test",
            public = false,
            link = "",
            creator = User(0, ""),
        )
    }

    @Test
    fun track_retrieveImageUrl_withSize_successExample() {
        val md5 = "ff6298ecd4a80777f7442493b884fd7b"
        val expectedUrl = "${CDN_IMAGES_DEEZER_TEST}$md5/250x250$IMAGE_PATH_END_TEST"
        val track = track.copy(md5Image = md5)
        assertEquals(expectedUrl, track.retrieveImageUrl().withImageSize(ImageSizes.MEDIUM))
    }

    @Test
    fun track_retrieveImageUrl_base_success() {
        val md5 = "trackMd5"
        val track = track.copy(md5Image = md5)
        assertEquals("${CDN_IMAGES_DEEZER_TEST}$md5", track.retrieveImageUrl())
    }

    @Test
    fun track_retrieveImageUrl_nullMd5_throwsException() {
        val track = track.copy(md5Image = null)
        assertFailsWith<IllegalStateException> {
            track.retrieveImageUrl()
        }
    }

    @Test
    fun album_retrieveImageUrl_withSize_success() {
        val md5 = "albumMd5Hash"
        val album = Album(id = 1L, title = "Test Album", md5Image = md5)
        val expectedUrl = "${CDN_IMAGES_DEEZER_TEST}$md5/500x500$IMAGE_PATH_END_TEST"
        assertEquals(expectedUrl, album.retrieveImageUrl().withImageSize(ImageSizes.BIG))
    }

    @Test
    fun album_retrieveImageUrl_base_success() {
        val md5 = "albumMd5"
        val album = Album(id = 1L, title = "Test Album", md5Image = md5)
        assertEquals("${CDN_IMAGES_DEEZER_TEST}$md5", album.retrieveImageUrl())
    }

    @Test
    fun album_retrieveImageUrl_nullMd5_throwsException() {
        val album = Album(id = 1L, title = "Test Album", md5Image = null)
        assertFailsWith<IllegalStateException> {
            album.retrieveImageUrl()
        }
    }

    @Test
    fun playlist_retrieveImageUrl_withSize_success() {
        val md5 = "playlistMd5Hash"
        val playlist = playlist.copy(md5Image = md5)
        val expectedUrl = "${CDN_IMAGES_DEEZER_TEST}$md5/56x56$IMAGE_PATH_END_TEST"
        assertEquals(expectedUrl, playlist.retrieveImageUrl().withImageSize(ImageSizes.SMALL))
    }

    @Test
    fun playlist_retrieveImageUrl_base_success() {
        val md5 = "playlistMd5"
        val playlist = playlist.copy(md5Image = md5)
        assertEquals("${CDN_IMAGES_DEEZER_TEST}$md5", playlist.retrieveImageUrl())
    }

    @Test
    fun playlist_retrieveImageUrl_nullMd5_throwsException() {
        val playlist = playlist.copy(md5Image = null)
        assertFailsWith<IllegalStateException> {
            playlist.retrieveImageUrl()
        }
    }

    @Test
    fun radio_retrieveImageUrl_withSize_success() {
        val md5 = "radioMd5Hash"
        val radio = Radio(id = 1L, title = "Test Radio", md5Image = md5)
        val expectedUrl = "${CDN_IMAGES_DEEZER_TEST}$md5/1000x1000$IMAGE_PATH_END_TEST"
        assertEquals(expectedUrl, radio.retrieveImageUrl().withImageSize(ImageSizes.XL))
    }

    @Test
    fun radio_retrieveImageUrl_base_success() {
        val md5 = "radioMd5"
        val radio = Radio(id = 1L, title = "Test Radio", md5Image = md5)
        assertEquals("${CDN_IMAGES_DEEZER_TEST}$md5", radio.retrieveImageUrl())
    }

    @Test
    fun radio_retrieveImageUrl_nullMd5_throwsException() {
        val radio = Radio(id = 1L, title = "Test radio", md5Image = null)
        assertFailsWith<IllegalStateException> {
            radio.retrieveImageUrl()
        }
    }
}
