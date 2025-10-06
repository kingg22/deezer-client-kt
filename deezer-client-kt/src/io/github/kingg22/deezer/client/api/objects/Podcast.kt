package io.github.kingg22.deezer.client.api.objects

import dev.drewhamilton.poko.Poko
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmSynthetic

/**
 * A podcast object of [Deezer API](https://developers.deezer.com/api/).
 *
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/podcast">Deezer Podcast Object</a>
 *
 * @property id The podcast's Deezer id
 * @property title The podcast's title
 * @property description The podcast's description
 * @property isAvailable If the podcast is available or not
 * @property fans The number of podcast's fans
 * @property link The url of the podcast on Deezer
 * @property share The share link of the podcast on Deezer
 * @property picture The url of the podcast's cover
 * @property pictureSmall The url of the podcast's cover in size small
 * @property pictureMedium The url of the podcast's cover in size medium
 * @property pictureBig The url of the podcast's cover in size big
 * @property pictureXl The url of the podcast's cover in size xl
 */
@Poko
@Serializable
class Podcast @JvmOverloads constructor(
    override val id: Long,
    val title: String? = null,
    val description: String? = null,
    @SerialName("available") val isAvailable: Boolean? = null,
    val fans: Int? = null,
    val link: String? = null,
    val share: String? = null,
    val picture: String? = null,
    @SerialName("picture_small") val pictureSmall: String? = null,
    @SerialName("picture_medium") val pictureMedium: String? = null,
    @SerialName("picture_big") val pictureBig: String? = null,
    @SerialName("picture_xl") val pictureXl: String? = null,
    override val type: String = "podcast",
) : Resource {
    @JvmSynthetic
    override suspend fun reload() = client().podcasts.getById(this.id)

    companion object
}
