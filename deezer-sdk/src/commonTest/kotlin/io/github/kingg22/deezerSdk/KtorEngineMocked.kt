package io.github.kingg22.deezerSdk

import co.touchlab.kermit.Logger
import com.goncalossilva.resources.Resource
import io.github.kingg22.deezerSdk.gw.DeezerGwClientTest.Companion.GW_TOKEN
import io.ktor.client.engine.*
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

object KtorEngineMocked {
    @OptIn(ExperimentalSerializationApi::class)
    val jsonSerializer = Json {
        explicitNulls = false
        prettyPrint = true
        encodeDefaults = true
        prettyPrintIndent = "  "
    }

    fun readResourceFile(path: String): String =
        Resource("src/commonTest/resources/io/github/kingg22/deezerSdk/$path").readText()

    fun createMockEngine(): HttpClientEngine = MockEngine.Companion {
        respond(
            content = getJsonFromPath(it.url.fullPath),
            status = HttpStatusCode.Companion.OK,
            headers = headersOf(HttpHeaders.ContentType, "application/json; charset=utf-8"),
        )
    }

    fun getJsonFromPath(path: String): String = when (path) {
        "/album/302127" -> readResourceFile("/api/responses/album/get_album.json")
        "/album/302127/fans" -> readResourceFile("/api/responses/album/get_album_fans.json")
        "/album/302127/tracks" -> readResourceFile("/api/responses/album/get_album_tracks.json")
        "/album/upc:196589221285" -> readResourceFile("/api/responses/album/get_album_upc.json")
        "/artist/27" -> readResourceFile("/api/responses/artist/get_artist.json")
        "/artist/27/fans" -> readResourceFile("/api/responses/artist/get_artist_fans.json")
        "/artist/27/top" -> readResourceFile("/api/responses/artist/get_artist_top.json")
        "/artist/27/albums" -> readResourceFile("/api/responses/artist/get_artist_albums.json")
        "/artist/27/radio" -> readResourceFile("/api/responses/artist/get_artist_radio.json")
        "/artist/27/playlists" -> readResourceFile("/api/responses/artist/get_artist_playlists.json")
        "/artist/27/related" -> readResourceFile("/api/responses/artist/get_artist_related.json")
        "/chart" -> readResourceFile("/api/responses/chart/get_chart.json")
        "/chart/2" -> readResourceFile("/api/responses/chart/get_chart_id.json")
        "/chart/0/tracks" -> readResourceFile("/api/responses/chart/get_chart_tracks.json")
        "/chart/0/albums" -> readResourceFile("/api/responses/chart/get_chart_albums.json")
        "/chart/0/artists" -> readResourceFile("/api/responses/chart/get_chart_artists.json")
        "/chart/0/playlists" -> readResourceFile("/api/responses/chart/get_chart_playlists.json")
        "/chart/0/podcasts" -> readResourceFile("/api/responses/chart/get_chart_podcasts.json")
        "/editorial" -> readResourceFile("/api/responses/editorial/get_editorial.json")
        "/editorial/3" -> readResourceFile("/api/responses/editorial/get_editorial_id.json")
        "/editorial/0/selection" -> readResourceFile("/api/responses/editorial/get_editorial_selection.json")
        "/editorial/0/charts" -> readResourceFile("/api/responses/editorial/get_editorial_charts.json")
        "/editorial/0/releases" -> readResourceFile("/api/responses/editorial/get_editorial_releases.json")
        "/episode/526673645" -> readResourceFile("/api/responses/get_episode_id.json")
        "/infos" -> readResourceFile("/api/responses/get_infos.json") // WARNING: offers can be old and user_token
        "/genre" -> readResourceFile("/api/responses/genre/get_genre.json")
        "/genre/12" -> readResourceFile("/api/responses/genre/get_genre_id.json")
        "/genre/0/artists" -> readResourceFile("/api/responses/genre/get_genre_artists.json")
        "/genre/0/podcasts" -> {
            Logger.Companion.i("Json not have data")
            // TODO: find with data
            readResourceFile("/api/responses/genre/get_genre_podcasts.json")
        }

        "/genre/0/radios" -> readResourceFile("/api/responses/genre/get_genre_radios.json")
        "/options" -> readResourceFile("/api/responses/get_options.json")
        "/playlist/908622995" -> readResourceFile("/api/responses/playlist/get_playlist.json")
        "/playlist/4341978/fans" -> readResourceFile("/api/responses/playlist/get_playlist_fans.json") // Faked
        "/playlist/908622995/tracks" -> readResourceFile("/api/responses/playlist/get_playlist_tracks.json")
        "/playlist/908622995/radio" -> {
            Logger.Companion.i("Json not have data")
            // TODO: find with data
            readResourceFile("/api/responses/playlist/get_playlist_radio.json")
        }

        "/podcast" -> readResourceFile("/api/responses/podcast/get_podcast.json") // always empty?
        "/podcast/20269" ->
            readResourceFile("/api/responses/podcast/get_podcast_id.json") // special: available = false
        "/podcast/20269/episodes" ->
            readResourceFile("/api/responses/podcast/get_podcast_episodes.json") // special: data is empty and next not
        "/podcast/20289/episodes" -> readResourceFile("/api/responses/podcast/get_podcast_episodes_2.json")
        "/radio" -> readResourceFile("/api/responses/radio/get_radio.json")
        "/radio/lists" -> readResourceFile("/api/responses/radio/get_radio_lists.json")
        "/radio/1236" -> readResourceFile("/api/responses/radio/get_radio_id.json")
        "/radio/genres" -> readResourceFile("/api/responses/radio/get_radio_genres.json")
        "/radio/top" -> readResourceFile("/api/responses/radio/get_radio_top.json")
        "/radio/31061/tracks" -> readResourceFile("/api/responses/radio/get_radio_tracks.json")
        "/search?q=eminem" -> readResourceFile("/api/responses/search/get_search.json")
        "/search?q=Not+Afraid&strict=on" -> readResourceFile("/api/responses/search/get_search_strict.json")
        "/search?q=Not+Afraid&order=TRACK_ASC" -> readResourceFile("/api/responses/search/get_search_order.json")
        "/search?q=%22Not+Afraid%22+artist%3A%22eminem%22&index=10&limit=15" ->
            readResourceFile("/api/responses/search/get_search_advanced.json")

        "/search/album?q=%22King%22+artist%3A%22eminem%22&order=RATING_DESC" ->
            readResourceFile("/api/responses/search/get_search_album.json")

        "/search/artist?q=cat&order=ARTIST_DESC" ->
            readResourceFile("/api/responses/search/get_search_artist.json")

        "/search/playlist?q=eminem&order=RANKING" ->
            readResourceFile("/api/responses/search/get_search_playlist.json")

        "/search/podcast?q=eminem&order=RATING_ASC" ->
            readResourceFile("/api/responses/search/get_search_podcast.json")

        "/search/radio?q=Electro&order=DURATION_ASC" -> readResourceFile("/api/responses/search/get_search_radio.json")
        "/search/track?q=eminem&order=DURATION_DESC" ->
            readResourceFile("/api/responses/search/get_search_track.json")

        "/search/user?q=eminem&order=RANKING" -> readResourceFile("/api/responses/search/get_search_user.json")
        "/track/3135556" -> readResourceFile("/api/responses/track/get_track.json")
        "/track/isrc:GBDUW0000061" -> readResourceFile("/api/responses/track/get_track_isrc.json")
        "/user/2616835602" -> readResourceFile("/api/responses/user/get_user_id.json")

        // GW API
        "/ajax/gw-light.php/.?method=deezer.getUserData&api_version=1.0&input=3&api_token=null" ->
            readResourceFile("/gw/responses/get_user_data.json")
        "/ajax/gw-light.php/.?method=deezer.getUserData&api_version=1.0&input=1&api_token=null" ->
            readResourceFile("/gw/responses/error.json")
        "/ajax/gw-light.php/.?api_token=$GW_TOKEN&method=song.getData&api_version=1.0&input=3" ->
            readResourceFile("/gw/responses/get_song_data.json")
        "/ajax/gw-light.php/.?api_token=$GW_TOKEN&method=song.getListByAlbum&api_version=1.0&input=3" ->
            readResourceFile("/gw/responses/get_songs_album.json")
        "/ajax/gw-light.php/.?api_token=$GW_TOKEN&method=playlist.getSongs&api_version=1.0&input=3" ->
            readResourceFile("/gw/responses/get_songs_playlist.json")
        "/ajax/gw-light.php/.?api_token=$GW_TOKEN&method=deezer.pageSearch&api_version=1.0&input=3" ->
            readResourceFile("/gw/responses/search_eminem.json")
        "/ajax/gw-light.php/.?api_token=$GW_TOKEN&method=album.getData&api_version=1.0&input=3" ->
            readResourceFile("/gw/responses/get_album_data.json")
        "/ajax/gw-light.php/.?api_token=$GW_TOKEN&method=album.getDiscography&api_version=1.0&input=3" ->
            readResourceFile("/gw/responses/get_album_discography.json")
        "/ajax/gw-light.php/.?api_token=$GW_TOKEN&method=song.getListData&api_version=1.0&input=3" ->
            readResourceFile("/gw/responses/get_songs_data.json")
        "/ajax/gw-light.php/.?api_token=$GW_TOKEN&method=deezer.pageTrack&api_version=1.0&input=3" ->
            readResourceFile("/gw/responses/get_track_page.json")
        "/ajax/gw-light.php/.?api_token=$GW_TOKEN&method=artist.getData&api_version=1.0&input=3" ->
            readResourceFile("/gw/responses/artist/get_artist_data.json")
        "/ajax/gw-light.php/.?api_token=$GW_TOKEN&method=artist.getTopTrack&api_version=1.0&input=3" ->
            readResourceFile("/gw/responses/artist/get_artist_top_tracks.json")
        "/ajax/gw-light.php/.?api_token=$GW_TOKEN&method=song.getFavoriteIds&api_version=1.0&input=3" ->
            readResourceFile("/gw/responses/tracks/get_tracks_favorite_id.json")
        "/ajax/gw-light.php/.?api_token=$GW_TOKEN&method=deezer.pageProfile&api_version=1.0&input=3" ->
            readResourceFile("/gw/responses/get_user_page.json")

        // Media API
        "/v1/get_url" -> readResourceFile("/media/responses/get_medias.json")
        else -> {
            Logger.Companion.e("Mock request not mapped $path")
            readResourceFile("/api/responses/get_error.json")
        }
    }
}
