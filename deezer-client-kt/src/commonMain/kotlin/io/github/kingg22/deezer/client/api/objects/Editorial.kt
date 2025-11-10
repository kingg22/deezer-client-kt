package io.github.kingg22.deezer.client.api.objects

import io.github.kingg22.deezer.client.utils.DeezerApiPoko
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmSynthetic

/**
 * Represents an editorial object of [Deezer API](https://developers.deezer.com/api/).
 *
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/editorial">Deezer Editorial Object</a>
 *
 * @property id The editorial's Deezer id
 * @property name The editorial's name
 * @property picture The url of the editorial picture
 * @property pictureSmall The url of the editorial picture in size small
 * @property pictureMedium The url of the editorial picture in size medium
 * @property pictureBig The url of the editorial picture in size big
 * @property pictureXl The url of the editorial picture in size xl
 */
@DeezerApiPoko
@Serializable
class Editorial @JvmOverloads constructor(
    override val id: Long,
    val name: String,
    val picture: String? = null,
    @SerialName("picture_small") val pictureSmall: String? = null,
    @SerialName("picture_medium") val pictureMedium: String? = null,
    @SerialName("picture_big") val pictureBig: String? = null,
    @SerialName("picture_xl") val pictureXl: String? = null,
    override val type: String = "editorial",
) : Resource {
    @JvmSynthetic
    override suspend fun reload() = client().editorials.getById(this.id)

    companion object
}
