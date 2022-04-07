package id.xxx.module.domain.mediator

import id.xxx.module.data.mediator.ResourceNetworkBound
import id.xxx.module.domain.model.results.Results
import id.xxx.module.domain.model.results.Results.Companion.`when`
import id.xxx.module.domain.model.results.Results.Companion.whenWithReturn
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ResourcesNetworkBoundTest {

    @Test
    fun test() = runBlocking<Unit> {

        val dataTemp = MutableStateFlow<String?>("data_first")
        launch {
            delay(2000)
            dataTemp.emit("data_change")
        }

        ResourceNetworkBound.create<String, String>(
            loadMode = ResourceNetworkBound.LoadMode.NETWORK_FIRST,
            blockResult = {
                dataTemp.collect {

                }
                dataTemp
            },
            blockShouldFetch = {
                true
            },
            blockOnResult = {
                false
            },
            blockFetch = {
                flowOf(Results.Success("data_from_fetch"))
            },
            blockOnFetch = { r, _ ->
                val a = r.whenWithReturn(
                    blockError = {
                        ""
                    },
                    blockSuccess = {
                        ""
                    },

                )
                r.`when`(
                    onSuccess = {
                        launch { dataTemp.emit(it) }
                    }
                )
            }
        )
    }
}