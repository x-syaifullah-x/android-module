# Module Common Android Domain

```bash
./gradlew :domain:publishToMavenLocal
```

```gradle
repositories {
    mavenLocal()
}

dependencies {
    implementation "id.xxx.module:domain:$vModule"
}
```
---