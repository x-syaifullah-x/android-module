# Module Presentation Base

```bash
gradlew :presentation_base:publishToMavenLocal
```

```gradle
repositories {
    mavenLocal()
}

dependencies {
    implementation "id.xxx.module:presentation_base:$vModule"
}
```
---