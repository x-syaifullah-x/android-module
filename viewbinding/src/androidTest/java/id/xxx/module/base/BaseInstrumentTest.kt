package id.xxx.module.base

import androidx.test.espresso.IdlingRegistry
import androidx.test.platform.app.InstrumentationRegistry
import id.xxx.module.data.test.espresso.EspressoIdlingResource
import org.junit.After
import org.junit.Before

abstract class BaseInstrumentTest {

    @Suppress("HasPlatformType")
    protected val appContext = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    open fun before() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    open fun after() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }
}