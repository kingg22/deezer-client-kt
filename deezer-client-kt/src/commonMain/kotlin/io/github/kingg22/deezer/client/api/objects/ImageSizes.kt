package io.github.kingg22.deezer.client.api.objects

/**
 * _Unofficial_ Represents the size of the image.
 *
 * @property querySize The size of the image.
 * @author Kingg22
 */
enum class ImageSizes(val querySize: String) {
    /** Small size in 56x56 jpg format */
    SMALL("56x56$IMAGE_PATH_END"),

    /** Medium size in 250x250 jpg format */
    MEDIUM("250x250$IMAGE_PATH_END"),

    /** Big size in 500x500 jpg format */
    BIG("500x500$IMAGE_PATH_END"),

    /** Huge size in 1000x1000 jpg format */
    XL("1000x1000$IMAGE_PATH_END"),
}
