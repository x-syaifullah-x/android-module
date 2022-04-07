@file:JvmName("ActivityKtx")

package id.xxx.module.presentation.ktx

import android.app.Activity
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

inline fun <T : ViewBinding> Activity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
) = lazy(LazyThreadSafetyMode.NONE) {
    bindingInflater.invoke(layoutInflater)
}