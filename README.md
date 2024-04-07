# ClientViewer
[![](https://jitpack.io/v/aivruu/clientviewer.svg)](https://jitpack.io/#aivruu/clientviewer)
![GitHub Actions Workflow Status](https://img.shields.io/github/actions/workflow/status/aivruu/clientviewer/build)
![GitHub Release](https://img.shields.io/github/v/release/aivruu/clientviewer)
![GitHub Downloads (all assets, all releases)](https://img.shields.io/github/downloads/aivruu/clientviewer/total)
![Modrinth Downloads](https://img.shields.io/modrinth/dt/clientviewer)

`clientviewer` is a Paper plugin that allows to see the information about the client of any player use on your server.

> [!NOTE]\
> As the client is who provide this information, there's the possibility that some clients can modify this
> information to deceive, like provide a non-suspicious brand-name or protocol number, The plugin can block clients with
> invalid, or specific brand-names, but yet there's the possibility that a client can bypass this.

### Documentation
- [Javadoc](https://jitpack.io/com/github/aivruu/clientviewer/latest/javadoc/)

### Features
- Check what clients types and versions use the players.
- Prevent that clients with non-provided brand names can join.
- Prevent that specified clients can join.

## Requirements
- [Paper](https://papermc.io/downloads/paper) 1.19+
- Java 17+

### Download
You can visualize the latest version on the document header, or at the GitHub releases page.
```kotlin
repositories {
  maven("https://jitpack.io/")
}

dependencies {
  compileOnly("com.github.aivruu:clientviewer:VERSION")
}
```

```xml
<repositories>
  <repository>
    <id>jitpack-repo</id>
    <url>https://jitpack.io/</url>
  </repository>
</repositories>

<dependency>
  <groupId>com.github.aivruu</groupId>
  <artifactId>clientviewer</artifactId>
  <version>VERSION</version>
</dependency>
```

### Building
This library use Gradle-Kotlin for project structuration and building.
```
git clone https://github.com/aivruu/clientviewer.git
cd clientviewer
./gradlew build
```

JDK 17 or newer is fully required.
