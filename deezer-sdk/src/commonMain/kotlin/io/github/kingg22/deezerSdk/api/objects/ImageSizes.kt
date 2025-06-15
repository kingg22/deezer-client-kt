package io.github.kingg22.deezerSdk.api.objects

import io.ktor.http.URLBuilder
import io.ktor.http.Url

private const val IMAGE_PATH_END = "-000000-80-0-0.jpg"

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

/**
 * Retrieve the string with the size of the image.
 *
 * @param size The size of the image.
 * @return The string in format `string/'size'`
 *
 * @see ImageSizes
 * @see ImageSizes.querySize
 */
fun String.withImageSize(size: ImageSizes) = Url(this).withImageSize(size).toString()

/**
 * Retrieve the url with the size of the image.
 *
 * @param size The size of the image.
 * @return The Url with the path segment in format `string/'size'`
 *
 * @see ImageSizes
 * @see ImageSizes.querySize
 */
fun Url.withImageSize(size: ImageSizes) = URLBuilder(this).apply {
    this.withImageSize(size)
}.build()

/**
 * Concatenate the path segment of the image size.
 *
 * @param size The size of the image.
 *
 * @see ImageSizes
 * @see ImageSizes.querySize
 */
fun URLBuilder.withImageSize(size: ImageSizes) {
    pathSegments += size.querySize
}

/**
 * Retrieve the string with the size of the image.
 *
 * @param size The size of the image.
 * @return The string in format `string/'size'x'size'-000000-80-0-0.jpg`
 *
 * @see ImageSizes
 */
fun String.withImageSize(size: Int) = Url(this).withImageSize(size).toString()

/**
 * Retrieve the url with the size of the image.
 *
 * @param size The size of the image.
 * @return The Url with the path segment in format `/'size'x'size'-000000-80-0-0.jpg`
 *
 * @see ImageSizes
 */
fun Url.withImageSize(size: Int) = URLBuilder(this).apply {
    this.withImageSize(size)
}.build()

/**
 * Concatenate the path segment of the image size.
 * Format `/'size'x'size'-000000-80-0-0.jpg`
 *
 * @param size The size of the image.
 *
 * @see ImageSizes
 */
fun URLBuilder.withImageSize(size: Int) {
    pathSegments += "${size}x${size}$IMAGE_PATH_END"
}
