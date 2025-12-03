@file:JvmName("ImageSizesUtil")

package io.github.kingg22.deezer.client.api.objects

import io.ktor.http.URLBuilder
import io.ktor.http.Url
import kotlin.jvm.JvmName
import kotlin.jvm.JvmSynthetic

@JvmSynthetic
internal const val IMAGE_PATH_END = "-000000-80-0-0.jpg"

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
