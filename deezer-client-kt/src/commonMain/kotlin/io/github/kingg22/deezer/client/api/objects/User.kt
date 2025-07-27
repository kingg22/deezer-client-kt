package io.github.kingg22.deezer.client.api.objects

import kotlinx.datetime.LocalDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represents an User object of [Deezer API](https://developers.deezer.com/api/).
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/user">Deezer User Object</a>
 *
 * @property id The user's Deezer ID
 * @property name The user's Deezer nickname
 * @property lastname The user's last name
 * @property firstname The user's first name
 * @property email The user's email
 * @property status The user's status
 * @property birthday The user's birthday
 * @property inscriptionDate The user's inscription date
 * @property gender The user's gender: F or M
 * @property link The url of the profile for the user on Deezer
 * @property picture The url of the user's profile picture.
 * @property pictureSmall The url of the user's profile picture in size small.
 * @property pictureMedium The url of the user's profile picture in size medium.
 * @property pictureBig The url of the user's profile picture in size big.
 * @property pictureXl The url of the user's profile picture in size xl.
 * @property country The user's country
 * @property lang The user's language
 * @property isKid If the user is a kid or not
 * @property explicitContentLevel The user's explicit content level according to his country.
 * @property explicitContentLevelsAvailable The user's available explicit content levels according to his country
 * @property tracklist API Link to the flow of this user
 * @property type **unofficial** The type of object, usually the name of the class.
 */
@Serializable
data class User @JvmOverloads constructor(
    override val id: Long,
    val name: String,
    val lastname: String? = null,
    val firstname: String? = null,
    val email: String? = null,
    val status: Int? = null,
    val birthday: LocalDate? = null,
    @SerialName("inscription_date") val inscriptionDate: LocalDate? = null,
    val gender: Gender? = null,
    val link: String? = null,
    val picture: String? = null,
    @SerialName("picture_small") val pictureSmall: String? = null,
    @SerialName("picture_medium") val pictureMedium: String? = null,
    @SerialName("picture_big") val pictureBig: String? = null,
    @SerialName("picture_xl") val pictureXl: String? = null,
    val country: String? = null,
    val lang: String? = null,
    @SerialName("is_kid") val isKid: Boolean? = null,
    @SerialName("explicit_content_level") val explicitContentLevel: String? = null,
    @SerialName(
        "explicit_content_levels_available",
    ) val explicitContentLevelsAvailable: List<ExplicitContentLevels>? = null,
    val tracklist: String? = null,
    override val type: String = "user",
) : Resource() {
    @Serializable
    enum class Gender {
        @SerialName("M")
        MALE,

        @SerialName("F")
        FEMALE,
    }

    @Serializable
    enum class ExplicitContentLevels {
        @SerialName("explicit_display")
        EXPLICIT_DISPLAY,

        @SerialName("explicit_no_recommendation")
        EXPLICIT_NO_RECOMMENDATION,

        @SerialName("explicit_hide")
        EXPLICIT_HIDE,
    }

    @JvmSynthetic
    override suspend fun reload() = client.users.getById(this.id)

    // Needed to Java access to serializer of this and not abstract class
    companion object
}
