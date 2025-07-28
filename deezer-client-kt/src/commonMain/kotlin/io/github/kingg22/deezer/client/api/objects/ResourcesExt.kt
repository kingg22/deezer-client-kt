@file:JvmName("ResourcesExt")
@file:JvmMultifileClass

package io.github.kingg22.deezer.client.api.objects

import kotlin.jvm.JvmMultifileClass
import kotlin.jvm.JvmName

private const val CDN_IMAGES_DEEZER = "https://cdn-images.dzcdn.net/images/cover/"
private val MD5_NOT_FOUND_MSG = { "MD5 image not found" }

/**
 * Retrieve the image url of the track.
 *
 * **Missing size** if you fetch that's it, return not found, need size.
 *
 * @throws IllegalStateException if [Track.md5Image] is null
 * @see [Track.md5Image]
 * @see [String.withImageSize]
 */
@Throws(IllegalStateException::class)
fun Track.retrieveImageUrl() = "$CDN_IMAGES_DEEZER${checkNotNull(this.md5Image, MD5_NOT_FOUND_MSG)}"

/**
 * Retrieve the image url of the album.
 *
 * **Missing size** if you fetch that's it, return not found, need size.
 *
 * @throws IllegalStateException if [Album.md5Image] is null
 * @see [Album.md5Image]
 * @see [String.withImageSize]
 */
@Throws(IllegalStateException::class)
fun Album.retrieveImageUrl() = "$CDN_IMAGES_DEEZER${checkNotNull(this.md5Image, MD5_NOT_FOUND_MSG)}"

/**
 * Retrieve the image url of the playlist.
 *
 * **Missing size** if you fetch that's it, return not found, need size.
 * @throws IllegalStateException if [Playlist.md5Image] is null
 * @see [Playlist.md5Image]
 * @see [String.withImageSize]
 */
@Throws(IllegalStateException::class)
fun Playlist.retrieveImageUrl() = "$CDN_IMAGES_DEEZER${checkNotNull(this.md5Image, MD5_NOT_FOUND_MSG)}"

/**
 * Retrieve the image url of the radio.
 *
 * **Missing size** if you fetch that's it, return not found, need size.
 * @throws IllegalStateException if [Radio.md5Image] is null
 * @see [Radio.md5Image]
 * @see [String.withImageSize]
 */
@Throws(IllegalStateException::class)
fun Radio.retrieveImageUrl() = "$CDN_IMAGES_DEEZER${checkNotNull(this.md5Image, MD5_NOT_FOUND_MSG)}"
