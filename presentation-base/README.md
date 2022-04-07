# Module Presentation Base

```bash
gradlew :presentation-base:publishToMavenLocal
```

```gradle
repositories {
    mavenLocal()
}

dependencies {
    implementation "id.xxx.module:presentation-base:$vModule"
}
```

---