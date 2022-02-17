package id.xxx.auth.data.example

import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert
import org.junit.Test

class ExampleInstrumentTest {

    @Test
    fun test() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        Assert.assertNotNull(appContext)
    }
}