package io.github.kingg22.deezer.client.api.objects

import io.github.kingg22.deezer.client.utils.DeezerApiPoko
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlin.jvm.JvmOverloads

/**
 * Represent the information about the [Deezer API](https://developers.deezer.com/api/) in the current country.
 *
 * @author Kingg22
 * @see <a href="https://developers.deezer.com/api/infos">Deezer Infos Object</a>
 *
 * @property countryIso The current [country ISO code](https://en.wikipedia.org/wiki/List_of_ISO_3166_country_codes)
 * @property country The current country name
 * @property isOpen Indicates if Deezer is open in the current country or not
 * @property offers An array of available offers in the current country. _Note_: Unknown content of Array
 * @property hosts **unofficial** Links of each service on Deezer. See [HostDetails]
 * @property pop **unofficial** Default language for popular content in this country
 * @property uploadToken **unofficial** Token generated to authenticate the loading of country-related data
 * @property uploadTokenLifetime **unofficial** Lifetime of the charging token, expressed in seconds
 * @property userToken **unofficial** Token specific to the user using the API. It is null if there is no authenticated user
 * @property ads **unofficial** Settings related to ads on the platform
 * @property hasPodcast **unofficial** Indicates whether the country has support for podcasts
 */
@DeezerApiPoko
@Serializable
class Infos @JvmOverloads constructor(
    @SerialName("country_iso") val countryIso: String,
    val country: String,
    @SerialName("open") val isOpen: Boolean,
    /**
     * Example: **Warning**: Can be old, not found new response with this
     * ```json
     *   "offers": [
     *     {
     *       "id": 1,
     *       "name": "Deezer Premium+",
     *       "amount": "6.99",
     *       "currency": "EUR",
     *       "displayed_amount": "6,99 €",
     *       "tc": "https://cdns-files.deezer.com/pdf/CGV-ww.pdf",
     *       "try_and_buy": "15"
     *     },
     *     {
     *       "id": 2,
     *       "name": "Deezer Premium",
     *       "amount": "3.49",
     *       "currency": "EUR",
     *       "displayed_amount": "3,49 €",
     *       "tc": "https://cdns-files.deezer.com/pdf/CGV-ww.pdf",
     *       "try_and_buy": "15"
     *     }
     *   ]
     * ```
     */
    val offers: List<JsonElement> = emptyList(),
    val pop: String? = null,
    @SerialName("upload_token") val uploadToken: String? = null,
    @SerialName("upload_token_lifetime") val uploadTokenLifetime: Int? = null,
    @SerialName("user_token") val userToken: String? = null,
    val hosts: HostDetails? = null,
    /**
     * Example:
     * ```json
     * "ads": {
     *         "audio": {
     *             "default": {
     *                 "start": 1,
     *                 "interval": 3,
     *                 "unit": "track"
     *             }
     *         },
     *         "display": {
     *             "interstitial": {
     *                 "start": 900,
     *                 "interval": 900,
     *                 "unit": "sec"
     *             }
     *         },
     *         "big_native_ads_home": {
     *             "iphone": {
     *                 "enabled": false
     *             },
     *             "ipad": {
     *                 "enabled": false
     *             },
     *             "android": {
     *                 "enabled": false
     *             },
     *             "android_tablet": {
     *                 "enabled": false
     *             }
     *         }
     *     }
     * ```
     */
    val ads: JsonObject? = null,
    @SerialName("has_podcasts") val hasPodcast: Boolean? = null,
) {
    /**
     * **unofficial** Links of each service on Deezer
     *
     * @property stream **unofficial** CDN URL used to stream content in the country.
     * (for example `"http://e-cdn-proxy-{0}.deezer.com/mobile/1/"`)
     * The {0} parameter is likely to be dynamically replaced with region or server identifiers.
     * @property images Base URL to get images from the service, such as album covers, artists.
     */
    @DeezerApiPoko
    @Serializable
    class HostDetails @JvmOverloads constructor(val stream: String? = null, val images: String? = null)
}
