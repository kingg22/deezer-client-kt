package io.github.kingg22.deezer.client.api.objects

import dev.drewhamilton.poko.Poko
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmSynthetic

/**
 * Represents an Artist object of [Deezer API](https://developers.deezer.com/api/).
 *
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/artist">Deezer Artist Object</a>
 *
 * @property id The artist's Deezer id
 * @property name The artist's name
 * @property link The url of the artist on Deezer
 * @property share The share link of the artist on Deezer
 * @property picture The url of the artist picture
 * @property pictureSmall The url of the artist picture in size small.
 * @property pictureMedium The url of the artist picture in size medium.
 * @property pictureBig The url of the artist picture in size big.
 * @property pictureXl The url of the artist picture in size xl.
 * @property albumCount The number of artist's albums
 * @property fansCount The number of artist's fans
 * @property isRadio If the artist has a smart radio
 * @property tracklist API Link to the top of this artist
 * @property type **unofficial** The type of object, usually the name of the class.
 * @property role **unofficial _only on contributors_** The role of the artist on contributors ("Main", "Featured", ...)
 * @property position **unofficial _only on charts_** The position of the artist in the charts
 */
@Poko
@Serializable
class Artist @JvmOverloads constructor(
    override val id: Long,
    val name: String,
    val link: String? = null,
    val share: String? = null,
    val picture: String? = null,
    @SerialName("picture_small") val pictureSmall: String? = null,
    @SerialName("picture_medium") val pictureMedium: String? = null,
    @SerialName("picture_big") val pictureBig: String? = null,
    @SerialName("picture_xl") val pictureXl: String? = null,
    @SerialName("nb_album") val albumCount: Int? = null,
    @SerialName("nb_fan") val fansCount: Int? = null,
    @SerialName("radio") val isRadio: Boolean? = null,
    val tracklist: String? = null,
    override val type: String = "artist",
    val role: String? = null,
    val position: Int? = null,
) : Resource {
    @JvmSynthetic
    override suspend fun reload() = client().artists.getById(this.id)

    companion object
}
