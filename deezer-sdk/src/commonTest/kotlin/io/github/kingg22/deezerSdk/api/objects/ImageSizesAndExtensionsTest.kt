package io.github.kingg22.deezerSdk.api.objects

import io.ktor.http.URLBuilder
import io.ktor.http.Url
import kotlin.test.Test
import kotlin.test.assertEquals

class ImageSizesAndExtensionsTest {
    companion object {
        const val CDN_IMAGES_DEEZER_TEST = "https://cdn-images.dzcdn.net/images/cover/"
        const val IMAGE_PATH_END_TEST = "-000000-80-0-0.jpg"
    }

    @Test
    fun imageSizesEnum_valuesAreCorrect() {
        assertEquals("56x56$IMAGE_PATH_END_TEST", ImageSizes.SMALL.querySize)
        assertEquals("250x250$IMAGE_PATH_END_TEST", ImageSizes.MEDIUM.querySize)
        assertEquals("500x500$IMAGE_PATH_END_TEST", ImageSizes.BIG.querySize)
        assertEquals("1000x1000$IMAGE_PATH_END_TEST", ImageSizes.XL.querySize)
    }

    @Test
    fun string_withImageSizeEnum_appendsCorrectSize() {
        val baseMd5Url = "${CDN_IMAGES_DEEZER_TEST}someMd5"
        assertEquals("$baseMd5Url/56x56$IMAGE_PATH_END_TEST", baseMd5Url.withImageSize(ImageSizes.SMALL))
        assertEquals("$baseMd5Url/250x250$IMAGE_PATH_END_TEST", baseMd5Url.withImageSize(ImageSizes.MEDIUM))
    }

    @Test
    fun url_withImageSizeEnum_appendsCorrectSize() {
        val baseMd5Url = Url("${CDN_IMAGES_DEEZER_TEST}someMd5")
        assertEquals(
            "${CDN_IMAGES_DEEZER_TEST}someMd5/56x56$IMAGE_PATH_END_TEST",
            baseMd5Url.withImageSize(ImageSizes.SMALL).toString(),
        )
        assertEquals(
            "${CDN_IMAGES_DEEZER_TEST}someMd5/250x250$IMAGE_PATH_END_TEST",
            baseMd5Url.withImageSize(ImageSizes.MEDIUM).toString(),
        )
    }

    @Test
    fun urlBuilder_withImageSizeEnum_appendsCorrectSize() {
        val builder = URLBuilder("${CDN_IMAGES_DEEZER_TEST}someMd5")
        builder.withImageSize(ImageSizes.SMALL)
        assertEquals(
            "${CDN_IMAGES_DEEZER_TEST}someMd5/56x56$IMAGE_PATH_END_TEST",
            builder.build().toString(),
        )
    }

    @Test
    fun string_withImageSizeInt_appendsCorrectSize() {
        val baseMd5Url = "${CDN_IMAGES_DEEZER_TEST}someMd5"
        assertEquals("$baseMd5Url/300x300$IMAGE_PATH_END_TEST", baseMd5Url.withImageSize(300))
    }

    @Test
    fun url_withImageSizeInt_appendsCorrectSize() {
        val baseMd5Url = Url("${CDN_IMAGES_DEEZER_TEST}someMd5")
        assertEquals(
            "${CDN_IMAGES_DEEZER_TEST}someMd5/300x300$IMAGE_PATH_END_TEST",
            baseMd5Url.withImageSize(300).toString(),
        )
    }

    @Test
    fun urlBuilder_withImageSizeInt_appendsCorrectSize() {
        val builder = URLBuilder("${CDN_IMAGES_DEEZER_TEST}someMd5")
        builder.withImageSize(300)
        assertEquals(
            "${CDN_IMAGES_DEEZER_TEST}someMd5/300x300$IMAGE_PATH_END_TEST",
            builder.build().toString(),
        )
    }
}
