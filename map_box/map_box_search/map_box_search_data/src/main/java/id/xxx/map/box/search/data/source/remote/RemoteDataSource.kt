package id.xxx.map.box.search.data.source.remote

import id.xxx.map.box.search.data.source.remote.service.ApiService
import id.xxx.module.data.helper.DataHelper

class RemoteDataSource(private val apiService: ApiService) {

    fun getPlaces(query: String?) = DataHelper.getAsFlow(
//        blockFirst = { !query.isNullOrBlank() },
        blockFetch = {
            if (!query.isNullOrBlank()) {
                apiService.getPlaces(query)
            } else {
                null
            }
        },
    )
}