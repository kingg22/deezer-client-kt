package io.github.kingg22.deezer.client.api.objects

import dev.drewhamilton.poko.Poko
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmOverloads

/**
 * Represents an User's Options of [Deezer API](https://developers.deezer.com/api/).
 *
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/options">Deezer Options</a>
 *
 * @property isStreaming If the user can stream on the platform
 * @property streamingDuration The streaming duration of the user
 * @property isOffline The user can listen to the music in offline mode
 * @property isHq The HQ can be activated
 * @property isAdsDisplay Display ads
 * @property isAdsAudio Activates audio ads
 * @property isTooManyDevices If the user reached the limit of linked devices
 * @property isCanSubscribe If the user can subscribe to the service
 * @property radioSkips The limit of radio skips. 0 = no limit
 * @property isLossless Lossless is available
 * @property isPreview Allows displaying the preview of the tracks
 * @property isRadio Allows streaming the radio
 * @property type **unofficial** The type of object, usually the name of the class.
 */
@Poko
@Serializable
class Options @JvmOverloads constructor(
    @SerialName("streaming") val isStreaming: Boolean,
    @SerialName("streaming_duration") val streamingDuration: Int,
    @SerialName("offline") val isOffline: Boolean,
    @SerialName("hq") val isHq: Boolean,
    @SerialName("ads_display") val isAdsDisplay: Boolean,
    @SerialName("ads_audio") val isAdsAudio: Boolean,
    @SerialName("too_many_devices") val isTooManyDevices: Boolean,
    @SerialName("can_subscribe") val isCanSubscribe: Boolean,
    @SerialName("radio_skips") val radioSkips: Int,
    @SerialName("lossless") val isLossless: Boolean,
    @SerialName("preview") val isPreview: Boolean,
    @SerialName("radio") val isRadio: Boolean,
    val type: String = "options",
)
