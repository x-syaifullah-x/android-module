# Prevent R8 from leaving Data object members always null
-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# if android.enableR8.fullMode=true obfuscation not working
-keepclassmembers,allowobfuscation class id.xxx.map.box.search.data.source.remote.response.** { *; }