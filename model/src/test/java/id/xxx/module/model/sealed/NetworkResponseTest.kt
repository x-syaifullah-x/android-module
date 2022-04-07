package id.xxx.module.model.sealed

import org.junit.Assert
import org.junit.Test

class NetworkResponseTest {

    @Test
    fun network_response_success_test() {
        val result = "Success"
        val networkResourceSealed: NetworkResponse<String> =
            if (result.isNotBlank()) {
                NetworkResponse.Success(result)
            } else {
                NetworkResponse.Error(Throwable())
            }
        Assert.assertFalse(networkResourceSealed is NetworkResponse.Error)
        Assert.assertTrue(networkResourceSealed is NetworkResponse.Success)
        val data = (networkResourceSealed as NetworkResponse.Success).data
        Assert.assertEquals("ok", result, data)
    }

    @Test
    fun network_response_error_test() {
        val result = ""
        val networkResourceSealed: NetworkResponse<String> =
            if (result.isNotBlank()) {
                NetworkResponse.Success(result)
            } else {
                NetworkResponse.Error(IllegalArgumentException())
            }
        Assert.assertTrue(networkResourceSealed is NetworkResponse.Error)
        Assert.assertFalse(networkResourceSealed is NetworkResponse.Success)
        val error = (networkResourceSealed as NetworkResponse.Error).err
        Assert.assertTrue(error is IllegalArgumentException)
    }
}