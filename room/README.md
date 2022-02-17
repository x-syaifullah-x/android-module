# Module Room

```bash
./gradlew :room:publishToMavenLocal
```

```gradle
repositories {
    mavenLocal()
}

dependencies {
    implementation "id.xxx.module:room:$vModule"
}
```
---