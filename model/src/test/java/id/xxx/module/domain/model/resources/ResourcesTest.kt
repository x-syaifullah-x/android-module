package id.xxx.module.domain.model.resources

import id.xxx.module.domain.model.IModel
import id.xxx.module.domain.model.Model
import org.junit.Assert
import org.junit.Test

class ResourcesTest {

    private val dataSuccess = Model("Success")

    private val dataError = Throwable("Data Error")

    private val resourcesSuccess: Resources<IModel<String>> = Resources.Success(dataSuccess)

    private val resourcesLoading: Resources<IModel<String>> = Resources.Loading

    private val resourcesError: Resources<IModel<String>> = Resources.Error(dataError)

    @Test
    fun loading() {
        when (resourcesLoading) {
            is Resources.Loading ->
                Assert.assertEquals(resourcesLoading, resourcesLoading)
            is Resources.Success ->
                Assert.assertEquals(dataSuccess, resourcesLoading.result)
            is Resources.Error ->
                Assert.assertEquals(dataError, resourcesLoading.error)
            else -> throw IllegalStateException("ERROR")
        }
    }

    @Test
    fun success() {
        when (resourcesSuccess) {
            is Resources.Loading ->
                Assert.assertEquals(resourcesSuccess, resourcesSuccess)
            is Resources.Success ->
                Assert.assertEquals(dataSuccess, resourcesSuccess.result)
            is Resources.Error ->
                Assert.assertEquals(dataError, resourcesSuccess.error)
            else -> throw IllegalStateException("ERROR")
        }
    }

    @Test
    fun error() {
        when (resourcesError) {
            is Resources.Loading ->
                Assert.assertEquals(resourcesError, resourcesError)
            is Resources.Success ->
                Assert.assertEquals(dataSuccess, resourcesError.result)
            is Resources.Error ->
                Assert.assertEquals(dataError, resourcesError.error)
            else -> throw IllegalStateException("ERROR")
        }
    }
}