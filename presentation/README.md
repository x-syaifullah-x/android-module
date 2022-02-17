# Module Presentation

```bash
gradlew :presentation:publishToMavenLocal
```

```gradle
repositories {
    mavenLocal()
}

dependencies {
    implementation "id.xxx.module:presentation:$vModule"
}
```
---