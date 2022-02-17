# Module Common Test

```bash
gradlew :common-test:publishToMavenLocal
```

```gradle
repositories {
    mavenLocal()
}

dependencies {
    implementation "id.xxx.module:common-test:$vModule"
}
```
---