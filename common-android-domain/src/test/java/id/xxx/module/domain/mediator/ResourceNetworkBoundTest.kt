package id.xxx.module.domain.mediator

import id.xxx.module.data.mediator.ResourceNetworkBound
import id.xxx.module.model.sealed.Result
import id.xxx.module.model.sealed.Result.Companion.whenNoReturn
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ResourceNetworkBoundTest {
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
                flowOf(Result.Success("data_from_fetch"))
            },
            blockOnFetch = { r, _ ->
                r.whenNoReturn(
                    blockSuccess = { dataTemp.emit(it) }
                )
            }
        )
    }
}