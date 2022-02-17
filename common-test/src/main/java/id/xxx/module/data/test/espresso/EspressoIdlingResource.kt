package id.xxx.module.data.test.espresso

import androidx.test.espresso.idling.CountingIdlingResource
import id.xxx.module.data.test.BuildConfig

object EspressoIdlingResource {

    private const val RESOURCE = "GLOBAL"

    val idlingResource by lazy { CountingIdlingResource(RESOURCE) }

    @JvmStatic
    fun increment() {
        if (BuildConfig.DEBUG) idlingResource.increment()
    }

    @JvmStatic
    fun decrement() {
        if (BuildConfig.DEBUG) idlingResource.decrement()
    }
}