package id.xxx.module.dynamic.feature.ktx

import android.content.Context
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest

inline fun Context.openDynamicFeature(
    dynamicModuleName: String,
    onLoaded: () -> Unit = {},
    crossinline onOpen: () -> Unit,
    crossinline onFailure: (T: Throwable) -> Unit
) {
    val splitInstallManager = SplitInstallManagerFactory.create(this)
    if (splitInstallManager.installedModules.contains(dynamicModuleName)) {
        onOpen()
    } else {
        onLoaded()
        val request = SplitInstallRequest.newBuilder()
            .addModule(dynamicModuleName)
            .build()
        splitInstallManager.startInstall(request)
            .addOnSuccessListener { onOpen() }
            .addOnFailureListener { onFailure(it) }
    }
}