package androidx.fragment.app.delegate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentViewBinding<BindingClass : ViewBinding>(
    private val bindingClass: Class<BindingClass>,
    @IdRes private val containerId: Int? = null,
    private val attachToParent: Boolean = false,
    private val blockOnDestroyed: BindingClass.() -> Unit = {},
) : ReadOnlyProperty<Fragment, BindingClass> {

    private var _bindingClass: BindingClass? = null

    private val lifecycleEventObserver = object : LifecycleEventObserver {
        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            if (event == Lifecycle.Event.ON_DESTROY) {
                blockOnDestroyed(_bindingClass ?: return)
                _bindingClass = null
            }
        }
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>) = _bindingClass ?: run {
        thisRef.viewLifecycleOwnerLiveData.observe(thisRef) { lifecycleOwner ->
            lifecycleOwner.lifecycle.addObserver(lifecycleEventObserver)
        }

        val obj =
            if (thisRef.view == null) {
                val viewGroup =
                    if (containerId == null) {
                        getFragmentContainer(thisRef)
                    } else {
                        thisRef.activity?.findViewById(containerId)
                    }
                bindingClass.getMethod(
                    "inflate",
                    LayoutInflater::class.java,
                    ViewGroup::class.java,
                    Boolean::class.java
                ).invoke(null, thisRef.layoutInflater, viewGroup, attachToParent)
            } else {
                bindingClass.getMethod(
                    "bind", View::class.java
                ).invoke(null, thisRef.view)
            }

        @Suppress("UNCHECKED_CAST")
        return@run (obj as BindingClass).also { _bindingClass = it }
    }

    private fun getFragmentContainer(fragment: Fragment): ViewGroup? {
        val mContainer = Fragment::class.java.getDeclaredField("mContainer")
        mContainer.isAccessible = true
        return mContainer.get(fragment) as? ViewGroup
    }
}