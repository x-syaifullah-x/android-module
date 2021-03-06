@file:JvmName("ViewBindingKtx")

package id.xxx.module.view.binding.ktx

import android.app.Activity
import android.view.LayoutInflater
import androidx.fragment.app.delegate.FragmentViewBinding
import androidx.viewbinding.ViewBinding

inline fun <T : ViewBinding> Activity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
) = lazy(LazyThreadSafetyMode.NONE) {
    bindingInflater.invoke(layoutInflater)
}

inline fun <reified T : ViewBinding> viewBinding(
    attachToParent: Boolean = false,
    noinline onDestroyed: T.() -> Unit = {},
) = FragmentViewBinding(
    bindingClass = T::class.java,
    attachToParent = attachToParent,
    onDestroyed = onDestroyed,
)