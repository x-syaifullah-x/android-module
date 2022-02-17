# Module Common JVM

```bash
gradlew :common:publishToMavenLocal
```

```gradle
repositories {
    mavenLocal()
}

dependencies {
    implementation "id.xxx.module:common:$vModule"
}
```
---