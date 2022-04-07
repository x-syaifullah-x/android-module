@file:JvmName("ViewBinding")

package androidx.fragment.app.delegate

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : ViewBinding> viewBinding(
    attachToParent: Boolean = false,
    noinline onDestroyed: T.() -> Unit = {},
) = ViewBindingDelegate(
    bindingClass = T::class.java,
    attachToParent = attachToParent,
    onDestroyed = onDestroyed,
)

class ViewBindingDelegate<BindingClass : ViewBinding>(
    private val bindingClass: Class<BindingClass>,
    private val attachToParent: Boolean = false,
    private val onDestroyed: BindingClass.() -> Unit = {},
) : ReadOnlyProperty<Fragment, BindingClass> {

    companion object {
        private const val TAG = "FragmentViewBinding"
    }

    private var _bindingClass: BindingClass? = null

    private val lifecycleEventObserver = object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == Lifecycle.Event.ON_DESTROY) {
                onDestroyed(_bindingClass ?: return)
                _bindingClass = null
            }
        }
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>) =
        _bindingClass ?: synchronized(this) {
            if (_bindingClass != null)
                return@synchronized (_bindingClass as BindingClass)

            thisRef.viewLifecycleOwnerLiveData.observe(thisRef) { lifecycleOwner ->
                lifecycleOwner.lifecycle.addObserver(lifecycleEventObserver)
            }

            val binding =
                if (thisRef.view != null) {
                    bindingClass.getMethod(
                        "bind", View::class.java
                    ).invoke(null, thisRef.view)
                } else {
                    val viewGroup = getFragmentContainer(thisRef)
                    if (viewGroup == null) {
                        Log.i(TAG, "view group is null")
                    }
                    bindingClass.getMethod(
                        "inflate",
                        LayoutInflater::class.java,
                        ViewGroup::class.java,
                        Boolean::class.java
                    ).invoke(
                        null,
                        thisRef.layoutInflater,
                        viewGroup,
                        attachToParent
                    )
                }
            @Suppress("UNCHECKED_CAST")
            (binding as BindingClass)
                .also { _bindingClass = it }
        }

    private fun getFragmentContainer(fragment: Fragment): ViewGroup? {
        val mContainer = Fragment::class.java.getDeclaredField("mContainer")
        mContainer.isAccessible = true
        return mContainer.get(fragment) as? ViewGroup
    }
}