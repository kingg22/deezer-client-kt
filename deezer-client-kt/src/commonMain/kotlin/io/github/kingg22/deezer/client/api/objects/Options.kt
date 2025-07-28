package io.github.kingg22.deezer.client.api.objects

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads

/**
 * Represents an User's Options of [Deezer API](https://developers.deezer.com/api/).
 *
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/options">Deezer Options</a>
 *
 * @property streaming If the user can stream on the platform
 * @property streamingDuration The streaming duration of the user
 * @property offline The user can listen to the music in offline mode
 * @property hq The HQ can be activated
 * @property adsDisplay Display ads
 * @property adsAudio Activates audio ads
 * @property tooManyDevices If the user reached the limit of linked devices
 * @property canSubscribe If the user can subscribe to the service
 * @property radioSkips The limit of radio skips. 0 = no limit
 * @property lossless Lossless is available
 * @property preview Allows displaying the preview of the tracks
 * @property radio Allows streaming the radio
 * @property type **unofficial** The type of object, usually the name of the class.
 */
@Serializable
data class Options @JvmOverloads constructor(
    val streaming: Boolean,
    @SerialName("streaming_duration") val streamingDuration: Int,
    val offline: Boolean,
    val hq: Boolean,
    @SerialName("ads_display") val adsDisplay: Boolean,
    @SerialName("ads_audio") val adsAudio: Boolean,
    @SerialName("too_many_devices") val tooManyDevices: Boolean,
    @SerialName("can_subscribe") val canSubscribe: Boolean,
    @SerialName("radio_skips") val radioSkips: Int,
    val lossless: Boolean,
    val preview: Boolean,
    val radio: Boolean,
    val type: String = "options",
)
