package io.github.kingg22.deezerSdk.api.objects

import io.github.kingg22.deezerSdk.exceptions.DeezerApiException
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlin.jvm.JvmStatic

/**
 * Represent the explicit levels of [Deezer API](https://developers.deezer.com/api/).
 *
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/album">Deezer Album explicit_content_lyrics Field</a>
 */
@Serializable(with = Explicit.ExplicitSerializer::class)
enum class Explicit(val value: Int) {
    NOT_EXPLICIT(0),
    EXPLICIT(1),
    UNKNOWN(2),
    EDITED(3),

    /**
     * Album "lyrics" only
     * @see Album
     * @see Album.explicitLyrics
     * @see Album.explicitContentLyrics
     */
    PARTIALLY_EXPLICIT(4),

    /**
     * Album "lyrics" only
     * @see Album
     * @see Album.explicitLyrics
     * @see Album.explicitContentLyrics
     */
    PARTIALLY_UNKNOWN(5),
    NO_ADVICE_AVAILABLE(6),

    /**
     * Album "lyrics" only
     * @see Album
     * @see Album.explicitLyrics
     * @see Album.explicitContentLyrics
     */
    PARTIALLY_NO_ADVICE_AVAILABLE(7),
    ;

    companion object {
        @JvmStatic
        fun fromValue(value: Int) = entries.firstOrNull { it.value == value }
    }

    internal data object ExplicitSerializer : KSerializer<Explicit> {
        override val descriptor: SerialDescriptor =
            PrimitiveSerialDescriptor("io.github.kingg22.deezerSdk.api.objects.Explicit", PrimitiveKind.INT)

        override fun serialize(encoder: Encoder, value: Explicit) {
            encoder.encodeInt(value.value)
        }

        override fun deserialize(decoder: Decoder) = fromValue(decoder.decodeInt()) ?: throw DeezerApiException(
            errorMessage = "Unexpected explicit value: '${decoder.decodeInt()}'",
        )
    }
}
