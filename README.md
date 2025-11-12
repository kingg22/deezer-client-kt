# 🎧 Unofficial Deezer Client KMP

![Kotlin Multiplatform](https://img.shields.io/badge/Kotlin_Multiplatform-%237F52FF.svg?style=flat-square&logo=kotlin&logoColor=white)
![Ktor Client](https://img.shields.io/badge/Ktor_Client-D93FD1.svg?style=flat-square&logo=ktor&logoColor=white&link=https%3A%2F%2Fktor.io%2F)
![KtorGen](https://img.shields.io/badge/KtorGen-CF561B.svg?style=flat-square&logo=ktor&logoColor=black)

![Maven Central Version](https://img.shields.io/maven-central/v/io.github.kingg22/deezer-client-kt)
![Maven Central Last Update](https://img.shields.io/maven-central/last-update/io.github.kingg22/deezer-client-kt)

![GitHub License](https://img.shields.io/github/license/kingg22/deezer-client-kt)
![GitHub last commit (branch)](https://img.shields.io/github/last-commit/kingg22/deezer-client-kt/main)

A Kotlin Multiplatform client for Deezer’s official REST API.
Using [Ktor Client](https://ktor.io/).
Supports Android (min SDK 24 / JVM 8+), JVM Java, Kotlin/JVM.

## 🛠️ Instalación
- Android **minSdk Version 24**
- JVM target **Java 8+** / Kotlin JVM 1.8

![Maven Central Version](https://img.shields.io/maven-central/v/io.github.kingg22/deezer-client-kt)

**Gradle**
```kotlin
// common main
implementation("io.github.kingg22:deezer-client-kt:<latest-version>")
```
**Maven**
```xml
<dependency>
    <groupId>io.github.kingg22</groupId>
    <artifactId>deezer-client-kt-jvm</artifactId>
    <version>current-version</version>
</dependency>
```

**Install a Ktor client engine**, [see detail](https://ktor.io/docs/client-engines.html).

![Ktor Maven Central Version](https://img.shields.io/maven-central/v/io.ktor/ktor-client?label=Ktor%20client%20version)

Example with CIO (Coroutines based):

Gradle
```kotlin
implementation("io.ktor:ktor-client-cio:$ktor_version")
```
Maven
```xml
<dependency>
    <groupId>io.ktor</groupId>
    <artifactId>ktor-client-cio-jvm</artifactId>
    <version>${ktor_version}</version>
</dependency>
```

## 🧪 Usage
- ✅ Kotlin
```kotlin
val client = DeezerApiClient()
val artist: Artist = client.artists.getById(27) // suspend fun
println("Artist: ${artist.name}") // Artist: Daft Punk
```
- ⚠️ Java (Blocking)
```java
public class Test {
  static void main(String[] args) {
    final var client = new DeezerApiJavaClient();
    final Artist artist = client.artists.getById(27);
    System.out.println("Artist: " + artist.getName()); // Artist: Daft Punk
  }
}
```
- ✅ Java (CompletableFuture)

```java
import java.util.concurrent.CompletableFuture;

public class Test {
  static void main(String[] args) {
    final var client = new DeezerApiJavaClient();
    final CompletableFuture<Artist> future = client.artists.getByIdFuture(27);
    future.whenComplete((artist, err) -> {
      if (err != null) err.printStackTrace();
      else System.out.println("Artist: " + artist.getName()); // Artist: Daft Punk
    });
  }
}
```

#### Paginated Response
**Kotlin**
```kotlin
val client: DeezerApiClient // use the same client in the app
val result: PaginatedResponse<Track> = client.searches.search("eminem")
checkNotNull(result.next)
val nextPage: PaginatedResponse<Track> = checkNotNull(tested.fetchNext(expand=true))
// because the next is not null, fetchNext don't return null
// expand means the previous data (List<Track>) going to expand with the new response
// fetchNext is an extension function*
```
**Java**

```java
import java.util.concurrent.CompletableFuture;
import io.github.kingg22.deezer.client.api.objects.PaginatedResponses;

public class Test {
  static void main(String[] args) {
    final DeezerApiJavaClient client = "";// use the same client in the app
    final PaginatedResponse<Track> response = client.searches.search("eminem");
    final PaginatedResponse<Track> nextPage =
      // Import the java helper
      // Blocking
      PaginatedResponses.fetchNext(response, Track.class, /* expand */ true);

    final CompletableFuture<PaginatedResponse<Track>> nextPageFuture =
      // async
      PaginatedResponses.fetchNextFuture(response, Track.class, /* expand */ true);

    // Null type is the same, but in java isn't a mandatory check, is recommended!
  }
}
```

#### Reloading resources
**Kotlin**
```kotlin
val client: DeezerApiClient // use the same client in the app
val episode: Episode = client.episodes.getById(526673645) // suspend fun
// after do things ...
val freshEpisode: Episode = episode.reload() // suspend fun
```
**Java**

```java
import java.util.concurrent.CompletableFuture;

class Test {
  static void main(String[] args) {
    final DeezerApiJavaClient client = "";// use the same client in the app
    final Episode episode = client.episodes.getById(526673645);
    final Episode freshEpisode = Resources.reload(tested); // blocking
    final CompletableFuture<Episode> freshEpisodeFuture = Resources.reloadFuture(tested); // async
  }
}
```

#### Advanced search
**Kotlin**
```kotlin
import io.github.kingg22.deezer.client.api.objects.SearchOrder
import io.github.kingg22.deezer.client.api.routes.SearchRoutes.Companion.buildAdvancedQuery
import io.github.kingg22.deezer.client.api.routes.SearchRoutes.Companion.setStrict
import kotlin.time.Duration.Companion.minutes

val client: DeezerApiClient // use the same client in the app

val query = buildAdvancedQuery { // DSL builder
  artist = "eminem"
  durationMin = 1.minutes
}
client.searches.searchPlaylist(query, strict = setString(true))

client.searches.searchTrack(
  q = buildAdvancedQuery(q = "Not Afraid", artist = "eminem"), // function
  order = SearchOrder.RANKING,
  limit = 15,
  index = 10,
)
```
**Java**
```java
import static io.github.kingg22.deezer.client.api.routes.SearchRoutes.buildAdvancedQuery;
import static io.github.kingg22.deezer.client.api.routes.SearchRoutes.setStrict;

public class Test {
  static void main(String[] args) {
    final DeezerApiJavaClient client = "";// use the same client in the app
    client.searches.searchAlbum(
      /* q =*/ buildAdvancedQuery(/* q = */ "King") // Only access to Java builder style
        .artist("eminem")
        .build(),
      /* strict = */ setStrict(true),
      /* order = */ SearchOrder.RATING_DESC,
      /* index = */ 10,
      /* limit = */ 15
    );
  }
}
```

**_But where is the http client of ktor?_**

Because the client needs to configure custom validators, throw exceptions; etc. I create an `HttpClientBuilder`,
you can use it to configure some specs.
The default constructor of the client expects you to add an engine on your dependencies, and that's it.

**_Why not data class?_**

[Read more about this here](https://kotlinlang.org/docs/api-guidelines-backward-compatibility.html#avoid-using-data-classes-in-your-api).
I take the decision to use class with [Poko](https://github.com/drewhamilton/Poko) because immutability is guaranteed and equals are generated.
The idea of this api client is stateless, fetch what u need, and that's it.
Only `PaginatedResponse` is data class to easy duplicate and fetch data.

### 🆚 Differences between kotlin and java versions
- Kotlin can't access to the java version and viceversa
- Kotlin extensions vs. an external object with static methods (helpers / utility class)
- Java can't access some kotlin specific like DSL, reified types, suspend methods, function with kotlin duration
- Java wrappers of kotlin stuffs, so kotlin is a priority and dictates the route
- Java needs to use kotlin datetime LocalDate and LocalDateTime when get dates :/
- _Similitude_ kotlin suspend and java blocking it reads the same >_<

## 🛣 Roadmap
- [ ] Support authenticated endpoints (user edit, user playlist edit, etc.)
- [ ] OAuth flow and token refresh support

### ⛓️‍💥 Dependencies
- [ktor-client-content-negotiation-json](https://ktor.io/docs/client-serialization.html#k53369_158) with kotlinx-serialization
- [ktor-client-logging](https://ktor.io/docs/client-logging.html).
- [kotlinx-datetime](https://github.com/Kotlin/kotlinx-datetime) for java is not supported easy less,
  but can use long as seconds in some cases.
- [Poko](https://github.com/drewhamilton/Poko) and [KtorGen](https://github.com/kingg22/ktorgen) in compile time.

### 📃 References
- [Deezer Developer API](https://developers.deezer.com/api/)

##  🐞 Issue Guidelines
Please include one of the following types in your issue title or description:
- Serialization error – e.g. the JSON response missing required field, non-nullable property, etc.
- Missing field in response – field present in official API but absent in models (or extra non‑official data)
- Others

Also state if the issue refers to an official Deezer change or unofficial additional field.

### 🤝 Contributions
We welcome contributions if:
- Code is written in Kotlin
- Uses the official Deezer API (no scraping or unofficial endpoints)
- Includes at least one test with a sample JSON response

Please fork, open PRs, and ensure tests pass.

## 📄 License
Licensed under AGPL-3.0 — contributions must respect [Deezer API Terms & Conditions](https://developers.deezer.com/termsofuse), including attribution and rate‑limit policies.
