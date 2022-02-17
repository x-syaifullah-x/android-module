# Module Security

```bash
gradlew :security:publishToMavenLocal
```

```gradle
repositories {
    mavenLocal()
}

dependencies {
    implementation "id.xxx.module:security:$vModule"
}
```
---