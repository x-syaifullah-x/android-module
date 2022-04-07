package id.xxx.module.domain.model.results

import id.xxx.module.domain.model.IModel
import id.xxx.module.domain.model.Model
import org.junit.Assert
import org.junit.Test

class ResultsTest {

    private val dataSuccess = Model("Success")

    private val dataError = Throwable("Data Error")

    private val resourcesSuccess: Results<IModel<String>> = Results.Success(dataSuccess)

    private val resourcesError: Results<IModel<String>> = Results.Error(dataError)

    @Test
    fun success() {
        when (resourcesSuccess) {
            is Results.Success ->
                Assert.assertEquals(dataSuccess, resourcesSuccess.result)
            is Results.Error ->
                Assert.assertEquals(dataError, resourcesSuccess.error)
            else -> throw IllegalStateException("ERROR")
        }
    }

    @Test
    fun error() {
        when (resourcesError) {
            is Results.Success ->
                Assert.assertEquals(dataSuccess, resourcesError.result)
            is Results.Error ->
                Assert.assertEquals(dataError, resourcesError.error)
            else -> throw IllegalStateException("ERROR")
        }
    }
}