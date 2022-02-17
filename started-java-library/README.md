# Module Started Java Library

```bash
gradlew :started-java-library:publishToMavenLocal
```

```gradle
repositories {
    mavenLocal()
}

dependencies {
    implementation "id.xxx.module:started-java-library:$vModule"
}
```
---