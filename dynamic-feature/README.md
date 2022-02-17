# Module Dynamic Feature

```bash
./gradlew :xxx:publishToMavenLocal
```

```gradle
repositories {
    mavenLocal()
}

dependencies {
    implementation "id.xxx.module:xxx:$vModule"
}
```
---