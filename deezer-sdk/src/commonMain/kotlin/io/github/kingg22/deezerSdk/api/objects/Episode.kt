package io.github.kingg22.deezerSdk.api.objects

import io.github.kingg22.deezerSdk.utils.LocalDateTimeSerializer
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Represent an episode object of [Deezer API](https://developers.deezer.com/api/).
 *
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/episode">Deezer Episode Object</a>
 *
 * @property id The episode's Deezer id
 * @property title The episode's title
 * @property description The episode's description
 * @property available If the episode is available or not
 * @property releaseDate The episode's release date
 * @property duration The episode's duration (seconds)
 * @property link The url of the episode on Deezer
 * @property share The share link of the episode on Deezer
 * @property picture The url of the episode's cover
 * @property pictureSmall The url of the episode's cover in size small
 * @property pictureMedium The url of the episode's cover in size medium
 * @property pictureBig The url of the episode's cover in size big
 * @property pictureXl The url of the episode's cover in size xl
 * @property trackToken The track token for media service
 */
@Serializable
data class Episode(
    override val id: Long,
    val title: String,
    val description: String? = null,
    val available: Boolean? = null,
    @Serializable(LocalDateTimeSerializer::class) @SerialName("release_date") val releaseDate: LocalDateTime,
    val duration: Int,
    val link: String? = null,
    val share: String? = null,
    val picture: String? = null,
    @SerialName("picture_small") val pictureSmall: String? = null,
    @SerialName("picture_medium") val pictureMedium: String? = null,
    @SerialName("picture_big") val pictureBig: String? = null,
    @SerialName("picture_xl") val pictureXl: String? = null,
    @SerialName("track_token") val trackToken: String? = null,
    /**
     * Podcast containing:
     *
     * @property Podcast.id
     * @property Podcast.title
     * @property Podcast.link
     * @property Podcast.picture
     * @property Podcast.pictureSmall
     * @property Podcast.pictureMedium
     * @property Podcast.pictureBig
     * @property Podcast.pictureXl
     * @property Podcast.type **unofficial**
     */
    val podcast: Podcast? = null,
    override val type: String = "episode",
) : Resource() {
    override suspend fun reload() = client.episodes.getById(this.id)
}
