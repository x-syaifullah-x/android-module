# Module Common Android

```bash
gradlew :common-android:publishToMavenLocal
```

```gradle
repositories {
    mavenLocal()
}

dependencies {
    implementation "id.xxx.module:common-android:$vModule"
}
```
---