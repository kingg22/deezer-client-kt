package io.github.kingg22.deezer.client.api.objects

import dev.drewhamilton.poko.Poko
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmSynthetic

/**
 * Represent a radio object of [Deezer API](https://developers.deezer.com/api/).
 *
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/explorer?url=album/302127">Deezer Radio Object</a>
 *
 * @property id The radio deezer ID
 * @property title The radio title
 * @property description The radio title _description right?_
 * @property share The share link of the radio on Deezer
 * @property picture The url of the radio picture.
 * @property pictureSmall The url of the radio picture in size small.
 * @property pictureMedium The url of the radio picture in size medium.
 * @property pictureBig The url of the radio picture in size big.
 * @property pictureXl    The url of the radio picture in size xl.
 * @property tracklist    API Link to the track list of this radio
 * @property md5Image The MD5 hash of the album's cover image
 */
@Poko
@Serializable
class Radio @JvmOverloads constructor(
    override val id: Long,
    val title: String,
    val description: String? = null,
    val share: String? = null,
    val picture: String? = null,
    @SerialName("picture_small") val pictureSmall: String? = null,
    @SerialName("picture_medium") val pictureMedium: String? = null,
    @SerialName("picture_big") val pictureBig: String? = null,
    @SerialName("picture_xl") val pictureXl: String? = null,
    val tracklist: String? = null,
    @SerialName("md5_image") val md5Image: String? = null,
    override val type: String = "radio",
) : Resource {
    @JvmSynthetic
    override suspend fun reload() = client().radios.getById(this.id)

    companion object
}
