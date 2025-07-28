package io.github.kingg22.deezer.client.api.objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class ImageSizesUtilTest {
    @Test
    void string_withImageSizeEnum_appendsCorrectSize() {
        final String baseMd5Url = ImageSizesAndExtensionsTest.CDN_IMAGES_DEEZER_TEST + "someMd5";
        assertEquals(
            baseMd5Url + "/56x56" + ImageSizesAndExtensionsTest.IMAGE_PATH_END_TEST,
            ImageSizesUtil.withImageSize(baseMd5Url, ImageSizes.SMALL)
        );
        assertEquals(
            baseMd5Url + "/250x250" + ImageSizesAndExtensionsTest.IMAGE_PATH_END_TEST,
            ImageSizesUtil.withImageSize(baseMd5Url, ImageSizes.MEDIUM)
        );
    }

    @Test
    void string_withImageSizeInt_appendsCorrectSize() {
        final String baseMd5Url = ImageSizesAndExtensionsTest.CDN_IMAGES_DEEZER_TEST + "someMd5";
        assertEquals(
            baseMd5Url + "/300x300" + ImageSizesAndExtensionsTest.IMAGE_PATH_END_TEST,
            ImageSizesUtil.withImageSize(baseMd5Url, 300)
        );
    }
}
