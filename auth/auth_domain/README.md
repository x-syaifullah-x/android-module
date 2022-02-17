# Module Auth Domain

```bash
./gradlew :auth::auth_domain:publishToMavenLocal
```

```gradle
repositories {
    mavenLocal()
}

dependencies {
    implementation "id.xxx.module::auth_domain:$vModule"
}
```
---
