# Module Auth Data

#### Add the file to auth/auth_data/src/main/res/values/google-services.xml fill in the values according to google-services.json
    <resources>
        <!-- oauth_client.client_id -->
        <string name="default_web_client_id">YOUR DEFAULT WEB CLIENT ID</string>
        <!-- project_info.firebase_url -->
        <string name="firebase_database_url">YOUR FIREBASE DATABASE URL</string>
        <!-- project_info.project_number-->
        <string name="gcm_defaultSenderId">YOUR GCM DEFAULT SENDER ID</string>
        <!-- api_key[0].current_key-->
        <string name="google_api_key">YOUR GOOGLE API KEY</string>
        <!-- client_info.mobilesdk_app_id -->
        <string name="google_app_id">YOUR GOOGLE APP ID</string>
        <!-- api_key[1].current_key-->
        <string name="google_crash_reporting_api_key">YOUR GOOGLE CRASH REPORTING API KEY</string>
        <!--project_info.storage_bucket-->
        <string name="google_storage_bucket">YOUR GOOGLE STORAGE BUCKET</string>
        <!--project_info.project_id-->
        <string name="project_id">YOUR PROJECT ID</string>
    </resources>

```bash
./gradlew :auth:auth_data:publishToMavenLocal
```

```gradle
repositories {
    mavenLocal()
}

dependencies {
    implementation "id.xxx.module:auth_data:$vModule"
}
```
---
