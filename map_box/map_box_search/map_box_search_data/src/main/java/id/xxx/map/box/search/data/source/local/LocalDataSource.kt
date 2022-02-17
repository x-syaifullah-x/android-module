package id.xxx.map.box.search.data.source.local

import id.xxx.map.box.search.data.source.local.dao.PlacesDao
import id.xxx.map.box.search.data.source.local.entity.PlacesEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class LocalDataSource(private val dao: PlacesDao) {

    fun getPlaces(query: String?): Flow<List<PlacesEntity>> {
        if (query.isNullOrBlank()) return flowOf(listOf())
        return dao.getPlaces(query)
    }

    suspend fun saveDataPlaces(entity: List<PlacesEntity>) = dao.insert(entity)

    fun isShouldFetch(value: String?) = !dao.isQueryNotEmpty(value)

}