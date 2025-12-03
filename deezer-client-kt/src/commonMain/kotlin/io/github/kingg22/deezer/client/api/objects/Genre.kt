package io.github.kingg22.deezer.client.api.objects

import io.github.kingg22.deezer.client.api.DeezerApiClient
import io.github.kingg22.deezer.client.utils.DeezerApiPoko
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

/**
 * Represents an Genre object of [Deezer API](https://developers.deezer.com/api/).
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/genre">Deezer Genre Object</a>
 *
 * @property id The editorial's Deezer id
 * @property name The editorial's name
 * @property picture The url of the genre picture.
 * @property pictureSmall The url of the genre picture in size small
 * @property pictureMedium The url of the genre picture in size medium.
 * @property pictureBig The url of the genre picture in size big.
 * @property pictureXl The url of the genre picture in size xl.
 * @property type **unofficial** The type of object, usually the name of the class.
 * @property radios **unofficial _only on radio_** The radios of the genre
 */
@DeezerApiPoko
@Serializable
@OptIn(ExperimentalSerializationApi::class)
class Genre @JvmOverloads constructor(
    override val id: Long,
    @JsonNames("title") val name: String,
    val picture: String? = null,
    @SerialName("picture_small") val pictureSmall: String? = null,
    @SerialName("picture_medium") val pictureMedium: String? = null,
    @SerialName("picture_big") val pictureBig: String? = null,
    @SerialName("picture_xl") val pictureXl: String? = null,
    override val type: String = "genre",
    val radios: List<Radio>? = null,
) : Resource() {
    @JvmSynthetic
    override suspend fun reload(client: DeezerApiClient) = client.genres.getById(this.id)

    companion object
}
